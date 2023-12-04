package com.iwanickimarcel.freat.di

import com.iwanickimarcel.core.ImageStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val imageModule = module {
    single {
        ImageStorage(androidContext())
    }
}