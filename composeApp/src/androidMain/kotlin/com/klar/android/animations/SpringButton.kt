package com.klar.android.animations

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

/**
 * A modifier that applies spring physics animation when pressed.
 * Mechanical feel: High stiffness + low bouncy damping = physical "click" and "snap"
 * Like a well-oiled machine or precision tool cabinet drawer.
 */
fun Modifier.springPress(
    enabled: Boolean = true,
    onTap: () -> Unit = {}
): Modifier = composed {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,  // Mechanical bounce
            stiffness = Spring.StiffnessHigh               // Crisp snap
        ),
        label = "spring_scale"
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(enabled) {
            if (enabled) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onTap() }
                )
            }
        }
}

/**
 * A composable wrapper for spring button effects.
 * Use this for complete control over content.
 */
@Composable
fun SpringButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.springPress(enabled, onClick)
    ) {
        content()
    }
}

/**
 * Animated scale for word bubbles with extra bounce.
 */
fun Modifier.wordBubbleBounce(
    enabled: Boolean = true,
    onTap: () -> Unit = {}
): Modifier = composed {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.90f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy, // Extra bouncy!
            stiffness = Spring.StiffnessHigh
        ),
        label = "bubble_bounce"
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(enabled) {
            if (enabled) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onTap() }
                )
            }
        }
}
