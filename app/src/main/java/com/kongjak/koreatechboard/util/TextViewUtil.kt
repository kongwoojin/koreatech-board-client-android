package com.kongjak.koreatechboard.util

import android.text.Html
import android.text.util.Linkify
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.kongjak.koreatechboard.domain.model.Files
import java.util.regex.Pattern

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
