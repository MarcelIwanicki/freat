package com.iwanickimarcel.freat.feature.products_search.data

import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryItem
import com.iwanickimarcel.freat.productssearchhistorydatabase.ProductsSearchHistoryEntity

fun ProductsSearchHistoryEntity.toProductsSearchHistoryItem(): ProductsSearchHistoryItem {
    return ProductsSearchHistoryItem(
        name = name
    )
}