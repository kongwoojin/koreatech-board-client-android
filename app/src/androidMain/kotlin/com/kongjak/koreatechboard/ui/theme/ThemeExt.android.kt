package com.kongjak.koreatechboard.ui.theme

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.kongjak.koreatechboard.util.ContextUtil
import com.kongjak.koreatechboard.util.ContextUtil.getApplicationContext
import org.koin.compose.getKoin
import org.koin.compose.koinInject

@RequiresApi(Build.VERSION_CODES.S)
actual fun fixedDynamicLightColorScheme() = lightColorScheme(
    primary = getColor(android.R.color.system_accent1_600),
    onPrimary = getColor(android.R.color.system_accent1_0),
    primaryContainer = getColor(android.R.color.system_accent1_100),
    onPrimaryContainer = getColor(android.R.color.system_accent1_900),
    inversePrimary = getColor(android.R.color.system_accent1_200),
    secondary = getColor(android.R.color.system_accent2_600),
    onSecondary = getColor(android.R.color.system_accent2_0),
    secondaryContainer = getColor(android.R.color.system_accent2_100),
    onSecondaryContainer = getColor(android.R.color.system_accent2_900),
    tertiary = getColor(android.R.color.system_accent3_600),
    onTertiary = getColor(android.R.color.system_accent3_0),
    tertiaryContainer = getColor(android.R.color.system_accent3_100),
    onTertiaryContainer = getColor(android.R.color.system_accent3_900),
    onBackground = getColor(android.R.color.system_neutral1_900),
    onSurface = getColor(android.R.color.system_neutral1_900),
    surfaceVariant = getColor(android.R.color.system_neutral2_100),
    onSurfaceVariant = getColor(android.R.color.system_neutral2_700),
    inverseSurface = getColor(android.R.color.system_neutral1_800),
    inverseOnSurface = getColor(android.R.color.system_neutral1_50),
    outline = getColor(android.R.color.system_neutral2_500),
    outlineVariant = getColor(android.R.color.system_neutral2_200),
    surfaceContainerLowest = getColor(android.R.color.system_neutral2_0),
    surfaceContainerHighest = getColor(android.R.color.system_neutral2_100)
)

@RequiresApi(Build.VERSION_CODES.S)
actual fun fixedDynamicDarkColorScheme() = darkColorScheme(
    primary = getColor(android.R.color.system_accent1_200),
    onPrimary = getColor(android.R.color.system_accent1_800),
    primaryContainer = getColor(android.R.color.system_accent1_700),
    onPrimaryContainer = getColor(android.R.color.system_accent1_100),
    inversePrimary = getColor(android.R.color.system_accent1_600),
    secondary = getColor(android.R.color.system_accent2_200),
    onSecondary = getColor(android.R.color.system_accent2_800),
    secondaryContainer = getColor(android.R.color.system_accent2_700),
    onSecondaryContainer = getColor(android.R.color.system_accent2_100),
    tertiary = getColor(android.R.color.system_accent3_200),
    onTertiary = getColor(android.R.color.system_accent3_800),
    tertiaryContainer = getColor(android.R.color.system_accent3_700),
    onTertiaryContainer = getColor(android.R.color.system_accent3_100),
    onBackground = getColor(android.R.color.system_neutral1_100),
    onSurface = getColor(android.R.color.system_neutral1_100),
    surfaceVariant = getColor(android.R.color.system_neutral2_700),
    onSurfaceVariant = getColor(android.R.color.system_neutral2_200),
    inverseSurface = getColor(android.R.color.system_neutral1_100),
    inverseOnSurface = getColor(android.R.color.system_neutral1_800),
    outline = getColor(android.R.color.system_neutral2_400),
    outlineVariant = getColor(android.R.color.system_neutral2_700),
    surfaceContainerLow = getColor(android.R.color.system_neutral2_900)
)

internal fun getColor(resId: Int, context: Context = getApplicationContext()): Color {
    return Color(context.getColor(resId))
}