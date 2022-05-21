package com.poketmoney.api.models.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResendOtpResponse(
    @Json(name = "message")
    val message: String,
    @Json(name = "type")
    val type: String
)