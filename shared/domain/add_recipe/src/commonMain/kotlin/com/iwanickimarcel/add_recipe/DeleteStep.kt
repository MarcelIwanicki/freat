package com.iwanickimarcel.add_recipe

import com.iwanickimarcel.recipes.Recipe

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