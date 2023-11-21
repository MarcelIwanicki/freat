package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.feature.add_product.domain.ValidateProduct
import com.iwanickimarcel.freat.feature.add_recipe.domain.ValidateRecipe
import com.iwanickimarcel.freat.feature.add_step.domain.ValidateStep
import com.iwanickimarcel.freat.feature.products.domain.FilterProductsByQuery
import com.iwanickimarcel.freat.feature.recipes.domain.FilterRecipesByQuery
import com.iwanickimarcel.freat.feature.recipes.domain.GetRecipesWithOwnedProductsPercent

class UseCaseModule {
    val filterProductsByQuery: FilterProductsByQuery by lazy {
        FilterProductsByQuery()
    }

    val filterRecipesByQuery: FilterRecipesByQuery by lazy {
        FilterRecipesByQuery()
    }

    val getRecipesWithOwnedProductsPercent: GetRecipesWithOwnedProductsPercent by lazy {
        GetRecipesWithOwnedProductsPercent()
    }

    val validateProduct: ValidateProduct by lazy {
        ValidateProduct()
    }

    val validateRecipe: ValidateRecipe by lazy {
        ValidateRecipe()
    }

    val validateStep: ValidateStep by lazy {
        ValidateStep()
    }
}