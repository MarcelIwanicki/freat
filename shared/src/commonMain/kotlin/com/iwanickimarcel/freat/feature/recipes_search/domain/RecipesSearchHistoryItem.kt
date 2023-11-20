package com.iwanickimarcel.freat.feature.recipes_search.domain

data class RecipesSearchHistoryItem(
    val query: String,
    val type: Type,
) {
    enum class Type {
        History,
        Search
    }
}