package com.poketmoney.api.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "msg")
    val msg: String,
    @Json(name = "status")
    val status: Boolean,
    @Json(name = "my_referral_code")
    val myReferralCode: String
)