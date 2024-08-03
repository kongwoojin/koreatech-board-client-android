package com.kongjak.koreatechboard.util

class DesktopPlatform : PlatformInfo {
    override val platform: PlatformType = PlatformType.Desktop
    override val os: String = System.getProperty("os.name")
    override val isFirebaseSupported: Boolean = false
    override val isDynamicThemeSupported: Boolean = false
}

actual fun getPlatformInfo(): PlatformInfo = DesktopPlatform()
