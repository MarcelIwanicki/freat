package com.iwanickimarcel.products_search

import androidx.compose.ui.text.input.TextFieldValue

sealed interface ProductsSearchEvent {
    data class OnQueryChange(val query: TextFieldValue) : ProductsSearchEvent
    data class OnItemPress(val itemName: String) : ProductsSearchEvent
    object OnSearchConfirm : ProductsSearchEvent
    object OnBackPressed : ProductsSearchEvent
}