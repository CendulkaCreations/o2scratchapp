package com.o2.sctrachapp.feature_scratchcard.data.repository

import com.o2.sctrachapp.feature_scratchcard.data.local.CardDataStore
import com.o2.sctrachapp.feature_scratchcard.data.remote.ScratchCardApi
import com.o2.sctrachapp.feature_scratchcard.domain.model.ScratchCardModel
import com.o2.sctrachapp.feature_scratchcard.domain.repository.ScratchCardRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import timber.log.Timber
import java.util.UUID

class ScratchCardRepositoryImpl(
    private val api: ScratchCardApi,
    private val dataStore: CardDataStore,
) : ScratchCardRepository {

    override suspend fun activateCard(code: String): Result<Boolean> {
        return try {
            val result = api.activateCard(code)
            if ((result.version.toIntOrNull() ?: 0) >= MIN_API_VERSION_SUPPORTED) {
                dataStore.setIsActivated(true)
                Result.success(true)
            } else {
                Result.failure(IllegalArgumentException("API version not supported"))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure(e)
        }
    }

    override suspend fun generateCode() {
        delay(2000L)
        val code = UUID.randomUUID().toString()
        dataStore.saveScratchCode(code)
    }

    override fun getCardFlow(): Flow<ScratchCardModel> {
        return combine(
            dataStore.scratchCodeFlow,
            dataStore.isActivatedFlow
        ) { code, isActivated ->
            ScratchCardModel(
                isActivated = isActivated,
                isScratched = code != null,
                code = code.orEmpty()
            )
        }
    }

    private companion object {
        const val MIN_API_VERSION_SUPPORTED = 277028
    }
}