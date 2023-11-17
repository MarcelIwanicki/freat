package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.ui.text.input.TextFieldValue
import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe

data class AddRecipeState(
    val name: String? = null,
    val nameError: String? = null,
    val photoBytes: ByteArray? = null,
    val ingredients: List<Product> = emptyList(),
    val steps: List<Recipe.Step> = emptyList(),
    val tags: List<Recipe.Tag> = emptyList(),
    var tagsTextFieldValue: TextFieldValue = TextFieldValue(""),
    val addIngredientOpen: Boolean = false,
    val addStepOpen: Boolean = false,
)