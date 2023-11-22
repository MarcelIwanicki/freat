package com.iwanickimarcel.freat.feature.home.presentation

import com.iwanickimarcel.freat.feature.products.domain.Product

data class HomeState(
    val products: List<Product> = emptyList(),
    val isSearchBarClicked: Boolean = false
)