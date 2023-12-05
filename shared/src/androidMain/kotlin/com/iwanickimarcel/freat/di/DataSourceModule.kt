package com.iwanickimarcel.freat.di

import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.products.SqlDelightProductDataSource
import com.iwanickimarcel.products_search.ProductsSearchHistoryDataSource
import com.iwanickimarcel.products_search.SqlDelightProductsSearchHistoryDataSource
import com.iwanickimarcel.recipes.RecipeDataSource
import com.iwanickimarcel.recipes.SqlDelightRecipeDataSource
import com.iwanickimarcel.recipes_search.RecipesSearchHistoryDataSource
import com.iwanickimarcel.recipes_search.SqlDelightRecipesSearchHistoryDataSource
import org.koin.dsl.module

actual val dataSourceModule = module {
    single<ProductDataSource> {
        SqlDelightProductDataSource(
            database = get(),
            imageStorage = get()
        )
    }

    single<ProductsSearchHistoryDataSource> {
        SqlDelightProductsSearchHistoryDataSource(
            database = get()
        )
    }

    single<RecipeDataSource> {
        SqlDelightRecipeDataSource(
            database = get(),
            imageStorage = get()
        )
    }

    single<RecipesSearchHistoryDataSource> {
        SqlDelightRecipesSearchHistoryDataSource(
            database = get()
        )
    }
}