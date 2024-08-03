package com.kongjak.koreatechboard.util

class DesktopBuildInfo : BuildInfo {
    override val buildType: BuildType = BuildType.Release
    override val applicationVersion: String = "1.0.0"
}

actual fun getBuildInfo(): BuildInfo = DesktopBuildInfo()
