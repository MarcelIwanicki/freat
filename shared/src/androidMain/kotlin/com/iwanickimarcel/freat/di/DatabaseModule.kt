package com.iwanickimarcel.freat.di

import android.content.Context
import com.iwanickimarcel.products.ProductsDatabaseDriverFactory
import com.iwanickimarcel.products.database.ProductsDatabase
import com.iwanickimarcel.products_search.ProductsSearchHistoryDatabaseDriverFactory
import com.iwanickimarcel.products_search.database.ProductsSearchHistoryDatabase
import com.iwanickimarcel.recipes.RecipesDatabaseDriverFactory
import com.iwanickimarcel.recipes.database.RecipesDatabase
import com.iwanickimarcel.recipes_search.RecipesSearchHistoryDatabaseDriverFactory
import com.iwanickimarcel.recipes_search.database.RecipesSearchHistoryDatabase

actual class DatabaseModule(
    private val context: Context
) {
    actual val productsDatabase: ProductsDatabase by lazy {
        ProductsDatabase(
            driver = ProductsDatabaseDriverFactory(context).create()
        )
    }

    actual val productsSearchHistoryDatabase: ProductsSearchHistoryDatabase by lazy {
        ProductsSearchHistoryDatabase(
            driver = ProductsSearchHistoryDatabaseDriverFactory(context).create()
        )
    }

    actual val recipesDatabase: RecipesDatabase by lazy {
        RecipesDatabase(
            driver = RecipesDatabaseDriverFactory(context).create()
        )
    }

    actual val recipesSearchHistoryDatabase: RecipesSearchHistoryDatabase by lazy {
        RecipesSearchHistoryDatabase(
            driver = RecipesSearchHistoryDatabaseDriverFactory(context).create()
        )
    }
}