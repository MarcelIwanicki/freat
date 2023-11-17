package com.iwanickimarcel.freat.feature.add_product.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.iwanickimarcel.freat.core.presentation.ImagePicker
import com.iwanickimarcel.freat.feature.products.presentation.AddProductPlaceholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel,
    imagePicker: ImagePicker,
    editProductName: String?,
    onDismiss: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    imagePicker.registerPicker {
        viewModel.onEvent(AddProductEvent.OnPhotoSelected(it))
    }

    editProductName?.let {
        LaunchedEffect(Unit) {
            viewModel.onEvent(AddProductEvent.OnEditProductProvided(it))
        }
    }

    if (state.success) {
        onDismiss()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        editProductName?.let {
                            buildString {
                                append("Edit ")
                                append(it)
                            }
                        } ?: "Add product"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
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
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = state.name ?: "",
                        placeholder = {
                            Text(text = "Insert name of the item...")
                        },
                        label = {
                            Text(text = state.nameError ?: "Name")
                        },
                        onValueChange = {
                            viewModel.onEvent(AddProductEvent.OnNameChanged(it))
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(68.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Book,
                                contentDescription = "Name"
                            )
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        isError = state.nameError != null
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.65f)
                                .height(68.dp),
                            value = state.amount?.toString() ?: "",
                            placeholder = {
                                Text(text = "Insert amount...")
                            },
                            label = {
                                Text(text = state.amountError ?: "Amount")
                            },
                            onValueChange = {
                                viewModel.onEvent(AddProductEvent.OnAmountChanged(it))
                            },
                            shape = RoundedCornerShape(20.dp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Numbers,
                                    contentDescription = "Amount"
                                )
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            isError = state.amountError != null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        ExposedDropdownMenuBox(
                            expanded = state.amountUnitMenuExpanded,
                            onExpandedChange = {
                                viewModel.onEvent(AddProductEvent.OnAmountUnitMenuStateChanged)
                            },
                        ) {
                            OutlinedTextField(
                                modifier = Modifier.menuAnchor(),
                                shape = RoundedCornerShape(16.dp),
                                readOnly = true,
                                value = state.amountUnit.abbreviation,
                                onValueChange = {

                                },
                                label = {
                                    Text("Unit")
                                },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.amountUnitMenuExpanded) },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(
                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                ),
                            )
                            ExposedDropdownMenu(
                                expanded = state.amountUnitMenuExpanded,
                                onDismissRequest = {
                                    viewModel.onEvent(AddProductEvent.OnAmountUnitMenuStateChanged)
                                },
                            ) {
                                AddProductViewModel.AMOUNT_UNIT_OPTIONS.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption) },
                                        onClick = {
                                            viewModel.onEvent(
                                                AddProductEvent.OnAmountUnitChanged(
                                                    selectionOption
                                                )
                                            )
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                    )
                                }
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = {
                        viewModel.onEvent(AddProductEvent.OnAddProductClick)
                    }
                ) {
                    Text(
                        editProductName?.let {
                            "Save changes"
                        } ?: "Add product",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
    )
}