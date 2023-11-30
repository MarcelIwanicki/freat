package com.iwanickimarcel.freat.di

import android.content.Context
import com.iwanickimarcel.freat.core.data.ProductsSearchHistoryDatabaseDriverFactory
import com.iwanickimarcel.freat.core.data.RecipesDatabaseDriverFactory
import com.iwanickimarcel.freat.core.data.RecipesSearchHistoryDatabaseDriverFactory
import com.iwanickimarcel.freat.products_search_history_database.ProductsSearchHistoryDatabase
import com.iwanickimarcel.freat.recipes_database.RecipesDatabase
import com.iwanickimarcel.freat.recipes_search_history_database.RecipesSearchHistoryDatabase
import com.iwanickimarcel.products.ProductsDatabaseDriverFactory
import com.iwanickimarcel.products.database.ProductsDatabase

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