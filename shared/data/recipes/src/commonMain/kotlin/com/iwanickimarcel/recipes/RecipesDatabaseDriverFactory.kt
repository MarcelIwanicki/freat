package com.iwanickimarcel.recipes

import app.cash.sqldelight.db.SqlDriver

expect class RecipesDatabaseDriverFactory {
    fun create(): SqlDriver
}