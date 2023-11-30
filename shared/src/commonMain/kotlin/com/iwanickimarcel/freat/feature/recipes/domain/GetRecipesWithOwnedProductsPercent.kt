package com.iwanickimarcel.freat.feature.recipes.domain

import com.iwanickimarcel.products.Product
import kotlin.math.roundToInt

class GetRecipesWithOwnedProductsPercent {
    operator fun invoke(
        recipes: List<Recipe>,
        products: List<Product>
    ): List<Recipe> = buildList {
        recipes.forEach { recipe ->
            var completeGramatureOwned = 0.0
            var recipeGramature = 0.0

            products.forEach { product ->
                val productFoundInRecipe = recipe.products.find { it.name == product.name }

                productFoundInRecipe?.let {
                    completeGramatureOwned += product.amount.toGrams().coerceAtMost(
                        it.amount.toGrams()
                    )
                }
            }

            recipe.products.forEach {
                recipeGramature += it.amount.toGrams()
            }

            val ownedProductsFactor = completeGramatureOwned / recipeGramature
            val ownedProductsPercent = (ownedProductsFactor * 10.0).roundToInt()

            add(
                recipe.copy(ownedProductsPercent = ownedProductsPercent)
            )
        }
    }
}