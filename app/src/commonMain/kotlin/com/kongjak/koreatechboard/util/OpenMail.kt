package com.kongjak.koreatechboard.util

expect fun openMail(context: Any, address: String, subject: String, body: String, exception: (message: String) -> Unit)
