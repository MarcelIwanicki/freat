package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.feature.add_product.domain.ValidateProduct
import com.iwanickimarcel.freat.feature.add_recipe.domain.DeleteStep
import com.iwanickimarcel.freat.feature.add_recipe.domain.EditStep
import com.iwanickimarcel.freat.feature.add_recipe.domain.ValidateRecipe
import com.iwanickimarcel.freat.feature.add_step.domain.ValidateStep
import com.iwanickimarcel.freat.feature.recipes_search.domain.FilterRecipesSearchHistoryItems
import com.iwanickimarcel.products.FilterProductsByQuery
import com.iwanickimarcel.products_search.FilterProductsSearchHistoryItems
import com.iwanickimarcel.recipes.FilterRecipesByQuery
import com.iwanickimarcel.recipes.GetRecipesWithOwnedProductsPercent

class UseCaseModule {
    val filterProductsByQuery: FilterProductsByQuery by lazy {
        FilterProductsByQuery()
    }

    val filterRecipesByQuery: FilterRecipesByQuery by lazy {
        FilterRecipesByQuery()
    }

    val filterProductsSearchHistoryItems: FilterProductsSearchHistoryItems by lazy {
        FilterProductsSearchHistoryItems()
    }

    val filterRecipesSearchHistoryItems: FilterRecipesSearchHistoryItems by lazy {
        FilterRecipesSearchHistoryItems()
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

    val deleteStep: DeleteStep by lazy {
        DeleteStep()
    }

    val editStep: EditStep by lazy {
        EditStep()
    }
}