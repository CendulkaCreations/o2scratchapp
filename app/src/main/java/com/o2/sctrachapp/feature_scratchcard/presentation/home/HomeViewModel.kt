package com.o2.sctrachapp.feature_scratchcard.presentation.home

import androidx.lifecycle.ViewModel
import com.o2.sctrachapp.feature_scratchcard.domain.repository.ScratchCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    cardRepository: ScratchCardRepository,
): ViewModel() {

    val cardFlow = cardRepository.getCardFlow()
}