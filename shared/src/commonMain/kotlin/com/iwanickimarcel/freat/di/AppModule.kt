package com.iwanickimarcel.freat.di

import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.core.presentation.ImagePicker
import com.iwanickimarcel.freat.feature.products.domain.ProductDataSource

expect class AppModule {
    val productDataSource: ProductDataSource

    @get:Composable
    val imagePicker: ImagePicker
}