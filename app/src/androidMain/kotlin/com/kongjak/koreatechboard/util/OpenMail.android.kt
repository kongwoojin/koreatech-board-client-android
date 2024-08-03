package com.kongjak.koreatechboard.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.kongjak.koreatechboard.util.ContextUtil.getApplicationContext

actual fun openMail(address: String, subject: String, body: String, exception: (message: String) -> Unit) {
    val context = getApplicationContext()

    val mailIntent = Intent(Intent.ACTION_SENDTO)
    mailIntent.data = Uri.parse("mailto:")
    mailIntent.putExtra(
        Intent.EXTRA_EMAIL,
        arrayOf(address)
    )
    mailIntent.putExtra(
        Intent.EXTRA_SUBJECT,
        subject
    )
    mailIntent.putExtra(
        Intent.EXTRA_TEXT,
        body
    )
    try {
        mailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(mailIntent)
    } catch (e: ActivityNotFoundException) {
        exception("Error opening mail client: ${e.message}")
    }
}
