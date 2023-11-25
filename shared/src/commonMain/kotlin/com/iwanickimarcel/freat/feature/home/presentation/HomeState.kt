package com.iwanickimarcel.freat.feature.home.presentation

import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe

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