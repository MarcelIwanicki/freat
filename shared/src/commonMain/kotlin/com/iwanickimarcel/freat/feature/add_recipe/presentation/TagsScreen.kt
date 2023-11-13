package com.iwanickimarcel.freat.feature.add_recipe.presentation

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun TagsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var text by remember { mutableStateOf("") }
        var chipItems by remember { mutableStateOf(emptyList<String>()) }
        var textFieldHeight by remember { mutableIntStateOf(0) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ChipList(chipItems) { height ->
                textFieldHeight = height
            }
            OutlinedTextField(
                value = TextFieldValue(
                    text = text,
                    selection = TextRange(text.length)
                ),
                onValueChange = {
                    text = it.text
                },
                label = { Text("Type tag name and press Enter") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),

                keyboardActions = KeyboardActions(
                    onDone = {
                        if (text.isNotBlank()) {
                            chipItems = chipItems + text
                            text = ""
                        }
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(
                        textFieldHeight.takeIf { it > 0 }?.let {
                            it + 16
                        }?.dp ?: 64.dp
                    )
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipList(chipItems: List<String>, onHeightMeasured: (Int) -> Unit) {
    val context = LocalContext.current

    FlowRow(
        Modifier
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                bottom = 8.dp,
                start = 8.dp,
                end = 8.dp
            )
            .wrapContentHeight(align = Alignment.Top)
            .onSizeChanged {
                onHeightMeasured(it.height.toDp(context))
            },
        horizontalArrangement = Arrangement.Start,
    ) {
        chipItems.forEach {
            AssistChip(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .align(alignment = Alignment.CenterVertically),
                onClick = {

                },
                label = { Text(it) }
            )
        }
    }
}

fun Int.toDp(context: Context): Int {
    return (this / context.resources.displayMetrics.density).toInt()
}

