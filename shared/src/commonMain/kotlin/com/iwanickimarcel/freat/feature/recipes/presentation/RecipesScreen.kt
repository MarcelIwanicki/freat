package com.iwanickimarcel.freat.feature.recipes.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.iwanickimarcel.freat.navigation.AddRecipe
import com.iwanickimarcel.freat.navigation.BottomNavigationBar
import com.iwanickimarcel.freat.navigation.Recipes
import com.iwanickimarcel.freat.navigation.RecipesSearch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RecipesScreen(
    getViewModel: @Composable () -> RecipesViewModel,
    searchQuery: String?
) {
    val viewModel = getViewModel()
    val navigator = LocalNavigator.current ?: return
    val state by viewModel.state.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    searchQuery?.let {
        LaunchedEffect(Unit) {
            viewModel.onEvent(RecipesEvent.OnSearchQuery(it))
        }
    }

    if (state.searchBarPressed) {
        navigator.push(RecipesSearch)
    }

    state.recipeToDelete?.let {
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(RecipesEvent.OnDeleteRecipeMenuDismiss)
            },
            title = {
                Text(
                    text = buildString {
                        append("Delete ")
                        append(it.name)
                        append("?")
                    }
                )
            },
            text = {
                Text(text = "You will not be able to recover it")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(RecipesEvent.OnDeleteRecipeConfirm)
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(RecipesEvent.OnDeleteRecipeMenuDismiss)
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    state.recipeToEdit?.let {
        LaunchedEffect(Unit) {
            navigator.push(
                AddRecipe(recipeId = it.id)
            )
        }
    }

    state.longPressedRecipe?.let {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(RecipesEvent.OnRecipeLongPressDismiss)
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            LongPressBottomSheetContent(
                productsState = state,
                onDeleteProductPressed = {
                    viewModel.onEvent(
                        RecipesEvent.OnDeleteRecipePress(
                            state.longPressedRecipe ?: return@LongPressBottomSheetContent
                        )
                    )
                },
                onEditProductPressed = {
                    viewModel.onEvent(
                        RecipesEvent.OnEditRecipePress(
                            state.longPressedRecipe ?: return@LongPressBottomSheetContent
                        )
                    )
                }
            )
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(16.dp),
                title = {},
                actions = {
                    OutlinedTextField(
                        value = state.searchQuery,
                        readOnly = true,
                        enabled = false,
                        placeholder = {
                            Text(text = "Search for recipes...")
                        },
                        onValueChange = { },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(RecipesEvent.OnSearchBarClick)
                            },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "Search for recipes"
                            )
                        }
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        content = {
            LazyVerticalStaggeredGrid(
                modifier = Modifier.padding(it),
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 16.dp
            ) {
                itemsIndexed(state.recipes) { index, item ->
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
                            .combinedClickable(
                                onClick = {

                                },
                                onLongClick = {
                                    viewModel.onEvent(RecipesEvent.OnRecipeLongPress(item))
                                }
                            ),
                    ) {
                        RecipePhoto(
                            recipe = item,
                            modifier = Modifier.fillMaxSize()
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                item.tags.firstOrNull()?.let {
                                    ElevatedFilterChip(
                                        selected = false,
                                        onClick = { },
                                        label = {
                                            Text(it.name)
                                        },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = FilterChipDefaults.filterChipColors(
                                            containerColor = MaterialTheme.colorScheme.tertiary,
                                            labelColor = MaterialTheme.colorScheme.onTertiary
                                        )
                                    )
                                }

                                IconButton(
                                    onClick = {

                                    },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Outlined.Favorite,
                                            contentDescription = "Favorite",
                                        )
                                    }
                                )
                            }

                            Column {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = buildString {
                                        append("You have ")
                                        append(item.ownedProductsPercent)
                                        append("% of ingredients")
                                    },
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(Recipes())
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navigator.push(AddRecipe())
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