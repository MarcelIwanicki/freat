package com.iwanickimarcel.freat.feature.add_recipe.domain

import com.iwanickimarcel.freat.feature.recipes.domain.Recipe

class DeleteStep {
    operator fun invoke(
        steps: List<Recipe.Step>,
        stepToDelete: Recipe.Step
    ): List<Recipe.Step> {
        val stepsWithRemovedValue = steps.toMutableList().apply {
            remove(stepToDelete)
        }

        return stepsWithRemovedValue.map {
            Recipe.Step(
                step = stepsWithRemovedValue.indexOf(it) + 1,
                description = it.description
            )
        }
    }
}