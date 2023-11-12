package com.iwanickimarcel.freat.feature.add_ingredient.domain

typealias ValidationResult = String?

object StepValidator {
    fun validateStep(step: String?): ValidationResult = if (step.isNullOrEmpty()) {
        "Fill step"
    } else {
        null
    }
}