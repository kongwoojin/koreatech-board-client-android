package com.kongjak.koreatechboard.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BasicPreference(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(1f), text = title)
            Column(horizontalAlignment = Alignment.End) {
                if (content != null) {
                    content()
                }
            }
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
    }
}

@Composable
fun BasicPreference(
    modifier: Modifier = Modifier,
    title: String,
    summary: String,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title)
                Text(text = summary, fontSize = MaterialTheme.typography.bodySmall.fontSize)
            }
            Column(horizontalAlignment = Alignment.End) {
                if (content != null) {
                    content()
                }
            }
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
    }
}

@Composable
fun BasicPreference(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)? = null
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
            Text(modifier = Modifier.weight(1f), text = title)
            Column(horizontalAlignment = Alignment.End) {
                if (content != null) {
                    content()
                }
            }
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
    }
}

@Composable
fun BasicPreference(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    summary: String,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)? = null
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
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title)
                Text(text = summary, fontSize = MaterialTheme.typography.bodySmall.fontSize)
            }
            Column(horizontalAlignment = Alignment.End) {
                if (content != null) {
                    content()
                }
            }
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
    }
}
