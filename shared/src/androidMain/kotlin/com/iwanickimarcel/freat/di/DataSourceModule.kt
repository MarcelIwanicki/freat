package com.iwanickimarcel.freat.di

import com.iwanickimarcel.core.ImageStorage
import com.iwanickimarcel.freat.feature.products_search.data.SqlDelightProductsSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.recipes_search.data.SqlDelightRecipesSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.recipes_search.domain.RecipesSearchHistoryDataSource
import com.iwanickimarcel.freat.products_search_history_database.ProductsSearchHistoryDatabase
import com.iwanickimarcel.freat.recipes_search_history_database.RecipesSearchHistoryDatabase
import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.products.SqlDelightProductDataSource
import com.iwanickimarcel.products.database.ProductsDatabase
import com.iwanickimarcel.recipes.RecipeDataSource
import com.iwanickimarcel.recipes.SqlDelightRecipeDataSource
import com.iwanickimarcel.recipes.database.RecipesDatabase

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