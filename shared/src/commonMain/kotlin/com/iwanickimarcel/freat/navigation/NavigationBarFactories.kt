package com.iwanickimarcel.freat.navigation

import androidx.compose.runtime.Composable
import com.iwanickimarcel.core.NavigationBarFactory

class ProductsNavigationBarFactory : NavigationBarFactory {
    @Composable
    override fun NavigationBar() {
        BottomNavigationBar(Products())
    }
}