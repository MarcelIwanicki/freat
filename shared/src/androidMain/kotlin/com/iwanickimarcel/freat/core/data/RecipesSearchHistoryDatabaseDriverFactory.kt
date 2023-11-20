package com.iwanickimarcel.freat.core.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.iwanickimarcel.freat.recipes_search_history_database.RecipesSearchHistoryDatabase

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