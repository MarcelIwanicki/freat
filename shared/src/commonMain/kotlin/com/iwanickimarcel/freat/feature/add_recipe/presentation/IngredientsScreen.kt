package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.iwanickimarcel.freat.feature.products.domain.Product

@Composable
fun IngredientsScreen(
    addRecipeState: AddRecipeState,
    onAddIngredientPressed: () -> Unit,
    onIngredientAdded: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(addRecipeState.ingredients) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    buildString {
                        append(it.name)
                        append(", ")
                        append(it.amount.amount)
                        append(" ")
                        append(it.amount.unit.abbreviation)
                    },
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        item {
            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.secondary),
                onClick = {
                    onAddIngredientPressed()
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add ingredient",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            )
        }
    }
}