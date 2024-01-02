package com.iwanickimarcel.products_search

import androidx.compose.ui.text.input.TextFieldValue

data class ProductsSearchState(
    val query: TextFieldValue = TextFieldValue(""),
    val items: List<ProductsSearchHistoryItem> = emptyList(),
    val itemPressedName: String? = null,
    val isSearchConfirmed: Boolean = false,
    val isBackPressed: Boolean = false,
)