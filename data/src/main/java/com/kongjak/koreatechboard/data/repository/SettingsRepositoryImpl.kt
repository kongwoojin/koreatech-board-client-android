package com.kongjak.koreatechboard.data.repository

import com.kongjak.koreatechboard.data.datasource.local.SettingsLocalDataSource
import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsLocalDataSource: SettingsLocalDataSource
) : SettingsRepository {
    override suspend fun setUserDepartment(index: Int) {
        settingsLocalDataSource.setUserDepartment(index)
    }
    override fun getUserDepartment(): Flow<Int> = settingsLocalDataSource.getUserDepartment()

    override suspend fun setInitDepartment(index: Int) {
        settingsLocalDataSource.setInitDepartment(index)
    }

    override fun getInitDepartment(): Flow<Int> = settingsLocalDataSource.getInitDepartment()

    override suspend fun setDynamicTheme(state: Boolean) {
        settingsLocalDataSource.setDynamicTheme(state)
    }
    override fun getDynamicTheme(): Flow<Boolean> {
        return settingsLocalDataSource.getDynamicTheme()
    }

    override suspend fun setDarkTheme(theme: Int) {
        settingsLocalDataSource.setDarkTheme(theme)
    }

    override fun getDarkTheme(): Flow<Int> {
        return settingsLocalDataSource.getDarkTheme()
    }

    override suspend fun setShowArticleNumber(state: Boolean) {
        settingsLocalDataSource.setShowArticleNumber(state)
    }

    override fun getShowArticleNumber(): Flow<Boolean> {
        return settingsLocalDataSource.getShowArticleNumber()
    }
}
