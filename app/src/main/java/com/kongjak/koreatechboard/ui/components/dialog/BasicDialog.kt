package com.kongjak.koreatechboard.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16)
        ) {
            Box(modifier = modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
fun BasicDialog(
    modifier: Modifier = Modifier,
    title: String,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16)
        ) {
            Column(
                modifier = modifier.padding(vertical = 16.dp, horizontal = 32.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                Box(modifier = Modifier.padding(bottom = 16.dp)) {
                    content()
                }
            }
        }
    }
}

@Composable
fun BasicDialog(
    modifier: Modifier = Modifier,
    title: String,
    onActionString: String,
    onAction: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onAction) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16)
        ) {
            Column(
                modifier = modifier.padding(vertical = 16.dp, horizontal = 32.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                content()
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onAction() }) {
                        Text(text = onActionString)
                    }
                }
            }
        }
    }
}

@Composable
fun BasicDialog(
    modifier: Modifier = Modifier,
    title: String,
    onConfirmString: String = stringResource(id = R.string.dialog_confirm),
    onDismissString: String = stringResource(id = R.string.dialog_dismiss),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16)
        ) {
            Column(
                modifier = modifier.padding(vertical = 16.dp, horizontal = 32.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                content()
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
        content = {
            Text("Content")
        },
        onDismissRequest = {}
    )
}

@Preview
@Composable
fun BasicDialogWithTitlePreview() {
    BasicDialog(
        title = "Title",
        content = {
            Text("Content")
        },
        onDismissRequest = {}
    )
}

@Preview
@Composable
fun BasicDialogWithActionPreview() {
    BasicDialog(
        title = "Title",
        onActionString = "Action",
        onAction = {},
        content = {
            Text("Content")
        }
    )
}

@Preview
@Composable
fun BasicDialogWithConfirmPreview() {
    BasicDialog(
        title = "Title",
        onConfirm = {},
        onDismiss = {},
        content = {
            Text("Content")
        }
    )
}
