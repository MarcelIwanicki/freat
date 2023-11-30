package com.iwanickimarcel.freat.feature.home.presentation

import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.products.Product

sealed interface HomeEvent {
    object OnSearchBarClick : HomeEvent
    object OnShowAllProductsClick : HomeEvent
    data class OnProductClick(val product: Product) : HomeEvent
    object OnShowAllRecipesClick : HomeEvent
    data class OnRecipeClick(val recipe: Recipe) : HomeEvent
    object OnScanBillClick : HomeEvent
}