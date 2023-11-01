package com.iwanickimarcel.freat.feature.products.domain

import kotlinx.coroutines.flow.Flow

interface ProductDataSource {
    fun getProducts(): Flow<List<Product>>
    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(name: String)
}