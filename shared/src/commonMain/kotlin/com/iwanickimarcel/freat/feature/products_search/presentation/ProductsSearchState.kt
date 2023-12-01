package com.iwanickimarcel.freat.feature.products_search.presentation

import com.iwanickimarcel.products_search.ProductsSearchHistoryItem

data class ProductsSearchState(
    val query: String = "",
    val items: List<ProductsSearchHistoryItem> = emptyList(),
    val itemPressedName: String? = null,
    val isSearchConfirmed: Boolean = false,
    val isBackPressed: Boolean = false,
)