package com.kongjak.koreatechboard.ui.components

import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.kongjak.koreatechboard.util.HtmlImageGetter

@Composable
fun HtmlText(html: String, isDarkTheme: Boolean) {
    key(isDarkTheme) {
        AndroidView(
            factory = { context ->
                TextView(context).apply {
                    htmlText = html
                    textSize = 16F
                    autoLinkMask = 0x0f
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            update = {
                it.setTextColor(
                    if (isDarkTheme) {
                        Color(0xFFFFFFFF)
                    } else {
                        Color(0xFF000000)
                    }.toArgb()
                )
            }
        )
    }
}

private var TextView.htmlText: String?
    get() {
        return null
    }
    set(value) {
        if (value != null) {
            val htmlText = Html.fromHtml(
                value.toString(),
                HtmlCompat.FROM_HTML_MODE_COMPACT,
                HtmlImageGetter(this),
                null
            )
            text = htmlText
        }
    }
