package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun IngredientsScreen(
    addRecipeState: AddRecipeState,
    onAddIngredientPressed: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(addRecipeState.ingredients) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    buildString {
                        append(it.name)
                        append(", ")
                        append(it.amount.amount)
                        append(" ")
                        append(it.amount.unit.abbreviation)
                    },
                )
            }
        }
        item {
            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                onClick = {
                    onAddIngredientPressed()
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add ingredient",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )
        }
    }
}