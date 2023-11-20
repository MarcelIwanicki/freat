package com.iwanickimarcel.freat.feature.recipes_search.presentation

import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import com.iwanickimarcel.freat.feature.recipes_search.data.toRecipesSearchHistoryItem
import com.iwanickimarcel.freat.feature.recipes_search.domain.RecipesSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.recipes_search.domain.RecipesSearchHistoryItem
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipesSearchViewModel(
    recipeDataSource: RecipeDataSource,
    private val recipesSearchHistoryDataSource: RecipesSearchHistoryDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(RecipesSearchState())
    val state = combine(
        _state,
        recipesSearchHistoryDataSource.getLatestRecipesSearchHistoryItems(4),
        recipeDataSource.getRecipes()
    ) { state, historyItems, recipes ->

        val items = historyItems.filter {
            it.query.lowercase().contains(state.query.lowercase())
        } + recipes.map {
            it.toRecipesSearchHistoryItem()
        }.filter { historyItem ->
            historyItem.query.lowercase().contains(state.query.lowercase()) && !historyItems.any {
                it.query == historyItem.query
            }
        }.take(10)

        state.copy(
            items = items
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecipesSearchState())

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