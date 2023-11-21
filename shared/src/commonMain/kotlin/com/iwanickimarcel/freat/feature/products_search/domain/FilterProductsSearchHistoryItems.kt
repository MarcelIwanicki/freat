package com.iwanickimarcel.freat.feature.products_search.domain

import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.products_search.data.toProductsSearchHistoryItem

class FilterProductsSearchHistoryItems {
    operator fun invoke(
        items: List<ProductsSearchHistoryItem>,
        products: List<Product>,
        query: String
    ): List<ProductsSearchHistoryItem> = items.filter {
        it.name.lowercase().contains(query.lowercase())
    } + products.map {
        it.toProductsSearchHistoryItem()
    }.filter { historyItem ->
        historyItem.name.lowercase().contains(query.lowercase()) && !items.any {
            it.name == historyItem.name
        }
    }.take(10)
}