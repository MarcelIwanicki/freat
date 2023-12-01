package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.feature.scan_bill.domain.ImageAnalyzer

actual class ImageAnalyzerModule {

    actual val imageAnalyzer: ImageAnalyzer by lazy {
        ImageAnalyzer()
    }
}