package com.iwanickimarcel.recipes

data class RecipesState(
    val recipes: List<Recipe> = emptyList(),
    val longPressedRecipe: Recipe? = null,
    val recipeToDelete: Recipe? = null,
    val recipeToEdit: Recipe? = null,
    val searchQuery: String = "",
    val searchBarPressed: Boolean = false,
)