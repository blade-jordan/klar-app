package com.klar.shared.architecture

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Android implementation of mainDispatcher.
 * Uses Dispatchers.Main for Android UI thread.
 */
actual val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
