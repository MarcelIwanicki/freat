package com.iwanickimarcel.freat.feature.recipes.data

import com.iwanickimarcel.core.ImageStorage
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.freat.recipesdatabase.RecipeEntity
import com.iwanickimarcel.freat.recipesdatabase.RecipeProductsEntity
import com.iwanickimarcel.freat.recipesdatabase.RecipeStepEntity
import com.iwanickimarcel.freat.recipesdatabase.RecipeTagsEntity
import com.iwanickimarcel.products.Amount
import com.iwanickimarcel.products.AmountUnit
import com.iwanickimarcel.products.Product

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
        photoBytes = imagePath?.let { imageStorage.getBytes(it) },
        ownedProductsPercent = 0
    )
}

fun RecipeProductsEntity.toProduct(): Product {
    return Product(
        name = productName,
        amount = Amount(
            amount = productAmount,
            unit = AmountUnit.values()[productAmountUnitId.toInt()]
        ),
        photoBytes = null
    )
}

fun RecipeTagsEntity.toTag(): Recipe.Tag {
    return Recipe.Tag(
        name = tagName
    )
}

fun RecipeStepEntity.toStep(): Recipe.Step {
    return Recipe.Step(
        step = step.toInt(),
        description = description
    )
}