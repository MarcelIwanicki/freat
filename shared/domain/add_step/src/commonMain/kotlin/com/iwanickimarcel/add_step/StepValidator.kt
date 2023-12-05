package com.iwanickimarcel.add_step

typealias ValidationResult = String?

object StepValidator {
    fun validateStep(step: String?): ValidationResult = if (step.isNullOrEmpty()) {
        "Fill step"
    } else {
        null
    }
}