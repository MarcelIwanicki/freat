package com.iwanickimarcel.products_search

import kotlinx.coroutines.flow.Flow

interface ProductsSearchHistoryDataSource {
    fun getLatestProductsSearchHistoryItems(limit: Int): Flow<List<ProductsSearchHistoryItem>>
    suspend fun insertProductsSearchHistoryItem(item: ProductsSearchHistoryItem)
}