package com.iwanickimarcel.freat.core.data

import app.cash.sqldelight.db.SqlDriver

expect class RecipesSearchHistoryDatabaseDriverFactory {
    fun create(): SqlDriver
}