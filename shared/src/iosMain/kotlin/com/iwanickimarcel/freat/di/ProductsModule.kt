package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.core.data.ProductsDatabaseDriverFactory
import com.iwanickimarcel.freat.database.ProductsDatabase
import com.iwanickimarcel.freat.feature.products.data.SqlDelightProductDataSource
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource

actual class ProductsModule {
    actual val productDataSource: ProductDataSource by lazy {
        SqlDelightProductDataSource(
            database = ProductsDatabase(
                driver = ProductsDatabaseDriverFactory().create()
            )
        )
    }
}