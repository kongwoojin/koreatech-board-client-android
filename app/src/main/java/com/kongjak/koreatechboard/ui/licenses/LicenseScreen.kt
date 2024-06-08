package com.kongjak.koreatechboard.ui.licenses

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.kongjak.koreatechboard.constraint.REGEX_HTTP_HTTPS
import com.kongjak.koreatechboard.ui.components.CustomClickableText
import kotlin.random.Random

@Composable
fun LicenseScreen() {
    val context = LocalContext.current

    context.assets.open("open_source_licenses.txt").use {
        val bytes = it.readBytes()
        val text = bytes.decodeToString()

        val urlOffsets = extractAllURLOffsets(text)

        val annotatedText = buildAnnotatedString {
            append(text)
            for (offset in urlOffsets) {
                val urlTag = "${Random.nextInt(0, 1000)}"
                addStringAnnotation(
                    tag = urlTag,
                    annotation = text.substring(offset.first, offset.last),
                    start = offset.first,
                    end = offset.last
                )
                addStyle(
                    SpanStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    offset.first,
                    offset.last
                )
            }
        }

        CustomClickableText(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(offset, offset).firstOrNull()?.let { url ->
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse(url.item))
                }
            }
        )
    }
}

private fun extractAllURLOffsets(text: String): List<IntRange> {
    val urlRegex = Regex(REGEX_HTTP_HTTPS)
    val matches = urlRegex.findAll(text)
    return matches.map { it.range.first..it.range.last + 1 }.toList()
}
