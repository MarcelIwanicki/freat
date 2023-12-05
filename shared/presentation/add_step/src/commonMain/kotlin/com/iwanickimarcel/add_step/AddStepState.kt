package com.iwanickimarcel.add_step

data class AddStepState(
    val step: String? = null,
    val stepError: String? = null,
    val success: Boolean = false
)