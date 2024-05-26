package com.kongjak.koreatechboard.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFBrowser
import org.cef.browser.CefRendering

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun WebView(
    modifier: Modifier,
    html: String,
    baseUrl: String,
    loading: @Composable () -> Unit
) {
    val client = remember { KCEF.newClientBlocking() }

    val width = LocalWindowInfo.current.containerSize.width.dp

    val browser = remember {
        client.createBrowserWithHtml(
            html = html,
            KCEFBrowser.BLANK_URI,
            CefRendering.DEFAULT,
            false
        )
    }

    /*
     * SwingPanel need to define the size of the panel.
     * The width is the same as the window width.
     * Currently, the height is calculated by the number of rows in the table.
     * And, we hard-coded the size of row as 30.
     * This should be corrected in the future.
     */

    val rowHeight = 30
    val rowCount = html.split("tr").size - 1

    SwingPanel(
        factory = {
            browser.uiComponent
        },
        modifier = modifier.width(width).height((rowHeight * rowCount).dp)
    )
}
