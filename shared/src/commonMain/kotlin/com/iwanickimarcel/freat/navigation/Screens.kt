package com.iwanickimarcel.freat.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.iwanickimarcel.freat.di.AppModule
import com.iwanickimarcel.freat.feature.add_product.presentation.AddProductScreen
import com.iwanickimarcel.freat.feature.home.presentation.HomeScreen
import com.iwanickimarcel.freat.feature.products.presentation.ProductsScreen
import com.iwanickimarcel.freat.feature.recipes.presentation.RecipesScreen
import com.iwanickimarcel.freat.feature.settings.presentation.SettingsScreen

data class Home(
    val appModule: AppModule
) : Screen {
    @Composable
    override fun Content() {
        HomeScreen(appModule)
    }
}

data class Recipes(
    val appModule: AppModule
) : Screen {
    @Composable
    override fun Content() {
        RecipesScreen(appModule)
    }
}

data class Products(
    val appModule: AppModule
) : Screen {
    @Composable
    override fun Content() {
        ProductsScreen(appModule)
    }
}

data class AddProduct(
    val appModule: AppModule
) : Screen {
    @Composable
    override fun Content() {
        AddProductScreen(appModule)
    }
}

data class Settings(
    val appModule: AppModule
) : Screen {
    @Composable
    override fun Content() {
        SettingsScreen(appModule)
    }
}