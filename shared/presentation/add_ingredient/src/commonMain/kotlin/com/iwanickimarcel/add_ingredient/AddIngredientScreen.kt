package com.iwanickimarcel.add_ingredient

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
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Close
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.iwanickimarcel.products.Product

private const val TEST_TAG_ADD_INGREDIENT_NAME = "add_ingredient_name"
private const val TEST_TAG_ADD_INGREDIENT_AMOUNT = "add_ingredient_amount"
private const val TEST_TAG_CONFIRM_ADD_INGREDIENT = "confirm_add_ingredient"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientScreen(
    getViewModel: @Composable () -> AddIngredientViewModel,
    onIngredientAdded: (Product) -> Unit,
    onDismiss: () -> Unit,
    editProduct: Product? = null,
) {
    val viewModel = getViewModel()
    val state by viewModel.state.collectAsState()

    editProduct?.let {
        LaunchedEffect(Unit) {
            viewModel.onEvent(AddIngredientEvent.OnEditProductProvided(it))
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
                        editProduct?.let {
                            buildString {
                                append("Edit ")
                                append(state.name ?: "ingredient")
                            }
                        } ?: "Add ingredient"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
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
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = state.name ?: TextFieldValue(""),
                        placeholder = {
                            Text(text = "Insert name of the item...")
                        },
                        label = {
                            Text(text = state.nameError ?: "Name")
                        },
                        onValueChange = {
                            viewModel.onEvent(AddIngredientEvent.OnNameChanged(it))
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(68.dp)
                            .testTag(TEST_TAG_ADD_INGREDIENT_NAME),
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
                                .height(68.dp)
                                .testTag(TEST_TAG_ADD_INGREDIENT_AMOUNT),
                            value = state.amount?.toString() ?: "",
                            placeholder = {
                                Text(text = "Insert amount...")
                            },
                            label = {
                                Text(text = state.amountError ?: "Amount")
                            },
                            onValueChange = {
                                viewModel.onEvent(AddIngredientEvent.OnAmountChanged(it))
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
                                viewModel.onEvent(AddIngredientEvent.OnAmountUnitMenuStateChanged)
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
                                    viewModel.onEvent(AddIngredientEvent.OnAmountUnitMenuStateChanged)
                                },
                            ) {
                                AddIngredientViewModel.AMOUNT_UNIT_OPTIONS.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption) },
                                        onClick = {
                                            viewModel.onEvent(
                                                AddIngredientEvent.OnAmountUnitChanged(
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
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .testTag(TEST_TAG_CONFIRM_ADD_INGREDIENT),
                    onClick = {
                        viewModel.onEvent(AddIngredientEvent.OnAddIngredientClick(onIngredientAdded))
                    }
                ) {
                    Text(
                        if (editProduct != null) {
                            "Save changes"
                        } else {
                            "Confirm adding ingredient"
                        },
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}