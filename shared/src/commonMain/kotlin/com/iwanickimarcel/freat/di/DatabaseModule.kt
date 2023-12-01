package com.iwanickimarcel.freat.di

import com.iwanickimarcel.products.database.ProductsDatabase
import com.iwanickimarcel.products_search.database.ProductsSearchHistoryDatabase
import com.iwanickimarcel.recipes.database.RecipesDatabase
import com.iwanickimarcel.recipes_search.database.RecipesSearchHistoryDatabase

expect class DatabaseModule {
    val productsDatabase: ProductsDatabase
    val productsSearchHistoryDatabase: ProductsSearchHistoryDatabase
    val recipesDatabase: RecipesDatabase
    val recipesSearchHistoryDatabase: RecipesSearchHistoryDatabase
}