package com.kongjak.koreatechboard.util

import android.app.Application
import android.content.Context

object ContextUtil {
    private lateinit var application: Application

    fun setContext(context: Context) {
        application = context as Application
    }

    fun getApplicationContext(): Context {
        if (::application.isInitialized.not()) throw IllegalStateException("Context is not initialized")
        return application.applicationContext
    }
}
