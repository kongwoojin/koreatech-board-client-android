package com.kongjak.koreatechboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.kongjak.koreatechboard.di.appModule
import com.kongjak.koreatechboard.di.databaseModule
import com.kongjak.koreatechboard.di.networkModule
import com.kongjak.koreatechboard.di.platformModule
import com.kongjak.koreatechboard.di.useCaseModule
import dev.datlag.kcef.KCEF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.KoinApplication
import java.io.File
import kotlin.math.max

fun main() = application {
    val windowState = rememberWindowState()

    Window(onCloseRequest = ::exitApplication, state = windowState, title = "Koreatech Board") {
        KoinApplication(application = {
            modules(appModule(), useCaseModule(), networkModule(), platformModule(), databaseModule())
        }) {
            val screenWidth = windowState.size.width.value.dp
            val padding = screenWidth / 16

            var restartRequired by remember { mutableStateOf(false) }
            var downloading by remember { mutableStateOf(0F) }
            var initialized by remember { mutableStateOf(false) }
            val bundleLocation = System.getProperty("compose.application.resources.dir")?.let { File(it) } ?: File(".")

            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    KCEF.init(builder = {
                        installDir(File(bundleLocation, "kcef-bundle"))
                        progress {
                            onDownloading {
                                downloading = max(it, 0F)
                            }
                            onInitialized {
                                initialized = true
                            }
                        }
                        release("jbr-release-17.0.10b1087.23")
                        settings {
                            cachePath = File("cache").absolutePath
                        }
                    }, onError = {
                        it?.printStackTrace()
                    }, onRestartRequired = {
                        restartRequired = true
                    })
                }
            }

            if (restartRequired) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Restart required.")
                }
            } else {
                if (initialized) {
                    App()
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(padding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LinearProgressIndicator(
                            progress = { downloading / 100 },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                text = "Downloading required package..."
                            )
                            Text(
                                text = "${downloading.toInt()}%/100%"
                            )
                        }
                    }
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    KCEF.disposeBlocking()
                }
            }
        }
    }
}
