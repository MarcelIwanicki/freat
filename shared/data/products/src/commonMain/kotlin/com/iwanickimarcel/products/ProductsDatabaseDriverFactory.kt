package com.iwanickimarcel.products

import app.cash.sqldelight.db.SqlDriver

expect class ProductsDatabaseDriverFactory {
    fun create(): SqlDriver
}