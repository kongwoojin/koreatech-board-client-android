package com.kongjak.koreatechboard.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.kongjak.koreatechboard.domain.DARK_THEME_DARK_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_LIGHT_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.kongjak.koreatechboard.ui.components.preference.DialogPreference
import com.kongjak.koreatechboard.ui.components.preference.ListPreference
import com.kongjak.koreatechboard.ui.components.preference.Preference
import com.kongjak.koreatechboard.ui.components.preference.PreferenceColumn
import com.kongjak.koreatechboard.ui.components.preference.SwitchPreference
import com.kongjak.koreatechboard.ui.permission.CheckNotificationPermission
import com.kongjak.koreatechboard.util.getPlatformInfo
import com.kongjak.koreatechboard.util.openMail
import com.kongjak.koreatechboard.util.openUrl
import com.kongjak.koreatechboard.util.routes.Department
import koreatech_board.app.generated.resources.Res
import koreatech_board.app.generated.resources.setting_dark_theme_dark
import koreatech_board.app.generated.resources.setting_dark_theme_light
import koreatech_board.app.generated.resources.setting_dark_theme_system_default
import koreatech_board.app.generated.resources.setting_dark_theme_title
import koreatech_board.app.generated.resources.setting_default_board_title
import koreatech_board.app.generated.resources.setting_delete_all_new_notices_confirm
import koreatech_board.app.generated.resources.setting_delete_all_new_notices_summary
import koreatech_board.app.generated.resources.setting_delete_all_new_notices_title
import koreatech_board.app.generated.resources.setting_dynamic_theme_summary
import koreatech_board.app.generated.resources.setting_dynamic_theme_title
import koreatech_board.app.generated.resources.setting_enquiry_mail_title
import koreatech_board.app.generated.resources.setting_header_info
import koreatech_board.app.generated.resources.setting_header_notification
import koreatech_board.app.generated.resources.setting_header_theme
import koreatech_board.app.generated.resources.setting_license_title
import koreatech_board.app.generated.resources.setting_mail_address
import koreatech_board.app.generated.resources.setting_mail_app_not_found
import koreatech_board.app.generated.resources.setting_mail_subject
import koreatech_board.app.generated.resources.setting_mail_text
import koreatech_board.app.generated.resources.setting_source_code_title
import koreatech_board.app.generated.resources.setting_source_code_url
import koreatech_board.app.generated.resources.setting_subscribe_new_notice_department
import koreatech_board.app.generated.resources.setting_subscribe_new_notice_dorm
import koreatech_board.app.generated.resources.setting_subscribe_new_notice_school
import koreatech_board.app.generated.resources.setting_subscribe_new_notice_summary_department
import koreatech_board.app.generated.resources.setting_subscribe_new_notice_summary_dorm
import koreatech_board.app.generated.resources.setting_subscribe_new_notice_summary_school
import koreatech_board.app.generated.resources.setting_user_department_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

val deptList = listOf(
    Department.Cse,
    Department.Mechanical,
    Department.Mechatronics,
    Department.Ite,
    Department.Ide,
    Department.Arch,
    Department.Mse,
    Department.Ace,
    Department.Sim
)

val deptListString = deptList.map { it.stringResource }
val deptListName = deptList.map { it.name }

val darkTheme = listOf(DARK_THEME_SYSTEM_DEFAULT, DARK_THEME_LIGHT_THEME, DARK_THEME_DARK_THEME)

val darkThemeString = listOf(
    Res.string.setting_dark_theme_system_default,
    Res.string.setting_dark_theme_light,
    Res.string.setting_dark_theme_dark
)

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = koinViewModel(),
    openLicenses: () -> Unit,
    showSnackbar: (message: String) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val uiState by settingsViewModel.collectAsState()

    settingsViewModel.collectSideEffect { settingsViewModel.handleSideEffect(it) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        val userDepartment = uiState.userDepartment

        PreferenceColumn {
            ListPreference(
                title = stringResource(Res.string.setting_user_department_title),
                summary = stringResource(deptListString[userDepartment]),
                itemStringResource = deptListString,
                itemValue = deptListName,
                selectedIndex = userDepartment
            ) { item ->
                val subscribe = uiState.subscribeDepartment
                if (subscribe) {
                    settingsViewModel.sendSideEffect(
                        SettingsSideEffect.UpdateDepartmentSubscribe(
                            false
                        )
                    )
                }
                settingsViewModel.sendSideEffect(SettingsSideEffect.SetUserDepartment(item))
                if (subscribe) {
                    settingsViewModel.sendSideEffect(
                        SettingsSideEffect.UpdateDepartmentSubscribe(
                            true
                        )
                    )
                }
            }

            val initDepartment = uiState.initDepartment

            val initDeptList = listOf(
                Department.School,
                Department.Dorm,
                deptList[userDepartment]
            )

            ListPreference(
                title = stringResource(Res.string.setting_default_board_title),
                summary = stringResource(initDeptList.map { it.stringResource }[initDepartment]),
                itemStringResource = initDeptList.map { it.stringResource },
                itemValue = initDeptList.map { it.name },
                selectedIndex = initDepartment
            ) { item ->
                settingsViewModel.sendSideEffect(SettingsSideEffect.SetInitDepartment(item))
            }
        }

        if (getPlatformInfo().isFirebaseSupported) {
            PreferenceColumn(title = stringResource(Res.string.setting_header_notification)) {
                var isNotificationPermissionGranted by remember { mutableStateOf(false) }
                CheckNotificationPermission(
                    onPermissionGranted = {
                        settingsViewModel.sendSideEffect(SettingsSideEffect.SetSubscribe(true))
                        isNotificationPermissionGranted = true
                    }
                )
                SwitchPreference(
                    title = stringResource(Res.string.setting_subscribe_new_notice_school),
                    summary = stringResource(Res.string.setting_subscribe_new_notice_summary_school),
                    checked = uiState.subscribeSchool,
                    enabled = isNotificationPermissionGranted,
                    onCheckedChange = {
                        settingsViewModel.sendSideEffect(SettingsSideEffect.UpdateSchoolSubscribe(it))
                    }
                )

                SwitchPreference(
                    title = stringResource(Res.string.setting_subscribe_new_notice_dorm),
                    summary = stringResource(Res.string.setting_subscribe_new_notice_summary_dorm),
                    checked = uiState.subscribeDormitory,
                    enabled = isNotificationPermissionGranted,
                    onCheckedChange = {
                        settingsViewModel.sendSideEffect(SettingsSideEffect.UpdateDormSubscribe(it))
                    }
                )

                SwitchPreference(
                    title = stringResource(Res.string.setting_subscribe_new_notice_department),
                    summary = stringResource(Res.string.setting_subscribe_new_notice_summary_department),
                    checked = uiState.subscribeDepartment,
                    enabled = isNotificationPermissionGranted,
                    onCheckedChange = {
                        settingsViewModel.sendSideEffect(SettingsSideEffect.UpdateDepartmentSubscribe(it))
                    }
                )

                DialogPreference(
                    title = stringResource(Res.string.setting_delete_all_new_notices_title),
                    summary = stringResource(Res.string.setting_delete_all_new_notices_summary),
                    dialogTitle = stringResource(Res.string.setting_delete_all_new_notices_title),
                    dialogContent = stringResource(Res.string.setting_delete_all_new_notices_confirm),
                    onConfirm = {
                        settingsViewModel.sendSideEffect(SettingsSideEffect.DeleteAllNewArticle)
                    },
                    onDismiss = {
                        // Do nothing
                    }
                )
            }
        }

        val sourceCodeUrl = stringResource(Res.string.setting_source_code_url)
        val mailAddress = stringResource(Res.string.setting_mail_address)
        val mailSubject = stringResource(Res.string.setting_mail_subject)
        val mailText = stringResource(
            Res.string.setting_mail_text,
            "",
            ""
        )
        val mailAppNotFound = stringResource(Res.string.setting_mail_app_not_found)

        PreferenceColumn(title = stringResource(Res.string.setting_header_info)) {
            Preference(
                title = stringResource(Res.string.setting_license_title)
            ) {
                openLicenses()
            }

            Preference(title = stringResource(Res.string.setting_source_code_title)) {
                openUrl(
                    uriHandler = uriHandler,
                    url = sourceCodeUrl
                )
            }

            Preference(title = stringResource(Res.string.setting_enquiry_mail_title)) {
                openMail(
                    address = mailAddress,
                    subject = mailSubject,
                    body = mailText,
                    exception = { message ->
                        showSnackbar(message)
                    }
                )
            }
        }

        PreferenceColumn(title = stringResource(Res.string.setting_header_theme)) {
            if (getPlatformInfo().isDynamicThemeSupported) {
                val isChecked = uiState.isDynamicTheme

                SwitchPreference(
                    title = stringResource(Res.string.setting_dynamic_theme_title),
                    summary = stringResource(Res.string.setting_dynamic_theme_summary),
                    checked = isChecked,
                    onCheckedChange = {
                        settingsViewModel.sendSideEffect(
                            SettingsSideEffect.SetDynamicTheme(
                                it
                            )
                        )
                    }
                )
            }

            val isDarkTheme = uiState.isDarkTheme

            ListPreference(
                title = stringResource(Res.string.setting_dark_theme_title),
                summary = stringResource(
                    darkThemeString[isDarkTheme]
                ),
                itemStringResource = darkThemeString,
                itemValue = darkTheme,
                selectedIndex = isDarkTheme
            ) { theme ->
                settingsViewModel.sendSideEffect(SettingsSideEffect.SetDarkTheme(theme))
            }
        }
    }
}
