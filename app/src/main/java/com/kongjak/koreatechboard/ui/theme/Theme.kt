package com.kongjak.koreatechboard.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Immutable
data class NavigationColors(
    val bottomNavigationBackground: Color
)

val LocalNavigationColors = staticCompositionLocalOf {
    NavigationColors(
        bottomNavigationBackground = Color.Unspecified
    )
}

private val DarkColors = darkColors(
    primary = KoreatechSub1,
    onPrimary = Color.White,
    secondary = KoreatechSub3,
    onSecondary = Color.White,
    background = darkBackground,
    onBackground = Color.White
)

private val LightColors = lightColors(
    primary = KoreatechMain1,
    secondary = KoreatechMain1,
    background = whiteBackground,
    onBackground = Color.Black
)

private val DarkNavigationColors = NavigationColors(
    bottomNavigationBackground = darkBackground
)

private val WhiteNavigationColors = NavigationColors(
    bottomNavigationBackground = whiteBackground
)

@Composable
fun KoreatechBoardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val color = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) DarkColors else LightColors
        }

        darkTheme -> DarkColors
        else -> LightColors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = color.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val navigationColors = if (darkTheme) DarkNavigationColors else WhiteNavigationColors

    CompositionLocalProvider(LocalNavigationColors provides navigationColors) {
        MaterialTheme(
            colors = color,
            content = content
        )
    }
}

object NavigationTheme {
    val colors: NavigationColors
        @Composable
        get() = LocalNavigationColors.current
}
