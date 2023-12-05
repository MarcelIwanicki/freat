package com.iwanickimarcel.add_recipe

import androidx.compose.ui.text.input.TextFieldValue
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.recipes.Recipe

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