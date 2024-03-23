package com.kongjak.koreatechboard.ui.main.settings

import com.kongjak.koreatechboard.ui.base.UiEvent

sealed class SettingsEvent : UiEvent {
    object GetUserDepartment : SettingsEvent()
    data class SetUserDepartment(val index: Int) : SettingsEvent()
    object GetInitDepartment : SettingsEvent()
    data class SetInitDepartment(val index: Int) : SettingsEvent()
    object GetDynamicTheme : SettingsEvent()
    data class SetDynamicTheme(val state: Boolean) : SettingsEvent()
    object GetDarkTheme : SettingsEvent()
    data class SetDarkTheme(val index: Int) : SettingsEvent()
    object GetShowArticleNumber : SettingsEvent()
    data class SetShowArticleNumber(val state: Boolean) : SettingsEvent()
    data class UpdateSchoolSubscribe(val subscribe: Boolean) : SettingsEvent()
    data class UpdateDormSubscribe(val subscribe: Boolean) : SettingsEvent()
    data class UpdateDepartmentSubscribe(val subscribe: Boolean) : SettingsEvent()
    object GetSchoolSubscribe : SettingsEvent()
    object GetDormSubscribe : SettingsEvent()
    object GetDepartmentSubscribe : SettingsEvent()
}
