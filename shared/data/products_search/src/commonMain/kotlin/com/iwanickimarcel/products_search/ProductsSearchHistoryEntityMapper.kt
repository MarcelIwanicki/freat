package com.iwanickimarcel.products_search

import com.iwanickimarcel.productssearch.database.ProductsSearchHistoryEntity

fun ProductsSearchHistoryEntity.toProductsSearchHistoryItem(): ProductsSearchHistoryItem {
    return ProductsSearchHistoryItem(
        name = name,
        type = ProductsSearchHistoryItem.Type.History
    )
}