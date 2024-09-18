package com.o2.sctrachapp.feature_scratchcard.presentation.scratch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.o2.sctrachapp.feature_scratchcard.domain.repository.ScratchCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScratchCardViewModel @Inject constructor(
    private val cardRepository: ScratchCardRepository,
): ViewModel() {

    private val _state = MutableStateFlow(ScratchCardState())
    val state: StateFlow<ScratchCardState> = _state

    init {
        viewModelScope.launch {
            cardRepository.getCardFlow().collect { scratchCardModel ->
                _state.value = _state.value.copy(card = scratchCardModel)
            }
        }
    }


    fun generateCode() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            cardRepository.generateCode()
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}