package com.iwanickimarcel.add_step

import androidx.compose.ui.text.input.TextFieldValue
import com.iwanickimarcel.recipes.Recipe

sealed interface AddStepEvent {
    data class OnEditStepProvided(val step: Recipe.Step) : AddStepEvent
    data class OnStepChanged(val step: TextFieldValue) : AddStepEvent
    data class OnAddStepClick(
        val stepsCount: Int,
        val onStepAdded: suspend (Recipe.Step) -> Unit
    ) : AddStepEvent
}