package com.iwanickimarcel.settings

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.iwanickimarcel.core.NavigationBarFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navigationBarFactory: NavigationBarFactory
) {
    Scaffold(
        content = {
            Text("Settings screen")
        },
        bottomBar = {
            with(navigationBarFactory) {
                NavigationBar()
            }
        }
    )
}