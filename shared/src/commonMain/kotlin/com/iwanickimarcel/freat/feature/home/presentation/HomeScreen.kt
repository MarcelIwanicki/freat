package com.iwanickimarcel.freat.feature.home.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.navigation.BottomNavigationBar
import com.iwanickimarcel.freat.navigation.Home

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Scaffold(
        content = {
            Text("Home screen")
        },
        bottomBar = {
            BottomNavigationBar(Home)
        }
    )
}