package com.iwanickimarcel.products_search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeProductsSearchHistoryDataSource : ProductsSearchHistoryDataSource {

    private val fakeProductsSearchHistoryItems = mutableListOf<ProductsSearchHistoryItem>()

    override fun getLatestProductsSearchHistoryItems(limit: Int): Flow<List<ProductsSearchHistoryItem>> {
        return flowOf(fakeProductsSearchHistoryItems)
    }

    override suspend fun insertProductsSearchHistoryItem(item: ProductsSearchHistoryItem) {
        fakeProductsSearchHistoryItems.removeIf {
            it.name == item.name
        }
        fakeProductsSearchHistoryItems.add(item)
    }
}