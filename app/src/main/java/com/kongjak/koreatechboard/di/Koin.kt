package com.kongjak.koreatechboard.di

import com.kongjak.koreatechboard.data.api.API
import com.kongjak.koreatechboard.data.api.APIImpl
import com.kongjak.koreatechboard.data.datasource.local.DatabaseLocalDataSource
import com.kongjak.koreatechboard.data.datasource.local.SettingsLocalDataSource
import com.kongjak.koreatechboard.data.datasource.remote.ArticleRemoteDataSource
import com.kongjak.koreatechboard.data.datasource.remote.BoardRemoteDataSource
import com.kongjak.koreatechboard.data.repository.ArticleRepositoryImpl
import com.kongjak.koreatechboard.data.repository.BoardRepositoryImpl
import com.kongjak.koreatechboard.data.repository.DatabaseRepositoryImpl
import com.kongjak.koreatechboard.data.repository.SettingsRepositoryImpl
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import com.kongjak.koreatechboard.domain.repository.DatabaseRepository
import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import com.kongjak.koreatechboard.domain.usecase.api.GetArticleUseCase
import com.kongjak.koreatechboard.domain.usecase.api.GetBoardMinimumUseCase
import com.kongjak.koreatechboard.domain.usecase.api.GetBoardUseCase
import com.kongjak.koreatechboard.domain.usecase.api.SearchBoardWithTitleUseCase
import com.kongjak.koreatechboard.domain.usecase.database.DeleteAllNewNoticesUseCase
import com.kongjak.koreatechboard.domain.usecase.database.DeleteNewNoticeUseCase
import com.kongjak.koreatechboard.domain.usecase.database.GetAllNewNoticesUseCase
import com.kongjak.koreatechboard.domain.usecase.database.InsertMultipleNewNoticesUseCase
import com.kongjak.koreatechboard.domain.usecase.database.UpdateNewNoticeReadUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetUserDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.SetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.SetUserDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.GetDepartmentNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.GetDormNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.GetSchoolNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.SetDepartmentNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.SetDormNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.SetSchoolNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.theme.GetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.theme.GetDynamicThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.theme.SetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.theme.SetDynamicThemeUseCase
import com.kongjak.koreatechboard.ui.article.ArticleViewModel
import com.kongjak.koreatechboard.ui.board.BoardViewModel
import com.kongjak.koreatechboard.ui.home.HomeBoardViewModel
import com.kongjak.koreatechboard.ui.home.HomeViewModel
import com.kongjak.koreatechboard.ui.main.MainViewModel
import com.kongjak.koreatechboard.ui.network.NetworkViewModel
import com.kongjak.koreatechboard.ui.notice.NoticeViewModel
import com.kongjak.koreatechboard.ui.search.SearchViewModel
import com.kongjak.koreatechboard.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun appModule() = module {
    single { BoardRemoteDataSource(get()) }
    single { ArticleRemoteDataSource(get()) }
    single { DatabaseLocalDataSource(get()) }
    single { SettingsLocalDataSource(get()) }

    single<BoardRepository> { BoardRepositoryImpl(get(), get()) }
    single<ArticleRepository> { ArticleRepositoryImpl(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<DatabaseRepository> { DatabaseRepositoryImpl(get(), get()) }

    single { provideNetworkUtil(androidContext()) }

    factory {
        MainViewModel(
            getUserDepartmentUseCase = get(),
            getInitDepartmentUseCase = get(),
            getDynamicThemeUseCase = get(),
            getDarkThemeUseCase = get()
        )
    }
    factory {
        HomeBoardViewModel(
            getBoardMinimumUseCase = get(),
        )
    }
    factory {
        HomeViewModel(get())
    }
    factory {
        ArticleViewModel(
            getArticleUseCase = get(),
        )
    }
    factory {
        BoardViewModel(get())
    }
    factory {
        NetworkViewModel(get())
    }
    factory {
        SettingsViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    factory {
        NoticeViewModel(get(), get(), get(), get())
    }
    factory {
        SearchViewModel(get())
    }
}

fun useCaseModule() = module {
    single { GetBoardMinimumUseCase(get()) }
    single { GetArticleUseCase(get()) }
    single { GetBoardUseCase(get()) }
    single { SearchBoardWithTitleUseCase(get()) }
    single { DeleteAllNewNoticesUseCase(get()) }
    single { DeleteNewNoticeUseCase(get()) }
    single { GetAllNewNoticesUseCase(get()) }
    single { InsertMultipleNewNoticesUseCase(get()) }
    single { UpdateNewNoticeReadUseCase(get()) }
    single { GetUserDepartmentUseCase(get()) }
    single { GetInitDepartmentUseCase(get()) }
    single { SetUserDepartmentUseCase(get()) }
    single { SetInitDepartmentUseCase(get()) }
    single { GetDepartmentNoticeSubscribe(get()) }
    single { GetDormNoticeSubscribe(get()) }
    single { GetSchoolNoticeSubscribe(get()) }
    single { SetDepartmentNoticeSubscribe(get()) }
    single { SetDormNoticeSubscribe(get()) }
    single { SetSchoolNoticeSubscribe(get()) }
    single { GetDarkThemeUseCase(get()) }
    single { GetDynamicThemeUseCase(get()) }
    single { SetDarkThemeUseCase(get()) }
    single { SetDynamicThemeUseCase(get()) }
}

fun networkModule() = module {
    single<API> { APIImpl(get()) }
    single { provideHttpClient() }
}

fun databaseModule() = module {
    single { provideAppDatabase(androidContext()) }
    single { provideArticleDao(get()) }
}