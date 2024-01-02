package com.iwanickimarcel.recipes_search

import androidx.compose.ui.text.input.TextFieldValue

data class RecipesSearchState(
    val query: TextFieldValue = TextFieldValue(""),
    val items: List<RecipesSearchHistoryItem> = emptyList(),
    val itemPressedQuery: String? = null,
    val isSearchConfirmed: Boolean = false,
    val isBackPressed: Boolean = false
)