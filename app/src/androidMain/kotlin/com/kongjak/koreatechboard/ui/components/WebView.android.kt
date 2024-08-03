package com.kongjak.koreatechboard.ui.components

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun WebView(
    modifier: Modifier,
    html: String,
    baseUrl: String,
    loading: @Composable () -> Unit
) {
    var isWebViewLoaded by remember { mutableStateOf(false) }

    if (!isWebViewLoaded) {
        loading()
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    isForceDarkAllowed = true
                }
                webViewClient = KoreatechBoardWebViewClient(
                    setLoaded = { loaded ->
                        isWebViewLoaded = loaded
                    }
                )
                isVerticalScrollBarEnabled = false

                setBackgroundColor(Color.TRANSPARENT)
                settings.apply {
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    cacheMode = WebSettings.LOAD_NO_CACHE
                    setSupportZoom(false)
                    builtInZoomControls = false
                }
            }
        },
        modifier = modifier,
        update = {
            it.loadDataWithBaseURL(
                baseUrl,
                fullHtml(html),
                "text/html",
                "UTF-8",
                null
            )
        }
    )
}

class KoreatechBoardWebViewClient(private val setLoaded: (Boolean) -> Unit = {}) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(view!!.context, Uri.parse(url))
            return true
        }
        return false
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        setLoaded(false)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        setLoaded(true)
    }
}
