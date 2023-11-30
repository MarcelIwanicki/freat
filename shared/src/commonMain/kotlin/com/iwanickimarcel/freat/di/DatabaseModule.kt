package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.products_search_history_database.ProductsSearchHistoryDatabase
import com.iwanickimarcel.freat.recipes_database.RecipesDatabase
import com.iwanickimarcel.freat.recipes_search_history_database.RecipesSearchHistoryDatabase
import com.iwanickimarcel.products.database.ProductsDatabase

expect class DatabaseModule {
    val productsDatabase: ProductsDatabase
    val productsSearchHistoryDatabase: ProductsSearchHistoryDatabase
    val recipesDatabase: RecipesDatabase
    val recipesSearchHistoryDatabase: RecipesSearchHistoryDatabase
}