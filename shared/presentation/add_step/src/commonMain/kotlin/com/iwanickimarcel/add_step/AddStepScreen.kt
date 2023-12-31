package com.iwanickimarcel.add_step

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.iwanickimarcel.recipes.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStepScreen(
    getViewModel: @Composable () -> AddStepViewModel,
    stepsCount: Int,
    onStepAdded: (Recipe.Step) -> Unit,
    onDismiss: () -> Unit,
    editStep: Recipe.Step? = null,
) {
    val viewModel = getViewModel()
    val state by viewModel.state.collectAsState()

    editStep?.let {
        LaunchedEffect(Unit) {
            viewModel.onEvent(AddStepEvent.OnEditStepProvided(it))
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
                        text = editStep?.let {
                            "Edit step"
                        } ?: "Add step"
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
                        value = state.step ?: TextFieldValue(""),
                        singleLine = false,
                        placeholder = {
                            Text(text = "Insert step...")
                        },
                        label = {
                            Text(text = state.stepError ?: "Step")
                        },
                        onValueChange = {
                            viewModel.onEvent(AddStepEvent.OnStepChanged(it))
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(68.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Badge,
                                contentDescription = "Name"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        isError = state.stepError != null
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = {
                        viewModel.onEvent(
                            AddStepEvent.OnAddStepClick(
                                stepsCount = editStep?.let {
                                    it.step - 1
                                } ?: stepsCount,
                                onStepAdded = onStepAdded
                            )
                        )
                    }
                ) {
                    Text(
                        if (editStep != null) {
                            "Save changes"
                        } else {
                            "Add step"
                        },
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}