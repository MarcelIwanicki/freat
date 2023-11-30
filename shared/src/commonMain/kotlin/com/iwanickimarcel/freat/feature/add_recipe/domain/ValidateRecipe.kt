package com.iwanickimarcel.freat.feature.add_recipe.domain

import com.iwanickimarcel.freat.core.extensions.generateUniqueId
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.recipes.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateRecipe {
    suspend operator fun invoke(
        editId: Long?,
        name: String?,
        ingredients: List<Product>,
        steps: List<Recipe.Step>,
        tags: List<Recipe.Tag>,
        photoBytes: ByteArray?,
        onDeleteRecipe: suspend (Long) -> Unit,
        onInsertRecipe: suspend (Recipe) -> Unit,
        onSuccess: () -> Unit,
        onError: (result: ValidationResult) -> Unit
    ) {
        val validations = listOf(
            RecipeValidator.validateName(name),
            RecipeValidator.validateIngredients(ingredients),
            RecipeValidator.validateSteps(steps),
            RecipeValidator.validateTags(tags)
        )

        if (validations.all { it == null }) {
            withContext(Dispatchers.IO) {
                val recipe = Recipe(
                    id = editId ?: generateUniqueId(),
                    name = name ?: return@withContext,
                    photoBytes = photoBytes,
                    products = ingredients,
                    tags = tags,
                    steps = steps,
                    ownedProductsPercent = 0
                )

                if (editId != null) {
                    onDeleteRecipe(editId)
                }

                onInsertRecipe(recipe)
                onSuccess()
            }

            return
        }

        validations.find { it != null }?.let {
            onError(it)
        }
    }
}