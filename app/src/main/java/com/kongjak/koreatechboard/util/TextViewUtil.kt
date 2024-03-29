package com.kongjak.koreatechboard.util

import android.text.Html
import android.widget.TextView
import androidx.core.text.HtmlCompat

var TextView.htmlText: String?
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
