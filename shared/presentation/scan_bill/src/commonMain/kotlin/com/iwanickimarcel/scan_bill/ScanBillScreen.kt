package com.iwanickimarcel.scan_bill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.iwanickimarcel.add_ingredient.AddIngredientScreen
import com.iwanickimarcel.add_ingredient.AddIngredientViewModel
import com.iwanickimarcel.add_recipe.IngredientsScreen
import com.iwanickimarcel.core.AddProductPlaceholder
import com.iwanickimarcel.core.ImagePicker
import com.iwanickimarcel.core.rememberBitmapFromBytes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanBillScreen(
    getViewModel: @Composable () -> ScanBillViewModel,
    getAddIngredientViewModel: @Composable () -> AddIngredientViewModel,
    imagePicker: ImagePicker
) {
    val viewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    val navigator = LocalNavigator.current ?: return

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    imagePicker.registerPicker {
        viewModel.onEvent(ScanBillEvent.OnPhotoSelected(it))
    }

    state.photoBytes?.let {
        val bitmap = rememberBitmapFromBytes(it)
        LaunchedEffect(Unit) {
            viewModel.onEvent(
                ScanBillEvent.OnImageAnalysisRequested(
                    bitmap ?: return@LaunchedEffect
                )
            )
        }
    }

    state.editProduct?.let {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(ScanBillEvent.OnEditProductDismiss)
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            AddIngredientScreen(
                editProduct = it,
                getViewModel = getAddIngredientViewModel,
                onIngredientAdded = {
                    viewModel.onEvent(ScanBillEvent.OnProductEdited(it))
                },
                onDismiss = {
                    viewModel.onEvent(ScanBillEvent.OnEditProductDismiss)
                },
            )
        }
    }

    if (state.success) {
        LaunchedEffect(Unit) {
            navigator.pop()
        }
    }

    if (state.isAddProductOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(ScanBillEvent.OnAddProductDismiss)
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            AddIngredientScreen(
                getViewModel = getAddIngredientViewModel,
                onIngredientAdded = {
                    viewModel.onEvent(ScanBillEvent.OnProductAdded(it))
                },
                onDismiss = {
                    viewModel.onEvent(ScanBillEvent.OnAddProductDismiss)
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Scan the bill to add products")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.pop() }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIos,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                AddProductPlaceholder(
                    text = if (state.photoBytes == null) {
                        "Add a photo"
                    } else {
                        "Edit photo"
                    },
                    icon = Icons.Outlined.AddAPhoto,
                    photoBytes = state.photoBytes,
                    modifier = Modifier.fillMaxWidth()
                        .padding(8.dp)
                ) {
                    imagePicker.pickImage()
                }

                IngredientsScreen(
                    ingredients = state.products,
                    onEditIngredientPressed = {
                        viewModel.onEvent(ScanBillEvent.OnEditProductPress(it))
                    },
                    onAddIngredientPressed = {
                        viewModel.onEvent(ScanBillEvent.OnAddProductPress)
                    },
                    onDeleteIngredientPressed = {
                        viewModel.onEvent(ScanBillEvent.OnDeleteProductPress(it))
                    },
                    onConfirmClick = {
                        viewModel.onEvent(ScanBillEvent.OnAddProductsConfirm)
                    },
                    confirmButtonText = "Confirm adding products",
                    confirmContainerColor = MaterialTheme.colorScheme.primary,
                    confirmTextColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}