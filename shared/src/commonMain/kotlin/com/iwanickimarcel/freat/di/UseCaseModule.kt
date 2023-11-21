package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.feature.recipes.domain.FilterRecipesByQuery
import com.iwanickimarcel.freat.feature.recipes.domain.GetRecipesWithOwnedProductsPercent

class UseCaseModule {
    val filterRecipesByQuery: FilterRecipesByQuery by lazy {
        FilterRecipesByQuery()
    }

    val getRecipesWithOwnedProductsPercent: GetRecipesWithOwnedProductsPercent by lazy {
        GetRecipesWithOwnedProductsPercent()
    }
}