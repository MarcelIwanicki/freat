package com.iwanickimarcel.products_search

import com.iwanickimarcel.products.Product

fun Product.toProductsSearchHistoryItem(): ProductsSearchHistoryItem {
    return ProductsSearchHistoryItem(
        name = name,
        type = ProductsSearchHistoryItem.Type.Search
    )
}