package com.iwanickimarcel.freat.di

import androidx.compose.runtime.Composable
import com.iwanickimarcel.core.ImageStorage
import com.iwanickimarcel.freat.core.presentation.ImagePicker

expect class ImageModule {
    val imageStorage: ImageStorage

    @get:Composable
    val imagePicker: ImagePicker
}