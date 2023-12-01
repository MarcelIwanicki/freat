package com.iwanickimarcel.freat.di

import com.iwanickimarcel.core.ImageStorage
import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.products.SqlDelightProductDataSource
import com.iwanickimarcel.products.database.ProductsDatabase
import com.iwanickimarcel.products_search.ProductsSearchHistoryDataSource
import com.iwanickimarcel.products_search.SqlDelightProductsSearchHistoryDataSource
import com.iwanickimarcel.products_search.database.ProductsSearchHistoryDatabase
import com.iwanickimarcel.recipes.RecipeDataSource
import com.iwanickimarcel.recipes.SqlDelightRecipeDataSource
import com.iwanickimarcel.recipes.database.RecipesDatabase
import com.iwanickimarcel.recipes_search.RecipesSearchHistoryDataSource
import com.iwanickimarcel.recipes_search.SqlDelightRecipesSearchHistoryDataSource
import com.iwanickimarcel.recipes_search.database.RecipesSearchHistoryDatabase

actual class DataSourceModule(
    private val productsDatabase: ProductsDatabase,
    private val productsSearchHistoryDatabase: ProductsSearchHistoryDatabase,
    private val recipesDatabase: RecipesDatabase,
    private val recipesSearchHistoryDatabase: RecipesSearchHistoryDatabase,
    private val imageStorage: ImageStorage
) {

    actual val productDataSource: ProductDataSource by lazy {
        SqlDelightProductDataSource(
            database = productsDatabase,
            imageStorage = imageStorage
        )
    }

    actual val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource by lazy {
        SqlDelightProductsSearchHistoryDataSource(
            database = productsSearchHistoryDatabase
        )
    }

    actual val recipeDataSource: RecipeDataSource by lazy {
        SqlDelightRecipeDataSource(
            database = recipesDatabase,
            imageStorage = imageStorage
        )
    }

    actual val recipesSearchHistoryDataSource: RecipesSearchHistoryDataSource by lazy {
        SqlDelightRecipesSearchHistoryDataSource(
            database = recipesSearchHistoryDatabase
        )
    }
}