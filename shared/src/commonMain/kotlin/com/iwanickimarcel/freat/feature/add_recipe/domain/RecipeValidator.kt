package com.iwanickimarcel.freat.feature.add_recipe.domain

import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.products.Product

typealias ValidationResult = String?

object RecipeValidator {
    fun validateName(name: String?): ValidationResult = if (name.isNullOrEmpty()) {
        "Name cannot be empty, please add recipe name"
    } else {
        null
    }

    fun validateIngredients(ingredients: List<Product>) = if (ingredients.isEmpty()) {
        "Ingredients cannot be empty, please add some ingredients"
    } else {
        null
    }

    fun validateSteps(steps: List<Recipe.Step>) = if (steps.isEmpty()) {
        "Steps cannot be empty, please add some steps"
    } else {
        null
    }

    fun validateTags(tags: List<Recipe.Tag>) = if (tags.isEmpty()) {
        "Tags cannot be empty, please add some tags"
    } else {
        null
    }
}