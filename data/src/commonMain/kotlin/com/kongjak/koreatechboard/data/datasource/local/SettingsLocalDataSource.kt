package com.kongjak.koreatechboard.data.datasource.local

import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getBooleanFlow
import com.russhwolf.settings.coroutines.getIntFlow
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class SettingsLocalDataSource(
    private val settings: ObservableSettings
) {
    fun setUserDepartment(index: Int) {
        settings.putInt(USER_DEPARTMENT_KEY, index)
    }

    fun getUserDepartment(): Flow<Int> = settings.getIntFlow(USER_DEPARTMENT_KEY, 0)

    fun setInitDepartment(index: Int) {
        settings.putInt(INIT_DEPARTMENT_KEY, index)
    }

    fun getInitDepartment(): Flow<Int> = settings.getIntFlow(INIT_DEPARTMENT_KEY, 0)

    fun setDynamicTheme(newState: Boolean) {
        settings.putBoolean(DYNAMIC_THEME_KEY, newState)
    }

    fun getDynamicTheme(): Flow<Boolean> = settings.getBooleanFlow(DYNAMIC_THEME_KEY, true)

    fun setDarkTheme(newTheme: Int) {
        settings.putInt(DARK_THEME_KEY, newTheme)
    }

    fun getDarkTheme(): Flow<Int> = settings.getIntFlow(DARK_THEME_KEY, DARK_THEME_SYSTEM_DEFAULT)

    fun setSchoolNoticeSubscribe(state: Boolean) {
        settings.putBoolean(SCHOOL_NOTICE_SUBSCRIBE_KEY, state)
    }

    fun getSchoolNoticeSubscribe(): Flow<Boolean> = settings.getBooleanFlow(SCHOOL_NOTICE_SUBSCRIBE_KEY, false)

    fun setDormNoticeSubscribe(state: Boolean) {
        settings.putBoolean(DORM_NOTICE_SUBSCRIBE_KEY, state)
    }

    fun getDormNoticeSubscribe(): Flow<Boolean> = settings.getBooleanFlow(DORM_NOTICE_SUBSCRIBE_KEY, false)

    fun setDepartmentNoticeSubscribe(state: Boolean) {
        settings.putBoolean(DEPARTMENT_NOTICE_SUBSCRIBE_KEY, state)
    }

    fun getDepartmentNoticeSubscribe(): Flow<Boolean> = settings.getBooleanFlow(DEPARTMENT_NOTICE_SUBSCRIBE_KEY, false)

    companion object {
        const val USER_DEPARTMENT_KEY = "user_department"
        const val INIT_DEPARTMENT_KEY = "init_department"
        const val DYNAMIC_THEME_KEY = "dynamic_theme"
        const val DARK_THEME_KEY = "dark_theme"
        const val SCHOOL_NOTICE_SUBSCRIBE_KEY = "school_notice_subscribe"
        const val DORM_NOTICE_SUBSCRIBE_KEY = "dorm_notice_subscribe"
        const val DEPARTMENT_NOTICE_SUBSCRIBE_KEY = "department_notice_subscribe"
    }
}
