package com.hevar.tvapp.theme

import androidx.compose.runtime.Composable
import androidx.tv.material3.ColorScheme
import androidx.tv.material3.MaterialTheme

@Composable
fun TvAppTheme(content: @Composable () -> Unit) {
    val colorScheme = ColorScheme(
        primary = AccentBlue,
        onPrimary = TextPrimary,
        primaryContainer = SurfaceHighlight,
        onPrimaryContainer = TextPrimary,
        inversePrimary = AccentGreen,
        secondary = AccentGreen,
        onSecondary = AppBackground,
        secondaryContainer = SurfaceDark,
        onSecondaryContainer = TextPrimary,
        tertiary = BorderFocused,
        onTertiary = AppBackground,
        tertiaryContainer = SurfaceHighlight,
        onTertiaryContainer = TextPrimary,
        background = AppBackground,
        onBackground = TextPrimary,
        surface = SurfaceDark,
        onSurface = TextPrimary,
        surfaceVariant = SurfaceHighlight,
        onSurfaceVariant = TextSecondary,
        surfaceTint = AccentBlue,
        inverseSurface = TextPrimary,
        inverseOnSurface = AppBackground,
        border = BorderFocused,
        borderVariant = SurfaceHighlight,
        scrim = AppBackground,
        error = androidx.compose.ui.graphics.Color(0xFFFF7A96),
        onError = TextPrimary,
        errorContainer = androidx.compose.ui.graphics.Color(0xFF5A1E33),
        onErrorContainer = TextPrimary
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
