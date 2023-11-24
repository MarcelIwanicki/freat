package com.iwanickimarcel.freat.feature.add_ingredient.presentation

import com.iwanickimarcel.freat.feature.products.domain.Product

sealed interface AddIngredientEvent {
    data class OnEditProductProvided(val product: Product) : AddIngredientEvent
    data class OnNameChanged(val name: String) : AddIngredientEvent
    data class OnAmountChanged(val amount: String) : AddIngredientEvent
    data class OnAmountUnitChanged(val unit: String) : AddIngredientEvent
    object OnAmountUnitMenuStateChanged : AddIngredientEvent
    data class OnAddIngredientClick(val onIngredientAdded: suspend (Product) -> Unit) :
        AddIngredientEvent
}