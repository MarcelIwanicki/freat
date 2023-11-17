package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.products_database.ProductsDatabase
import com.iwanickimarcel.freat.products_search_history_database.ProductsSearchHistoryDatabase
import com.iwanickimarcel.freat.recipes_database.RecipesDatabase

expect class DatabaseModule {
    val productsDatabase: ProductsDatabase
    val productsSearchHistoryDatabase: ProductsSearchHistoryDatabase
    val recipesDatabase: RecipesDatabase
}