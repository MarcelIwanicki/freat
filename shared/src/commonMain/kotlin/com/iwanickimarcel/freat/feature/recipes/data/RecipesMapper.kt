package com.iwanickimarcel.freat.feature.recipes.data

import com.iwanickimarcel.freat.core.data.ImageStorage
import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.freat.recipesdatabase.RecipeEntity
import com.iwanickimarcel.freat.recipesdatabase.RecipeStepEntity

suspend fun RecipeEntity.toRecipe(
    imageStorage: ImageStorage,
    products: List<Product>,
    tags: List<Recipe.Tag>,
    steps: List<Recipe.Step>
): Recipe {
    return Recipe(
        id = id,
        name = name,
        products = products,
        tags = tags,
        steps = steps,
        photoBytes = imagePath?.let { imageStorage.getBytes(it) }
    )
}

fun String.toTag(): Recipe.Tag {
    return Recipe.Tag(
        name = this
    )
}

fun RecipeStepEntity.toStep(): Recipe.Step {
    return Recipe.Step(
        step = step.toInt(),
        description = description
    )
}