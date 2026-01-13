package com.klar.android.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.klar.android.animations.animatedGradient
import com.klar.android.animations.pulseGradient
import com.klar.android.animations.rememberHapticFeedback
import com.klar.android.animations.springPress
import com.klar.android.ui.theme.KlarShapes
import com.klar.android.ui.theme.KlarTheme
import com.klar.shared.data.Lesson
import com.klar.shared.viewmodel.PathUiState
import com.klar.shared.viewmodel.PathViewModel
import org.koin.compose.koinInject

class PathScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: PathViewModel = koinInject()
        val haptics = rememberHapticFeedback()
        val colors = KlarTheme.colors

        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background) // Industrial Black
        ) {
            // Animated gradient header
            PathScreenHeader()

            when (val state = uiState) {
                is PathUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = colors.primary) // Signal Orange
                    }
                }

                is PathUiState.Success -> {
                    // Learning Path - Vertical scrollable list
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        itemsIndexed(
                            items = state.lessons,
                            key = { _, lesson -> lesson.lessonId }
                        ) { index, lesson ->
                            LessonNode(
                                lesson = lesson,
                                isCompleted = false,
                                isLocked = false,
                                onClick = {
                                    haptics.performClick()
                                    navigator.push(LessonScreen(lesson))
                                }
                            )

                            // Add connecting line between nodes (except for last item)
                            if (index < state.lessons.size - 1) {
                                PathConnector()
                            }
                        }
                    }
                }

                is PathUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Text(
                                text = "ERROR",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = colors.error,
                                letterSpacing = 2.sp
                            )
                            Text(
                                text = state.message,
                                fontSize = 14.sp,
                                color = colors.onBackground.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.body2
                            )
                            Button(
                                onClick = { viewModel.retry() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colors.primary
                                )
                            ) {
                                Text(
                                    text = "RETRY",
                                    color = colors.onPrimary,
                                    letterSpacing = 1.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PathScreenHeader() {
    val colors = KlarTheme.colors

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .animatedGradient(
                colors = listOf(
                    colors.slateGrey,
                    colors.surface,
                    colors.industrialBlack
                ),
                durationMillis = 4000
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pulsing icon with Signal Orange
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(KlarShapes.sharp)
                    .pulseGradient(
                        colors = listOf(
                            colors.primary.copy(alpha = 0.2f),
                            colors.primary.copy(alpha = 0.4f)
                        ),
                        durationMillis = 2000
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "⚡", // Industrial/mechanical icon
                    fontSize = 40.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "klar",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground, // Ash White
                style = MaterialTheme.typography.h1
            )

            Text(
                text = "Master German. Mechanically precise.",
                fontSize = 14.sp,
                color = colors.onBackground.copy(alpha = 0.7f),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun LessonNode(
    lesson: Lesson,
    isCompleted: Boolean,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    val colors = KlarTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(KlarShapes.lessonNode) // Sharp 4dp corners
            .background(colors.surface) // Slate Grey
            .border(
                width = 2.dp,
                color = if (isLocked) colors.disabled else colors.surface,
                shape = KlarShapes.lessonNode
            )
            .springPress(enabled = !isLocked, onTap = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Lesson Icon/Status with Signal Orange
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(KlarShapes.sharp) // Perfectly sharp square
                .background(
                    when {
                        isCompleted -> colors.success
                        isLocked -> colors.disabled
                        else -> colors.primary // Signal Orange for active
                    }
                )
                .border(2.dp, colors.industrialBlack, KlarShapes.sharp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when {
                    isCompleted -> "✓"
                    isLocked -> "⊗"
                    else -> "▸" // Play symbol - more mechanical than star
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (isLocked) colors.onDisabled else colors.industrialBlack
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Lesson Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = lesson.title.uppercase(), // Industrial caps
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isLocked) colors.onDisabled else colors.onSurface,
                style = MaterialTheme.typography.h6,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${lesson.exercises.size} exercises • ${lesson.level.name}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (isLocked) colors.onDisabled else colors.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.caption
            )
        }

        // Arrow indicator - Signal Orange
        if (!isLocked) {
            Text(
                text = "→",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primary
            )
        }
    }
}

@Composable
fun PathConnector() {
    val colors = KlarTheme.colors

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(colors.slateGrey)
        )
    }
}
