package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.core.domain.ImageAnalyzer

actual class ImageAnalyzerModule {
    
    actual val imageAnalyzer: ImageAnalyzer by lazy {
        ImageAnalyzer()
    }
}