package com.iwanickimarcel.freat.core.data

import app.cash.sqldelight.db.SqlDriver

expect class RecipesDatabaseDriverFactory {
    fun create(): SqlDriver
}