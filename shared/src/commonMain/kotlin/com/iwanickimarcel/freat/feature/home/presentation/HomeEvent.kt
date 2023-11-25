package com.iwanickimarcel.freat.feature.home.presentation

import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe

sealed interface HomeEvent {
    object OnSearchBarClick : HomeEvent
    object OnShowAllProductsClick : HomeEvent
    data class OnProductClick(val product: Product) : HomeEvent
    object OnShowAllRecipesClick : HomeEvent
    data class OnRecipeClick(val recipe: Recipe) : HomeEvent
    object OnScanBillClick : HomeEvent
}