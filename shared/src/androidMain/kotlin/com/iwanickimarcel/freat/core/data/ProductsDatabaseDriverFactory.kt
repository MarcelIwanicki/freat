package com.iwanickimarcel.freat.core.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.iwanickimarcel.freat.database.ProductsDatabase

actual class ProductsDatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            ProductsDatabase.Schema,
            context,
            "product.db"
        )
    }
}