package com.example.wonhoi_cleanarchitecture_shoppingmall_app05

import android.app.Application
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class shoppingmall_app05Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@shoppingmall_app05Application)
            modules(appModule)
        }

    }

}