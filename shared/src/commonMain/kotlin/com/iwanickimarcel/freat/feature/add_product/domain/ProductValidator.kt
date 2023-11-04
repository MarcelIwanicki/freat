package com.iwanickimarcel.freat.feature.add_product.domain

typealias ValidationResult = String?

object ProductValidator {
    fun validateName(name: String?): ValidationResult = if (name.isNullOrEmpty()) {
        "Fill the name"
    } else {
        null
    }

    fun validateAmount(amount: Double?): ValidationResult = if (amount == null) {
        "Fill the amount"
    } else {
        null
    }
}