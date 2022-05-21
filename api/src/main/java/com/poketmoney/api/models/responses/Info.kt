package com.poketmoney.api.models.responses


import com.poketmoney.api.models.entities.OfferDetail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Info(
    @Json(name = "category_name")
    val categoryName: String,
    @Json(name = "offer_details")
    val offerDetails: List<OfferDetail>
)