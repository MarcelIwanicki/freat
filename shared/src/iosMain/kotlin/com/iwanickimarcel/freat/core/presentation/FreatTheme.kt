package com.iwanickimarcel.freat.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.ui.theme.DarkColors
import com.iwanickimarcel.freat.ui.theme.LightColors
import com.iwanickimarcel.freat.ui.theme.Typography

@Composable
actual fun FreatTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
        typography = Typography
    )
}