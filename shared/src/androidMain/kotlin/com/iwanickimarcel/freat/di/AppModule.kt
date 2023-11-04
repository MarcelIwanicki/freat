package com.iwanickimarcel.freat.di

import android.content.Context
import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.core.data.ProductsDatabaseDriverFactory
import com.iwanickimarcel.freat.core.presentation.ImagePicker
import com.iwanickimarcel.freat.core.presentation.ImagePickerFactory
import com.iwanickimarcel.freat.database.ProductsDatabase
import com.iwanickimarcel.freat.feature.products.data.SqlDelightProductDataSource
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource

actual class AppModule(
    private val context: Context
) {
    actual val productDataSource: ProductDataSource by lazy {
        SqlDelightProductDataSource(
            database = ProductsDatabase(
                driver = ProductsDatabaseDriverFactory(context).create()
            )
        )
    }

    actual val imagePicker: ImagePicker
        @Composable
        get() {
            return ImagePickerFactory().createPicker()
        }
}