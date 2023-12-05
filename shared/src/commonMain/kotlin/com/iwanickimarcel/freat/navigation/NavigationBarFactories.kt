package com.iwanickimarcel.freat.navigation

import androidx.compose.runtime.Composable
import com.iwanickimarcel.core.NavigationBarFactory

class HomeNavigationBarFactory : NavigationBarFactory {
    @Composable
    override fun NavigationBar() {
        BottomNavigationBar(Home)
    }
}

class RecipesNavigationBarFactory : NavigationBarFactory {
    @Composable
    override fun NavigationBar() {
        BottomNavigationBar(Recipes())
    }
}

class ProductsNavigationBarFactory : NavigationBarFactory {
    @Composable
    override fun NavigationBar() {
        BottomNavigationBar(Products())
    }
}
