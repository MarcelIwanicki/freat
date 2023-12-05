package com.iwanickimarcel.add_step

import com.iwanickimarcel.recipes.Recipe

sealed interface AddStepEvent {
    data class OnEditStepProvided(val step: Recipe.Step) : AddStepEvent
    data class OnStepChanged(val step: String) : AddStepEvent
    data class OnAddStepClick(
        val stepsCount: Int,
        val onStepAdded: suspend (Recipe.Step) -> Unit
    ) : AddStepEvent
}