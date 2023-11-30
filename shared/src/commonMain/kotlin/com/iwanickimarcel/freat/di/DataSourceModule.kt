package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.recipes.domain.RecipeDataSource
import com.iwanickimarcel.freat.feature.recipes_search.domain.RecipesSearchHistoryDataSource
import com.iwanickimarcel.products.ProductDataSource

expect class DataSourceModule {
    val productDataSource: ProductDataSource
    val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource
    val recipeDataSource: RecipeDataSource
    val recipesSearchHistoryDataSource: RecipesSearchHistoryDataSource
}