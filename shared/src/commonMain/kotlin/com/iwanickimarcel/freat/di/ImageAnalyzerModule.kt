package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.core.domain.ImageAnalyzer

expect class ImageAnalyzerModule {
    val imageAnalyzer: ImageAnalyzer
}