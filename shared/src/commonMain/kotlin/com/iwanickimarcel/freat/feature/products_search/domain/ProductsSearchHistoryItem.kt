package com.iwanickimarcel.freat.feature.products_search.domain

data class ProductsSearchHistoryItem(
    val name: String,
    val type: Type,
) {
    enum class Type {
        History,
        Search
    }
}