package com.kongjak.koreatechboard.util

import androidx.compose.ui.platform.UriHandler

actual fun openUrl(context: Any, uriHandler: UriHandler, url: String) {
    uriHandler.openUri(url)
}