package com.kongjak.koreatechboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kongjak.koreatechboard.R

@Composable
fun BasicDialog(
    title: String,
    content: String,
    onConfirmString: String = stringResource(id = R.string.dialog_confirm),
    onDismissString: String = stringResource(id = R.string.dialog_dismiss),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = onDismissString)
                    }

                    TextButton(onClick = { onConfirm() }) {
                        Text(text = onConfirmString)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BasicDialogPreview() {
    BasicDialog(
        title = "Title",
        content = "Content",
        onConfirm = {},
        onDismiss = {}
    )
}
