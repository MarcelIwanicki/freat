package com.iwanickimarcel.add_step

import androidx.compose.ui.text.input.TextFieldValue

data class AddStepState(
    val step: TextFieldValue? = null,
    val stepError: String? = null,
    val success: Boolean = false
)