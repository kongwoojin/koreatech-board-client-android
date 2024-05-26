package com.kongjak.koreatechboard.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.kongjak.koreatechboard.data.AppDatabase
import com.kongjak.koreatechboard.data.dao.ArticleDao
import com.kongjak.koreatechboard.data.dao.ArticleDaoImpl
import com.kongjak.koreatechboard.service.FCMService
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun databaseModule() = module {
    single<SqlDriver> {
        AndroidSqliteDriver(AppDatabase.Schema, androidContext(), "notice.db")
    }

    single { AppDatabase(get()) }

    single<ArticleDao> { ArticleDaoImpl(get()) }
}

actual fun platformModule() = module {
    single { createObservableSettings(get()) }
    single { FCMService() }
}

private fun createObservableSettings(context: Context): ObservableSettings {
    return SharedPreferencesSettings(context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE))
}
