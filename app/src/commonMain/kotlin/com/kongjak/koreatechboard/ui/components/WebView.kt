package com.kongjak.koreatechboard.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebView(
    modifier: Modifier = Modifier,
    html: String,
    baseUrl: String,
    loading: @Composable () -> Unit = {}
)

fun fullHtml(html: String): String {
    return """
        <html>
        <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        </head>
        <body>
        <style>
            body {margin: 0!important;padding: 0!important;}
            table {height: auto;max-width: 100%;width: auto!important;}
            h1, h2, h3, h4, h5, h6, p, span {font-size: 16px!important;}
            *{line-height: normal!important;}
        </style>
        $html
        </body>
        </html>
    """.trimIndent()
}
