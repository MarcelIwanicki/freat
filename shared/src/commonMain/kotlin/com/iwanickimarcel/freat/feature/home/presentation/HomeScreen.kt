package com.iwanickimarcel.freat.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.iwanickimarcel.core.TriangleShape
import com.iwanickimarcel.freat.feature.recipes.presentation.RecipePhoto
import com.iwanickimarcel.freat.navigation.BottomNavigationBar
import com.iwanickimarcel.freat.navigation.Home
import com.iwanickimarcel.freat.navigation.Products
import com.iwanickimarcel.freat.navigation.Recipes
import com.iwanickimarcel.freat.navigation.RecipesSearch
import com.iwanickimarcel.freat.navigation.ScanBill
import com.iwanickimarcel.products.ProductPhoto

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

    state.clickedRecipe?.let {
        navigator.push(Recipes(it.name))
    }

    if (state.isSearchBarClicked) {
        navigator.push(RecipesSearch)
    }

    if (state.isShowAllProductsClicked) {
        navigator.push(Products())
    }

    if (state.isShowAllRecipesClicked) {
        navigator.push(Recipes())
    }

    if (state.isScanBillClicked) {
        navigator.push(ScanBill)
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
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
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
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
                    Spacer(modifier = Modifier.height(28.dp))
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    state.products.takeIf { it.isNotEmpty() }?.let {
                        TriangleShape(
                            width = 32.dp,
                            height = 16.dp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable {
                                viewModel.onEvent(HomeEvent.OnScanBillClick)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(start = 32.dp),
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                modifier = Modifier.width(160.dp),
                                text = "Scan your bill to upload products",
                                fontSize = 20.sp
                            )
                            Button(
                                onClick = {
                                    viewModel.onEvent(HomeEvent.OnScanBillClick)
                                },
                                content = {
                                    Text("Take a picture")
                                }
                            )
                        }
                        Icon(
                            modifier = Modifier
                                .size(142.dp)
                                .padding(end = 22.dp),
                            imageVector = Icons.Outlined.Receipt,
                            contentDescription = "Scan your bill"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                state.recipes.takeIf { it.isNotEmpty() }?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recipes",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        TextButton(
                            modifier = Modifier.padding(top = 4.dp),
                            onClick = {
                                viewModel.onEvent(HomeEvent.OnShowAllRecipesClick)
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
                            .height(110.dp)
                    ) {
                        items(it) {
                            Box(
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(110.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        viewModel.onEvent(HomeEvent.OnRecipeClick(it))
                                    }
                                    .padding(horizontal = 16.dp)
                            ) {
                                RecipePhoto(
                                    recipe = it,
                                    modifier = Modifier.fillMaxSize()
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(
                                            start = 12.dp,
                                            end = 12.dp,
                                            bottom = 8.dp,
                                            top = 4.dp,
                                        ),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        it.tags.firstOrNull()?.let {
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
                                            text = it.name,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = buildString {
                                                append("You have ")
                                                append(it.ownedProductsPercent)
                                                append("% of ingredients")
                                            },
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(Home)
        }
    )
}