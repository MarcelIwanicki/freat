package com.iwanickimarcel.freat.feature.add_product.presentation

sealed interface AddProductEvent {
    data class OnNameChanged(val value: String) : AddProductEvent
    data class OnAmountChanged(val value: String) : AddProductEvent
    object OnAddProductClick : AddProductEvent
}