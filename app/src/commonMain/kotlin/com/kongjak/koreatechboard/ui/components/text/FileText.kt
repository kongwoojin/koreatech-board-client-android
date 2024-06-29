package com.kongjak.koreatechboard.ui.components.text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import coil3.compose.LocalPlatformContext
import com.kongjak.koreatechboard.domain.model.Files
import com.kongjak.koreatechboard.util.openUrl

@Composable
fun FileText(modifier: Modifier = Modifier, files: List<Files>) {
    val annotatedString = buildAnnotatedString {
        for (file in files) {
            pushStringAnnotation(file.fileName, file.fileUrl)
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(file.fileName)
            }
            pop()
            append("\n")
        }
    }

    val uriHandler = LocalUriHandler.current
    val context = LocalPlatformContext.current

    ClickableText(modifier = modifier, text = annotatedString) { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.let { url ->
                openUrl(
                    context = context,
                    uriHandler = uriHandler,
                    url = if (url.item.startsWith("http")) {
                        url.item
                    } else {
                        "http://www.koreatech.ac.kr/${url.item}"
                    }
                )
            }
    }
}
