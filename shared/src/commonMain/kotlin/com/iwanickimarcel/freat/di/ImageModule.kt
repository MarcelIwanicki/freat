package com.iwanickimarcel.freat.di

import androidx.compose.runtime.Composable
import com.iwanickimarcel.core.ImagePicker
import com.iwanickimarcel.core.ImageStorage

expect class ImageModule {
    val imageStorage: ImageStorage

    @get:Composable
    val imagePicker: ImagePicker
}