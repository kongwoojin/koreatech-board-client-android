package com.kongjak.koreatechboard.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
actual fun getScreenWidth(): Int {
    return LocalConfiguration.current.screenWidthDp
}

@Composable
actual fun getScreenHeight(): Int {
    return LocalConfiguration.current.screenHeightDp
}
