package com.kongjak.koreatechboard.di

import com.kongjak.koreatechboard.data.repository.ArticleRepositoryImpl
import com.kongjak.koreatechboard.data.repository.BoardRepositoryImpl
import com.kongjak.koreatechboard.data.repository.remote.ArticleRemoteDataSource
import com.kongjak.koreatechboard.data.repository.remote.ArticleRemoteDataSourceImpl
import com.kongjak.koreatechboard.data.repository.remote.BoardRemoteDataSource
import com.kongjak.koreatechboard.data.repository.remote.BoardRemoteDataSourceImpl
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import com.kongjak.koreatechboard.domain.usecase.GetArticleUseCase
import com.kongjak.koreatechboard.domain.usecase.GetBoardUseCase
import com.kongjak.koreatechboard.viewmodel.ArticleViewModel
import com.kongjak.koreatechboard.viewmodel.BoardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<BoardRepository> { BoardRepositoryImpl(get()) }
    single<ArticleRepository> { ArticleRepositoryImpl(get()) }

    single<BoardRemoteDataSource> { BoardRemoteDataSourceImpl() }
    single<ArticleRemoteDataSource> { ArticleRemoteDataSourceImpl() }

    single { GetBoardUseCase(get()) }
    single { GetArticleUseCase(get()) }
}

val viewModelModule = module {
    viewModel {
        BoardViewModel(get())
    }
    viewModel {
        ArticleViewModel(get())
    }
}