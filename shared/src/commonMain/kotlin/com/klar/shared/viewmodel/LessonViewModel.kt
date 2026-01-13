package com.klar.shared.viewmodel

import com.klar.shared.architecture.BaseViewModel
import com.klar.shared.data.Exercise
import com.klar.shared.data.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for the Lesson Screen that manages exercise progression and state.
 * Handles word selection, answer checking, and navigation between exercises.
 */
class LessonViewModel(
    private val lesson: Lesson
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(
        LessonUiState(
            currentExerciseIndex = 0,
            selectedWords = emptyList(),
            showSuccess = false,
            showError = false,
            totalXp = 0,
            isComplete = false
        )
    )
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    /**
     * Current exercise being displayed.
     */
    val currentExercise: Exercise
        get() = lesson.exercises[_uiState.value.currentExerciseIndex]

    /**
     * Current progress through the lesson (0.0 to 1.0).
     */
    val progress: Float
        get() = (_uiState.value.currentExerciseIndex + 1).toFloat() / lesson.exercises.size

    /**
     * Select a word for word bubble exercises.
     * Adds the word to the selected words list.
     */
    fun selectWord(word: String) {
        _uiState.update { currentState ->
            currentState.copy(selectedWords = currentState.selectedWords + word)
        }
    }

    /**
     * Remove a word from the selected words list.
     * @param index The position of the word to remove
     */
    fun removeWord(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedWords = currentState.selectedWords.toMutableList().apply {
                    if (index in 0 until size) {
                        removeAt(index)
                    }
                }
            )
        }
    }

    /**
     * Check if the provided answer is correct.
     * Updates state to show success or error overlay.
     * @param answer The user's answer to check
     * @return true if correct, false otherwise
     */
    fun checkAnswer(answer: String): Boolean {
        val isCorrect = answer == currentExercise.correctAnswer

        _uiState.update { currentState ->
            if (isCorrect) {
                currentState.copy(
                    showSuccess = true,
                    totalXp = currentState.totalXp + currentExercise.xpReward
                )
            } else {
                currentState.copy(showError = true)
            }
        }

        return isCorrect
    }

    /**
     * Move to the next exercise or complete the lesson.
     * Resets selected words and feedback states.
     */
    fun nextExercise() {
        val nextIndex = _uiState.value.currentExerciseIndex + 1

        _uiState.update { currentState ->
            if (nextIndex < lesson.exercises.size) {
                // Move to next exercise
                currentState.copy(
                    currentExerciseIndex = nextIndex,
                    selectedWords = emptyList(),
                    showSuccess = false,
                    showError = false
                )
            } else {
                // Lesson complete
                currentState.copy(
                    isComplete = true,
                    showSuccess = false,
                    showError = false
                )
            }
        }
    }

    /**
     * Reset the error state.
     * Called after error overlay timeout.
     */
    fun resetError() {
        _uiState.update { currentState ->
            currentState.copy(showError = false)
        }
    }

    /**
     * Reset the success state.
     * Called after success overlay timeout before moving to next exercise.
     */
    fun resetSuccess() {
        _uiState.update { currentState ->
            currentState.copy(showSuccess = false)
        }
    }

    /**
     * Clear selected words without checking the answer.
     * Useful for word bubble exercises when user wants to restart.
     */
    fun clearSelection() {
        _uiState.update { currentState ->
            currentState.copy(selectedWords = emptyList())
        }
    }
}

/**
 * UI State for the Lesson Screen.
 * Contains all state needed to render the lesson screen.
 */
data class LessonUiState(
    /**
     * Current exercise index (0-based).
     */
    val currentExerciseIndex: Int,

    /**
     * Selected words for word bubble exercises.
     */
    val selectedWords: List<String>,

    /**
     * Whether to show the success overlay.
     */
    val showSuccess: Boolean,

    /**
     * Whether to show the error overlay.
     */
    val showError: Boolean,

    /**
     * Total XP earned in this lesson so far.
     */
    val totalXp: Int,

    /**
     * Whether the lesson is complete (all exercises finished).
     */
    val isComplete: Boolean
)
