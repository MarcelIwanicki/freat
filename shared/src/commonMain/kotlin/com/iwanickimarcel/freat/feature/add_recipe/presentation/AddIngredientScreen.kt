package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iwanickimarcel.freat.feature.products.domain.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientScreen(
    onIngredientAdded: (Product) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add ingredient"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIos,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(it)
            ) {

            }
        }
    )
}