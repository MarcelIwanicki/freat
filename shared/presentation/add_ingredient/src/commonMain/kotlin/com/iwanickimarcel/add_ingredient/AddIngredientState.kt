package com.iwanickimarcel.add_ingredient

import androidx.compose.ui.text.input.TextFieldValue
import com.iwanickimarcel.products.AmountUnit

data class AddIngredientState(
    val name: TextFieldValue? = null,
    val nameError: String? = null,
    val amount: Double? = null,
    val amountError: String? = null,
    val amountUnit: AmountUnit = AmountUnit.MilliGram,
    val amountUnitMenuExpanded: Boolean = false,
    val success: Boolean = false
)