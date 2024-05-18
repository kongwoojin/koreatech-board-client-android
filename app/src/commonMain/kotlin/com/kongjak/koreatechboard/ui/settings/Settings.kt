package com.kongjak.koreatechboard.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.kongjak.koreatechboard.domain.DARK_THEME_DARK_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_LIGHT_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.kongjak.koreatechboard.ui.components.preference.*
import com.kongjak.koreatechboard.ui.permission.RequestNotificationPermission
import com.kongjak.koreatechboard.ui.permission.isNotificationPermissionGranted
import com.kongjak.koreatechboard.util.routes.Department
import koreatech_board.app.generated.resources.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

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

@OptIn(ExperimentalResourceApi::class)
val deptListString = deptList.map { it.stringResource }
val deptListName = deptList.map { it.name }

val darkTheme = listOf(DARK_THEME_SYSTEM_DEFAULT, DARK_THEME_LIGHT_THEME, DARK_THEME_DARK_THEME)

@OptIn(ExperimentalResourceApi::class)
val darkThemeString = listOf(
    Res.string.setting_dark_theme_system_default,
    Res.string.setting_dark_theme_light,
    Res.string.setting_dark_theme_dark
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel = koinViewModel()) {
    val context = LocalContext.current
    val uiState = settingsViewModel.collectAsState().value

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

        PreferenceColumn(title = stringResource(Res.string.setting_header_notification)) {
            val isNotificationPermissionGranted =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    isNotificationPermissionGranted()
                } else {
                    true
                }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isNotificationPermissionGranted) {
                RequestNotificationPermission()
            }
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

        val sourceCodeUrl = stringResource(Res.string.setting_source_code_url)
        val mailAddress = stringResource(Res.string.setting_mail_address)
        val mailSubject = stringResource(Res.string.setting_mail_subject)
        val mailText = stringResource(
            Res.string.setting_mail_text, Build.MODEL,
            Build.VERSION.SDK_INT
        )
        val mailAppNotFound = stringResource(Res.string.setting_mail_app_not_found)

        PreferenceColumn(title = stringResource(Res.string.setting_header_info)) {
            Preference(
                title = stringResource(Res.string.setting_license_title)
            ) {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            }

            Preference(title = stringResource(Res.string.setting_source_code_title)) {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(
                    context,
                    Uri.parse(sourceCodeUrl)
                )
            }

            Preference(title = stringResource(Res.string.setting_enquiry_mail_title)) {
                val mailIntent = Intent(Intent.ACTION_SENDTO)
                mailIntent.data = Uri.parse("mailto:")
                mailIntent.putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(mailAddress)
                )
                mailIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    mailSubject
                )
                mailIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    mailText
                )
                try {
                    context.startActivity(mailIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        context,
                        mailAppNotFound,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        PreferenceColumn(title = stringResource(Res.string.setting_header_theme)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
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
