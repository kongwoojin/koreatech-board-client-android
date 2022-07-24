package com.kongjak.koreatechboard.binding

import android.text.Html
import android.text.util.Linkify
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kongjak.koreatechboard.adapter.BoardAdapter
import com.kongjak.koreatechboard.data.GlideImageGetter
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.model.Files
import java.util.regex.Pattern

object BindingAdapter {
    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: ArrayList<Board>?) {
        if (recyclerView.adapter == null)
            recyclerView.adapter = BoardAdapter()

        val boardAdapter = recyclerView.adapter as BoardAdapter

        boardAdapter.submitList(items)
    }

    @BindingAdapter("html_text")
    @JvmStatic
    fun setHtmlText(textView: TextView, text: String?) {

        if (text != null) {
            val htmlText = Html.fromHtml(
                text.toString(),
                HtmlCompat.FROM_HTML_MODE_COMPACT,
                GlideImageGetter(textView),
                null
            )
            textView.text = htmlText
        }
    }

    @BindingAdapter("file_text")
    @JvmStatic
    fun setFileText(textView: TextView, files: ArrayList<Files>?) {
        if (files != null) {
            for (file in files) {
                textView.append(file.fileName)
                if (files.iterator().hasNext())
                    textView.append("\n")
            }
            val transform = Linkify.TransformFilter { _, _ -> "" }

            for (file in files) {
                val pattern = Pattern.compile(file.fileName.replace("(", "\\(").replace(")", "\\)"))
                Linkify.addLinks(textView, pattern, file.fileUri, null, transform)
            }
        }
    }
}
