package com.kongjak.koreatechboard.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title)
        }
        Divider(color = Color.Gray, thickness = 0.5.dp)
    }
}

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    title: String,
    summary: String,
    onClick: () -> Unit
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = title)
                Text(text = summary, fontSize = MaterialTheme.typography.bodySmall.fontSize)
            }
        }
        Divider(color = Color.Gray, thickness = 0.5.dp)
    }
}

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = title)
        }
        Divider(color = Color.Gray, thickness = 0.5.dp)
    }
}

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    summary: String,
    onClick: () -> Unit
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.padding(end = 8.dp)
            )
            Column {
                Text(text = title)
                Text(text = summary, fontSize = MaterialTheme.typography.bodySmall.fontSize)
            }
        }
        Divider(color = Color.Gray, thickness = 0.5.dp)
    }
}

@Preview
@Composable
fun PreferencePreview() {
    Column {
        Preference(title = "Title") {}
        Preference(title = "Title", summary = "summary") {}
        Preference(icon = Icons.Filled.Settings, title = "Title") {}
        Preference(icon = Icons.Filled.Settings, title = "Title", summary = "summary") {}
    }
}
