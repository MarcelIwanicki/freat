package com.iwanickimarcel.freat.core.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.iwanickimarcel.freat.recipes_database.RecipesDatabase

actual class RecipesDatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            RecipesDatabase.Schema,
            context,
            "recipe.db"
        )
    }
}