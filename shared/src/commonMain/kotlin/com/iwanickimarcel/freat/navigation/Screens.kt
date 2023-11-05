package com.iwanickimarcel.freat.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.iwanickimarcel.freat.appModule
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

object Products : Screen {
    @Composable
    override fun Content() {
        ProductsScreen(appModule)
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

object Settings : Screen {
    @Composable
    override fun Content() {
        SettingsScreen()
    }
}