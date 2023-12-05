package com.iwanickimarcel.recipes

import com.iwanickimarcel.products.ProductDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class RecipesViewModel(
    private val recipeDataSource: RecipeDataSource,
    productDataSource: ProductDataSource,
    filterRecipesByQuery: FilterRecipesByQuery,
    getRecipesWithOwnedProductsPercent: GetRecipesWithOwnedProductsPercent
) : ViewModel() {

    companion object {
        private val STOP_TIMEOUT = 5.seconds
    }

    private val _state = MutableStateFlow(RecipesState())
    val state = combine(
        _state,
        recipeDataSource.getRecipes(),
        productDataSource.getProducts()
    ) { state, recipes, products ->

        val recipesFilteredByQuery = filterRecipesByQuery(
            recipes = recipes,
            searchQuery = state.searchQuery
        )

        state.copy(
            recipes = getRecipesWithOwnedProductsPercent(
                recipes = recipesFilteredByQuery,
                products = products
            )
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT), RecipesState())

    fun onEvent(event: RecipesEvent) {
        when (event) {
            is RecipesEvent.OnSearchQuery -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }

            is RecipesEvent.OnSearchBarClick -> {
                _state.value = _state.value.copy(
                    searchBarPressed = true
                )
            }

            is RecipesEvent.OnRecipeLongPress -> {
                _state.value = _state.value.copy(
                    longPressedRecipe = event.recipe
                )
            }

            is RecipesEvent.OnRecipeLongPressDismiss -> {
                _state.value = _state.value.copy(
                    longPressedRecipe = null
                )
            }

            is RecipesEvent.OnDeleteRecipePress -> {
                _state.value = _state.value.copy(
                    recipeToDelete = event.recipe,
                    longPressedRecipe = null
                )
            }

            is RecipesEvent.OnDeleteRecipeConfirm -> {
                val recipe = _state.value.recipeToDelete ?: return

                viewModelScope.launch {
                    recipeDataSource.deleteRecipe(recipe.id)
                }

                _state.value = _state.value.copy(
                    recipeToDelete = null
                )
            }

            is RecipesEvent.OnDeleteRecipeMenuDismiss -> {
                _state.value = _state.value.copy(
                    recipeToDelete = null
                )
            }

            is RecipesEvent.OnEditRecipePress -> {
                _state.value = _state.value.copy(
                    recipeToEdit = event.recipe,
                    longPressedRecipe = null
                )
            }
        }
    }
}