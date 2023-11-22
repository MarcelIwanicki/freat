package com.iwanickimarcel.freat.feature.recipes_search.presentation

sealed interface RecipesSearchEvent {
    data class OnQueryChange(val query: String) : RecipesSearchEvent
    data class OnItemPress(val itemQuery: String) : RecipesSearchEvent
    object OnSearchConfirm : RecipesSearchEvent
    object OnBackPressed : RecipesSearchEvent
}