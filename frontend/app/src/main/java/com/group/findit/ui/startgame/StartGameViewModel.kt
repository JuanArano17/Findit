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
    //Indica si hay algun mensaje de error a mostrar
    private val _error = MutableStateFlow<Throwable?>(null)
    val error : StateFlow<Throwable?> = _error
    // Propiedad MutableStateFlow para la nueva cita (Quotation)
    private val _participant = MutableStateFlow<Participant?>(null)
    val participant: StateFlow<Participant?> = _participant.asStateFlow()
    fun getNewParticipant(){
        viewModelScope.launch{
            try{
                val result =
                    repository.getNewParticipant()
                result.fold(
                    onSuccess = { participant ->
                        _participant.value = participant
                    },
                    onFailure = { error ->
                        _error.value = error
                        _participant.value = null
                    }
                )
            } catch (e: Exception){
                // Captura cualquier excepción inesperada (incluyendo NoInternetException)
                _error.value = e
                _participant.value = null
            }finally {

            }
        }
    }
}