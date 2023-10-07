package com.kongjak.koreatechboard.ui.components

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kongjak.koreatechboard.util.HtmlTags
import com.kongjak.koreatechboard.util.parseColor
import com.kongjak.koreatechboard.util.parseFontWeight
import com.kongjak.koreatechboard.util.parseTextDecoration
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HtmlView(
    modifier: Modifier = Modifier,
    html: String
) {
    val xmlPullParserFactory = XmlPullParserFactory.newInstance()
    val parser = xmlPullParserFactory.newPullParser()
    parser.setInput(html.byteInputStream(), "UTF-8")

    var eventType = parser.eventType

    var annotatedString = buildAnnotatedString { }

    /* Styles */
    var color = Color.Unspecified
    var background = Color.Unspecified
    var fontWeight: FontWeight? = null
    var textDecoration: TextDecoration = TextDecoration.None

    // Image or link url
    var url = ""

    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                when (parser.name.lowercase()) {
                    HtmlTags.BR.tag -> annotatedString = buildAnnotatedString {
                        append(annotatedString)
                        append("\n")
                    }

                    HtmlTags.IMG.tag -> GlideImage(
                        model = parser.getAttributeValue(null, "src"),
                        contentDescription = ""
                    )

                    HtmlTags.B.tag -> fontWeight = FontWeight.Bold

                    HtmlTags.U.tag -> textDecoration = TextDecoration.Underline

                    HtmlTags.STRIKE.tag -> textDecoration = TextDecoration.LineThrough
                }

                for (i in 0 until parser.attributeCount) {
                    // parse styles
                    if (parser.getAttributeName(i) == "style") {
                        val styles = parser.getAttributeValue(i).trim()
                        val styleList =
                            styles.split(";").map { it.trim() }.filter { it.isNotEmpty() }
                        for (style in styleList) {
                            val (name, value) = style.split(":").map { it.trim() }
                            when (name.lowercase()) {
                                "color" -> {
                                    color = parseColor(value)
                                }

                                "background-color" -> {
                                    background = parseColor(value)
                                }

                                "font-weight" -> {
                                    fontWeight = parseFontWeight(value)
                                }

                                "text-decoration" -> {
                                    textDecoration = parseTextDecoration(value)
                                }
                            }
                        }
                    } else if (parser.getAttributeName(i) == "href") {
                        url = parser.getAttributeValue(i)
                    } else if (parser.getAttributeName(i) == "src") {
                        url = parser.getAttributeValue(i)
                    }
                }
            }

            XmlPullParser.TEXT -> {
                if (parser.text.trim().isNotEmpty()) {
                    annotatedString = buildAnnotatedString {
                        append(annotatedString)
                        pushStyle(
                            style = SpanStyle(
                                color = color,
                                background = background,
                                fontWeight = fontWeight,
                                textDecoration = textDecoration
                            )
                        )
                        append(parser.text.trim())
                        pop()
                    }

                    /* Reset styles */
                    color = Color.Unspecified
                    background = Color.Unspecified
                    fontWeight = null
                    textDecoration = TextDecoration.None

                    // Reset url
                    url = ""
                }
            }

            XmlPullParser.END_TAG -> {
                // Build UI
                if (HtmlTags.valueOf(parser.name.uppercase()).isBlock) {
                    when (parser.name) {
                        HtmlTags.IMG.tag -> GlideImage(
                            model = url,
                            contentDescription = ""
                        )

                        else -> {
                            Text(
                                modifier = modifier,
                                text = annotatedString
                            )

                            annotatedString = buildAnnotatedString { }
                        }
                    }
                }
            }
        }
        try {
            eventType = parser.next()
        } catch (e: XmlPullParserException) {
            e.message?.let { Log.e("Exception", it) }
        }
    }
}
