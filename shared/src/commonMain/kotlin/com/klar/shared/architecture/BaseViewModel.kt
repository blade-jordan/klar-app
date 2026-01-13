package com.klar.shared.architecture

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Platform-specific main dispatcher.
 * Implemented in each platform's source set.
 */
expect val mainDispatcher: CoroutineDispatcher

/**
 * A lightweight BaseViewModel for KMP.
 * Ideally, this would use androidx.lifecycle.ViewModel once it's fully KMP stable,
 * or a library like Decompose or Voyager for navigation/lifecycle.
 * For now, this provides a basic CoroutineScope that can be cleared.
 */
open class BaseViewModel {

    /**
     * CoroutineScope for ViewModel operations.
     * Uses platform-specific main dispatcher via expect/actual pattern.
     */
    protected val viewModelScope = CoroutineScope(SupervisorJob() + mainDispatcher)

    /**
     * Called when the ViewModel is no longer needed.
     * Cancels all coroutines in viewModelScope.
     */
    open fun onCleared() {
        viewModelScope.cancel()
    }
}
