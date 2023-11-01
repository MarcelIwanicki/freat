package com.iwanickimarcel.freat.core.data

import app.cash.sqldelight.db.SqlDriver

expect class ProductsDatabaseDriverFactory {
    fun create(): SqlDriver
}