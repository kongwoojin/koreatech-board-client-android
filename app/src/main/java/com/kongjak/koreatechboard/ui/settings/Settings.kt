package com.kongjak.koreatechboard.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.kongjak.koreatechboard.util.routes.Department

val deptList = listOf(
    Department.Cse,
    Department.Mechanical,
    Department.Mechatronics,
    Department.Ite,
    Department.Ide,
    Department.Arch,
    Department.Emc,
    Department.Sim
)

val deptListName = listOf(
    Department.Cse.stringResource,
    Department.Mechanical.stringResource,
    Department.Mechatronics.stringResource,
    Department.Ite.stringResource,
    Department.Ide.stringResource,
    Department.Arch.stringResource,
    Department.Emc.stringResource,
    Department.Sim.stringResource
)

val deptListValue = listOf(
    Department.Cse.name,
    Department.Mechanical.name,
    Department.Mechatronics.name,
    Department.Ite.name,
    Department.Ide.name,
    Department.Arch.name,
    Department.Emc.name,
    Department.Sim.name
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

    Column(modifier = Modifier.fillMaxSize()) {
        val selectedIndex by settingsViewModel.department.observeAsState()

        ListPreference(
            title = stringResource(id = R.string.setting_department_title),
            summary = stringResource(id = deptListName[selectedIndex ?: 0]),
            itemStringResource = deptListName,
            itemValue = deptListValue,
            selectedIndex = selectedIndex ?: 0,
        ) { item ->
            settingsViewModel.setDepartment(item)
        }

        PreferenceHeader(title = stringResource(id = R.string.setting_header_info))

        Preference(
            title = stringResource(id = R.string.license_title)
        ) {
            context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
        }

        Preference(title = stringResource(id = R.string.source_code_title)) {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(
                context,
                Uri.parse("https://github.com/kongwoojin/koreatech_board_client_android")
            )
        }

        Preference(title = stringResource(id = R.string.enquiry_mail_title)) {
            val mailIntent = Intent(Intent.ACTION_SENDTO)
            mailIntent.data = Uri.parse("mailto:")
            mailIntent.putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(context.getString(R.string.mail_address))
            )
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.mail_subject))
            mailIntent.putExtra(
                Intent.EXTRA_TEXT,
                context.getString(R.string.mail_text, Build.MODEL, Build.VERSION.SDK_INT)
            )
            try {
                context.startActivity(mailIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.mail_app_not_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        PreferenceHeader(title = stringResource(id = R.string.setting_header_theme))

        val isChecked by settingsViewModel.isDynamicTheme.observeAsState(true)

        SwitchPreference(
            title = stringResource(id = R.string.setting_dynamic_theme_title),
            checked = isChecked,
            onCheckedChange = { settingsViewModel.setDynamicTheme(it) }
        )

        val isDarkTheme by settingsViewModel.isDarkTheme.observeAsState()

        ListPreference(
            title = stringResource(id = R.string.setting_dark_theme_title),
            summary = stringResource(
                id = darkThemeString[isDarkTheme ?: DARK_THEME_SYSTEM_DEFAULT]
            ),
            itemStringResource = darkThemeString,
            itemValue = darkTheme,
            selectedIndex = isDarkTheme ?: DARK_THEME_SYSTEM_DEFAULT,
        ) { theme ->
            settingsViewModel.setDarkTheme(theme)
        }
    }
}

@Preview
@Composable
fun SettingsPreview() {
    SettingsScreen()
}
