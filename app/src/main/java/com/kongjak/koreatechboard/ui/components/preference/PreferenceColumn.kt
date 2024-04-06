package com.kongjak.koreatechboard.ui.components.preference

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PreferenceColumn(
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            content()
        }
    }
}

@Composable
fun PreferenceColumn(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = title,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                fontStyle = MaterialTheme.typography.labelLarge.fontStyle,
                fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                color = MaterialTheme.typography.labelLarge.color
            )
            content()
        }
    }
}

@Preview
@Composable
fun PreferenceColumnPreview() {
    Surface {
        PreferenceColumn(
        ) {
            Preference(title = "Title") {

            }
            Preference(title = "Title") {

            }
        }
    }
}