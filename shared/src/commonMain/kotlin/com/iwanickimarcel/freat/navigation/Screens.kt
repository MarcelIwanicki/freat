package com.iwanickimarcel.freat.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.iwanickimarcel.freat.di.appModule
import com.iwanickimarcel.freat.feature.add_recipe.presentation.AddRecipeScreen
import com.iwanickimarcel.freat.feature.home.presentation.HomeScreen
import com.iwanickimarcel.freat.feature.products.presentation.ProductsScreen
import com.iwanickimarcel.freat.feature.products_search.presentation.ProductsSearchScreen
import com.iwanickimarcel.freat.feature.recipes.presentation.RecipesScreen
import com.iwanickimarcel.freat.feature.recipes_search.presentation.RecipesSearchScreen
import com.iwanickimarcel.freat.feature.settings.presentation.SettingsScreen

object Home : Screen {
    @Composable
    override fun Content() {
        HomeScreen()
    }
}

data class Recipes(
    val searchQuery: String? = null
) : Screen {
    @Composable
    override fun Content() {
        RecipesScreen(
            getViewModel = { appModule.viewModelModule.recipesViewModel },
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
            getViewModel = { appModule.viewModelModule.addRecipeViewModel },
            getAddIngredientViewModel = { appModule.viewModelModule.addIngredientViewModel },
            getAddStepViewModel = { appModule.viewModelModule.addStepViewModel },
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
            getViewModel = { appModule.viewModelModule.productsViewModel },
            getAddProductViewModel = { appModule.viewModelModule.addProductViewModel },
            imagePicker = appModule.imageModule.imagePicker,
            searchQuery = searchQuery
        )
    }
}

object ProductsSearch : Screen {
    @Composable
    override fun Content() {
        ProductsSearchScreen(
            getViewModel = { appModule.viewModelModule.productsSearchViewModel }
        )
    }
}

object RecipesSearch : Screen {
    @Composable
    override fun Content() {
        RecipesSearchScreen(
            getViewModel = { appModule.viewModelModule.recipesSearchViewModel }
        )
    }
}

object Settings : Screen {
    @Composable
    override fun Content() {
        SettingsScreen()
    }
}