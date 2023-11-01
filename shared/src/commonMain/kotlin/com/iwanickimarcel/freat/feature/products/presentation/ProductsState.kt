package com.iwanickimarcel.freat.feature.products.presentation

import com.iwanickimarcel.freat.feature.products.domain.Product

data class ProductsState(
    val products: List<Product> = emptyList(),
    val longPressedProduct: Product? = null,
    val searchQuery: String = "",
    val isRemoveItemMenuOpen: Boolean = false,
    val addProductPressed: Boolean = false,
    val errorMessage: String? = null,
)