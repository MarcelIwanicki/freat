package com.iwanickimarcel.freat.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun FreatTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)