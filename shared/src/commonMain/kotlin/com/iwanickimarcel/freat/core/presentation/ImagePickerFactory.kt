package com.iwanickimarcel.freat.core.presentation

import androidx.compose.runtime.Composable

expect class ImagePickerFactory {
    @Composable
    fun createPicker(): ImagePicker
}