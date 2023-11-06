package com.iwanickimarcel.freat.feature.products_search.data

import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryItem
import com.iwanickimarcel.freat.productssearchhistorydatabase.ProductsSearchHistoryEntity

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