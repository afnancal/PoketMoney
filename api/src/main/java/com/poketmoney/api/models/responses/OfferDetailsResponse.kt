package com.poketmoney.api.models.responses


import com.poketmoney.api.models.entities.UserOfferDetails
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OfferDetailsResponse(
    @Json(name = "info")
    val info: List<UserOfferDetails>,
    @Json(name = "status")
    val status: Boolean
)