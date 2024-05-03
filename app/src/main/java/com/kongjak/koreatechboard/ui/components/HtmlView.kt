package com.kongjak.koreatechboard.ui.components

import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.kongjak.koreatechboard.util.parseColor
import com.kongjak.koreatechboard.util.parseSpanStyle
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import kotlin.random.Random

@Composable
fun HtmlView(
    modifier: Modifier = Modifier,
    html: String,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    image: @Composable (String, String) -> Unit,
    webView: @Composable (String) -> Unit
) {
    val context = LocalContext.current
    val xmlPullParserFactory = XmlPullParserFactory.newInstance()
    val parser = xmlPullParserFactory.newPullParser()
    parser.setInput(html.byteInputStream(), "UTF-8")

    val customViewPosition: MutableList<Int> = mutableListOf(0)
    val customViewQueue = ArrayDeque<CustomView>()

    var isWebView = false
    var webViewHtml = ""

    var eventType = parser.eventType

    val text = buildAnnotatedString {
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        HTML_BR -> {
                            appendNewLine()
                        }

                        HTML_P -> {
                            appendNewLine()
                        }

                        HTML_UL -> {
                            append("\u2022")
                        }

                        HTML_LI -> {
                            append("\u2022")
                        }

                        HTML_DIV -> {
                            appendNewLine()
                        }

                        HTML_SPAN -> {
                            if (isWebView) {
                                parser.getAttributeValue(null, "style")?.let { style ->
                                    webViewHtml += "<span style=\"$style\">"
                                } ?: {
                                    webViewHtml += "<span>"
                                }
                            } else {
                                val style = parser.getAttributeValue(null, "style")
                                pushStyle(parseSpanStyle(style, isDarkMode))
                            }
                        }

                        HTML_STRONG,
                        HTML_B -> {
                            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        }

                        HTML_EM,
                        HTML_CITE,
                        HTML_DFN,
                        HTML_I -> {
                            pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                        }

                        HTML_BIG -> {
                            pushStyle(SpanStyle(fontSize = 20.sp))
                        }

                        HTML_SMALL -> {
                            pushStyle(SpanStyle(fontSize = 10.sp))
                        }

                        HTML_FONT -> {
                            val size = parser.getAttributeValue(null, "size")
                            val color = parser.getAttributeValue(null, "color")
                            var style = SpanStyle()
                            if (size != null) {
                                style = style.copy(fontSize = size.toInt().sp)
                            }
                            if (color != null) {
                                style = style.copy(color = parseColor(color, isDarkMode))
                            }
                            pushStyle(style)
                        }

                        HTML_BLOCKQUOTE -> {}
                        HTML_TT -> {
                            pushStyle(SpanStyle(fontFamily = FontFamily.Monospace))
                        }

                        HTML_A -> {
                            val href = parser.getAttributeValue(null, "href")
                            val urlTag = "url_${Random.nextInt(0, 1000)}"
                            pushStringAnnotation(
                                tag = urlTag,
                                annotation = href
                            )
                        }

                        HTML_U -> {
                            pushStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                        }

                        HTML_DEL,
                        HTML_S,
                        HTML_STRIKE -> {
                            pushStyle(SpanStyle(textDecoration = TextDecoration.LineThrough))
                        }

                        HTML_SUP -> {
                            pushStyle(
                                SpanStyle(
                                    fontSize = 10.sp,
                                    baselineShift = BaselineShift.Superscript
                                )
                            )
                        }

                        HTML_SUB -> {
                            pushStyle(
                                SpanStyle(
                                    fontSize = 10.sp,
                                    baselineShift = BaselineShift.Subscript
                                )
                            )
                        }

                        HTML_H1 -> {
                            pushStyle(SpanStyle(fontSize = 24.sp))
                            appendNewLine()
                        }

                        HTML_H2 -> {
                            pushStyle(SpanStyle(fontSize = 22.sp))
                            appendNewLine()
                        }

                        HTML_H3 -> {
                            pushStyle(SpanStyle(fontSize = 20.sp))
                            appendNewLine()
                        }

                        HTML_H4 -> {
                            pushStyle(SpanStyle(fontSize = 18.sp))
                            appendNewLine()
                        }

                        HTML_H5 -> {
                            pushStyle(SpanStyle(fontSize = 16.sp))
                            appendNewLine()
                        }

                        HTML_H6 -> {
                            pushStyle(SpanStyle(fontSize = 14.sp))
                            appendNewLine()
                        }

                        HTML_IMG -> {
                            customViewPosition.add(length)

                            customViewQueue.add(
                                CustomView(
                                    type = CustomView.CustomViewType.IMAGE,
                                    data = mapOf(
                                        "src" to parser.getAttributeValue(null, "src"),
                                        "alt" to (parser.getAttributeValue(null, "alt") ?: "")
                                    )
                                )
                            )
                        }

                        HTML_TABLE -> {
                            isWebView = true
                            val attributeMap = mutableMapOf<String, String>()

                            for (i in 0 until parser.attributeCount) {
                                val attributeName = parser.getAttributeName(i)
                                val attributeValue = parser.getAttributeValue(null, attributeName)
                                attributeMap[attributeName] = attributeValue
                            }
                            webViewHtml += "<table ${
                                attributeMap.map { (key, value) -> "$key=\"$value\"" }
                                    .joinToString(" ")
                            }>"
                        }

                        HTML_TR -> {
                            val attributeMap = mutableMapOf<String, String>()

                            for (i in 0 until parser.attributeCount) {
                                val attributeName = parser.getAttributeName(i)
                                val attributeValue = parser.getAttributeValue(null, attributeName)
                                attributeMap[attributeName] = attributeValue
                            }
                            webViewHtml += "<tr ${
                                attributeMap.map { (key, value) -> "$key=\"$value\"" }
                                    .joinToString(" ")
                            }>"
                        }

                        HTML_TD -> {
                            val attributeMap = mutableMapOf<String, String>()

                            for (i in 0 until parser.attributeCount) {
                                val attributeName = parser.getAttributeName(i)
                                val attributeValue = parser.getAttributeValue(null, attributeName)
                                attributeMap[attributeName] = attributeValue
                            }
                            webViewHtml += "<td ${
                                attributeMap.map { (key, value) -> "$key=\"$value\"" }
                                    .joinToString(" ")
                            }>"
                        }

                        HTML_TH -> {
                            val attributeMap = mutableMapOf<String, String>()

                            for (i in 0 until parser.attributeCount) {
                                val attributeName = parser.getAttributeName(i)
                                val attributeValue = parser.getAttributeValue(null, attributeName)
                                attributeMap[attributeName] = attributeValue
                            }
                            webViewHtml += "<th ${
                                attributeMap.map { (key, value) -> "$key=\"$value\"" }
                                    .joinToString(" ")
                            }>"
                        }

                        HTML_COLGROUP -> {
                            val attributeMap = mutableMapOf<String, String>()

                            for (i in 0 until parser.attributeCount) {
                                val attributeName = parser.getAttributeName(i)
                                val attributeValue = parser.getAttributeValue(null, attributeName)
                                attributeMap[attributeName] = attributeValue
                            }
                            webViewHtml += "<colgroup ${
                                attributeMap.map { (key, value) -> "$key=\"$value\"" }
                                    .joinToString(" ")
                            }>"
                        }

                        HTML_COL -> {
                            val attributeMap = mutableMapOf<String, String>()

                            for (i in 0 until parser.attributeCount) {
                                val attributeName = parser.getAttributeName(i)
                                val attributeValue = parser.getAttributeValue(null, attributeName)
                                attributeMap[attributeName] = attributeValue
                            }
                            webViewHtml += "<col ${
                                attributeMap.map { (key, value) -> "$key=\"$value\"" }
                                    .joinToString(" ")
                            }>"
                        }
                    }
                }

                XmlPullParser.TEXT -> {
                    if (parser.text.isNotBlank()) {
                        if (isWebView) {
                            webViewHtml += parser.text
                        } else {
                            append(parser.text)
                        }
                    }
                }

                XmlPullParser.END_TAG -> {
                    when (parser.name) {
                        HTML_BR -> {}
                        HTML_P -> {}
                        HTML_UL -> {}
                        HTML_LI -> {}
                        HTML_DIV -> {}
                        HTML_BLOCKQUOTE -> {}
                        HTML_A,
                        HTML_STRONG,
                        HTML_B,
                        HTML_EM,
                        HTML_CITE,
                        HTML_DFN,
                        HTML_I,
                        HTML_BIG,
                        HTML_SMALL,
                        HTML_FONT,
                        HTML_TT,
                        HTML_U,
                        HTML_DEL,
                        HTML_S,
                        HTML_STRIKE,
                        HTML_SUP,
                        HTML_SUB,
                        HTML_H1,
                        HTML_H2,
                        HTML_H3,
                        HTML_H4,
                        HTML_H5,
                        HTML_H6 -> {
                            pop()
                        }

                        HTML_SPAN -> {
                            if (isWebView) {
                                webViewHtml += "</span>"
                            } else {
                                pop()
                            }
                        }

                        HTML_TABLE -> {
                            webViewHtml += "</table>"
                            customViewPosition.add(length)
                            customViewQueue.add(
                                CustomView(
                                    type = CustomView.CustomViewType.WEB_VIEW,
                                    data = mapOf(
                                        "html" to webViewHtml
                                    )
                                )
                            )
                            isWebView = false
                            webViewHtml = ""
                        }

                        HTML_TR -> {
                            webViewHtml += "</tr>"
                        }

                        HTML_TD -> {
                            webViewHtml += "</td>"
                        }

                        HTML_TH -> {
                            webViewHtml += "</th>"
                        }

                        HTML_COLGROUP -> {
                            webViewHtml += "</colgroup>"
                        }

                        HTML_COL -> {
                            webViewHtml += "</col>"
                        }
                    }
                }
            }

            try {
                eventType = parser.next()
            } catch (e: XmlPullParserException) {
                e.message?.let { Log.e("Exception", it) }
            } catch (e: ArrayIndexOutOfBoundsException) {
                e.message?.let { Log.e("Exception", it) }
            }
        }
    }

    var pos = 0
    while (customViewQueue.isNotEmpty()) {
        text.subSequence(customViewPosition[pos], customViewPosition[pos + 1]).let { subText ->
            CustomClickableText(
                modifier = modifier,
                text = subText,
                onClick = { offset ->
                    subText.getStringAnnotations(offset, offset)
                        .firstOrNull()?.let { url ->
                            val builder = CustomTabsIntent.Builder()
                            val customTabsIntent = builder.build()
                            customTabsIntent.launchUrl(context, Uri.parse(url.item))
                        }
                })
        }
        pos++

        Box(modifier = modifier) {
            RenderCustomView(
                customViewQueue.removeFirst(),
                image = image,
                webView = webView
            )
        }
    }

    text.subSequence(customViewPosition[pos], text.length).let { subText ->
        CustomClickableText(
            modifier = modifier,
            text = subText,
            onClick = { offset ->
                subText.getStringAnnotations(offset, offset)
                    .firstOrNull()?.let { url ->
                        val builder = CustomTabsIntent.Builder()
                        val customTabsIntent = builder.build()
                        customTabsIntent.launchUrl(context, Uri.parse(url.item))
                    }
            })
    }
}

@Composable
fun RenderCustomView(
    customView: CustomView,
    image: @Composable (String, String) -> Unit,
    webView: @Composable (String) -> Unit
) {
    when (customView.type) {
        CustomView.CustomViewType.IMAGE -> {
            val src = customView.data["src"] as String
            val alt = customView.data["alt"] as String
            image(
                src,
                alt
            )
        }

        CustomView.CustomViewType.WEB_VIEW -> {
            webView(customView.data["html"] as String)
        }
    }
}

fun AnnotatedString.Builder.appendNewLine() {
    val len = this.length
    if (len > 0 && this.toAnnotatedString().text[len - 1] != '\n') {
        Log.d("HTML", this.toAnnotatedString().toString())
        append("\n\n")
    }
}

const val HTML_BR = "br"
const val HTML_P = "p"
const val HTML_UL = "ul"
const val HTML_LI = "li"
const val HTML_DIV = "div"
const val HTML_SPAN = "span"
const val HTML_STRONG = "strong"
const val HTML_B = "b"
const val HTML_EM = "em"
const val HTML_CITE = "cite"
const val HTML_DFN = "dfn"
const val HTML_I = "i"
const val HTML_BIG = "big"
const val HTML_SMALL = "small"
const val HTML_FONT = "font"
const val HTML_BLOCKQUOTE = "blockquote"
const val HTML_TT = "tt"
const val HTML_A = "a"
const val HTML_U = "u"
const val HTML_DEL = "del"
const val HTML_S = "s"
const val HTML_STRIKE = "strike"
const val HTML_SUP = "sup"
const val HTML_SUB = "sub"
const val HTML_H1 = "h1"
const val HTML_H2 = "h2"
const val HTML_H3 = "h3"
const val HTML_H4 = "h4"
const val HTML_H5 = "h5"
const val HTML_H6 = "h6"
const val HTML_IMG = "img"
const val HTML_TABLE = "table"
const val HTML_TR = "tr"
const val HTML_TD = "td"
const val HTML_TH = "th"
const val HTML_COLGROUP = "colgroup"
const val HTML_COL = "col"

data class CustomView(
    val type: CustomViewType,
    val data: Map<String, Any>
) {
    override fun toString(): String {
        return "CustomView(type=$type, data=$data)"
    }

    enum class CustomViewType {
        IMAGE,
        WEB_VIEW
    }
}
