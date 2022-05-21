package com.poketmoney.api.models.responses


import com.poketmoney.api.models.entities.Referral
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReferralResponse(
    @Json(name = "copyurl")
    val copyurl: String,
    @Json(name = "info")
    val info: List<Referral>,
    @Json(name = "referral_income")
    val referralIncome: Int,
    @Json(name = "referurl")
    val referurl: String,
    @Json(name = "status")
    val status: Boolean
)