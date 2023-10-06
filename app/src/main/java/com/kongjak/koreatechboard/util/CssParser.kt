package com.kongjak.koreatechboard.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.graphics.toColorInt

fun parseColor(value: String): Color {
    return if (value.startsWith("#")) {
        Color(value.toColorInt())
    } else if (value.startsWith("rgb")) {
        val (r, g, b) = parseRGB(value)
        Color(red = r, green = g, blue = b)
    } else {
        Color.Unspecified
    }
}

fun parseFontWeight(value: String): FontWeight? {
    return if (value.isNumber()) {
        FontWeight(Integer.parseInt(value))
    } else {
        when (value) {
            "normal" -> FontWeight.Normal
            "bold" -> FontWeight.Bold
            "bolder" -> FontWeight.ExtraBold
            "lighter" -> FontWeight.Light
            else -> null
        }
    }
}

fun parseTextDecoration(value: String): TextDecoration {
    return when (value) {
        "line-through" -> TextDecoration.LineThrough
        "underline" -> TextDecoration.Underline
        else -> TextDecoration.None
    }
}

fun String.isNumber(): Boolean {
    return when (this.toIntOrNull()) {
        null -> false
        else -> true
    }
}

private fun parseRGB(rgb: String): IntArray {
    return rgb
        .replace("rgb", "")
        .replace("(", "")
        .replace(")", "")
        .replace(";", "")
        .split(",").map { Integer.parseInt(it.trim()) }.toIntArray()
}
