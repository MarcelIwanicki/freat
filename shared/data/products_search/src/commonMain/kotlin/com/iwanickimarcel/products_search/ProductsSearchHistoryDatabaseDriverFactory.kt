package com.iwanickimarcel.products_search

import app.cash.sqldelight.db.SqlDriver

expect class ProductsSearchHistoryDatabaseDriverFactory {
    fun create(): SqlDriver
}