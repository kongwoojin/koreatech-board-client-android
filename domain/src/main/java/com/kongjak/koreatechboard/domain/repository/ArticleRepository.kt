package com.kongjak.koreatechboard.domain.repository

import com.kongjak.koreatechboard.domain.model.Article
import dagger.Binds
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticle(site: String, url: String): Article
}