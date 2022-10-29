package com.example

import android.app.Application
import com.example.mynote.di.networkModule
import com.example.mynote.di.viewModelModule
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@HiltAndroidApp
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val modules = listOf(networkModule, viewModelModule)

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            androidFileProperties()
            koin.loadModules(modules)
        }
    }
}