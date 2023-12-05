package com.iwanickimarcel.add_recipe

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iwanickimarcel.recipes.Recipe

@Composable
fun StepsScreen(
    addRecipeState: AddRecipeState,
    onEditStepPress: (Recipe.Step) -> Unit,
    onAddStepPressed: () -> Unit,
    onDeleteStepPressed: (Recipe.Step) -> Unit,
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
            items(addRecipeState.steps) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            onEditStepPress(it)
                        }
                        .padding(8.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = it.step.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                        )
                        Text(it.description)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        IconButton(
                            onClick = {
                                onEditStepPress(it)
                            },
                            content = {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Edit step"
                                )
                            }
                        )
                        IconButton(
                            onClick = {
                                onDeleteStepPressed(it)
                            },
                            content = {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete step"
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
                        onAddStepPressed()
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add step",
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