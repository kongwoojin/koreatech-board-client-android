package com.kongjak.koreatechboard.util

sealed class PlatformType {
    data object Android : PlatformType()
    data object Desktop : PlatformType()
}

interface PlatformInfo {
    val platform: PlatformType
    val os: String
    val isFirebaseSupported: Boolean
    val isDynamicThemeSupported: Boolean
}

expect fun getPlatformInfo(): PlatformInfo
