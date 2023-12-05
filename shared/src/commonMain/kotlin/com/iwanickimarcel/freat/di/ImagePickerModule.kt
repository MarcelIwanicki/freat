package com.iwanickimarcel.freat.di

import androidx.compose.runtime.Composable
import com.iwanickimarcel.core.ImagePicker
import com.iwanickimarcel.core.ImagePickerFactory

val imagePicker: ImagePicker
    @Composable
    get() = ImagePickerFactory().createPicker()
