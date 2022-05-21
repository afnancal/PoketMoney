package com.poketmoney.api.services

import com.poketmoney.api.models.entities.User
import com.poketmoney.api.models.responses.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 28/02/22.
 */
interface PoketMoneyAPI {

    @FormUrlEncoded
    @POST("affiliate_create")
    fun offer18Signup(
        @Query("api_access") apiAccess: String? = null,
        @Query("mid") mid: String? = null,
        @Query("api-key") apiKey: String? = null,
        @Query("secret-key") secretKey: String? = null,
        @Field("first_name") fName: String?,
        @Field("last_name") lName: String?,
        @Field("signup_ip") signupIP: String?,
        @Field("email") email: String?,
        @Field("mobile") mobile: String?,
        @Field("zip") zip: String?,
        @Field("country") country: String?,
        @Field("status") status: String?
    ): Call<Offer18SignupResponse>


    /**********************************************************************************************/


    @GET("otp")
    suspend fun sendOtpToMobile(
        @Query("template_id") templateId: String? = null,
        @Query("mobile") mobile: String? = null,
        @Query("authkey") authKey: String? = null,
        @Query("otp") otp: String? = null
    ): Response<SendOtpResponse>

    @GET("otp/retry")
    suspend fun reSendOtpToMobile(
        @Query("authkey") authKey: String? = null,
        @Query("retrytype") retryType: String? = null,
        @Query("mobile") mobile: String? = null
    ): Response<ResendOtpResponse>


    /**********************************************************************************************/


    @POST("poketsignup.php")
    suspend fun signupUser(
        @Body userCreds: User
    ): Response<SignupResponse>


    @GET("poketlogin.php")
    suspend fun getLoginInfoByMobile(
        @Query("phone_no") phoneNo: String? = null
    ): Response<LoginResponse>


    @GET("poketreferalcheck.php")
    suspend fun getReferralCheck(
        @Query("referral_code_check") referralCheck: String? = null
    ): Response<LoginResponse>


    @GET("poketoffercat.php")
    suspend fun getOfferList(
        @Query("phone_no") phoneNo: String? = null,
        @Query("category_slug") categorySlug: String? = null
    ): Response<OfferListResponse>


    @GET("poketofferdetails.php")
    suspend fun getOfferDetails(
        @Query("phone_no") phoneNo: String? = null,
        @Query("offerid") offerId: String? = null
    ): Response<OfferDetailsResponse>


    @GET("poketreferralmembers.php")
    suspend fun getReferralList(
        @Query("phone_number") phoneNo: String? = null
    ): Response<ReferralResponse>

}