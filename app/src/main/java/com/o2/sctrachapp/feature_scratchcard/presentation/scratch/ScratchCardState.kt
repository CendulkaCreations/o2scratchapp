package com.o2.sctrachapp.feature_scratchcard.presentation.scratch

import com.o2.sctrachapp.feature_scratchcard.domain.model.ScratchCardModel

data class ScratchCardState(
    val isLoading: Boolean = false,
    val card: ScratchCardModel = ScratchCardModel()
)