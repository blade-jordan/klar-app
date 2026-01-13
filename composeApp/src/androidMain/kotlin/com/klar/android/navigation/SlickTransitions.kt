package com.klar.android.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith

// Custom transitions for slick navigation effects
// These can be applied using ScreenTransition composable from Voyager
// Uses mechanical spring physics for positional animations to create industrial feel

object SlickTransitions {
    // Fade and slide transition for screen navigation with mechanical spring physics
    fun fadeAndSlideTransition() = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    ) + fadeIn(
        animationSpec = tween(200) // Fade can stay tween - visual effect only
    ) togetherWith slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    ) + fadeOut(
        animationSpec = tween(200) // Fade can stay tween - visual effect only
    )
}
