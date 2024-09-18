package com.o2.sctrachapp

import com.o2.sctrachapp.feature_scratchcard.data.local.CardDataStore
import com.o2.sctrachapp.feature_scratchcard.data.remote.ScratchCardApi
import com.o2.sctrachapp.feature_scratchcard.data.remote.dto.CardResponseDto
import com.o2.sctrachapp.feature_scratchcard.data.repository.ScratchCardRepositoryImpl
import com.o2.sctrachapp.feature_scratchcard.domain.repository.ScratchCardRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.UUID

class CardActivationUnitTests {

    private companion object {
        const val MIN_API_VERSION_SUPPORTED = 277028
    }

    private lateinit var api: ScratchCardApi
    private lateinit var cardDataStore: CardDataStore
    private lateinit var repository: ScratchCardRepository

    @Before
    fun setup() {
        api = mockk()
        cardDataStore = mockk(relaxed = true)
        repository = ScratchCardRepositoryImpl(api, cardDataStore)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `activates card successfully when API version is valid`() = runTest {
        val supportedVersion = MIN_API_VERSION_SUPPORTED.toString()
        val expectedResponse = CardResponseDto(version = supportedVersion)

        coEvery { api.activateCard(any()) } returns expectedResponse

        val result = repository.activateCard(UUID.randomUUID().toString())

        assert(result.isSuccess)
        assertEquals(true, result.isSuccess)

        coVerify { cardDataStore.setIsActivated(true) }
    }

    @Test
    fun `activateCard returns failure when android version is below min supported api version`() = runTest {
        val androidApiVersion = (MIN_API_VERSION_SUPPORTED - 1).toString()
        val expectedResponse = CardResponseDto(version = androidApiVersion)

        coEvery { api.activateCard(any()) } returns expectedResponse

        val result = repository.activateCard("65")

        assertEquals(true, result.isFailure)

        coVerify(exactly = 0) { cardDataStore.setIsActivated(true) }
    }

    @Test
    fun `activateCard returns failure when android version is not a number`() = runTest {
        val invalidResponse = CardResponseDto(version = "notNumber")

        coEvery { api.activateCard(any()) } returns invalidResponse

        val result = repository.activateCard("5")

        assert(result.isFailure)
        assert(result.exceptionOrNull() is IllegalArgumentException)

        coVerify(exactly = 0) { cardDataStore.setIsActivated(true) }
    }

}