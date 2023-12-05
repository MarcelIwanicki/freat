package com.iwanickimarcel.add_recipe

import com.iwanickimarcel.recipes.Recipe

class EditStep {
    operator fun invoke(
        steps: List<Recipe.Step>,
        stepToEdit: Recipe.Step
    ): List<Recipe.Step> {
        val indexToEdit = steps.indexOf(
            steps.find {
                it.step == stepToEdit.step
            }
        )

        return steps.toMutableList().apply {
            removeAt(indexToEdit)
            add(
                index = indexToEdit,
                element = stepToEdit
            )
        }
    }
}