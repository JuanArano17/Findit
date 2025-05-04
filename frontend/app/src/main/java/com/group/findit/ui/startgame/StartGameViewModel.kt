package com.group.findit.ui.startgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group.findit.ui.domain.model.Participant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class StartGameViewModel @Inject constructor(private val repository: StartGameRepository): ViewModel() {
}