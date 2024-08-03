package com.kongjak.koreatechboard.ui.components.dialog

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import koreatech_board.app.generated.resources.Res
import koreatech_board.app.generated.resources.dialog_confirm
import koreatech_board.app.generated.resources.dialog_dismiss
import org.jetbrains.compose.resources.stringResource

@Composable
fun TextDialog(
    title: String,
    content: String,
    onConfirmString: String = stringResource(Res.string.dialog_confirm),
    onDismissString: String = stringResource(Res.string.dialog_dismiss),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    BasicDialog(
        title = title,
        content = {
            Text(text = content, style = MaterialTheme.typography.bodyMedium)
        },
        onConfirmString = onConfirmString,
        onDismissString = onDismissString,
        onConfirm = onConfirm,
        onDismiss = onDismiss
    )
}
