package com.kongjak.koreatechboard.ui.components

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.kongjak.koreatechboard.util.HtmlTags
import com.kongjak.koreatechboard.util.parseColor
import com.kongjak.koreatechboard.util.parseFontWeight
import com.kongjak.koreatechboard.util.parseTextAlign
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
    var textAlign: TextAlign? = null

    var isTable = false

    // Image or link url
    var url = ""

    val tableRow = mutableListOf<AnnotatedString>()
    val tableItemList = mutableListOf<MutableList<AnnotatedString>>()

    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                when (parser.name.lowercase()) {
                    HtmlTags.BR.tag -> annotatedString = buildAnnotatedString {
                        append(annotatedString)
                        append("\n")
                    }

                    HtmlTags.B.tag -> fontWeight = FontWeight.Bold

                    HtmlTags.U.tag -> textDecoration = TextDecoration.Underline

                    HtmlTags.STRIKE.tag -> textDecoration = TextDecoration.LineThrough

                    HtmlTags.TABLE.tag -> isTable = true
                }

                for (i in 0 until parser.attributeCount) {
                    // parse styles
                    if (parser.getAttributeName(i) == "style") {
                        val styles = parser.getAttributeValue(i).trim()
                        val styleList =
                            styles.split(";").map { it.trim() }.filter { it.isNotEmpty() }
                        for (style in styleList) {
                            val (name, value) = style.split(":").map { it.trim().lowercase() }
                            when (name) {
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

                                "text-align" -> {
                                    textAlign = parseTextAlign(value)
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
                if (parser.text != null && parser.text.trim().isNotEmpty()) {
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
                if (parser.name != null) {
                    if (HtmlTags.valueOf(parser.name.uppercase()).isBlock) {
                        if (annotatedString.paragraphStyles.isEmpty()) {
                            // Apply paragraph style if tag is block tag
                            annotatedString = buildAnnotatedString {
                                pushStyle(style = ParagraphStyle(textAlign = textAlign))
                                append(annotatedString)
                                pop()
                            }
                            textAlign = null
                        }

                        when (parser.name.lowercase()) {
                            HtmlTags.TABLE.tag -> {
                                Table(modifier = modifier, tableItems = tableItemList)
                                isTable = false
                                tableItemList.clear()
                            }

                            HtmlTags.TBODY.tag, HtmlTags.THEAD.tag -> {
                                /*
                                Don't render tbody and thead,
                                will render at table
                                 */
                            }

                            HtmlTags.TD.tag -> {
                                tableRow.add(annotatedString)

                                annotatedString = buildAnnotatedString { }
                            }

                            HtmlTags.TR.tag -> {
                                val tmp = mutableListOf<AnnotatedString>()
                                tableRow.forEach {
                                    tmp.add(it)
                                }
                                tableItemList.add(tmp)
                                tableRow.clear()
                            }

                            else -> {
                                if (!isTable) {
                                    Text(
                                        modifier = modifier,
                                        text = annotatedString
                                    )

                                    annotatedString = buildAnnotatedString { }

                                } else {
                                    annotatedString = buildAnnotatedString {
                                        append(annotatedString)
                                        append("\n")
                                    }
                                }
                            }
                        }
                    } else {
                        when (parser.name.lowercase()) {
                            HtmlTags.IMG.tag -> GlideImage(
                                model = url,
                                contentDescription = "Image"
                            )
                        }
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
