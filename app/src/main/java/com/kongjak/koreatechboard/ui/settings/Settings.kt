package com.kongjak.koreatechboard.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.components.ListPreference
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

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel = hiltViewModel()) {
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

        val isChecked by settingsViewModel.isDynamicTheme.observeAsState(true)

        SwitchPreference(
            title = stringResource(id = R.string.setting_dynamic_theme_title),
            checked = isChecked,
            onCheckedChange = { settingsViewModel.setDynamicTheme(it) }
        )
    }
}

@Preview
@Composable
fun SettingsPreview() {
    SettingsScreen()
}
