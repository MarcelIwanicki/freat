package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.ui.text.input.TextFieldValue
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.products.Product

data class AddRecipeState(
    val editId: Long? = null,
    val name: String? = null,
    val nameError: String? = null,
    val photoBytes: ByteArray? = null,
    val ingredients: List<Product> = emptyList(),
    val steps: List<Recipe.Step> = emptyList(),
    val tags: List<Recipe.Tag> = emptyList(),
    var tagsTextFieldValue: TextFieldValue = TextFieldValue(""),
    val addIngredientOpen: Boolean = false,
    val editIngredient: Product? = null,
    val editStep: Recipe.Step? = null,
    val addStepOpen: Boolean = false,
    val success: Boolean = false,
    val finalErrorMessage: String? = null,
)