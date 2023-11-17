package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.iwanickimarcel.freat.core.presentation.ImagePicker
import com.iwanickimarcel.freat.feature.products.presentation.AddProductPlaceholder

@Composable
fun BasicInfoScreen(
    imagePicker: ImagePicker,
    addRecipeState: AddRecipeState,
    onNameChanged: (String) -> Unit,
    onNextClick: () -> Unit
) {
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
                text = if (addRecipeState.photoBytes == null) {
                    "Add a photo"
                } else {
                    "Edit photo"
                },
                icon = Icons.Outlined.AddAPhoto,
                photoBytes = addRecipeState.photoBytes,
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            ) {
                imagePicker.pickImage()
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = addRecipeState.name ?: "",
                placeholder = {
                    Text(text = "Insert name of the item...")
                },
                label = {
                    Text(text = addRecipeState.nameError ?: "Name")
                },
                onValueChange = {
                    onNameChanged(it)
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
                isError = addRecipeState.nameError != null
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                onNextClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Next",
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}