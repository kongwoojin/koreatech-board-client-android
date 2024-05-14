package com.kongjak.koreatechboard.di

import android.content.Context
import androidx.room.Room
import com.kongjak.koreatechboard.data.dao.ArticleDao
import com.kongjak.koreatechboard.data.database.AppDatabase


fun provideArticleDao(appDatabase: AppDatabase): ArticleDao = appDatabase.articleDao()

fun provideAppDatabase(
    context: Context
): AppDatabase = Room
    .databaseBuilder(context, AppDatabase::class.java, "koreatech_board_article.db")
    .build()
