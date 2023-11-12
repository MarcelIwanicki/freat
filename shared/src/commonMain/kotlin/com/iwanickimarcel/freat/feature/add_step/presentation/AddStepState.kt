package com.iwanickimarcel.freat.feature.add_step.presentation

data class AddStepState(
    val step: String? = null,
    val stepError: String? = null,
    val success: Boolean = false
)