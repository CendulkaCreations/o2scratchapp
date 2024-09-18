package com.o2.sctrachapp.feature_scratchcard.presentation.activation

data class ActivationState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val code: String = "",
    val activationSuccess: Boolean = false,
)
