package com.kongjak.koreatechboard.ui.components

import android.text.Html
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.kongjak.koreatechboard.util.HtmlImageGetter

@Composable
fun HtmlText(
    modifier: Modifier = Modifier,
    html: String
) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                htmlText = html
                textSize = 16F
                autoLinkMask = 0x0f
                setTextIsSelectable(true)
            }
        },
        modifier = modifier
    )
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
