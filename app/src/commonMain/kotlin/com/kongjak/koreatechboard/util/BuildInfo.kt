package com.kongjak.koreatechboard.util

sealed class BuildType {
    data object Debug : BuildType()
    data object Release : BuildType()
}

interface BuildInfo {
    val buildType: BuildType
    val applicationVersion: String
}

expect fun getBuildInfo(): BuildInfo
