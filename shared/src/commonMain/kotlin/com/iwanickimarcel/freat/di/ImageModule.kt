package com.iwanickimarcel.freat.di

import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.core.data.ImageStorage
import com.iwanickimarcel.freat.core.presentation.ImagePicker

expect class ImageModule {
    val imageStorage: ImageStorage

    @get:Composable
    val imagePicker: ImagePicker
}