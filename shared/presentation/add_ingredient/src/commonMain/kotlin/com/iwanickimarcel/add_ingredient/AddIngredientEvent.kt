package com.iwanickimarcel.add_ingredient

import androidx.compose.ui.text.input.TextFieldValue
import com.iwanickimarcel.products.Product

sealed interface AddIngredientEvent {
    data class OnEditProductProvided(val product: Product) : AddIngredientEvent
    data class OnNameChanged(val name: TextFieldValue) : AddIngredientEvent
    data class OnAmountChanged(val amount: String) : AddIngredientEvent
    data class OnAmountUnitChanged(val unit: String) : AddIngredientEvent
    object OnAmountUnitMenuStateChanged : AddIngredientEvent
    data class OnAddIngredientClick(
        val onIngredientAdded: suspend (Product) -> Unit
    ) : AddIngredientEvent
}