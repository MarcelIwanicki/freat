package com.iwanickimarcel.freat.feature.recipes.presentation

import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class RecipesViewModel(
    private val recipeDataSource: RecipeDataSource,
    productDataSource: ProductDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(RecipesState())
    val state = combine(
        _state,
        recipeDataSource.getRecipes(),
        productDataSource.getProducts()
    ) { state, recipes, products ->

        val recipesFilteredByQuery = recipes.filter {
            it.name.lowercase().contains(state.searchQuery.lowercase())
        }

        val recipesWithOwnedProductFactor = mutableListOf<Recipe>()

        recipesFilteredByQuery.forEach { recipe ->
            var completeGramatureOwned = 0.0
            var recipeGramature = 0.0

            products.forEach { product ->
                val productFoundInRecipe = recipe.products.find { it.name == product.name }

                productFoundInRecipe?.let {
                    completeGramatureOwned += product.amount.toGrams()
                        .coerceAtMost(it.amount.toGrams())
                }
            }

            recipe.products.forEach {
                recipeGramature += it.amount.toGrams()
            }

            val ownedProductsFactor = completeGramatureOwned / recipeGramature
            val ownedProductsPercent = (ownedProductsFactor * 10.0).roundToInt()

            recipesWithOwnedProductFactor.add(
                recipe.copy(ownedProductsPercent = ownedProductsPercent)
            )
        }


        state.copy(
            recipes = recipesWithOwnedProductFactor
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecipesState())

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