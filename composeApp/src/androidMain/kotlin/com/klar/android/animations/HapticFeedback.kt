package com.klar.android.animations

import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalView

/**
 * Premium haptic feedback system for language learning interactions.
 */
interface LinguistHapticFeedback {
    /**
     * Short, sharp feedback for correct answers and successful interactions.
     */
    fun performSuccess()

    /**
     * Longer, more pronounced feedback for errors.
     */
    fun performError()

    /**
     * Light feedback for general button presses.
     */
    fun performClick()

    /**
     * Subtle feedback for word bubble selections.
     */
    fun performTick()
}

class AndroidHapticFeedback(private val view: View) : LinguistHapticFeedback {
    override fun performSuccess() {
        view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
    }

    override fun performError() {
        view.performHapticFeedback(HapticFeedbackConstants.REJECT)
    }

    override fun performClick() {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }

    override fun performTick() {
        view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
    }
}

/**
 * No-op implementation for testing or when haptics are disabled.
 */
class NoOpHapticFeedback : LinguistHapticFeedback {
    override fun performSuccess() {}
    override fun performError() {}
    override fun performClick() {}
    override fun performTick() {}
}

val LocalHapticFeedback = staticCompositionLocalOf<LinguistHapticFeedback> {
    NoOpHapticFeedback()
}

/**
 * Provides haptic feedback to the composition tree.
 * Use this at the root of your app.
 */
@Composable
fun ProvideHapticFeedback(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val hapticFeedback = AndroidHapticFeedback(view)

    CompositionLocalProvider(
        LocalHapticFeedback provides hapticFeedback
    ) {
        content()
    }
}

/**
 * Helper composable to access haptic feedback easily.
 */
@Composable
fun rememberHapticFeedback(): LinguistHapticFeedback {
    return LocalHapticFeedback.current
}
