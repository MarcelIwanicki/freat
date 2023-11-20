package com.iwanickimarcel.freat.feature.recipes_search.presentation

import com.iwanickimarcel.freat.feature.recipes_search.domain.RecipesSearchHistoryItem

data class RecipesSearchState(
    val query: String = "",
    val items: List<RecipesSearchHistoryItem> = emptyList(),
    val itemPressedQuery: String? = null,
    val isSearchConfirmed: Boolean = false,
    val isBackPressed: Boolean = false
)