package com.poketmoney.api.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Referral(
    @Json(name = "earning_amount")
    val earningAmount: Int,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "phone_number")
    val phoneNumber: String,
    @Json(name = "received_amount")
    val receivedAmount: Int,
    @Json(name = "referral_code")
    val referralCode: String,
    @Json(name = "user_image")
    val userImage: String
)