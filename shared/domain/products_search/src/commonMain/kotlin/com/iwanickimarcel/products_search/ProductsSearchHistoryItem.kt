package com.iwanickimarcel.products_search

data class ProductsSearchHistoryItem(
    val name: String,
    val type: Type,
) {
    enum class Type {
        History,
        Search
    }
}