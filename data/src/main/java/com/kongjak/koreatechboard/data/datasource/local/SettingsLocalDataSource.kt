package com.kongjak.koreatechboard.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SettingsLocalDataSource @Inject constructor(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val departmentKey = stringPreferencesKey("department")

    suspend fun setDepartment(newDepartment: String) {
        context.dataStore.edit { department ->
            department[departmentKey] = newDepartment
        }
    }

    fun getDepartment(): String? {
        return runBlocking { context.dataStore.data.first()[departmentKey] }
    }
}