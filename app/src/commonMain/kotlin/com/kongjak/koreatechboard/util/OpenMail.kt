package com.kongjak.koreatechboard.util

expect fun openMail(address: String, subject: String, body: String, exception: (message: String) -> Unit)
