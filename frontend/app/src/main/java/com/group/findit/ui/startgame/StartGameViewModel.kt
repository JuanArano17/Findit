package com.group.findit.ui.startgame

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the Start Game screen.
 * Manages UI-related data for starting a game in a lifecycle-conscious way.
 * @constructor Injects the [StartGameRepository] for data operations.
 */
@HiltViewModel

class StartGameViewModel @Inject constructor(private val repository: StartGameRepository): ViewModel() {
}