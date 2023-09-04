package com.kongjak.koreatechboard.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    BasicPreference(modifier = modifier, title = title, onClick = {
        onCheckedChange(!checked)
    }, content = {
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Switch(checked = checked, onCheckedChange = onCheckedChange)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    title: String,
    summary: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    BasicPreference(modifier = modifier, title = title, summary = summary, onClick = {
        onCheckedChange(!checked)
    }, content = {
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Switch(checked = checked, onCheckedChange = onCheckedChange)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    BasicPreference(modifier = modifier, icon = icon, title = title, onClick = {
        onCheckedChange(!checked)
    }, content = {
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Switch(checked = checked, onCheckedChange = onCheckedChange)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    summary: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    BasicPreference(modifier = modifier, icon = icon, title = title, summary = summary, onClick = {
        onCheckedChange(!checked)
    }, content = {
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Switch(checked = checked, onCheckedChange = onCheckedChange)
            }
        })
}

@Preview
@Composable
fun SwitchPreferencePreview() {
    SwitchPreference(modifier = Modifier, title = "Title", summary = "Summary", checked = false) {
    }
}
