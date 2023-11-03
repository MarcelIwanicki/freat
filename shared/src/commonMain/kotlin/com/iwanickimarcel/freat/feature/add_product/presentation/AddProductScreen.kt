package com.iwanickimarcel.freat.feature.add_product.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.iwanickimarcel.freat.di.AppModule
import com.iwanickimarcel.freat.feature.products.presentation.AddProductPlaceholder
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddProductScreen(
    appModule: AppModule
) {
    val navigator = LocalNavigator.current ?: return

    val viewModel = getViewModel(
        key = "add-product-screen",
        factory = viewModelFactory {
            AddProductViewModel(appModule.productDataSource)
        }
    )

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add product")
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
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(64.dp))
                    AddProductPlaceholder(
                        text = "Add a photo",
                        icon = Icons.Outlined.AddAPhoto,
                        modifier = Modifier.fillMaxWidth()
                            .padding(8.dp)
                    ) {

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
                        modifier = Modifier.fillMaxWidth(),
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
                    OutlinedTextField(
                        value = state.amount?.toString() ?: "",
                        placeholder = {
                            Text(text = "Insert amount of the item...")
                        },
                        label = {
                            Text(text = state.amountError ?: "Amount")
                        },
                        onValueChange = {
                            viewModel.onEvent(AddProductEvent.OnAmountChanged(it))
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth(),
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
                }
                Button(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    onClick = {
                        viewModel.onEvent(AddProductEvent.OnAddProductClick)
                    }
                ) {
                    Text("Add product")
                }
            }
        },
    )
}