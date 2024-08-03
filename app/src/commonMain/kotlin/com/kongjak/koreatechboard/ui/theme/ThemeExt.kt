package com.kongjak.koreatechboard.ui.theme

import androidx.compose.material3.ColorScheme

/*
 * Fixed dynamic color scheme
 * Issue: https://github.com/material-components/material-components-android/issues/3924
 * Based on https://github.com/material-components/material-components-android/blob/master/docs/theming/Color.md
 */

expect fun fixedDynamicLightColorScheme(): ColorScheme

expect fun fixedDynamicDarkColorScheme(): ColorScheme

