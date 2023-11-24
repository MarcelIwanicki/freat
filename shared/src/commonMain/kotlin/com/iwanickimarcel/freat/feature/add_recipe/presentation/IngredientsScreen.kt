package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.iwanickimarcel.freat.feature.products.domain.Product

@Composable
fun IngredientsScreen(
    addRecipeState: AddRecipeState,
    onEditIngredientPressed: (Product) -> Unit,
    onAddIngredientPressed: () -> Unit,
    onDeleteIngredientPressed: (Product) -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
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
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            onEditIngredientPressed(it)
                        }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
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
                    Row(
                        modifier = Modifier.width(80.dp)
                    ) {
                        IconButton(
                            onClick = {
                                onEditIngredientPressed(it)
                            },
                            content = {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Edit ingredient"
                                )
                            }
                        )
                        IconButton(
                            onClick = {
                                onDeleteIngredientPressed(it)
                            },
                            content = {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete ingredient"
                                )
                            }
                        )
                    }
                }
            }
            item {
                IconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            shape = RoundedCornerShape(16.dp)
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
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                onNextClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "Next",
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}