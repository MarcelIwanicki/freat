package com.iwanickimarcel.home

import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.recipes.RecipeDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class HomeViewModel(
    productDataSource: ProductDataSource,
    private val recipeDataSource: RecipeDataSource
) : ViewModel() {

    companion object {
        private val STOP_TIMEOUT = 5.seconds
    }

    private val _state = MutableStateFlow(HomeState())
    val state = combine(
        _state,
        productDataSource.getProducts(),
        recipeDataSource.getRecipes(),
    ) { state, products, recipes ->
        state.copy(
            loading = false,
            products = products,
            recipes = recipes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT), HomeState())

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnSearchBarClick -> {
                _state.value = _state.value.copy(
                    isSearchBarClicked = true
                )
            }

            is HomeEvent.OnShowAllProductsClick -> {
                _state.value = _state.value.copy(
                    isShowAllProductsClicked = true
                )
            }

            is HomeEvent.OnProductClick -> {
                _state.value = _state.value.copy(
                    clickedProduct = event.product
                )
            }

            is HomeEvent.OnShowAllRecipesClick -> {
                _state.value = _state.value.copy(
                    isShowAllRecipesClicked = true
                )
            }

            is HomeEvent.OnRecipeClick -> {
                _state.value = _state.value.copy(
                    clickedRecipe = event.recipe
                )
            }

            is HomeEvent.OnScanBillClick -> {
                _state.value = _state.value.copy(
                    isScanBillClicked = true
                )
            }

            is HomeEvent.OnFavoriteClick -> {
                viewModelScope.launch {
                    val favoriteRecipe = event.recipe.copy(
                        isFavorite = !event.recipe.isFavorite
                    )
                    recipeDataSource.insertRecipe(favoriteRecipe)
                }
            }
        }
    }
}