package com.iwanickimarcel.freat.di

import android.annotation.SuppressLint

expect class AppModule {
    val imageModule: ImageModule
    val imageAnalyzerModule: ImageAnalyzerModule
    val databaseModule: DatabaseModule
    val dataSourceModule: DataSourceModule
    val viewModelModule: ViewModelModule
}

@SuppressLint("StaticFieldLeak")
lateinit var appModule: AppModule