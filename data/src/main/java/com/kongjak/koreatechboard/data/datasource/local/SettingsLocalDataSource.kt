package com.kongjak.koreatechboard.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    fun getDepartment(): Flow<String> {
        val departmentFlow: Flow<String> = context.dataStore.data
            .map { preferences ->
                preferences[departmentKey] ?: "cse"
            }

        return departmentFlow
    }
}
