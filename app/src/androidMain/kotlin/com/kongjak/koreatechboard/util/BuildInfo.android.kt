package com.kongjak.koreatechboard.util

import com.kongjak.koreatechboard.BuildConfig

class AndroidBuildInfo : BuildInfo {
    override val buildType: BuildType = if (BuildConfig.BUILD_TYPE == "debug") BuildType.Debug else BuildType.Release
    override val applicationVersion: String = BuildConfig.VERSION_NAME
}

actual fun getBuildInfo(): BuildInfo = AndroidBuildInfo()