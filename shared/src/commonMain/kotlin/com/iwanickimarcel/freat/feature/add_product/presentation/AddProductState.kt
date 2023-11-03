package com.iwanickimarcel.freat.feature.add_product.presentation

data class AddProductState(
    val name: String? = null,
    val nameError: String? = null,
    val amount: Double? = null,
    val amountError: String? = null,
    val success: Boolean = false
)