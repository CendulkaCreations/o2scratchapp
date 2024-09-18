package com.o2.sctrachapp.feature_scratchcard.presentation.activation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.o2.sctrachapp.feature_scratchcard.domain.repository.ScratchCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivationViewModel @Inject constructor(
    private val cardRepository: ScratchCardRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ActivationState())
    val state: StateFlow<ActivationState> = _state

    private val longRunScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        viewModelScope.launch {
            cardRepository.getCardFlow()
                .collect { savedCard ->
                    _state.value = _state.value.copy(code = savedCard.code)
                }
        }
    }

    fun activateCard() {
        longRunScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val code = _state.value.code
            val result = cardRepository.activateCard(code)
            when {
                result.isSuccess -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        activationSuccess = result.getOrNull() == true
                    )
                }

                result.isFailure -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.exceptionOrNull()?.message
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(isLoading = false, error = null)
    }
}