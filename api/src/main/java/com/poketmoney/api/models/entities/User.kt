package com.poketmoney.api.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "email_id")
    val emailId: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "full_address")
    val fullAddress: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "phone_number")
    val phoneNumber: String,
    @Json(name = "pin_code")
    val pinCode: String,
    @Json(name = "referral_code")
    val referralCode: String,
    @Json(name = "user_image")
    val userImage: String,
    @Json(name = "affiliate_id")
    val affiliateId: Int,
    @Json(name = "api_access")
    val apiAccess: String,
    @Json(name = "api_key")
    val apiKey: String
)