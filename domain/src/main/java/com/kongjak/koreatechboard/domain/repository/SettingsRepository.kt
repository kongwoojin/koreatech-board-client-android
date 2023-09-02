package com.kongjak.koreatechboard.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setDepartment(index: Int)
    fun getDepartment(): Flow<Int>
    suspend fun setDynamicTheme(state: Boolean)
    fun getDynamicTheme(): Flow<Boolean>
    suspend fun setDarkTheme(theme: Int)
    fun getDarkTheme(): Flow<Int>
}
