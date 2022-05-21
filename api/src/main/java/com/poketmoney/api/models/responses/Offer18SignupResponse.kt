package com.poketmoney.api.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Offer18SignupResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "affiliate_id")
    val affiliateId: Int,
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "signup_ip")
    val signupIp: String,
    @Json(name = "api_access")
    val apiAccess: String,
    @Json(name = "api_key")
    val apiKey: String
)