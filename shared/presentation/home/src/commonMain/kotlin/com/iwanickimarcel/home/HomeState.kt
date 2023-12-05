package com.iwanickimarcel.home

import com.iwanickimarcel.products.Product
import com.iwanickimarcel.recipes.Recipe

data class HomeState(
    val products: List<Product> = emptyList(),
    val recipes: List<Recipe> = emptyList(),
    val isSearchBarClicked: Boolean = false,
    val isShowAllProductsClicked: Boolean = false,
    val isShowAllRecipesClicked: Boolean = false,
    val clickedProduct: Product? = null,
    val clickedRecipe: Recipe? = null,
    val isScanBillClicked: Boolean = false,
)