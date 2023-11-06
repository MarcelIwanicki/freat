package com.iwanickimarcel.freat.feature.products_search.presentation

sealed interface ProductsSearchEvent {
    data class OnQueryChange(val query: String) : ProductsSearchEvent
    data class OnItemPress(val itemName: String) : ProductsSearchEvent
    object OnSearchConfirm : ProductsSearchEvent
    object OnBackPressed : ProductsSearchEvent
}