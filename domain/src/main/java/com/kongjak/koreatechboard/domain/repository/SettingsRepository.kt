package com.kongjak.koreatechboard.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setDepartment(index: Int)
    fun getDepartment(): Flow<Int>
}
