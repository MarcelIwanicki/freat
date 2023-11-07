package com.iwanickimarcel.freat.feature.recipes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.iwanickimarcel.freat.feature.products.domain.Product
import com.iwanickimarcel.freat.feature.products.domain.g
import com.iwanickimarcel.freat.feature.recipes.domain.Recipe
import com.iwanickimarcel.freat.navigation.BottomNavigationBar
import com.iwanickimarcel.freat.navigation.Recipes

@Composable
fun RecipesScreen() {

    val fakeRecipes = remember {
        listOf(
            Recipe(
                id = 0,
                name = "Chicken nuggets",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Apple",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Banana",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open oven"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert chicken"
                    )
                ),
                tags = listOf(
                    Recipe.Tag("Chicken"),
                    Recipe.Tag("Nuggets"),
                    Recipe.Tag("Quick"),
                )
            ),
            Recipe(
                id = 1,
                name = "Cooked banana",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Banana",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Peanut butter",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open oven"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert banana"
                    )
                ),
                tags = listOf(
                    Recipe.Tag("Peanut"),
                    Recipe.Tag("butter"),
                    Recipe.Tag("banana"),
                )
            ),
            Recipe(
                id = 2,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 3,
                name = "Chicken nuggets",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Apple",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Banana",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open oven"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert chicken"
                    )
                ),
                tags = listOf(
                    Recipe.Tag("Chicken"),
                    Recipe.Tag("Nuggets"),
                    Recipe.Tag("Quick"),
                )
            ),
            Recipe(
                id = 4,
                name = "Cooked banana",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Banana",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Peanut butter",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open oven"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert banana"
                    )
                ),
                tags = listOf(
                    Recipe.Tag("Peanut"),
                    Recipe.Tag("butter"),
                    Recipe.Tag("banana"),
                )
            ),
            Recipe(
                id = 5,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),

            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
            Recipe(
                id = 6,
                name = "Smoked salmon",
                photoBytes = null,
                products = listOf(
                    Product(
                        name = "Salmon",
                        amount = 20.0.g,
                        photoBytes = null
                    ),
                    Product(
                        name = "Potato",
                        amount = 100.0.g,
                        photoBytes = null
                    )
                ),
                steps = listOf(
                    Recipe.Step(
                        step = 1,
                        description = "Open smoker"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Salmon"
                    ),
                    Recipe.Step(
                        step = 2,
                        description = "Insert Potato"
                    ),
                ),
                tags = listOf(
                    Recipe.Tag("Smoked"),
                    Recipe.Tag("Salmon"),
                    Recipe.Tag("Potato"),
                )
            ),
        )
    }

    Scaffold(
        content = {
            LazyVerticalStaggeredGrid(
                modifier = Modifier.padding(it),
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 16.dp
            ) {

                itemsIndexed(fakeRecipes) { index, item ->
                    val offset = index % 5
                    val height = if (offset == 1 || offset == 3) {
                        256.dp
                    } else {
                        120.dp
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)

                    ) {
                        Text(
                            text = index.toString()
                        )
                    }
                }
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