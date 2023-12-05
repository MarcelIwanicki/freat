package com.iwanickimarcel.products_search

sealed interface ProductsSearchEvent {
    data class OnQueryChange(val query: String) : ProductsSearchEvent
    data class OnItemPress(val itemName: String) : ProductsSearchEvent
    object OnSearchConfirm : ProductsSearchEvent
    object OnBackPressed : ProductsSearchEvent
}