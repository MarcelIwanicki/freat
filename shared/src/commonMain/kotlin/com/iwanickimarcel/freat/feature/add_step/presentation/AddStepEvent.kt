package com.iwanickimarcel.freat.feature.add_step.presentation

import com.iwanickimarcel.freat.feature.recipes.domain.Recipe

sealed interface AddStepEvent {
    data class OnEditStepProvided(val step: Recipe.Step) : AddStepEvent
    data class OnStepChanged(val step: String) : AddStepEvent
    data class OnAddStepClick(
        val stepsCount: Int,
        val onStepAdded: suspend (Recipe.Step) -> Unit
    ) : AddStepEvent
}