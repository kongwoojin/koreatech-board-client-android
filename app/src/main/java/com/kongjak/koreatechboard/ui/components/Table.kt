package com.kongjak.koreatechboard.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Table(
    modifier: Modifier = Modifier,
    tableItems: MutableList<MutableList<AnnotatedString>>
) {
    Column(modifier = modifier.border(1.dp, MaterialTheme.colorScheme.onSurface)) {
        for (row in tableItems) {
            Row(
                modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.onSurface),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                for (item in row) {
                    TableItem(
                        modifier = Modifier
                            .weight(1f),
                        item = item
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.TableItem(
    modifier: Modifier = Modifier,
    item: AnnotatedString
) {
    // Force align start for now
    if (item.text.isNotEmpty()) {
        Text(modifier = modifier, text = item.trim(), textAlign = TextAlign.Start)
    }
}

fun AnnotatedString.trim(): AnnotatedString {
    return AnnotatedString(this.text.trim(), this.spanStyles)
}
