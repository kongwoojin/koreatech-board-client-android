package com.kongjak.koreatechboard.util

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.platform.UriHandler
import com.kongjak.koreatechboard.util.ContextUtil.getApplicationContext

actual fun openUrl(uriHandler: UriHandler, url: String) {
    val context = getApplicationContext()
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    customTabsIntent.launchUrl(context, Uri.parse(url))
}
