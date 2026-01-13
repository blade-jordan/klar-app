package com.klar.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

/**
 * klar Theme
 * Industrial minimalist design system with high-contrast safety colors.
 *
 * Usage:
 * ```
 * KlarTheme {
 *     // Your composables
 * }
 * ```
 *
 * Access colors via:
 * ```
 * val colors = LocalKlarColors.current
 * colors.signalOrange
 * ```
 */
@Composable
fun KlarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val klarColors = KlarColors()

    // Map klar colors to Material colors for compatibility
    val materialColors = darkColors(
        primary = klarColors.primary,
        primaryVariant = klarColors.signalOrange,
        secondary = klarColors.slateGrey,
        secondaryVariant = klarColors.slateGrey,
        background = klarColors.background,
        surface = klarColors.surface,
        error = klarColors.error,
        onPrimary = klarColors.onPrimary,
        onSecondary = klarColors.onSurface,
        onBackground = klarColors.onBackground,
        onSurface = klarColors.onSurface,
        onError = klarColors.onError
    )

    MaterialTheme(
        colors = materialColors,
        typography = klarTypography,
        shapes = klarShapes
    ) {
        CompositionLocalProvider(
            LocalKlarColors provides klarColors
        ) {
            content()
        }
    }
}

/**
 * Helper object to access klar theme values from composables.
 */
object KlarTheme {
    val colors: KlarColors
        @Composable
        get() = LocalKlarColors.current
}
