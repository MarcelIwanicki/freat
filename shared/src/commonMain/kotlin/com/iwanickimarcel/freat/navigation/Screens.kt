package com.iwanickimarcel.freat.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.iwanickimarcel.freat.appModule
import com.iwanickimarcel.freat.feature.add_product.presentation.AddProductScreen
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
        RecipesScreen(appModule)
    }
}

data class AddRecipe(
    val recipeId: Int? = null
) : Screen {
    @Composable
    override fun Content() {
        AddRecipeScreen(
            appModule = appModule,
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
            appModule = appModule,
            searchQuery = searchQuery
        )
    }
}

data class AddProduct(
    val productName: String? = null
) : Screen {
    @Composable
    override fun Content() {
        AddProductScreen(
            appModule = appModule,
            editProductName = productName
        )
    }
}

object ProductsSearch : Screen {
    @Composable
    override fun Content() {
        ProductsSearchScreen(appModule)
    }
}

object Settings : Screen {
    @Composable
    override fun Content() {
        SettingsScreen()
    }
}