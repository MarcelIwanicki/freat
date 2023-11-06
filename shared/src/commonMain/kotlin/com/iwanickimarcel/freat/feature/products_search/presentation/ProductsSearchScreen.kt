package com.iwanickimarcel.freat.feature.products_search.presentation

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
import com.iwanickimarcel.freat.di.AppModule
import com.iwanickimarcel.freat.feature.products_search.domain.ProductsSearchHistoryItem
import com.iwanickimarcel.freat.navigation.Products
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsSearchScreen(
    appModule: AppModule
) {
    val navigator = LocalNavigator.current ?: return

    val viewModel = getViewModel(
        key = "products-search-screen",
        factory = viewModelFactory {
            ProductsSearchViewModel(
                productDataSource = appModule.productDataSource,
                productsSearchHistoryDataSource = appModule.productsSearchHistoryDataSource
            )
        }
    )
    val state by viewModel.state.collectAsState()

    val focusRequester = remember { FocusRequester() }

    if (state.isBackPressed) {
        LaunchedEffect(Unit) {
            navigator.pop()
        }
    }

    if (state.isSearchConfirmed) {
        LaunchedEffect(Unit) {
            navigator.replace(
                Products(searchQuery = state.query)
            )
        }
    }

    state.itemPressedName?.let {
        LaunchedEffect(Unit) {
            navigator.replace(
                Products(searchQuery = it)
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
                            Text(text = "Search for products...")
                        },
                        onValueChange = {
                            viewModel.onEvent(ProductsSearchEvent.OnQueryChange(it))
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(ProductsSearchEvent.OnBackPressed)
                                },
                                content = {
                                    Icon(
                                        imageVector = Icons.Outlined.ArrowBackIos,
                                        contentDescription = "Search for products"
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
                                viewModel.onEvent(ProductsSearchEvent.OnSearchConfirm)
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
                                viewModel.onEvent(ProductsSearchEvent.OnItemPress(it.name))
                            }
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )

                    ) {
                        Icon(
                            imageVector = if (it.type == ProductsSearchHistoryItem.Type.Search) {
                                Icons.Outlined.Search
                            } else {
                                Icons.Outlined.History
                            },
                            contentDescription = it.name
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = it.name
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