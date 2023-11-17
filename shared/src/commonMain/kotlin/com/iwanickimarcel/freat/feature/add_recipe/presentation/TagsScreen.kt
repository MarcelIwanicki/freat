package com.iwanickimarcel.freat.feature.add_recipe.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

fun interface OnTagAdded {
    operator fun invoke(name: String)
}

fun interface OnTagRemoved {
    operator fun invoke(index: Int)
}

fun interface OnTextFieldValueChanged {
    operator fun invoke(value: TextFieldValue)
}

@Composable
fun TagsScreen(
    addRecipeState: AddRecipeState,
    onTagAdded: OnTagAdded,
    onTagRemoved: OnTagRemoved,
    onTextFieldValueChanged: OnTextFieldValueChanged,
    onAddRecipeConfirmed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val focusRequester = remember { FocusRequester() }
        val interaction = remember { MutableInteractionSource() }
        val rowInteraction = remember { MutableInteractionSource() }

        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
        ) {
            TagTextField(
                textFieldValue = addRecipeState.tagsTextFieldValue,
                onValueChanged = {
                    if (it.text.isEmpty()) {
                        return@TagTextField
                    }

                    val values = it.text.split(" ", "\n")

                    onTextFieldValueChanged(
                        value = if (values.size >= 2) {
                            onTagAdded(values[0])
                            addRecipeState.tagsTextFieldValue.copy(text = "")
                        } else {
                            it
                        }
                    )
                },
                focusRequester = focusRequester,
                textFieldInteraction = interaction,
                label = null,
                placeholder = "Insert tags here...",
                rowInteraction = rowInteraction,
                listOfChips = addRecipeState.tags.map { it.name },
                modifier = Modifier
                    .onKeyEvent {
                        if (it.key.keyCode == Key.Backspace.keyCode && addRecipeState.tagsTextFieldValue.text.isEmpty()) {
                            if (addRecipeState.tags.isNotEmpty()) {
                                onTagRemoved(
                                    index = addRecipeState.tags.lastIndex
                                )
                            }
                        }
                        false
                    },
                onChipClick = { chipIndex ->
                    onTagRemoved(
                        index = chipIndex
                    )
                }
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary),
            onClick = {
                onAddRecipeConfirmed()
            }
        ) {
            Text(
                "Add recipe",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TagTextField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    textFieldInteraction: MutableInteractionSource,
    label: String?,
    placeholder: String,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Default
    ),
    rowInteraction: MutableInteractionSource,
    listOfChips: List<String> = emptyList(),
    onChipClick: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                vertical = 10.dp,
                horizontal = 20.dp
            )
            .clickable(
                indication = null,
                interactionSource = rowInteraction,
                onClick = {
                    focusRequester.requestFocus()
                    keyboardManager?.show()
                }
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center
            ) {

                if (label != null) {
                    Text(
                        text = "$label:",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                TextFieldContent(
                    textFieldValue = textFieldValue,
                    placeholder = placeholder,
                    onValueChanged = onValueChanged,
                    focusRequester = focusRequester,
                    textFieldInteraction = textFieldInteraction,
                    readOnly = readOnly,
                    keyboardOptions = keyboardOptions,
                    focusManager = focusManager,
                    listOfChips = listOfChips,
                    modifier = modifier,
                    onChipClick = onChipClick
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TextFieldContent(
    textFieldValue: TextFieldValue,
    placeholder: String,
    onValueChanged: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    textFieldInteraction: MutableInteractionSource,
    readOnly: Boolean,
    keyboardOptions: KeyboardOptions,
    focusManager: FocusManager,
    listOfChips: List<String>,
    modifier: Modifier,
    onChipClick: (Int) -> Unit
) {
    Box {

        if (textFieldValue.text.isEmpty() && listOfChips.isEmpty()) {
            Text(
                text = placeholder,
                modifier = Modifier.align(alignment = Alignment.CenterStart)
            )
        }

        FlowRow(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            repeat(times = listOfChips.size) { index ->
                InputChip(
                    onClick = { onChipClick(index) },
                    modifier = Modifier.wrapContentWidth(),
                    label = {
                        Text(text = listOfChips[index])
                    },
                    selected = false,
                    trailingIcon = {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .padding(3.dp)
                        ) {
                            Icon(
                                painter = rememberVectorPainter(image = Icons.Filled.Close),
                                contentDescription = null,
                                modifier = Modifier.size(12.dp),
                            )
                        }
                    }
                )
            }

            BasicTextField(
                value = textFieldValue,
                onValueChange = onValueChanged,
                modifier = modifier
                    .focusRequester(focusRequester)
                    .width(IntrinsicSize.Min),
                singleLine = false,
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .defaultMinSize(minHeight = 48.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier.wrapContentWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                modifier = Modifier
                                    .defaultMinSize(minWidth = 4.dp)
                                    .wrapContentWidth(),
                            ) {
                                innerTextField()
                            }
                        }
                    }
                },
                interactionSource = textFieldInteraction,
                readOnly = readOnly,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
        }
    }
}