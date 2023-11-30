package com.iwanickimarcel.freat.feature.add_ingredient.presentation

import com.iwanickimarcel.products.AmountUnit

data class AddIngredientState(
    val name: String? = null,
    val nameError: String? = null,
    val amount: Double? = null,
    val amountError: String? = null,
    val amountUnit: AmountUnit = AmountUnit.MilliGram,
    val amountUnitMenuExpanded: Boolean = false,
    val success: Boolean = false
)