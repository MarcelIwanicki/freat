package com.iwanickimarcel.products_search

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.iwanickimarcel.products_search.database.ProductsSearchHistoryDatabase

actual class ProductsSearchHistoryDatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            ProductsSearchHistoryDatabase.Schema,
            context,
            "products_search_history.db"
        )
    }
}