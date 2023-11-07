package com.iwanickimarcel.freat.feature.recipes.data

import com.iwanickimarcel.freat.core.data.ImageStorage
import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.freat.recipesdatabase.RecipeEntity

suspend fun RecipeEntity.toRecipe(
    imageStorage: ImageStorage,
    products: List<Product>,
    tags: List<Recipe.Tag>
): Recipe {
    return Recipe(
        id = id,
        name = name,
        products = products,
        tags = tags,
        photoBytes = imagePath?.let { imageStorage.getBytes(it) }
    )
}

fun String.toTag(): Recipe.Tag {
    return Recipe.Tag(
        name = this
    )
}