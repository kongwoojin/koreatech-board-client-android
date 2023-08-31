package com.kongjak.koreatechboard.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsLocalDataSource @Inject constructor(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val departmentKey = intPreferencesKey("department")
    private val dynamicThemeKey = booleanPreferencesKey("dynamic_theme")

    suspend fun setDepartment(newIndex: Int) {
        context.dataStore.edit { preferences ->
            preferences[departmentKey] = newIndex
        }
    }

    fun getDepartment(): Flow<Int> {
        val departmentFlow: Flow<Int> = context.dataStore.data
            .map { preferences ->
                preferences[departmentKey] ?: 0
            }

        return departmentFlow
    }

    suspend fun setDynamicTheme(newState: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[dynamicThemeKey] = newState
        }
    }

    fun getDynamicTheme(): Flow<Boolean> =
        context.dataStore.data
            .map { preferences ->
                preferences[dynamicThemeKey] ?: true
            }
}
