package com.iwanickimarcel.add_step

import com.iwanickimarcel.recipes.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateStep {
    suspend operator fun invoke(
        step: String?,
        stepsCount: Int,
        onStepAdded: suspend (Recipe.Step) -> Unit,
        onSuccess: () -> Unit,
        onError: (result: ValidationResult) -> Unit
    ) {
        val stepValidation = StepValidator.validateStep(step)

        if (stepValidation == null) {
            withContext(Dispatchers.IO) {
                onStepAdded(
                    Recipe.Step(
                        step = stepsCount + 1,
                        description = step ?: return@withContext
                    )
                )
                onSuccess()
            }

            return
        }

        onError(stepValidation)
    }
}