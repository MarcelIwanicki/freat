package com.iwanickimarcel.freat.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.iwanickimarcel.freat.di.ProductsModule
import com.iwanickimarcel.freat.feature.add_product.presentation.AddProductScreen
import com.iwanickimarcel.freat.feature.home.presentation.HomeScreen
import com.iwanickimarcel.freat.feature.products.presentation.ProductsScreen
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
        RecipesScreen()
    }
}

data class Products(
    val productsModule: ProductsModule
) : Screen {
    @Composable
    override fun Content() {
        ProductsScreen(productsModule)
    }
}

data class AddProduct(
    val productsModule: ProductsModule
) : Screen {
    @Composable
    override fun Content() {
        AddProductScreen(productsModule)
    }
}

object Settings : Screen {
    @Composable
    override fun Content() {
        SettingsScreen()
    }
}