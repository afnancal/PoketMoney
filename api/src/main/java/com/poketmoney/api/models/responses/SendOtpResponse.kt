package com.poketmoney.api.models.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendOtpResponse(
    @Json(name = "request_id")
    val requestId: String,
    @Json(name = "type")
    val type: String
)