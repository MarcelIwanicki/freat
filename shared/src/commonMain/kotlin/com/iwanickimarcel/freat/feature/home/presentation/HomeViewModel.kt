package com.iwanickimarcel.freat.feature.home.presentation

import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.seconds

class HomeViewModel(
    productDataSource: ProductDataSource,
    recipeDataSource: RecipeDataSource
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
        }
    }
}