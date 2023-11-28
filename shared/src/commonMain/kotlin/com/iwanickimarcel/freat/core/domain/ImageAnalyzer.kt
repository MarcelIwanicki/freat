package com.iwanickimarcel.freat.core.domain

import androidx.compose.ui.graphics.ImageBitmap
import com.iwanickimarcel.freat.feature.products.domain.Product
import kotlinx.coroutines.flow.Flow

expect class ImageAnalyzer {
    fun getProductsFromImage(imageBitmap: ImageBitmap): Flow<List<Product>>
}