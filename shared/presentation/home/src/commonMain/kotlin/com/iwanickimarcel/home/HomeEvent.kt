package com.iwanickimarcel.home

import com.iwanickimarcel.products.Product
import com.iwanickimarcel.recipes.Recipe

sealed interface HomeEvent {
    object OnSearchBarClick : HomeEvent
    object OnShowAllProductsClick : HomeEvent
    data class OnProductClick(val product: Product) : HomeEvent
    object OnShowAllRecipesClick : HomeEvent
    data class OnRecipeClick(val recipe: Recipe) : HomeEvent
    object OnScanBillClick : HomeEvent
}