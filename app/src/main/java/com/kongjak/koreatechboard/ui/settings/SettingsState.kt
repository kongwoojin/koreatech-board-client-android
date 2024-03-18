package com.kongjak.koreatechboard.ui.settings

import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.kongjak.koreatechboard.ui.base.UiState

data class SettingsState(
    val userDepartment: Int = 0,
    val initDepartment: Int = 0,
    val isDynamicTheme: Boolean = true,
    val isDarkTheme: Int = DARK_THEME_SYSTEM_DEFAULT,
    val showNumber: Boolean = true
): UiState