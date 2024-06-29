package com.kongjak.koreatechboard.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kongjak.koreatechboard.ui.theme.boardItemAboveText
import com.kongjak.koreatechboard.ui.theme.boardItemSubText
import com.kongjak.koreatechboard.ui.theme.boardItemTitle

@Composable
fun BoardItem(
    modifier: Modifier = Modifier,
    aboveText: String? = null,
    title: String,
    writer: String,
    date: String,
    isNew: Boolean = false,
    isRead: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (aboveText != null) {
            Text(
                text = aboveText,
                style = if (isRead) {
                    MaterialTheme.typography.boardItemAboveText.copy(
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    )
                } else {
                    MaterialTheme.typography.boardItemAboveText
                }
            )
        }
        Column {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth(),
                style = when {
                    isNew -> MaterialTheme.typography.boardItemTitle.copy(
                        fontWeight = FontWeight.Bold
                    )

                    isRead -> MaterialTheme.typography.boardItemTitle.copy(
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic
                    )

                    else -> MaterialTheme.typography.boardItemTitle
                }
            )
            Row(modifier = Modifier.padding(top = 4.dp)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = writer,
                    style = if (isRead) {
                        MaterialTheme.typography.boardItemSubText.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                    } else {
                        MaterialTheme.typography.boardItemSubText
                    }
                )
                Text(
                    text = date,
                    style = if (isRead) {
                        MaterialTheme.typography.boardItemSubText.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                    } else {
                        MaterialTheme.typography.boardItemSubText
                    }
                )
            }
        }
    }
}
