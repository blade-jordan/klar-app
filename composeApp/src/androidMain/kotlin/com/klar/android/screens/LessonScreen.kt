package com.klar.android.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import com.klar.android.animations.*
import com.klar.android.ui.theme.KlarShapes
import com.klar.android.ui.theme.KlarTheme
import com.klar.shared.data.Exercise
import com.klar.shared.data.ExerciseType
import com.klar.shared.data.Lesson
import com.klar.shared.viewmodel.LessonViewModel
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

data class LessonScreen(val lesson: Lesson) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: LessonViewModel = koinInject { parametersOf(lesson) }
        val haptics = rememberHapticFeedback()
        val confettiState = rememberConfettiState()
        val colors = KlarTheme.colors

        val uiState by viewModel.uiState.collectAsState()
        val currentExercise = viewModel.currentExercise
        val progress = viewModel.progress

        // Handle lesson completion
        LaunchedEffect(uiState.isComplete) {
            if (uiState.isComplete) {
                navigator.pop()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.background) // Industrial Black
            ) {
                // Progress Bar
                LessonProgressBar(progress = progress)

                Spacer(modifier = Modifier.height(24.dp))

                // Exercise content based on type
                when (currentExercise.type) {
                    ExerciseType.WORD_BUBBLES -> {
                        WordBubblesExercise(
                            exercise = currentExercise,
                            selectedWords = uiState.selectedWords,
                            onWordSelected = { word ->
                                haptics.performTick()
                                viewModel.selectWord(word)
                            },
                            onWordRemoved = { index ->
                                haptics.performTick()
                                viewModel.removeWord(index)
                            },
                            onCheck = {
                                val answer = uiState.selectedWords.joinToString(" ")
                                val isCorrect = viewModel.checkAnswer(answer)
                                if (isCorrect) {
                                    haptics.performSuccess()
                                    confettiState.trigger()
                                } else {
                                    haptics.performError()
                                }
                            },
                            enabled = uiState.selectedWords.isNotEmpty()
                        )
                    }

                    ExerciseType.MULTIPLE_CHOICE -> {
                        MultipleChoiceExercise(
                            exercise = currentExercise,
                            onOptionSelected = { option ->
                                val isCorrect = viewModel.checkAnswer(option)
                                if (isCorrect) {
                                    haptics.performSuccess()
                                    confettiState.trigger()
                                } else {
                                    haptics.performError()
                                }
                            }
                        )
                    }

                    ExerciseType.LISTEN_AND_TYPE -> {
                        Text(
                            "LISTEN & TYPE → COMING SOON",
                            modifier = Modifier.padding(24.dp),
                            color = colors.onBackground,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }

            // Confetti overlay
            ConfettiCannon(
                isTriggered = confettiState.isTriggered,
                onComplete = { confettiState.reset() }
            )

            // Success overlay
            AnimatedVisibility(
                visible = uiState.showSuccess,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.industrialBlack.copy(alpha = 0.85f)),
                    contentAlignment = Alignment.Center
                ) {
                    SuccessCard(xpEarned = currentExercise.xpReward) {
                        LaunchedEffect(Unit) {
                            delay(1500)
                            viewModel.resetSuccess()
                            viewModel.clearSelection()
                            viewModel.nextExercise()
                        }
                    }
                }
            }

            // Error overlay
            AnimatedVisibility(
                visible = uiState.showError,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.industrialBlack.copy(alpha = 0.85f)),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorCard {
                        LaunchedEffect(Unit) {
                            delay(1000)
                            viewModel.resetError()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LessonProgressBar(progress: Float) {
    val colors = KlarTheme.colors
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "progress"
    )

    LinearProgressIndicator(
        progress = animatedProgress,
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp),
        color = colors.primary, // Signal Orange
        backgroundColor = colors.slateGrey
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordBubblesExercise(
    exercise: Exercise,
    selectedWords: List<String>,
    onWordSelected: (String) -> Unit,
    onWordRemoved: (Int) -> Unit,
    onCheck: () -> Unit,
    enabled: Boolean
) {
    val colors = KlarTheme.colors

    Column(modifier = Modifier.fillMaxSize()) {
        // Prompt - Industrial caps
        Text(
            text = exercise.prompt.uppercase(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground,
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.h5,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Hint with Signal Orange
        exercise.hint?.let {
            Text(
                text = "▸ $it",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = colors.primary,
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Selected words area
        SelectedWordsArea(
            selectedWords = selectedWords,
            onRemoveWord = onWordRemoved
        )

        Spacer(modifier = Modifier.weight(1f))

        // Available word bubbles
        WordBubbleGrid(
            availableWords = exercise.options.filter { it !in selectedWords },
            onWordSelected = onWordSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        // CHECK button - Industrial style
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .height(56.dp)
                .clip(KlarShapes.button)
                .background(if (enabled) colors.primary else colors.disabled)
                .border(
                    width = 2.dp,
                    color = if (enabled) colors.industrialBlack else colors.disabled,
                    shape = KlarShapes.button
                )
                .springPress(enabled = enabled, onTap = onCheck),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CHECK",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (enabled) colors.industrialBlack else colors.onDisabled,
                style = MaterialTheme.typography.button,
                letterSpacing = 2.sp
            )
        }
    }
}

@Composable
fun MultipleChoiceExercise(
    exercise: Exercise,
    onOptionSelected: (String) -> Unit
) {
    val colors = KlarTheme.colors

    Column(modifier = Modifier.fillMaxSize()) {
        // Prompt
        Text(
            text = exercise.prompt.uppercase(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground,
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.h5,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Hint
        exercise.hint?.let {
            Text(
                text = "▸ $it",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = colors.primary,
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Options
        exercise.options.forEach { option ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .height(64.dp)
                    .clip(KlarShapes.button)
                    .background(colors.surface)
                    .border(2.dp, colors.slateGrey, KlarShapes.button)
                    .springPress(onTap = { onOptionSelected(option) })
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = option,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.onSurface,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordBubbleGrid(
    availableWords: List<String>,
    onWordSelected: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        availableWords.forEach { word ->
            WordBubble(word = word, onClick = { onWordSelected(word) })
        }
    }
}

@Composable
fun WordBubble(word: String, onClick: () -> Unit) {
    val colors = KlarTheme.colors

    Box(
        modifier = Modifier
            .clip(KlarShapes.wordBubble) // Sharp 4dp corners
            .background(colors.surface)
            .border(2.dp, colors.slateGrey, KlarShapes.wordBubble)
            .wordBubbleBounce(onTap = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = word,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colors.onSurface,
            style = MaterialTheme.typography.body1
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedWordsArea(
    selectedWords: List<String>,
    onRemoveWord: (Int) -> Unit
) {
    val colors = KlarTheme.colors

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .heightIn(min = 100.dp)
            .clip(KlarShapes.card)
            .background(colors.surface)
            .border(2.dp, colors.slateGrey, KlarShapes.card)
            .padding(16.dp)
    ) {
        if (selectedWords.isEmpty()) {
            Text(
                text = "TAP WORDS TO BUILD ANSWER",
                color = colors.onSurface.copy(alpha = 0.5f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.caption,
                letterSpacing = 1.sp
            )
        } else {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                selectedWords.forEachIndexed { index, word ->
                    SelectedWordBubble(word = word, onClick = { onRemoveWord(index) })
                }
            }
        }
    }
}

@Composable
fun SelectedWordBubble(word: String, onClick: () -> Unit) {
    val colors = KlarTheme.colors

    Box(
        modifier = Modifier
            .clip(KlarShapes.wordBubble)
            .background(colors.primary) // Signal Orange
            .border(2.dp, colors.industrialBlack, KlarShapes.wordBubble)
            .wordBubbleBounce(onTap = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = word,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colors.industrialBlack,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun SuccessCard(xpEarned: Int, onAnimationComplete: @Composable () -> Unit) {
    val colors = KlarTheme.colors

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(280.dp)
            .clip(KlarShapes.card)
            .background(colors.surface)
            .border(4.dp, colors.primary, KlarShapes.card)
            .padding(32.dp)
    ) {
        Text(
            text = "⚡",
            fontSize = 64.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "PERFECT",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colors.primary, // Signal Orange
            style = MaterialTheme.typography.h2,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "+$xpEarned XP",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface,
            style = MaterialTheme.typography.h6,
            letterSpacing = 1.sp
        )
        onAnimationComplete()
    }
}

@Composable
fun ErrorCard(onAnimationComplete: @Composable () -> Unit) {
    val colors = KlarTheme.colors

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(280.dp)
            .clip(KlarShapes.card)
            .background(colors.surface)
            .border(4.dp, colors.error, KlarShapes.card)
            .padding(32.dp)
    ) {
        Text(
            text = "⊗",
            fontSize = 64.sp,
            color = colors.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "RETRY",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colors.error, // Caution Red
            style = MaterialTheme.typography.h3,
            letterSpacing = 2.sp
        )
        onAnimationComplete()
    }
}
