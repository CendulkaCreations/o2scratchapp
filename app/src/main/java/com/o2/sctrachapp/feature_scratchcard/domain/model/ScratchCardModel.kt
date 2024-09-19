package com.o2.sctrachapp.feature_scratchcard.domain.model

data class ScratchCardModel(
    val isActivated: Boolean = false,
    val isScratched: Boolean = false,
    val code: String = "",
)
