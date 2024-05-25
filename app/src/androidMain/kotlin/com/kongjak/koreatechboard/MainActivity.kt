package com.kongjak.koreatechboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.setCustomKeys
import com.google.firebase.ktx.Firebase
import com.kongjak.koreatechboard.di.appModule
import com.kongjak.koreatechboard.di.databaseModule
import com.kongjak.koreatechboard.di.networkModule
import com.kongjak.koreatechboard.di.platformModule
import com.kongjak.koreatechboard.di.useCaseModule
import com.kongjak.koreatechboard.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val crashlytics = Firebase.crashlytics
        crashlytics.setCustomKeys {
            key("build_type", BuildConfig.BUILD_TYPE)
        }

        setContent {
            KoinApplication(application = {
                modules(
                    appModule(),
                    useCaseModule(),
                    networkModule(),
                    platformModule(),
                    databaseModule(),
                    viewModelModule(),
                    module { androidContext(this@MainActivity) })
            }) {
                App()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}
