package com.iwanickimarcel.freat.feature.add_product.presentation

sealed interface AddProductEvent {
    data class OnNameChanged(val name: String) : AddProductEvent
    data class OnAmountChanged(val amount: String) : AddProductEvent
    data class OnAmountUnitChanged(val unit: String) : AddProductEvent
    object OnAmountUnitMenuStateChanged : AddProductEvent
    object OnAddProductClick : AddProductEvent
}