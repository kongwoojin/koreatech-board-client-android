package com.kongjak.koreatechboard.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ListPreference(
    modifier: Modifier = Modifier,
    title: String,
    itemStringResource: List<Int>,
    itemValue: List<Any>,
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    val zippedList = itemStringResource.zip(itemValue)
    val showDialog = remember { mutableStateOf(false) }

    Preference(modifier = modifier, title = title) {
        showDialog.value = true
    }

    if (showDialog.value) {
        ListPreferenceDialog(
            zippedList = zippedList,
            showDialog = showDialog,
            selectedIndex = selectedIndex,
            onClick = onClick
        )
    }
}

@Composable
fun ListPreference(
    modifier: Modifier = Modifier,
    title: String,
    summary: String,
    itemStringResource: List<Int>,
    itemValue: List<Any>,
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    val zippedList = itemStringResource.zip(itemValue)
    val showDialog = remember { mutableStateOf(false) }

    Preference(modifier = modifier, title = title, summary = summary) {
        showDialog.value = true
    }

    if (showDialog.value) {
        ListPreferenceDialog(
            zippedList = zippedList,
            showDialog = showDialog,
            selectedIndex = selectedIndex,
            onClick = onClick
        )
    }
}

@Composable
fun ListPreference(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    itemStringResource: List<Int>,
    itemValue: List<Any>,
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    val zippedList = itemStringResource.zip(itemValue)
    val showDialog = remember { mutableStateOf(false) }

    Preference(modifier = modifier, title = title, icon = icon) {
        showDialog.value = true
    }

    if (showDialog.value) {
        ListPreferenceDialog(
            zippedList = zippedList,
            showDialog = showDialog,
            selectedIndex = selectedIndex,
            onClick = onClick
        )
    }
}

@Composable
fun ListPreference(
    modifier: Modifier = Modifier,
    title: String,
    summary: String,
    icon: ImageVector,
    itemStringResource: List<Int>,
    itemValue: List<Any>,
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    val zippedList = itemStringResource.zip(itemValue)
    val showDialog = remember { mutableStateOf(false) }

    Preference(modifier = modifier, title = title, summary = summary, icon = icon) {
        showDialog.value = true
    }

    if (showDialog.value) {
        ListPreferenceDialog(
            zippedList = zippedList,
            showDialog = showDialog,
            selectedIndex = selectedIndex,
            onClick = onClick
        )
    }
}

@Composable
fun ListPreferenceDialog(
    zippedList: List<Pair<Int, Any>>,
    showDialog: MutableState<Boolean>,
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    Dialog(
        onDismissRequest = { showDialog.value = false },
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val (selectedValue, onValueSelected) = remember { mutableIntStateOf(selectedIndex) }

                    zippedList.forEachIndexed { index, item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.selectable(
                                selected = (selectedValue == index),
                                onClick = {
                                    onClick(index)
                                    onValueSelected(index)
                                    showDialog.value = false
                                }
                            )
                        ) {
                            RadioButton(selected = selectedValue == index, onClick = {
                                onClick(index)
                                onValueSelected(index)
                                showDialog.value = false
                            })
                            Text(modifier = Modifier.fillMaxWidth(), text = stringResource(id = item.first))
                        }
                    }
                }
            }
        }
    )
}
