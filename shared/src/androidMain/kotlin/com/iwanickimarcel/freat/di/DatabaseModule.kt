package com.iwanickimarcel.freat.di

import com.iwanickimarcel.products.ProductsDatabaseDriverFactory
import com.iwanickimarcel.products.database.ProductsDatabase
import com.iwanickimarcel.products_search.ProductsSearchHistoryDatabaseDriverFactory
import com.iwanickimarcel.products_search.database.ProductsSearchHistoryDatabase
import com.iwanickimarcel.recipes.RecipesDatabaseDriverFactory
import com.iwanickimarcel.recipes.database.RecipesDatabase
import com.iwanickimarcel.recipes_search.RecipesSearchHistoryDatabaseDriverFactory
import com.iwanickimarcel.recipes_search.database.RecipesSearchHistoryDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val databaseModule = module {
    single {
        ProductsDatabase(
            driver = ProductsDatabaseDriverFactory(androidContext()).create()
        )
    }

    single {
        ProductsSearchHistoryDatabase(
            driver = ProductsSearchHistoryDatabaseDriverFactory(androidContext()).create()
        )
    }

    single {
        RecipesDatabase(
            driver = RecipesDatabaseDriverFactory(androidContext()).create()
        )
    }

    single {
        RecipesSearchHistoryDatabase(
            driver = RecipesSearchHistoryDatabaseDriverFactory(androidContext()).create()
        )
    }
}