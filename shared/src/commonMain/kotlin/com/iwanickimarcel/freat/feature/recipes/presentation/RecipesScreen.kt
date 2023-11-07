package com.iwanickimarcel.freat.feature.recipes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iwanickimarcel.freat.navigation.BottomNavigationBar
import com.iwanickimarcel.freat.navigation.Recipes

@Composable
fun RecipesScreen() {
    Scaffold(
        content = {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Text("Recipes screen")
            }
        },
        bottomBar = {
            BottomNavigationBar(Recipes)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {

                },
                text = {
                    Text(text = "Add recipe")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add recipe"
                    )
                },
            )
        }
    )
}