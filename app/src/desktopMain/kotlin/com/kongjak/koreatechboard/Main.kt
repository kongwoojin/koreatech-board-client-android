package com.kongjak.koreatechboard

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kongjak.koreatechboard.di.appModule
import com.kongjak.koreatechboard.di.databaseModule
import com.kongjak.koreatechboard.di.networkModule
import com.kongjak.koreatechboard.di.platformModule
import com.kongjak.koreatechboard.di.useCaseModule
import org.koin.compose.KoinApplication

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Koreatech Board") {
        KoinApplication(application = {
            modules(appModule(), useCaseModule(), networkModule(), platformModule(), databaseModule())
        }) {
            App()
        }
    }
}
