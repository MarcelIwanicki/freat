package com.iwanickimarcel.recipes_search

import app.cash.sqldelight.db.SqlDriver

expect class RecipesSearchHistoryDatabaseDriverFactory {
    fun create(): SqlDriver
}