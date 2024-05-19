package com.kongjak.koreatechboard.ui.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.kongjak.koreatechboard.ui.components.dialog.TextDialog
import koreatech_board.app.generated.resources.Res
import koreatech_board.app.generated.resources.permission_notification_denied
import koreatech_board.app.generated.resources.permission_notification_request_content
import koreatech_board.app.generated.resources.permission_notification_request_rationale_content
import koreatech_board.app.generated.resources.permission_notification_request_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
actual fun CheckNotificationPermission() {
    val isPermissionGranted = isNotificationPermissionGranted()

    if (!isPermissionGranted) {
        RequestNotificationPermission()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun RequestNotificationPermission() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        return
    }
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (permissionState.status.shouldShowRationale) {
        RationaleDialog()
    } else {
        PermissionDialog { permissionState.launchPermissionRequest() }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun isNotificationPermissionGranted(): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        return true
    }
    return rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS).status.isGranted
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun RationaleDialog() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        TextDialog(
            title = stringResource(Res.string.permission_notification_request_title),
            content = stringResource(Res.string.permission_notification_request_rationale_content),
            onConfirm = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun PermissionDialog(content: () -> Unit) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(true) }
    val notificationPermissionDenied = stringResource(Res.string.permission_notification_denied)
    if (showDialog) {
        TextDialog(
            title = stringResource(Res.string.permission_notification_request_title),
            content = stringResource(Res.string.permission_notification_request_content),
            onConfirm = {
                content()
                showDialog = false
            },
            onDismiss = {
                Toast.makeText(
                    context,
                    notificationPermissionDenied,
                    Toast.LENGTH_SHORT
                ).show()
                showDialog = false
            }
        )
    }
}
