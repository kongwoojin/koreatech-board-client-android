package com.kongjak.koreatechboard.ui.main.settings

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.domain.DARK_THEME_DARK_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_LIGHT_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.kongjak.koreatechboard.ui.components.ListPreference
import com.kongjak.koreatechboard.ui.components.Preference
import com.kongjak.koreatechboard.ui.components.PreferenceHeader
import com.kongjak.koreatechboard.ui.components.SwitchPreference
import com.kongjak.koreatechboard.ui.permission.RequestNotificationPermission
import com.kongjak.koreatechboard.ui.permission.isNotificationPermissionGranted
import com.kongjak.koreatechboard.util.routes.Department

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

val fullDeptList = listOf(
    Department.School,
    Department.Dorm,
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

val darkTheme = listOf(DARK_THEME_SYSTEM_DEFAULT, DARK_THEME_LIGHT_THEME, DARK_THEME_DARK_THEME)
val darkThemeString = listOf(
    R.string.setting_dark_theme_system_default,
    R.string.setting_dark_theme_light,
    R.string.setting_dark_theme_dark
)

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val uiState by settingsViewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        val userDepartment = uiState.userDepartment

        ListPreference(
            title = stringResource(id = R.string.setting_user_department_title),
            summary = stringResource(id = deptListString[userDepartment]),
            itemStringResource = deptListString,
            itemValue = deptListName,
            selectedIndex = userDepartment
        ) { item ->
            val subscribe = uiState.subscribeDepartment
            if (subscribe) {
                settingsViewModel.sendEvent(SettingsEvent.UpdateDepartmentSubscribe(false))
            }
            settingsViewModel.sendEvent(SettingsEvent.SetUserDepartment(item))
            if (subscribe) {
                settingsViewModel.sendEvent(SettingsEvent.UpdateDepartmentSubscribe(true))
            }
        }

        val initDepartment = uiState.initDepartment

        val initDeptList = listOf(
            Department.School,
            Department.Dorm,
            deptList[userDepartment]
        )

        ListPreference(
            title = stringResource(id = R.string.setting_default_board_title),
            summary = stringResource(id = initDeptList.map { it.stringResource }[initDepartment]),
            itemStringResource = initDeptList.map { it.stringResource },
            itemValue = initDeptList.map { it.name },
            selectedIndex = initDepartment
        ) { item ->
            settingsViewModel.sendEvent(SettingsEvent.SetInitDepartment(item))
        }

        val showNumber = uiState.showNumber

        SwitchPreference(
            title = stringResource(id = R.string.setting_show_article_number_title),
            summary = stringResource(id = R.string.setting_show_article_number_summary),
            checked = showNumber,
            onCheckedChange = { settingsViewModel.sendEvent(SettingsEvent.SetShowArticleNumber(it)) }
        )

        PreferenceHeader(title = stringResource(id = R.string.setting_header_notification))

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
            title = stringResource(id = R.string.setting_subscribe_new_notice_school),
            summary = stringResource(id = R.string.setting_subscribe_new_notice_summary_school),
            checked = uiState.subscribeSchool,
            enabled = isNotificationPermissionGranted,
            onCheckedChange = {
                settingsViewModel.sendEvent(SettingsEvent.UpdateSchoolSubscribe(it))
            }
        )

        SwitchPreference(
            title = stringResource(id = R.string.setting_subscribe_new_notice_dorm),
            summary = stringResource(id = R.string.setting_subscribe_new_notice_summary_dorm),
            checked = uiState.subscribeDormitory,
            enabled = isNotificationPermissionGranted,
            onCheckedChange = {
                settingsViewModel.sendEvent(SettingsEvent.UpdateDormSubscribe(it))
            }
        )

        SwitchPreference(
            title = stringResource(id = R.string.setting_subscribe_new_notice_department),
            summary = stringResource(id = R.string.setting_subscribe_new_notice_summary_department),
            checked = uiState.subscribeDepartment,
            enabled = isNotificationPermissionGranted,
            onCheckedChange = {
                settingsViewModel.sendEvent(SettingsEvent.UpdateDepartmentSubscribe(it))
            }
        )

        PreferenceHeader(title = stringResource(id = R.string.setting_header_info))

        Preference(
            title = stringResource(id = R.string.setting_license_title)
        ) {
            context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
        }

        Preference(title = stringResource(id = R.string.setting_source_code_title)) {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(
                context,
                Uri.parse("https://github.com/kongwoojin/koreatech_board_client_android")
            )
        }

        Preference(title = stringResource(id = R.string.setting_enquiry_mail_title)) {
            val mailIntent = Intent(Intent.ACTION_SENDTO)
            mailIntent.data = Uri.parse("mailto:")
            mailIntent.putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(context.getString(R.string.setting_mail_address))
            )
            mailIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                context.getString(R.string.setting_mail_subject)
            )
            mailIntent.putExtra(
                Intent.EXTRA_TEXT,
                context.getString(R.string.setting_mail_text, Build.MODEL, Build.VERSION.SDK_INT)
            )
            try {
                context.startActivity(mailIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.setting_mail_app_not_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        PreferenceHeader(title = stringResource(id = R.string.setting_header_theme))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val isChecked = uiState.isDynamicTheme

            SwitchPreference(
                title = stringResource(id = R.string.setting_dynamic_theme_title),
                checked = isChecked,
                onCheckedChange = { settingsViewModel.sendEvent(SettingsEvent.SetDynamicTheme(it)) }
            )
        }

        val isDarkTheme = uiState.isDarkTheme

        ListPreference(
            title = stringResource(id = R.string.setting_dark_theme_title),
            summary = stringResource(
                id = darkThemeString[isDarkTheme]
            ),
            itemStringResource = darkThemeString,
            itemValue = darkTheme,
            selectedIndex = isDarkTheme
        ) { theme ->
            settingsViewModel.sendEvent(SettingsEvent.SetDarkTheme(theme))
        }
    }
}

@Preview
@Composable
fun SettingsPreview() {
    SettingsScreen()
}
