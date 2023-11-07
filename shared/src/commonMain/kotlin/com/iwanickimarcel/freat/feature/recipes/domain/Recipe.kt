package com.iwanickimarcel.freat.feature.recipes.domain

import com.iwanickimarcel.freat.feature.products.domain.Product

data class Recipe(
    val id: Long,
    val name: String,
    val photoBytes: ByteArray?,
    val products: List<Product>,
    val tags: List<Tag>,
    val steps: List<Step>
) {
    data class Tag(
        val name: String
    )

    data class Step(
        val step: Int,
        val description: String
    )
}