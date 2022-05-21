package com.poketmoney.api.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserOfferDetails(
    @Json(name = "affiliate_id")
    val affiliateId: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "documents")
    val documents: String,
    @Json(name = "feature")
    val feature: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "how_works")
    val howWorks: String,
    @Json(name = "in_off_status")
    val inOffStatus: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "my_referral_code")
    val myReferralCode: String,
    @Json(name = "offer_category")
    val offerCategory: String,
    @Json(name = "offer_creatives")
    val offerCreatives: String,
    @Json(name = "offer_currency")
    val offerCurrency: String,
    @Json(name = "offer_kpi")
    val offerKpi: String,
    @Json(name = "offer_logo")
    val offerLogo: String,
    @Json(name = "offer_name")
    val offerName: String,
    @Json(name = "offer_price")
    val offerPrice: String,
    @Json(name = "offer_terms")
    val offerTerms: String,
    @Json(name = "offerid")
    val offerid: String,
    @Json(name = "process")
    val process: String,
    @Json(name = "short_des")
    val shortDes: String,
    @Json(name = "short_title")
    val shortTitle: String,
    @Json(name = "instruction")
    val instruction: String,
    @Json(name = "faq")
    val faq: String,
    @Json(name = "shareurl")
    val shareUrl: String
)