package com.iwanickimarcel.freat.feature.products.presentation

import com.iwanickimarcel.freat.feature.products.domain.Product

sealed interface ProductsEvent {
    object OnAddProductClick : ProductsEvent
    object OnScanTheBillClick : ProductsEvent
    object OnSearchClick : ProductsEvent
    data class OnProductLongPress(val product: Product) : ProductsEvent
    object OnRemoveProductPress : ProductsEvent
    object OnEditProductPress : ProductsEvent
    object DismissLongPressMenu : ProductsEvent
}