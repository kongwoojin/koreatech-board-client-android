package com.kongjak.koreatechboard.util

class AndroidPlatform : PlatformInfo {
    override val platform: PlatformType = PlatformType.Android
    override val os: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    override val isFirebaseSupported: Boolean = true
    override val isDynamicThemeSupported: Boolean = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) true else false
}

actual fun getPlatformInfo(): PlatformInfo = AndroidPlatform()
