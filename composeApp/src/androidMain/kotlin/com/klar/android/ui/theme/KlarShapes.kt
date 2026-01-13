package com.klar.android.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

/**
 * klar Shape System
 * Industrial, sharp-edged design with subtle engineering feel.
 * Using 4dp corners for a slight "engineered" feel while maintaining the mechanical aesthetic.
 */
val klarShapes = Shapes(
    small = RoundedCornerShape(4.dp),   // Cards, buttons
    medium = RoundedCornerShape(4.dp),  // Dialogs, bottom sheets
    large = RoundedCornerShape(0.dp)    // Full screen modals - completely sharp
)

/**
 * Additional custom shapes for specific klar components.
 */
object KlarShapes {
    val button = RoundedCornerShape(4.dp)
    val card = RoundedCornerShape(4.dp)
    val wordBubble = RoundedCornerShape(4.dp)  // Sharp instead of pill-shaped
    val lessonNode = RoundedCornerShape(4.dp)
    val sharp = RoundedCornerShape(0.dp)       // For perfectly industrial elements
}
