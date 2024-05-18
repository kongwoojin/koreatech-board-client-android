package com.kongjak.koreatechboard.ui.components.dialog

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import koreatech_board.app.generated.resources.Res
import koreatech_board.app.generated.resources.dialog_confirm
import koreatech_board.app.generated.resources.dialog_dismiss
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TextFieldDialog(
    title: String,
    label: String = "",
    onConfirmString: String = stringResource(Res.string.dialog_confirm),
    onDismissString: String = stringResource(Res.string.dialog_dismiss),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    value: String,
    onValueChange: (String) -> Unit
) {
    BasicDialog(
        title = title,
        onConfirmString = onConfirmString,
        onDismissString = onDismissString,
        onConfirm = { onConfirm() },
        onDismiss = { onDismiss() }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = label) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onConfirm()
                }
            )
        )
    }
}
