package com.example.radiofrancestation

import android.app.Application
import com.example.radiofrance.di.diBridgeModule
import com.example.radiofrance.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    diBridgeModule,      // From Data Module
                    presentationModule // From Presentation Module
                )
            )
        }
    }
}