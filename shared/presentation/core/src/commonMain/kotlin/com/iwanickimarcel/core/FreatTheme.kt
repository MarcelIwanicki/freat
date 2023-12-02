package com.iwanickimarcel.core

import androidx.compose.runtime.Composable

@Composable
expect fun FreatTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)