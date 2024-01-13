package com.iwanickimarcel.add_recipe

import com.iwanickimarcel.recipes.Recipe
import com.iwanickimarcel.recipes.RecipeDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRecipeDataSource : RecipeDataSource {

    private val fakeRecipes = mutableListOf<Recipe>()

    override fun getRecipes(): Flow<List<Recipe>> {
        return flowOf(fakeRecipes)
    }

    override suspend fun getRecipeById(id: Long): Recipe {
        return fakeRecipes.find {
            it.id == id
        } ?: Recipe(
            id = id,
            name = "",
            photoBytes = null,
            products = emptyList(),
            tags = emptyList(),
            steps = emptyList(),
            ownedProductsPercent = 0,
            isFavorite = false
        )
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        fakeRecipes.add(recipe)
    }

    override suspend fun deleteRecipe(id: Long) {
        fakeRecipes.remove(
            fakeRecipes.find {
                it.id == id
            }
        )
    }


}