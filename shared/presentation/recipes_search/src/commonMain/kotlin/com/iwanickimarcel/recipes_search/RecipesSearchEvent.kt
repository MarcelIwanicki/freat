package com.iwanickimarcel.recipes_search

sealed interface RecipesSearchEvent {
    data class OnQueryChange(val query: String) : RecipesSearchEvent
    data class OnItemPress(val itemQuery: String) : RecipesSearchEvent
    object OnSearchConfirm : RecipesSearchEvent
    object OnBackPressed : RecipesSearchEvent
}