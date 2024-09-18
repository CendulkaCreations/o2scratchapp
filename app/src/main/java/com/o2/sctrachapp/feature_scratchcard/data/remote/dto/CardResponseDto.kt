package com.o2.sctrachapp.feature_scratchcard.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CardResponseDto(
    @SerializedName("android") val version: String,
)
