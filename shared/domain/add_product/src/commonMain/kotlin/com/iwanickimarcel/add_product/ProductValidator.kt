package com.iwanickimarcel.add_product

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