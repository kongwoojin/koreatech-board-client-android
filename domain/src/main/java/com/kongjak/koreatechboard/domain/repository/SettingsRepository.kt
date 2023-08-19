package com.kongjak.koreatechboard.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setDepartment(department: String)
    fun getDepartment(): Flow<String>
}