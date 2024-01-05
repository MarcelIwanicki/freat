package com.iwanickimarcel.recipes

import com.iwanickimarcel.products.Product

typealias Percent = Int

data class Recipe(
    val id: Long,
    val name: String,
    val photoBytes: ByteArray?,
    val products: List<Product>,
    val tags: List<Tag>,
    val steps: List<Step>,
    val ownedProductsPercent: Percent,
    val isFavorite: Boolean,
) {
    data class Tag(
        val name: String
    )

    data class Step(
        val step: Int,
        val description: String
    )

    /*
        equals and hashcode have to be overwritten because of photoBytes ByteArray
     */

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (id != other.id) return false
        if (name != other.name) return false
        if (photoBytes != null) {
            if (other.photoBytes == null) return false
            if (!photoBytes.contentEquals(other.photoBytes)) return false
        } else if (other.photoBytes != null) return false
        if (products != other.products) return false
        if (tags != other.tags) return false
        if (steps != other.steps) return false
        if (ownedProductsPercent != other.ownedProductsPercent) return false
        if (isFavorite != other.isFavorite) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (photoBytes?.contentHashCode() ?: 0)
        result = 31 * result + products.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + steps.hashCode()
        result = 31 * result + ownedProductsPercent
        result = 31 * result + isFavorite.hashCode()
        return result
    }

}