package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.feature.scan_bill.domain.ImageAnalyzer

expect class ImageAnalyzerModule {
    val imageAnalyzer: ImageAnalyzer
}