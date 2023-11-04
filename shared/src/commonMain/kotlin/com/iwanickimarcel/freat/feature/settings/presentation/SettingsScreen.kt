package com.iwanickimarcel.freat.feature.settings.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.navigation.BottomNavigationBar
import com.iwanickimarcel.freat.navigation.Settings

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen() {
    Scaffold(
        content = {
            Text("Settings screen")
        },
        bottomBar = {
            BottomNavigationBar(Settings)
        }
    )
}