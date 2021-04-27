package com.example.photodemo

import android.app.Application
import com.example.photodemo.di.module.appModule
import com.example.photodemo.di.module.repoModule
import com.example.photodemo.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PhotoDemoApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level=Level.DEBUG)
            androidContext(this@PhotoDemoApplication)
            modules(listOf(appModule, viewModelModule, repoModule))
        }
    }
}