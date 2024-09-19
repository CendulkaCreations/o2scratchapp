package com.o2.sctrachapp.feature_scratchcard.domain.repository

import com.o2.sctrachapp.feature_scratchcard.domain.model.ScratchCardModel
import kotlinx.coroutines.flow.Flow

interface ScratchCardRepository {
    suspend fun activateCard(code: String): Result<Boolean>

    fun getCardFlow() : Flow<ScratchCardModel>

    suspend fun generateCode()
}