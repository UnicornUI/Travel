package com.edu.travel

import android.app.Application
import com.edu.travel.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TravelApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TravelApplication)
            modules(appModule)
        }
    }
}