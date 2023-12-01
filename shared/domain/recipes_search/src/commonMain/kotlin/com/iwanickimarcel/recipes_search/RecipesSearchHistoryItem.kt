package com.iwanickimarcel.recipes_search

data class RecipesSearchHistoryItem(
    val query: String,
    val type: Type,
) {
    enum class Type {
        History,
        Search
    }
}