package com.poketmoney.api.models.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OfferListResponse(
    @Json(name = "info")
    val info: List<Info>,
    @Json(name = "status")
    val status: Boolean
)