package com.iwanickimarcel.freat.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import com.iwanickimarcel.freat.App
import com.iwanickimarcel.freat.di.ProductsModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true,
                productsModule = ProductsModule(LocalContext.current.applicationContext)
            )
        }
    }
}
