package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.iwanickimarcel.freat.core.presentation.ImagePicker
import com.iwanickimarcel.freat.feature.add_ingredient.presentation.AddIngredientScreen
import com.iwanickimarcel.freat.feature.add_step.presentation.AddStepScreen
import kotlinx.coroutines.launch

data class PagerScreenItem(
    val index: Int,
    val title: String,
    val content: @Composable () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddRecipeScreen(
    viewModel: AddRecipeViewModel,
    imagePicker: ImagePicker,
    editRecipeId: Long?,
) {
    val navigator = LocalNavigator.current ?: return
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val pagerState = rememberPagerState(
        pageCount = { 4 }
    )

    val scope = rememberCoroutineScope()

    imagePicker.registerPicker {
        viewModel.onEvent(AddRecipeEvent.OnPhotoSelected(it))
    }

    val pagerScreens = remember {
        listOf(
            PagerScreenItem(
                index = 0,
                title = "Basic info",
                content = {
                    BasicInfoScreen(
                        imagePicker = imagePicker,
                        addRecipeState = state,
                        onNameChanged = {
                            viewModel.onEvent(AddRecipeEvent.OnNameChanged(it))
                        },
                        onNextClick = {
                            scope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    )
                }
            ),
            PagerScreenItem(
                index = 1,
                title = "Ingredients",
                content = {
                    IngredientsScreen(
                        addRecipeState = state,
                        onAddIngredientPressed = {
                            viewModel.onEvent(AddRecipeEvent.OnAddIngredientPress)
                        },
                        onNextClick = {
                            scope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    )
                }
            ),
            PagerScreenItem(
                index = 2,
                title = "Steps",
                content = {
                    StepsScreen(
                        addRecipeState = state,
                        onAddStepPressed = {
                            viewModel.onEvent(AddRecipeEvent.OnAddStepPress)
                        },
                        onNextClick = {
                            scope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    )
                }
            ),
            PagerScreenItem(
                index = 3,
                title = "Tags",
                content = {
                    TagsScreen(
                        addRecipeState = state,
                        onTagAdded = {
                            viewModel.onEvent(AddRecipeEvent.OnTagAdded(it))
                        },
                        onTagRemoved = {
                            viewModel.onEvent(AddRecipeEvent.OnTagRemoved(it))
                        },
                        onTextFieldValueChanged = {
                            viewModel.onEvent(AddRecipeEvent.OnTagTextFieldValueChanged(it))
                        },
                        onAddRecipeConfirmed = {
                            viewModel.onEvent(AddRecipeEvent.OnAddRecipeConfirm)
                        }
                    )
                }
            ),
        )
    }

    if (state.addIngredientOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(AddRecipeEvent.OnAddIngredientDismiss)
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            AddIngredientScreen(
                onIngredientAdded = {
                    viewModel.onEvent(AddRecipeEvent.OnIngredientAdded(it))
                },
                onDismiss = {
                    viewModel.onEvent(AddRecipeEvent.OnAddIngredientDismiss)
                }
            )
        }
    }

    if (state.success) {
        LaunchedEffect(Unit) {
            navigator.pop()
        }
    }

    if (state.addStepOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(AddRecipeEvent.OnAddStepDismiss)
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            AddStepScreen(
                stepsCount = state.steps.size,
                onStepAdded = {
                    viewModel.onEvent(AddRecipeEvent.OnStepAdded(it))
                },
                onDismiss = {
                    viewModel.onEvent(AddRecipeEvent.OnAddStepDismiss)
                }
            )
        }
    }

    editRecipeId?.let {
        LaunchedEffect(Unit) {
            viewModel.onEvent(AddRecipeEvent.OnEditRecipeProvided(it))
        }
    }

    state.finalErrorMessage?.let {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.showSnackbar(it)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                                onClick = {
                                    scope.launch {
                                        pagerState.scrollToPage(iteration)
                                    }
                                },
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