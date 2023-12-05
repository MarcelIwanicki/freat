package com.iwanickimarcel.recipes_search

data class RecipesSearchState(
    val query: String = "",
    val items: List<RecipesSearchHistoryItem> = emptyList(),
    val itemPressedQuery: String? = null,
    val isSearchConfirmed: Boolean = false,
    val isBackPressed: Boolean = false
)