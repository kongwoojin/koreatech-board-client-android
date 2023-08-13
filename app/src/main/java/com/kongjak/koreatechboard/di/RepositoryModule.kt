package com.kongjak.koreatechboard.di

import com.kongjak.koreatechboard.data.api.API
import com.kongjak.koreatechboard.data.datasource.remote.ArticleRemoteDataSource
import com.kongjak.koreatechboard.data.datasource.remote.BoardRemoteDataSource
import com.kongjak.koreatechboard.data.repository.ArticleRepositoryImpl
import com.kongjak.koreatechboard.data.repository.BoardRepositoryImpl
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideArticleRepository(
        articleRemoteDataSource: ArticleRemoteDataSource
    ): ArticleRepository {
        return ArticleRepositoryImpl(articleRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideBoardRepository(
        boardRemoteDataSource: BoardRemoteDataSource,
        api: API
    ): BoardRepository {
        return BoardRepositoryImpl(boardRemoteDataSource, api)
    }
}
