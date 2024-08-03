package com.kongjak.koreatechboard.util

import java.awt.Desktop
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.net.URLEncoder

actual fun openMail(address: String, subject: String, body: String, exception: (message: String) -> Unit) {
    val encodedSubject = URLEncoder.encode(subject, "UTF-8").replace("+", "%20")
    val encodedBody = URLEncoder.encode(body, "UTF-8").replace("+", "%20")
    val mailto = "mailto:$address?subject=$encodedSubject&body=$encodedBody"

    // Check if Desktop is supported and open the mail client
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
        try {
            val mailtoURI = URI(mailto)
            Desktop.getDesktop().mail(mailtoURI)
        } catch (e: URISyntaxException) {
            exception("Error opening mail client: ${e.message}")
        } catch (e: IOException) {
            exception("Error opening mail client: ${e.message}")
        }
    } else {
        exception("Desktop or mail action is not supported on this platform.")
    }
}
