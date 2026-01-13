package com.klar.shared.viewmodel

import com.klar.shared.architecture.BaseViewModel
import com.klar.shared.data.Lesson
import com.klar.shared.data.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Path Screen that displays available lessons.
 * Manages lesson loading state and error handling.
 */
class PathViewModel(
    private val lessonRepository: LessonRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<PathUiState>(PathUiState.Loading)
    val uiState: StateFlow<PathUiState> = _uiState.asStateFlow()

    init {
        loadLessons()
    }

    /**
     * Loads lessons from the repository with proper error handling.
     */
    fun loadLessons() {
        viewModelScope.launch {
            _uiState.value = PathUiState.Loading
            try {
                val lessons = lessonRepository.getLessons()
                _uiState.value = PathUiState.Success(lessons)
            } catch (e: Exception) {
                _uiState.value = PathUiState.Error(
                    message = e.message ?: "Unknown error occurred while loading lessons"
                )
            }
        }
    }

    /**
     * Retry loading lessons after an error.
     */
    fun retry() {
        loadLessons()
    }
}

/**
 * UI State for the Path Screen.
 * Represents all possible states of the lesson list.
 */
sealed class PathUiState {
    /**
     * Initial loading state.
     */
    object Loading : PathUiState()

    /**
     * Successfully loaded lessons.
     * @param lessons The list of available lessons
     */
    data class Success(val lessons: List<Lesson>) : PathUiState()

    /**
     * Error state when loading fails.
     * @param message Human-readable error message
     */
    data class Error(val message: String) : PathUiState()
}
