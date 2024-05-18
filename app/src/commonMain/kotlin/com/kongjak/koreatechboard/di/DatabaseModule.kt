package com.kongjak.koreatechboard.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.kongjak.koreatechboard.data.AppDatabase
import com.kongjak.koreatechboard.data.dao.ArticleDao
import com.kongjak.koreatechboard.data.dao.ArticleDaoImpl

fun provideArticleDao(appDatabase: AppDatabase): ArticleDao = ArticleDaoImpl(appDatabase)

fun provideAppDatabase(
    context: Context
): AppDatabase {
    val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "notice")
    return AppDatabase(driver)
}
