package com.iwanickimarcel.freat.di

import android.content.Context
import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.core.data.ImageStorage
import com.iwanickimarcel.freat.core.data.ProductsDatabaseDriverFactory
import com.iwanickimarcel.freat.core.data.ProductsSearchHistoryDatabaseDriverFactory
import com.iwanickimarcel.freat.core.presentation.ImagePicker
import com.iwanickimarcel.freat.core.presentation.ImagePickerFactory
import com.iwanickimarcel.freat.feature.products.data.SqlDelightProductDataSource
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import com.iwanickimarcel.freat.feature.products_search.data.SqlDelightProductsSearchHistoryDataSource
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryDataSource
import com.iwanickimarcel.freat.products_database.ProductsDatabase
import com.iwanickimarcel.freat.products_search_history_database.ProductsSearchHistoryDatabase

actual class AppModule(
    private val context: Context
) {
    actual val productDataSource: ProductDataSource by lazy {
        SqlDelightProductDataSource(
            database = ProductsDatabase(
                driver = ProductsDatabaseDriverFactory(context).create()
            ),
            imageStorage = ImageStorage(context)
        )
    }

    actual val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource by lazy {
        SqlDelightProductsSearchHistoryDataSource(
            database = ProductsSearchHistoryDatabase(
                driver = ProductsSearchHistoryDatabaseDriverFactory(context).create()
            )
        )
    }

    actual val imagePicker: ImagePicker
        @Composable
        get() {
            return ImagePickerFactory().createPicker()
        }
}