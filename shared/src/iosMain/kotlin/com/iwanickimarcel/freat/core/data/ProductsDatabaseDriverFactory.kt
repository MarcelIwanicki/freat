package com.iwanickimarcel.freat.core.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.iwanickimarcel.freat.database.ProductsDatabase

actual class ProductsDatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(ProductsDatabase.Schema, "product.db")
    }
}