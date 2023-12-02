package com.iwanickimarcel.core

import androidx.compose.runtime.Composable

expect class ImagePickerFactory {
    @Composable
    fun createPicker(): ImagePicker
}