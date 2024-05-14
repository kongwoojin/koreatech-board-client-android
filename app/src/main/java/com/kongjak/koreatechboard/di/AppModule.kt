package com.kongjak.koreatechboard.di

import android.content.Context
import com.kongjak.koreatechboard.util.NetworkUtil

fun provideNetworkUtil(
    context: Context
): NetworkUtil {
    return NetworkUtil(context)
}