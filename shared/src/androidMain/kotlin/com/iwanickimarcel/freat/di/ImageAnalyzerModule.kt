package com.iwanickimarcel.freat.di

import com.iwanickimarcel.scan_bill.ImageAnalyzer
import org.koin.dsl.module

actual val imageAnalyzerModule = module {
    single {
        ImageAnalyzer()
    }
}