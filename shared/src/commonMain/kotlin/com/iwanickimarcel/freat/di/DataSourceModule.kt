package com.iwanickimarcel.freat.di

import com.iwanickimarcel.products.ProductDataSource
import com.iwanickimarcel.products_search.ProductsSearchHistoryDataSource
import com.iwanickimarcel.recipes.RecipeDataSource
import com.iwanickimarcel.recipes_search.RecipesSearchHistoryDataSource

expect class DataSourceModule {
    val productDataSource: ProductDataSource
    val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource
    val recipeDataSource: RecipeDataSource
    val recipesSearchHistoryDataSource: RecipesSearchHistoryDataSource
}