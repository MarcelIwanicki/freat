package com.iwanickimarcel.freat.feature.home.presentation

import com.iwanickimarcel.freat.feature.products.domain.Product

sealed interface HomeEvent {
    object OnSearchBarClick : HomeEvent
    object OnShowAllProductsClick : HomeEvent
    data class OnProductClick(val product: Product) : HomeEvent
}