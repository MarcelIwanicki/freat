package com.iwanickimarcel.freat.di

import com.iwanickimarcel.add_product.ValidateProduct
import com.iwanickimarcel.add_recipe.DeleteStep
import com.iwanickimarcel.add_recipe.EditStep
import com.iwanickimarcel.add_recipe.ValidateRecipe
import com.iwanickimarcel.freat.feature.add_step.domain.ValidateStep
import com.iwanickimarcel.products.FilterProductsByQuery
import com.iwanickimarcel.products_search.FilterProductsSearchHistoryItems
import com.iwanickimarcel.recipes.FilterRecipesByQuery
import com.iwanickimarcel.recipes.GetRecipesWithOwnedProductsPercent
import com.iwanickimarcel.recipes_search.FilterRecipesSearchHistoryItems

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