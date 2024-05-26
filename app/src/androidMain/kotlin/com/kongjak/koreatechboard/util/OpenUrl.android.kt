package com.kongjak.koreatechboard.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.platform.UriHandler

actual fun openUrl(context: Any, uriHandler: UriHandler, url: String) {
    val androidContext = context as Context
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(androidContext, Uri.parse(url))
}
