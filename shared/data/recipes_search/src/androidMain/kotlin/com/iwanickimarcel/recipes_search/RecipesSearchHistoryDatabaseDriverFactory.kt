package com.iwanickimarcel.recipes_search

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.iwanickimarcel.recipes_search.database.RecipesSearchHistoryDatabase

actual class RecipesSearchHistoryDatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            RecipesSearchHistoryDatabase.Schema,
            context,
            "recipes_search_history.db"
        )
    }
}