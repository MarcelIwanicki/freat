package com.iwanickimarcel.freat.feature.recipes.domain

import kotlinx.coroutines.flow.Flow

interface RecipeDataSource {
    fun getRecipes(): Flow<List<Recipe>>
    suspend fun getRecipeById(id: Long): Recipe
    suspend fun insertRecipe(recipe: Recipe)
    suspend fun deleteRecipe(id: Long)
}