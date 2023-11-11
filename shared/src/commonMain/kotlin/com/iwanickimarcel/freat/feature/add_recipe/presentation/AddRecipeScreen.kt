package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.iwanickimarcel.freat.core.presentation.ImagePicker
import com.iwanickimarcel.freat.di.AppModule
import com.iwanickimarcel.freat.feature.products.presentation.AddProductPlaceholder

data class PagerScreenItem(
    val index: Int,
    val title: String,
    val content: @Composable () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddRecipeScreen(
    appModule: AppModule,
    editRecipeId: Int?
) {
    val navigator = LocalNavigator.current ?: return
    val imagePicker = appModule.imagePicker

    val pagerScreens = remember {
        listOf(
            PagerScreenItem(
                index = 0,
                title = "Basic info",
                content = {
                    BasicInfoScreen(imagePicker = imagePicker)
                }
            ),
            PagerScreenItem(
                index = 1,
                title = "Ingredients",
                content = {
                    BasicInfoScreen(imagePicker = imagePicker)
                }
            ),
            PagerScreenItem(
                index = 2,
                title = "Steps",
                content = {
                    BasicInfoScreen(imagePicker = imagePicker)
                }
            ),
            PagerScreenItem(
                index = 3,
                title = "Tags",
                content = {
                    BasicInfoScreen(imagePicker = imagePicker)
                }
            ),
        )
    }

    val pagerState = rememberPagerState(
        pageCount = { pagerScreens.size }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        editRecipeId?.let {
                            "Edit recipe"
                        } ?: "Add recipe"
                    )
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
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val isCurrentPage = pagerState.currentPage == iteration
                        val isChecked = iteration < pagerState.currentPage

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            ElevatedFilterChip(
                                selected = isCurrentPage || isChecked,
                                onClick = { },
                                label = {
                                    if (isChecked) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = "Step checked"
                                        )
                                    } else {
                                        Text((iteration + 1).toString())
                                    }
                                },
                                shape = RoundedCornerShape(32.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )

                            if (isCurrentPage) {
                                Text(pagerScreens[iteration].title)
                            }
                        }
                    }
                }

                Box(
                    Modifier
                        .fillMaxWidth(
                            when (pagerState.currentPage) {
                                0 -> 0.4f
                                pagerState.pageCount - 1 -> 1f
                                else -> 1.15f - (1f / (pagerState.currentPage + 1))
                            }
                        )
                        .height(2.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val pagerScreen = pagerScreens.find {
                        it.index == page
                    }
                    pagerScreen?.content?.invoke()
                }
            }
        }
    )
}

@Composable
fun BasicInfoScreen(
    imagePicker: ImagePicker
) {
    val fakeNameError: String? = null
    val fakeName: String? = null
    val fakePhotoBytes: ByteArray? = null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(16.dp))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddProductPlaceholder(
                text = if (fakePhotoBytes == null) {
                    "Add a photo"
                } else {
                    "Edit photo"
                },
                icon = Icons.Outlined.AddAPhoto,
                photoBytes = fakePhotoBytes,
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            ) {
                imagePicker.pickImage()
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = fakeName ?: "",
                placeholder = {
                    Text(text = "Insert name of the item...")
                },
                label = {
                    Text(text = fakeNameError ?: "Name")
                },
                onValueChange = {
//                    viewModel.onEvent(AddProductEvent.OnNameChanged(it))
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
                isError = fakeNameError != null
            )
        }
    }
}