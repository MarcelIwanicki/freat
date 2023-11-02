package com.iwanickimarcel.freat.feature.products.presentation

import com.iwanickimarcel.freat.feature.products.domain.Product

sealed interface ProductsEvent {
    object OnAddProductClick : ProductsEvent
    data class OnProductLongPress(val product: Product) : ProductsEvent
    object OnProductLongPressDismiss : ProductsEvent
}