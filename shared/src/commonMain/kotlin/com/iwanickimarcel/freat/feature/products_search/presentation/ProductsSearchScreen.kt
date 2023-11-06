package com.iwanickimarcel.freat.feature.products_search.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.iwanickimarcel.freat.di.AppModule
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(16.dp),
                title = {},
                actions = {
                    OutlinedTextField(
                        value = "",
                        placeholder = {
                            Text(text = "Search for products...")
                        },
                        onValueChange = {

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

                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

            }
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}