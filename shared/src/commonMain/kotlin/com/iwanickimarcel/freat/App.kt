package com.iwanickimarcel.freat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.iwanickimarcel.freat.core.presentation.FreatTheme
import com.iwanickimarcel.freat.di.AppModule
import com.iwanickimarcel.freat.navigation.Home

@SuppressLint("StaticFieldLeak")
lateinit var appModule: AppModule

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    module: AppModule,
) {
    appModule = module

    FreatTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Navigator(Home)
        }
    }
}