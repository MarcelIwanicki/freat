package com.iwanickimarcel.freat.feature.add_recipe.presentation

import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe

data class AddRecipeState(
    val name: String? = null,
    val nameError: String? = null,
    val photoBytes: ByteArray? = null,
    val ingredients: List<Product> = emptyList(),
    val steps: List<Recipe.Step> = emptyList(),
    val tags: List<Recipe.Tag> = emptyList(),
    val addIngredientOpen: Boolean = false,
)