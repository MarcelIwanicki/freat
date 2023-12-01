package com.iwanickimarcel.freat.feature.recipes_search.presentation

import com.iwanickimarcel.recipes.RecipeDataSource
import com.iwanickimarcel.recipes_search.FilterRecipesSearchHistoryItems
import com.iwanickimarcel.recipes_search.RecipesSearchHistoryDataSource
import com.iwanickimarcel.recipes_search.RecipesSearchHistoryItem
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class RecipesSearchViewModel(
    recipeDataSource: RecipeDataSource,
    filterRecipesSearchHistoryItems: FilterRecipesSearchHistoryItems,
    private val recipesSearchHistoryDataSource: RecipesSearchHistoryDataSource
) : ViewModel() {

    companion object {
        private const val SEARCH_HISTORY_ITEMS_LIMIT = 4
        private val STOP_TIMEOUT = 5.seconds
    }

    private val _state = MutableStateFlow(RecipesSearchState())
    val state = combine(
        _state,
        recipesSearchHistoryDataSource.getLatestRecipesSearchHistoryItems(SEARCH_HISTORY_ITEMS_LIMIT),
        recipeDataSource.getRecipes()
    ) { state, historyItems, recipes ->

        state.copy(
            items = filterRecipesSearchHistoryItems(
                items = historyItems,
                recipes = recipes,
                query = state.query
            )
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT), RecipesSearchState())

    fun onEvent(event: RecipesSearchEvent) {
        when (event) {
            is RecipesSearchEvent.OnQueryChange -> {
                _state.value = _state.value.copy(
                    query = event.query
                )
            }

            is RecipesSearchEvent.OnItemPress -> {
                viewModelScope.launch {
                    recipesSearchHistoryDataSource.insertRecipesSearchHistoryItem(
                        item = RecipesSearchHistoryItem(
                            query = event.itemQuery,
                            type = RecipesSearchHistoryItem.Type.History
                        )
                    )
                }

                _state.value = _state.value.copy(
                    itemPressedQuery = event.itemQuery
                )
            }

            is RecipesSearchEvent.OnSearchConfirm -> {
                _state.value.query.takeIf { it.isNotEmpty() }?.let {
                    viewModelScope.launch {
                        recipesSearchHistoryDataSource.insertRecipesSearchHistoryItem(
                            item = RecipesSearchHistoryItem(
                                query = it,
                                type = RecipesSearchHistoryItem.Type.History
                            )
                        )
                    }
                }

                _state.value = _state.value.copy(
                    isSearchConfirmed = true
                )
            }

            is RecipesSearchEvent.OnBackPressed -> {
                _state.value = _state.value.copy(
                    isBackPressed = true
                )
            }
        }
    }
}