package com.kongjak.koreatechboard.util

import android.text.Html
import android.text.util.Linkify
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.kongjak.koreatechboard.domain.model.Files
import java.util.regex.Pattern

var TextView.fileText: ArrayList<Files>?
    get() {
        return null
    }
    set(value) {
        if (value != null) {
            for (file in value) {
                append(file.fileName)
                if (value.iterator().hasNext()) {
                    append("\n")
                }
            }
            val transform = Linkify.TransformFilter { _, _ -> "" }

            for (file in value) {
                val pattern =
                    Pattern.compile(file.fileName.replace("(", "\\(").replace(")", "\\)"))
                Linkify.addLinks(this, pattern, file.fileUrl, null, transform)
            }
        }
    }

var TextView.htmlText: String?
    get() {
        return null
    }
    set(value) {
        if (value != null) {
            val htmlText = Html.fromHtml(
                value.toString(),
                HtmlCompat.FROM_HTML_MODE_COMPACT,
                GlideImageGetter(this),
                null
            )
            text = htmlText
        }
    }
