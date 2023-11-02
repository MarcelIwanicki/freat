package com.iwanickimarcel.freat.feature.products.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.AddChart
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.iwanickimarcel.freat.di.AppModule
import com.iwanickimarcel.freat.navigation.AddProduct
import com.iwanickimarcel.freat.navigation.BottomNavigationBar
import com.iwanickimarcel.freat.navigation.Products
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    appModule: AppModule
) {
    val navigator = LocalNavigator.current ?: return
    val viewModel = getViewModel(
        key = "products-screen",
        factory = viewModelFactory {
            ProductsViewModel(appModule.productDataSource)
        }
    )
    val state by viewModel.state.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (state.addProductPressed) {
        navigator.push(
            AddProduct(appModule)
        )
    }

    state.longPressedProduct?.let {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(ProductsEvent.OnProductLongPressDismiss)
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            LongPressBottomSheetContent(state)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    Box(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = "",
                            placeholder = {
                                Text(text = "Search for products...")
                            },
                            onValueChange = { },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = "Search for products"
                                )
                            }
                        )
                    }
                },
                scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
            )

        },
        content = {
            Column {
                Spacer(modifier = Modifier.height(64.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        AddProductPlaceholder(
                            text = "Add product",
                            icon = Icons.Outlined.AddChart,
                            modifier = Modifier.fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            viewModel.onEvent(ProductsEvent.OnAddProductClick)
                        }
                    }

                    item {
                        AddProductPlaceholder(
                            text = "Scan the bill",
                            icon = Icons.Outlined.AddAPhoto,
                            modifier = Modifier.fillMaxWidth()
                                .padding(8.dp)
                        ) {

                        }
                    }

                    if (state.products.isEmpty()) {
                        return@LazyVerticalGrid
                    }

                    item {
                        Text(
                            text = buildString {
                                append("Your products")
                                append(" (")
                                append(state.products.size)
                                append(")")
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    item {
                        Spacer(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    items(state.products) { product ->
                        ProductsItem(
                            product = product,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(ProductsEvent.OnProductLongPress(product))
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(
                Products(appModule),
                appModule
            )
        }
    )


}