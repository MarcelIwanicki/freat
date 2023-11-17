package com.iwanickimarcel.freat.di

import android.content.Context
import androidx.compose.runtime.Composable
import com.iwanickimarcel.freat.core.data.ImageStorage
import com.iwanickimarcel.freat.core.presentation.ImagePicker
import com.iwanickimarcel.freat.core.presentation.ImagePickerFactory

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