package com.iwanickimarcel.freat.feature.recipes_search.presentation

import com.iwanickimarcel.recipes_search.RecipesSearchHistoryItem

data class RecipesSearchState(
    val query: String = "",
    val items: List<RecipesSearchHistoryItem> = emptyList(),
    val itemPressedQuery: String? = null,
    val isSearchConfirmed: Boolean = false,
    val isBackPressed: Boolean = false
)