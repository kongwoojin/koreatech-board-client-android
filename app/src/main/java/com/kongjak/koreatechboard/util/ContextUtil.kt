package com.kongjak.koreatechboard.util
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

// from https://github.com/google/accompanist/blob/main/permissions/src/main/java/com/google/accompanist/permissions/PermissionsUtil.kt#L132

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Cannot find Activity")
}