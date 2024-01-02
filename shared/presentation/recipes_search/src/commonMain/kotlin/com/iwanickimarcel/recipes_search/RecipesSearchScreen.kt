package com.iwanickimarcel.recipes_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator

fun interface RecipesNavigation {
    operator fun invoke(navigator: Navigator, searchQuery: String?)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesSearchScreen(
    getViewModel: @Composable () -> RecipesSearchViewModel,
    navigateToRecipes: RecipesNavigation
) {
    val viewModel = getViewModel()
    val navigator = LocalNavigator.current ?: return
    val state by viewModel.state.collectAsState()
    val focusRequester = remember { FocusRequester() }

    if (state.isBackPressed) {
        LaunchedEffect(Unit) {
            navigator.pop()
        }
    }

    if (state.isSearchConfirmed) {
        LaunchedEffect(Unit) {
            navigateToRecipes(
                navigator = navigator,
                searchQuery = state.query.text
            )
        }
    }

    state.itemPressedQuery?.let {
        LaunchedEffect(Unit) {
            navigateToRecipes(
                navigator = navigator,
                searchQuery = it
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(16.dp),
                title = {},
                actions = {
                    OutlinedTextField(
                        value = state.query,
                        placeholder = {
                            Text(text = "Search for recipes...")
                        },
                        onValueChange = {
                            viewModel.onEvent(RecipesSearchEvent.OnQueryChange(it))
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(RecipesSearchEvent.OnBackPressed)
                                },
                                content = {
                                    Icon(
                                        imageVector = Icons.Outlined.ArrowBackIos,
                                        contentDescription = "Search for recipes"
                                    )
                                }
                            )

                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                viewModel.onEvent(RecipesSearchEvent.OnSearchConfirm)
                            }
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.items) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                viewModel.onEvent(RecipesSearchEvent.OnItemPress(it.query))
                            }
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )

                    ) {
                        Icon(
                            imageVector = if (it.type == RecipesSearchHistoryItem.Type.Search) {
                                Icons.Outlined.Search
                            } else {
                                Icons.Outlined.History
                            },
                            contentDescription = it.query
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = it.query
                        )
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}