package com.iwanickimarcel.products

import kotlinx.coroutines.flow.Flow

interface ProductDataSource {
    fun getProducts(): Flow<List<Product>>
    suspend fun getProductByName(name: String): Product
    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(name: String)
}