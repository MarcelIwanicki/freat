package com.iwanickimarcel.home

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
import cafe.adriel.voyager.navigator.Navigator
import com.iwanickimarcel.core.NavigationBarFactory
import com.iwanickimarcel.core.TriangleShape
import com.iwanickimarcel.core.shimmerEffect
import com.iwanickimarcel.products.ProductPhoto
import com.iwanickimarcel.recipes.RecipePhoto

fun interface ProductsNavigation {
    operator fun invoke(navigator: Navigator, searchQuery: String?)
}

fun interface RecipesNavigation {
    operator fun invoke(navigator: Navigator, searchQuery: String?)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    getViewModel: @Composable () -> HomeViewModel,
    navigationBarFactory: NavigationBarFactory,
    navigateToProducts: ProductsNavigation,
    navigateToRecipes: RecipesNavigation,
    navigateToRecipesSearch: (Navigator) -> Unit,
    navigateToScanBill: (Navigator) -> Unit,
) {
    val viewModel = getViewModel()
    val navigator = LocalNavigator.current ?: return
    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    state.clickedProduct?.let {
        navigateToProducts(
            navigator = navigator,
            searchQuery = it.name
        )
    }

    state.clickedRecipe?.let {
        navigateToRecipes(
            navigator = navigator,
            searchQuery = it.name
        )
    }

    if (state.isSearchBarClicked) {
        navigateToRecipesSearch(navigator)
    }

    if (state.isShowAllProductsClicked) {
        navigateToProducts(
            navigator = navigator,
            searchQuery = null
        )
    }

    if (state.isShowAllRecipesClicked) {
        navigateToRecipes(
            navigator = navigator,
            searchQuery = null
        )
    }

    if (state.isScanBillClicked) {
        navigateToScanBill(navigator)
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

                if (state.loading) {
                    ProductsShimmerLoading(viewModel)
                } else {
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

                Spacer(modifier = Modifier.height(28.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TriangleShape(
                        width = 32.dp,
                        height = 16.dp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
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

                if (state.loading) {
                    RecipesShimmerLoading(viewModel)
                } else {
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
                                                    viewModel.onEvent(HomeEvent.OnFavoriteClick(it))
                                                },
                                                content = {
                                                    Icon(
                                                        imageVector = Icons.Outlined.Favorite,
                                                        contentDescription = "Favorite",
                                                        tint = if (it.isFavorite) {
                                                            MaterialTheme.colorScheme.error
                                                        } else {
                                                            MaterialTheme.colorScheme.onBackground
                                                        }
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
            }
        },
        bottomBar = {
            with(navigationBarFactory) {
                NavigationBar()
            }
        }
    )
}

@Composable
private fun RecipesShimmerLoading(
    viewModel: HomeViewModel
) {
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
    ) {
        repeat(5) {

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(110.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .padding(horizontal = 16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
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
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .width(64.dp)
                                .height(32.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .shimmerEffect()
                        )

                        IconButton(
                            onClick = {},
                            content = {
                                Icon(
                                    imageVector = Icons.Outlined.Favorite,
                                    contentDescription = "Favorite",
                                )
                            }
                        )
                    }

                    Column {
                        Box(
                            modifier = Modifier
                                .width(64.dp)
                                .height(18.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .shimmerEffect()
                        )
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .width(110.dp)
                                .height(12.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductsShimmerLoading(
    viewModel: HomeViewModel
) {
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
    ) {
        repeat(5) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(64.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}