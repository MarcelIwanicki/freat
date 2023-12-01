package com.iwanickimarcel.freat.feature.scan_bill.domain

import androidx.compose.ui.graphics.ImageBitmap
import com.iwanickimarcel.products.Product
import kotlinx.coroutines.flow.Flow

expect class ImageAnalyzer {
    fun getProductsFromImage(imageBitmap: ImageBitmap): Flow<List<Product>>
}