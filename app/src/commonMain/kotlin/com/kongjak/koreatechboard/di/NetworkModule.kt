package com.kongjak.koreatechboard.di

import android.os.Build
import com.kongjak.koreatechboard.BuildConfig
import com.kongjak.koreatechboard.data.api.API
import com.kongjak.koreatechboard.data.api.APIImpl
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val API_PRODUCTION = "https://api.koreatech.kongjak.com/v3/"
private const val API_DEVELOPMENT = "https://dev.api.koreatech.kongjak.com/v3/"

private val apiUrl = if (BuildConfig.BUILD_TYPE == "release") {
    API_PRODUCTION
} else {
    API_DEVELOPMENT
}

fun provideHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        install(Logging) {
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json)
        }

        install(DefaultRequest) {
            url(apiUrl)
            header("User-Agent", "Koreatech-Board/${BuildConfig.VERSION_NAME} Android/${Build.VERSION.SDK_INT}")
        }
    }
}

fun provideApiService(httpClient: HttpClient): API = APIImpl(httpClient)
