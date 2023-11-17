package com.iwanickimarcel.freat.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.iwanickimarcel.freat.di.appModule
import com.iwanickimarcel.freat.feature.add_recipe.presentation.AddRecipeScreen
import com.iwanickimarcel.freat.feature.home.presentation.HomeScreen
import com.iwanickimarcel.freat.feature.products.presentation.ProductsScreen
import com.iwanickimarcel.freat.feature.products_search.presentation.ProductsSearchScreen
import com.iwanickimarcel.freat.feature.recipes.presentation.RecipesScreen
import com.iwanickimarcel.freat.feature.settings.presentation.SettingsScreen

object Home : Screen {
    @Composable
    override fun Content() {
        HomeScreen()
    }
}

object Recipes : Screen {
    @Composable
    override fun Content() {
        RecipesScreen(
            viewModel = appModule.viewModelModule.recipesViewModel
        )
    }
}

data class AddRecipe(
    val recipeId: Int? = null
) : Screen {
    @Composable
    override fun Content() {
        AddRecipeScreen(
            viewModel = appModule.viewModelModule.addRecipeViewModel,
            imagePicker = appModule.imageModule.imagePicker,
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
            viewModel = appModule.viewModelModule.productsViewModel,
            addProductViewModel = appModule.viewModelModule.addProductViewModel,
            imagePicker = appModule.imageModule.imagePicker,
            searchQuery = searchQuery
        )
    }
}

object ProductsSearch : Screen {
    @Composable
    override fun Content() {
        ProductsSearchScreen(
            viewModel = appModule.viewModelModule.productsSearchViewModel
        )
    }
}

object Settings : Screen {
    @Composable
    override fun Content() {
        SettingsScreen()
    }
}