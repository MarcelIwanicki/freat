package com.iwanickimarcel.products

import com.iwanickimarcel.core.ImageStorage
import com.iwanickimarcel.products.database.ProductEntity

suspend fun ProductEntity.toProduct(imageStorage: ImageStorage): Product {
    return Product(
        name = name,
        amount = Amount(
            amount = amount,
            unit = AmountUnit.values()[amountUnitId.toInt()]
        ),
        photoBytes = imagePath?.let { imageStorage.getBytes(it) }
    )
}