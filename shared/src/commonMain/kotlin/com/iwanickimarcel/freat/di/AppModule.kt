package com.iwanickimarcel.freat.di

import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.core.presentation.ImagePicker
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryDataSource

expect class AppModule {
    val productDataSource: ProductDataSource
    val productsSearchHistoryDataSource: ProductsSearchHistoryDataSource

    @get:Composable
    val imagePicker: ImagePicker
}