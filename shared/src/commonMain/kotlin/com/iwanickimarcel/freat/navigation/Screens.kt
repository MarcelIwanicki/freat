package com.iwanickimarcel.freat.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.iwanickimarcel.freat.di.imagePicker
import com.iwanickimarcel.freat.di.viewModelModule
import com.iwanickimarcel.freat.feature.add_recipe.presentation.AddRecipeScreen
import com.iwanickimarcel.freat.feature.home.presentation.HomeScreen
import com.iwanickimarcel.freat.feature.products_search.presentation.ProductsSearchScreen
import com.iwanickimarcel.freat.feature.recipes.presentation.RecipesScreen
import com.iwanickimarcel.freat.feature.recipes_search.presentation.RecipesSearchScreen
import com.iwanickimarcel.freat.feature.scan_bill.presentation.ScanBillScreen
import com.iwanickimarcel.freat.feature.settings.presentation.SettingsScreen
import com.iwanickimarcel.products.ProductsScreen

object Home : Screen {
    @Composable
    override fun Content() {
        HomeScreen(
            getViewModel = { viewModelModule.homeViewModel }
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
            getViewModel = { viewModelModule.productsSearchViewModel }
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
            getViewModel = { viewModelModule.recipesSearchViewModel }
        )
    }
}

object Settings : Screen {
    @Composable
    override fun Content() {
        SettingsScreen()
    }
}