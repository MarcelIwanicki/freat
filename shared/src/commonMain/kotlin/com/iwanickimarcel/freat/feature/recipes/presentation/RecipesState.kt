package com.iwanickimarcel.freat.feature.recipes.presentation

import com.iwanickimarcel.freat.feature.recipes.domain.Recipe

data class RecipesState(
    val recipes: List<Recipe> = emptyList(),
    val longPressedRecipe: Recipe? = null,
    val recipeToDelete: Recipe? = null,
    val recipeToEdit: Recipe? = null,
)