package com.iwanickimarcel.freat.feature.products.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LongPressBottomSheet(
    productsState: ProductsState,
    scaffoldState: BottomSheetScaffoldState,
    content: @Composable BoxScope.() -> Unit
) {
    val deleteProductText = buildString {
        append("Delete ")
        append(productsState.longPressedProduct?.name ?: "product")
    }

    val editProductText = buildString {
        append("Edit ")
        append(productsState.longPressedProduct?.name ?: "product")
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(
                            horizontal = 32.dp,
                            vertical = 16.dp
                        ),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = deleteProductText
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(deleteProductText)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(
                            horizontal = 32.dp,
                            vertical = 16.dp
                        ),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = editProductText
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(editProductText)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(
                            horizontal = 32.dp,
                            vertical = 16.dp
                        ),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search for recipes"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Search for recipes")
                }
            }
            Spacer(modifier = Modifier.height(86.dp))
        }) { innerPadding -> Box(modifier = Modifier.padding(innerPadding), content = content) }
}