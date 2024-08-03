package com.kongjak.koreatechboard.util

import androidx.compose.ui.platform.UriHandler

actual fun openUrl(uriHandler: UriHandler, url: String) {
    uriHandler.openUri(url)
}
