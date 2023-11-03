package com.iwanickimarcel.freat.feature.add_product.domain

typealias ValidationResult = String?

object ProductValidator {
    fun validateName(name: String?): ValidationResult = if (name.isNullOrEmpty()) {
        "Name cannot be empty"
    } else {
        null
    }

    fun validateAmount(amount: Double?): ValidationResult = if (amount == null) {
        "Amount cannot be empty"
    } else {
        null
    }
}