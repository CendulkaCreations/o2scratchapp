package com.o2.sctrachapp.feature_scratchcard.data.remote

import com.o2.sctrachapp.feature_scratchcard.data.remote.dto.CardResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ScratchCardApi {
    @GET("/version")
    suspend fun activateCard(@Query("code") code: String): CardResponseDto

    companion object {
        const val BASE_URL = "https://api.o2.sk/"
    }
}