package com.kongjak.koreatechboard.data.api

import com.kongjak.koreatechboard.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilder {

    private const val API_PRODUCTION = "https://api.kongjak.com/v3/"
    private const val API_DEVELOPMENT = "https://dev.api.kongjak.com/v3/"

    private val apiUrl = if (BuildConfig.BUILD_TYPE == "release") {
        API_PRODUCTION
    } else {
        API_DEVELOPMENT
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return if (BuildConfig.BUILD_TYPE == "release") {
            OkHttpClient.Builder()
                .build()
        } else {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }
}
