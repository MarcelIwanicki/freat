package com.iwanickimarcel.freat.feature.products_search.domain

import kotlinx.coroutines.flow.Flow

interface ProductsSearchHistoryDataSource {
    fun getLatestProductsSearchHistoryItems(limit: Int): Flow<List<ProductsSearchHistoryItem>>
    suspend fun insertProductsSearchHistoryItem(item: ProductsSearchHistoryItem)
}