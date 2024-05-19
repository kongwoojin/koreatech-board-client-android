package com.kongjak.koreatechboard.ui.permission

import androidx.compose.runtime.Composable

@Composable
expect fun CheckNotificationPermission()

@Composable
expect fun RequestNotificationPermission()

@Composable
expect fun isNotificationPermissionGranted(): Boolean
