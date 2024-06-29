package com.kongjak.koreatechboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.setCustomKeys
import com.google.firebase.ktx.Firebase
import com.kongjak.koreatechboard.util.routes.MainRoute
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val crashlytics = Firebase.crashlytics
        crashlytics.setCustomKeys {
            key("build_type", BuildConfig.BUILD_TYPE)
        }

        if (BuildConfig.DEBUG) {
            crashlytics.setCrashlyticsCollectionEnabled(false)
        } else {
            crashlytics.setCrashlyticsCollectionEnabled(true)
        }

        Napier.base(DebugAntilog())

        val defaultScreen = intent.getStringExtra("screen")

        setContent {
            App(
                startDestination = if (defaultScreen != null) {
                    MainRoute.valueOf(defaultScreen).name
                } else {
                    MainRoute.Home.name
                }
            )
        }
    }
}
