package com.kongjak.koreatechboard.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.kyant.m3color.dynamiccolor.MaterialDynamicColors
import com.kyant.m3color.hct.Hct
import com.kyant.m3color.scheme.SchemeContent
import com.kyant.m3color.scheme.SchemeExpressive
import com.kyant.m3color.scheme.SchemeFidelity
import com.kyant.m3color.scheme.SchemeFruitSalad
import com.kyant.m3color.scheme.SchemeMonochrome
import com.kyant.m3color.scheme.SchemeNeutral
import com.kyant.m3color.scheme.SchemeRainbow
import com.kyant.m3color.scheme.SchemeTonalSpot
import com.kyant.m3color.scheme.SchemeVibrant

fun dynamicColorScheme(
    keyColor: Color,
    isDark: Boolean,
    style: PaletteStyle = PaletteStyle.TonalSpot,
    contrastLevel: Double = 0.0
): ColorScheme {
    val hct = Hct.fromInt(keyColor.toArgb())
    val colors = MaterialDynamicColors()
    val scheme = when (style) {
        PaletteStyle.TonalSpot -> SchemeTonalSpot(hct, isDark, contrastLevel)
        PaletteStyle.Neutral -> SchemeNeutral(hct, isDark, contrastLevel)
        PaletteStyle.Vibrant -> SchemeVibrant(hct, isDark, contrastLevel)
        PaletteStyle.Expressive -> SchemeExpressive(hct, isDark, contrastLevel)
        PaletteStyle.Rainbow -> SchemeRainbow(hct, isDark, contrastLevel)
        PaletteStyle.FruitSalad -> SchemeFruitSalad(hct, isDark, contrastLevel)
        PaletteStyle.Monochrome -> SchemeMonochrome(hct, isDark, contrastLevel)
        PaletteStyle.Fidelity -> SchemeFidelity(hct, isDark, contrastLevel)
        PaletteStyle.Content -> SchemeContent(hct, isDark, contrastLevel)
    }

    return ColorScheme(
        primary = Color(colors.primary().getArgb(scheme)),
        onPrimary = Color(colors.onPrimary().getArgb(scheme)),
        primaryContainer = Color(colors.primaryContainer().getArgb(scheme)),
        onPrimaryContainer = Color(colors.onPrimaryContainer().getArgb(scheme)),
        inversePrimary = Color(colors.inversePrimary().getArgb(scheme)),
        secondary = Color(colors.secondary().getArgb(scheme)),
        onSecondary = Color(colors.onSecondary().getArgb(scheme)),
        secondaryContainer = Color(colors.secondaryContainer().getArgb(scheme)),
        onSecondaryContainer = Color(colors.onSecondaryContainer().getArgb(scheme)),
        tertiary = Color(colors.tertiary().getArgb(scheme)),
        onTertiary = Color(colors.onTertiary().getArgb(scheme)),
        tertiaryContainer = Color(colors.tertiaryContainer().getArgb(scheme)),
        onTertiaryContainer = Color(colors.onTertiaryContainer().getArgb(scheme)),
        background = Color(colors.background().getArgb(scheme)),
        onBackground = Color(colors.onBackground().getArgb(scheme)),
        surface = Color(colors.surface().getArgb(scheme)),
        onSurface = Color(colors.onSurface().getArgb(scheme)),
        surfaceVariant = Color(colors.surfaceVariant().getArgb(scheme)),
        onSurfaceVariant = Color(colors.onSurfaceVariant().getArgb(scheme)),
        surfaceTint = Color(colors.surfaceTint().getArgb(scheme)),
        inverseSurface = Color(colors.inverseSurface().getArgb(scheme)),
        inverseOnSurface = Color(colors.inverseOnSurface().getArgb(scheme)),
        error = Color(colors.error().getArgb(scheme)),
        onError = Color(colors.onError().getArgb(scheme)),
        errorContainer = Color(colors.errorContainer().getArgb(scheme)),
        onErrorContainer = Color(colors.onErrorContainer().getArgb(scheme)),
        outline = Color(colors.outline().getArgb(scheme)),
        outlineVariant = Color(colors.outlineVariant().getArgb(scheme)),
        scrim = Color(colors.scrim().getArgb(scheme)),
        surfaceBright = Color(colors.surfaceBright().getArgb(scheme)),
        surfaceDim = Color(colors.surfaceDim().getArgb(scheme)),
        surfaceContainer = Color(colors.surfaceContainer().getArgb(scheme)),
        surfaceContainerHigh = Color(colors.surfaceContainerHigh().getArgb(scheme)),
        surfaceContainerHighest = Color(colors.surfaceContainerHighest().getArgb(scheme)),
        surfaceContainerLow = Color(colors.surfaceContainerLow().getArgb(scheme)),
        surfaceContainerLowest = Color(colors.surfaceContainerLowest().getArgb(scheme)),
    )
}