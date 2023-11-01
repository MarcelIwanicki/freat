package com.iwanickimarcel.freat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.iwanickimarcel.freat.core.presentation.FreatTheme
import com.iwanickimarcel.freat.di.ProductsModule
import com.iwanickimarcel.freat.navigation.BottomNavigationBar
import com.iwanickimarcel.freat.navigation.Home

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    productsModule: ProductsModule
) {
    FreatTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Navigator(Home) {
                Scaffold(
                    content = {
                        CurrentScreen()
                    },
                    bottomBar = {
                        BottomNavigationBar(productsModule)
                    }
                )
            }
        }
    }
}