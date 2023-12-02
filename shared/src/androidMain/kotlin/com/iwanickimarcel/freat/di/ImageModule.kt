package com.iwanickimarcel.freat.di

import android.content.Context
import androidx.compose.runtime.Composable
import com.iwanickimarcel.core.ImagePicker
import com.iwanickimarcel.core.ImagePickerFactory
import com.iwanickimarcel.core.ImageStorage

actual class ImageModule(
    private val context: Context
) {

    actual val imageStorage: ImageStorage by lazy {
        ImageStorage(context)
    }

    actual val imagePicker: ImagePicker
        @Composable
        get() = ImagePickerFactory().createPicker()

}