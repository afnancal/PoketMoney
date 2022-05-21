package com.poketmoney.api.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OfferDetail(
    @Json(name = "offer_id")
    val offerId: String,
    @Json(name = "offer_kpi")
    val offerKpi: String,
    @Json(name = "offer_logo")
    val offerLogo: String,
    @Json(name = "offer_name")
    val offerName: String,
    @Json(name = "price")
    val price: String
)