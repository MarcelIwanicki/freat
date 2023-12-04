package com.iwanickimarcel.freat.di

import com.iwanickimarcel.freat.feature.scan_bill.domain.ImageAnalyzer
import org.koin.dsl.module

actual val imageAnalyzerModule = module {
    single {
        ImageAnalyzer()
    }
}