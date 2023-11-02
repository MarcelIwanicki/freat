package com.iwanickimarcel.freat.feature.recipes.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.di.AppModule
import com.iwanickimarcel.freat.navigation.BottomNavigationBar
import com.iwanickimarcel.freat.navigation.Recipes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipesScreen(
    appModule: AppModule
) {
    Scaffold(
        content = {
            Text("Recipes screen")
        },
        bottomBar = {
            BottomNavigationBar(
                Recipes(appModule),
                appModule
            )
        }
    )
}