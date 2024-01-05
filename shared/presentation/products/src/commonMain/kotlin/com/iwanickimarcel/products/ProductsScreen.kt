package com.iwanickimarcel.products

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.iwanickimarcel.add_product.AddProductScreen
import com.iwanickimarcel.add_product.AddProductViewModel
import com.iwanickimarcel.core.AddProductPlaceholder
import com.iwanickimarcel.core.ImagePicker
import com.iwanickimarcel.core.NavigationBarFactory

private const val TEST_TAG_ADD_PRODUCT = "add_product"
private const val TEST_TAG_SCAN_THE_BILL = "scan_the_bill"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductsScreen(
    navigator: Navigator? = LocalNavigator.current,
    getViewModel: @Composable () -> ProductsViewModel,
    getAddProductViewModel: @Composable () -> AddProductViewModel,
    imagePicker: ImagePicker,
    navigationBarFactory: NavigationBarFactory,
    navigateToProductsSearch: (Navigator) -> Unit,
    navigateToScanBill: (Navigator) -> Unit,
    searchQuery: String?,
) {
    val viewModel = getViewModel()
    val state by viewModel.state.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    searchQuery?.let {
        LaunchedEffect(Unit) {
            viewModel.onEvent(ProductsEvent.OnSearchQuery(it))
        }
    }

    if (state.addProductOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(ProductsEvent.OnAddProductDismiss)
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            AddProductScreen(
                viewModel = getAddProductViewModel(),
                imagePicker = imagePicker,
                editProductName = null,
                onDismiss = {
                    viewModel.onEvent(ProductsEvent.OnAddProductDismiss)
                }
            )
        }
    }

    if (state.searchBarPressed) {
        navigator?.let(navigateToProductsSearch)
    }

    if (state.isScanBillClicked) {
        navigator?.let(navigateToScanBill)
    }

    state.productToDelete?.let {
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(ProductsEvent.OnDeleteProductMenuDismiss)
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
                        viewModel.onEvent(ProductsEvent.OnDeleteProductConfirm)
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(ProductsEvent.OnDeleteProductMenuDismiss)
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    state.productToEdit?.let {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(ProductsEvent.OnEditProductDismiss)
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            AddProductScreen(
                viewModel = getAddProductViewModel(),
                imagePicker = imagePicker,
                editProductName = it.name,
                onDismiss = {
                    viewModel.onEvent(ProductsEvent.OnEditProductDismiss)
                }
            )
        }
    }

    state.longPressedProduct?.let {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(ProductsEvent.OnProductLongPressDismiss)
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            LongPressBottomSheetContent(
                productsState = state,
                onDeleteProductPressed = {
                    viewModel.onEvent(
                        ProductsEvent.OnDeleteProductPress(
                            state.longPressedProduct ?: return@LongPressBottomSheetContent
                        )
                    )
                },
                onEditProductPressed = {
                    viewModel.onEvent(
                        ProductsEvent.OnEditProductPress(
                            state.longPressedProduct ?: return@LongPressBottomSheetContent
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
                            Text(text = "Search for products...")
                        },
                        onValueChange = { },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(ProductsEvent.OnSearchBarClick)
                            },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "Search for products"
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
                modifier = Modifier.padding(
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    repeat(2) {
                        item {
                            Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding() / 1.5f))
                        }
                    }

                    item {
                        AddProductPlaceholder(
                            text = "Add product",
                            icon = Icons.Outlined.AddChart,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .testTag(TEST_TAG_ADD_PRODUCT)
                        ) {
                            viewModel.onEvent(ProductsEvent.OnAddProductClick)
                        }
                    }

                    item {
                        AddProductPlaceholder(
                            text = "Scan the bill",
                            icon = Icons.Outlined.AddAPhoto,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .testTag(TEST_TAG_SCAN_THE_BILL)
                        ) {
                            viewModel.onEvent(ProductsEvent.OnScanBillClick)
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
                                .padding(horizontal = 16.dp),
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
                                .combinedClickable(
                                    onClick = {
                                        viewModel.onEvent(ProductsEvent.OnEditProductPress(product))
                                    },
                                    onLongClick = {
                                        viewModel.onEvent(ProductsEvent.OnProductLongPress(product))
                                    }
                                )
                                .padding(8.dp)
                        )
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