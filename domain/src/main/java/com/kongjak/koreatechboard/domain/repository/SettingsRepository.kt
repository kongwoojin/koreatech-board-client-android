package com.kongjak.koreatechboard.domain.repository

interface SettingsRepository {
    suspend fun setDepartment(department: String)
    fun getDepartment(): String
}