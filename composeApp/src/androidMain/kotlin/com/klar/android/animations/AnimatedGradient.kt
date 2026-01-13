package com.klar.android.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Creates an animated linear gradient that flows smoothly.
 * Perfect for premium headers and backgrounds.
 */
@Composable
fun Modifier.animatedGradient(
    colors: List<Color> = listOf(
        Color(0xFF2B2F36),  // Slate Grey
        Color(0xFF0B0D0F),  // Industrial Black
        Color(0xFF2B2F36)   // Slate Grey
    ),
    durationMillis: Int = 3000
): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "gradient_transition")

    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradient_offset_x"
    )

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradient_offset_y"
    )

    return this.background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(offsetX, offsetY),
            end = Offset(offsetX + 1000f, offsetY + 1000f)
        )
    )
}

/**
 * Shimmer effect for loading states or highlights.
 */
@Composable
fun Modifier.shimmerEffect(
    baseColor: Color = Color(0xFFE0E0E0),
    highlightColor: Color = Color(0xFFF5F5F5),
    durationMillis: Int = 1500
): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer_transition")

    val offset by infiniteTransition.animateFloat(
        initialValue = -1000f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_offset"
    )

    return this.background(
        brush = Brush.linearGradient(
            colors = listOf(
                baseColor,
                highlightColor,
                baseColor
            ),
            start = Offset(offset, offset),
            end = Offset(offset + 500f, offset + 500f)
        )
    )
}

/**
 * Pulsing gradient for attention-grabbing elements.
 */
@Composable
fun Modifier.pulseGradient(
    colors: List<Color> = listOf(
        Color(0xFFFF6B00),  // Signal Orange
        Color(0xFFFF6B00).copy(alpha = 0.6f)
    ),
    durationMillis: Int = 2000
): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    return this.background(
        brush = Brush.linearGradient(
            colors = colors.map { it.copy(alpha = alpha) }
        )
    )
}

/**
 * Premium header gradient component.
 */
@Composable
fun AnimatedGradientHeader(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.animatedGradient(
            colors = listOf(
                Color(0xFF2B2F36),  // Slate Grey
                Color(0xFF0B0D0F),  // Industrial Black
                Color(0xFF2B2F36)   // Slate Grey
            ),
            durationMillis = 4000
        )
    ) {
        content()
    }
}
