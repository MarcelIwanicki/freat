package com.iwanickimarcel.freat.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.iwanickimarcel.add_recipe.AddRecipeScreen
import com.iwanickimarcel.freat.di.imagePicker
import com.iwanickimarcel.freat.di.viewModelModule
import com.iwanickimarcel.home.HomeScreen
import com.iwanickimarcel.products.ProductsScreen
import com.iwanickimarcel.products_search.ProductsSearchScreen
import com.iwanickimarcel.recipes.RecipesScreen
import com.iwanickimarcel.recipes_search.RecipesSearchScreen
import com.iwanickimarcel.scan_bill.ScanBillScreen
import com.iwanickimarcel.settings.SettingsScreen

object Home : Screen {
    @Composable
    override fun Content() {
        HomeScreen(
            getViewModel = { viewModelModule.homeViewModel },
            navigationBarFactory = HomeNavigationBarFactory(),
            navigateToProducts = { navigator, searchQuery ->
                navigator.push(Products(searchQuery))
            },
            navigateToRecipes = { navigator, searchQuery ->
                navigator.push(Recipes(searchQuery))
            },
            navigateToRecipesSearch = {
                it.push(RecipesSearch)
            },
            navigateToScanBill = {
                it.push(ScanBill)
            }
        )
    }
}

data class Recipes(
    val searchQuery: String? = null
) : Screen {
    @Composable
    override fun Content() {
        RecipesScreen(
            getViewModel = { viewModelModule.recipesViewModel },
            navigationBarFactory = RecipesNavigationBarFactory(),
            navigateToRecipesSearch = {
                it.push(RecipesSearch)
            },
            navigateToAddRecipe = { navigator, recipeId ->
                navigator.push(AddRecipe(recipeId))
            },
            searchQuery = searchQuery
        )
    }
}

data class AddRecipe(
    val recipeId: Long? = null
) : Screen {
    @Composable
    override fun Content() {
        AddRecipeScreen(
            getViewModel = { viewModelModule.addRecipeViewModel },
            getAddIngredientViewModel = { viewModelModule.addIngredientViewModel },
            getAddStepViewModel = { viewModelModule.addStepViewModel },
            imagePicker = imagePicker,
            editRecipeId = recipeId
        )
    }
}

data class Products(
    val searchQuery: String? = null
) : Screen {
    @Composable
    override fun Content() {
        ProductsScreen(
            getViewModel = { viewModelModule.productsViewModel },
            getAddProductViewModel = { viewModelModule.addProductViewModel },
            imagePicker = imagePicker,
            navigationBarFactory = ProductsNavigationBarFactory(),
            navigateToProductsSearch = {
                it.push(ProductsSearch)
            },
            navigateToScanBill = {
                it.push(ScanBill)
            },
            searchQuery = searchQuery
        )
    }
}

object ProductsSearch : Screen {
    @Composable
    override fun Content() {
        ProductsSearchScreen(
            getViewModel = { viewModelModule.productsSearchViewModel },
            navigateToProducts = { navigator, searchQuery ->
                navigator.replace(
                    Products(searchQuery)
                )
            }
        )
    }
}

object ScanBill : Screen {
    @Composable
    override fun Content() {
        ScanBillScreen(
            getViewModel = { viewModelModule.scanBillViewModel },
            getAddIngredientViewModel = { viewModelModule.addIngredientViewModel },
            imagePicker = imagePicker
        )
    }
}

object RecipesSearch : Screen {
    @Composable
    override fun Content() {
        RecipesSearchScreen(
            getViewModel = { viewModelModule.recipesSearchViewModel },
            navigateToRecipes = { navigator, searchQuery ->
                navigator.replace(Recipes(searchQuery))
            }
        )
    }
}

object Settings : Screen {
    @Composable
    override fun Content() {
        SettingsScreen(
            navigationBarFactory = SettingsNavigationBarFactory()
        )
    }
}