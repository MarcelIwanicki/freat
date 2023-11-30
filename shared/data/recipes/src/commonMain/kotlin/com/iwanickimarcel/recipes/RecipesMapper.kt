package com.iwanickimarcel.recipes

import com.iwanickimarcel.core.ImageStorage
import com.iwanickimarcel.products.Amount
import com.iwanickimarcel.products.AmountUnit
import com.iwanickimarcel.products.Product
import com.iwanickimarcel.recipes.database.RecipeEntity
import com.iwanickimarcel.recipes.database.RecipeProductsEntity
import com.iwanickimarcel.recipes.database.RecipeStepEntity
import com.iwanickimarcel.recipes.database.RecipeTagsEntity

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