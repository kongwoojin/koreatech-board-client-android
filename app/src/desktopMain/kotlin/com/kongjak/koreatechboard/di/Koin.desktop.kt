package com.kongjak.koreatechboard.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.kongjak.koreatechboard.data.AppDatabase
import com.kongjak.koreatechboard.data.dao.ArticleDao
import com.kongjak.koreatechboard.data.dao.ArticleDaoImpl
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.prefs.Preferences

actual fun databaseModule(): Module = module {
    single<SqlDriver> {
        JdbcSqliteDriver(url = "jdbc:sqlite:notice.db", schema = AppDatabase.Schema).apply {
            AppDatabase.Schema.create(this)
        }
    }

    single { AppDatabase(get()) }

    single<ArticleDao> { ArticleDaoImpl(get()) }
}

actual fun platformModule() = module {
    single<ObservableSettings> { PreferencesSettings(Preferences.userRoot()) }
}
