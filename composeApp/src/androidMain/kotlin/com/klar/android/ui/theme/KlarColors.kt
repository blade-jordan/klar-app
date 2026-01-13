package com.klar.android.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * klar Industrial Minimalist Color Palette
 * High-contrast "safety" colors against neutral industrial tones.
 */
@Immutable
data class KlarColors(
    // Primary Colors
    val industrialBlack: Color = Color(0xFF0B0D0F),
    val signalOrange: Color = Color(0xFFFF6B00),
    val slateGrey: Color = Color(0xFF2B2F36),
    val ashWhite: Color = Color(0xFFF2F4F7),
    val cautionRed: Color = Color(0xFFE63946),

    // Semantic Colors
    val background: Color = industrialBlack,
    val surface: Color = slateGrey,
    val primary: Color = signalOrange,
    val onBackground: Color = ashWhite,
    val onSurface: Color = ashWhite,
    val error: Color = cautionRed,
    val onPrimary: Color = industrialBlack,
    val onError: Color = ashWhite,

    // Success state - Signal Orange maintains brand consistency and represents precision achievement
    val success: Color = signalOrange, // Signal Orange for industrial "bullseye" success moment
    val onSuccess: Color = industrialBlack,

    // Disabled/Inactive
    val disabled: Color = slateGrey.copy(alpha = 0.5f),
    val onDisabled: Color = ashWhite.copy(alpha = 0.4f)
)

/**
 * CompositionLocal for accessing klar colors throughout the app.
 */
val LocalKlarColors = staticCompositionLocalOf { KlarColors() }
