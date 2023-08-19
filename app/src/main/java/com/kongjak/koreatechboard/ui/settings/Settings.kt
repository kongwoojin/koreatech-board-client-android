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
import com.kongjak.koreatechboard.util.routes.Department

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
        val selected by settingsViewModel.department.observeAsState()

        ListPreference(
            title = stringResource(id = R.string.setting_department_title),
            summary = stringResource(id = R.string.setting_department_summary),
            itemStringResource = deptListName,
            itemValue = deptListValue,
            savedValue = selected ?: Department.Cse.name,
        ) { item ->
            settingsViewModel.setDepartment(item)
        }
    }
}

@Preview
@Composable
fun SettingsPreview() {
    SettingsScreen()
}
