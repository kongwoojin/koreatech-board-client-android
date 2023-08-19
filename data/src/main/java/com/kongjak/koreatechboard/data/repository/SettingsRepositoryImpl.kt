package com.kongjak.koreatechboard.data.repository

import com.kongjak.koreatechboard.data.datasource.local.SettingsLocalDataSource
import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsLocalDataSource: SettingsLocalDataSource
): SettingsRepository {
    override suspend fun setDepartment(department: String) {
        settingsLocalDataSource.setDepartment(department)
    }
    override fun getDepartment(): String {
        return settingsLocalDataSource.getDepartment() ?: "cse" // Set default department to cse
    }
}