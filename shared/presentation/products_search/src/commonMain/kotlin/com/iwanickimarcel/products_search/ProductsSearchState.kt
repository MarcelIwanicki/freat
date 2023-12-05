package com.iwanickimarcel.products_search

data class ProductsSearchState(
    val query: String = "",
    val items: List<ProductsSearchHistoryItem> = emptyList(),
    val itemPressedName: String? = null,
    val isSearchConfirmed: Boolean = false,
    val isBackPressed: Boolean = false,
)