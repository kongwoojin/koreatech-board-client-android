package com.kongjak.koreatechboard

import android.app.Application
import com.kongjak.koreatechboard.di.appModule
import com.kongjak.koreatechboard.di.databaseModule
import com.kongjak.koreatechboard.di.networkModule
import com.kongjak.koreatechboard.di.platformModule
import com.kongjak.koreatechboard.di.useCaseModule
import com.kongjak.koreatechboard.di.viewModelModule
import com.kongjak.koreatechboard.util.ContextUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ContextUtil.setContext(applicationContext)

        startKoin {
            androidContext(this@MainApplication)
            modules(appModule(), useCaseModule(), networkModule(), databaseModule(), platformModule(), viewModelModule())
        }
    }
}
