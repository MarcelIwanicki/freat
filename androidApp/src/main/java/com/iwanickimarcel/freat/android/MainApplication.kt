package com.iwanickimarcel.freat.android

import android.app.Application
import com.iwanickimarcel.freat.di.dataSourceModule
import com.iwanickimarcel.freat.di.databaseModule
import com.iwanickimarcel.freat.di.imageAnalyzerModule
import com.iwanickimarcel.freat.di.imageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                imageModule,
                imageAnalyzerModule,
                databaseModule,
                dataSourceModule
            )
        }
    }
}