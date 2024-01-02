package com.iwanickimarcel.recipes_search

import androidx.compose.ui.text.input.TextFieldValue

sealed interface RecipesSearchEvent {
    data class OnQueryChange(val query: TextFieldValue) : RecipesSearchEvent
    data class OnItemPress(val itemQuery: String) : RecipesSearchEvent
    object OnSearchConfirm : RecipesSearchEvent
    object OnBackPressed : RecipesSearchEvent
}