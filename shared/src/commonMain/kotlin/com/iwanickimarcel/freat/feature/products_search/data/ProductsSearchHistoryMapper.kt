package com.iwanickimarcel.freat.feature.products_search.data

import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryItem
import com.iwanickimarcel.freat.productssearchhistorydatabase.ProductsSearchHistoryEntity
import com.iwanickimarcel.products.Product

fun ProductsSearchHistoryEntity.toProductsSearchHistoryItem(): ProductsSearchHistoryItem {
    return ProductsSearchHistoryItem(
        name = name,
        type = ProductsSearchHistoryItem.Type.History
    )
}

fun Product.toProductsSearchHistoryItem(): ProductsSearchHistoryItem {
    return ProductsSearchHistoryItem(
        name = name,
        type = ProductsSearchHistoryItem.Type.Search
    )
}