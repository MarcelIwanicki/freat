package com.iwanickimarcel.freat.feature.products.domain

class FilterProductsByQuery {
    operator fun invoke(
        products: List<Product>,
        query: String
    ): List<Product> = products.filter {
        it.name.lowercase().contains(query.lowercase())
    }
}