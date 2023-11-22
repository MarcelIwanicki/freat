package com.iwanickimarcel.freat.feature.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.iwanickimarcel.freat.feature.products.domain.ProductPhoto
import com.iwanickimarcel.freat.navigation.BottomNavigationBar
import com.iwanickimarcel.freat.navigation.Home
import com.iwanickimarcel.freat.navigation.Products
import com.iwanickimarcel.freat.navigation.RecipesSearch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    getViewModel: @Composable () -> HomeViewModel
) {
    val viewModel = getViewModel()
    val navigator = LocalNavigator.current ?: return
    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    state.clickedProduct?.let {
        navigator.push(Products(it.name))
    }

    if (state.isSearchBarClicked) {
        navigator.push(RecipesSearch)
    }

    if (state.isShowAllProductsClicked) {
        navigator.push(Products())
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(16.dp),
                title = {},
                actions = {
                    OutlinedTextField(
                        value = "",
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
                                viewModel.onEvent(HomeEvent.OnSearchBarClick)
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
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top
            ) {
                state.products.takeIf { it.isNotEmpty() }?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Your products",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        TextButton(
                            modifier = Modifier.padding(top = 4.dp),
                            onClick = {
                                viewModel.onEvent(HomeEvent.OnShowAllProductsClick)
                            },
                            content = {
                                Text(
                                    text = "Show all",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(1),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                    ) {
                        items(it) { item ->
                            Column(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clickable {
                                        viewModel.onEvent(HomeEvent.OnProductClick(item))
                                    }
                                    .padding(horizontal = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ProductPhoto(
                                    modifier = Modifier.size(64.dp),
                                    product = item,
                                    iconSize = 64.dp,
                                    shape = RoundedCornerShape(64.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    modifier = Modifier.width(64.dp),
                                    text = item.name,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }

            }
        },
        bottomBar = {
            BottomNavigationBar(Home)
        }
    )
}