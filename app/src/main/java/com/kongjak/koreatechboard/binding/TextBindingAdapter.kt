package com.kongjak.koreatechboard.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextBindingAdapter {
    @BindingAdapter("cjk_text")
    @JvmStatic
    fun setCjkText(textView: TextView, text: String) {
        val newText = text.replace(" ", "\u00A0")
        textView.text = newText
    }
}