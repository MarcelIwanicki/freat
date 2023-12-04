package com.iwanickimarcel.add_product

import com.iwanickimarcel.products.AmountUnit

data class AddProductState(
    val name: String? = null,
    val nameError: String? = null,
    val amount: Double? = null,
    val amountError: String? = null,
    val amountUnit: AmountUnit = AmountUnit.MilliGram,
    val amountUnitMenuExpanded: Boolean = false,
    val photoBytes: ByteArray? = null,
    val success: Boolean = false
)