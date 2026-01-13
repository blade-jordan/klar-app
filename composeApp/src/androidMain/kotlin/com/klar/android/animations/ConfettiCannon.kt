package com.klar.android.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class Particle(
    val startX: Float,
    val startY: Float,
    val velocityX: Float,
    val velocityY: Float,
    val color: Color,
    val size: Float,
    val rotation: Float,
    val rotationSpeed: Float,
    val isCircle: Boolean
)

@Composable
fun ConfettiCannon(
    isTriggered: Boolean,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var particles by remember { mutableStateOf<List<Particle>>(emptyList()) }
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(isTriggered) {
        if (isTriggered) {
            // Generate particles
            particles = generateParticles()

            // Animate from 0 to 1 over 2 seconds
            animationProgress.snapTo(0f)
            animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
            )

            // Clean up
            delay(100)
            particles = emptyList()
            onComplete()
        }
    }

    if (particles.isNotEmpty()) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val progress = animationProgress.value
            val gravity = 980f // pixels per second squared
            val timeInSeconds = progress * 2f // 2 seconds total

            particles.forEach { particle ->
                // Physics calculations
                val x = particle.startX + particle.velocityX * timeInSeconds
                val y = particle.startY + particle.velocityY * timeInSeconds +
                        0.5f * gravity * timeInSeconds * timeInSeconds

                val currentRotation = particle.rotation + particle.rotationSpeed * timeInSeconds

                // Fade out towards the end
                val alpha = if (progress > 0.7f) {
                    1f - ((progress - 0.7f) / 0.3f)
                } else {
                    1f
                }

                // Only draw if still visible on screen
                if (y < size.height && x in 0f..size.width) {
                    rotate(degrees = currentRotation, pivot = Offset(x, y)) {
                        if (particle.isCircle) {
                            drawCircle(
                                color = particle.color.copy(alpha = alpha),
                                radius = particle.size / 2f,
                                center = Offset(x, y)
                            )
                        } else {
                            drawRect(
                                color = particle.color.copy(alpha = alpha),
                                topLeft = Offset(x - particle.size / 2f, y - particle.size / 2f),
                                size = Size(particle.size, particle.size)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun generateParticles(): List<Particle> {
    val colors = listOf(
        Color(0xFFFF6B00), // Signal Orange (primary)
        Color(0xFF0B0D0F), // Industrial Black
        Color(0xFF2B2F36)  // Slate Grey
    )

    return List(50) {
        val angle = Random.nextDouble(-60.0, -120.0) // Upward angles in degrees
        val angleRad = Math.toRadians(angle)
        val speed = Random.nextFloat() * 400f + 600f // Initial velocity

        Particle(
            startX = 0f, // Will be set relative to screen center
            startY = 0f, // Will be set relative to screen bottom
            velocityX = (cos(angleRad) * speed).toFloat(),
            velocityY = (sin(angleRad) * speed).toFloat(),
            color = colors.random(),
            size = Random.nextFloat() * 8f + 8f, // 8-16 pixels
            rotation = Random.nextFloat() * 360f,
            rotationSpeed = Random.nextFloat() * 720f - 360f, // -360 to 360 degrees per second
            isCircle = Random.nextBoolean()
        )
    }
}

@Composable
fun rememberConfettiState(): ConfettiState {
    return remember { ConfettiState() }
}

class ConfettiState {
    private val _isTriggered = mutableStateOf(false)
    val isTriggered: Boolean by _isTriggered

    fun trigger() {
        _isTriggered.value = true
    }

    fun reset() {
        _isTriggered.value = false
    }
}
