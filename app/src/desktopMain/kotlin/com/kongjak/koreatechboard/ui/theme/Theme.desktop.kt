package com.kongjak.koreatechboard.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
actual fun getSeedColor(): Color {
    return Color(0xff7f00)
}

@Composable
actual fun PlatformSpecificTheme(colorScheme: ColorScheme, isDarkTheme: Boolean) {
}
