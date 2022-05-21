package com.poketmoney.api

import com.poketmoney.api.services.PoketMoneyAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 28/02/22.
 */
object PoketMoneyClient {

    private var authToken: String? = null

    private val authInterceptor = Interceptor { chain ->
        var req = chain.request()
        authToken?.let {
            req = req.newBuilder()
                .header("Authorization", "Token $it")
                .build()
        }
        chain.proceed(req)
    }

    private val okHttpBuilder = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(2, TimeUnit.SECONDS)

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://conduit.productionready.io/api/")
        .addConverterFactory(MoshiConverterFactory.create())

    val publicApi = retrofitBuilder
        .client(okHttpBuilder.build())
        .build()
        .create(PoketMoneyAPI::class.java)

    val authApi = retrofitBuilder
        .client(okHttpBuilder.addInterceptor(authInterceptor).build())
        .build()
        .create(PoketMoneyAPI::class.java)


    /*********************************************************************************************************************/


    // For Offer18 User Signup
    private val retrofitOffer18 = Retrofit.Builder()
        .baseUrl("https://api.offer18.com/api/m/")
        .addConverterFactory(MoshiConverterFactory.create())

    val apiOffer18 = retrofitOffer18
        .client(okHttpBuilder.build())
        .build()
        .create(PoketMoneyAPI::class.java)


    // For MSG91 sms sent
    private val retrofitMSG91 = Retrofit.Builder()
        .baseUrl("https://api.msg91.com/api/v5/")
        .addConverterFactory(MoshiConverterFactory.create())

    val apiMSG91 = retrofitMSG91
        .client(okHttpBuilder.build())
        .build()
        .create(PoketMoneyAPI::class.java)


    private val retrofitPoketMoneyAPI = Retrofit.Builder()
        .baseUrl("https://www.poketmoney.org/app/restapi/")
        .addConverterFactory(MoshiConverterFactory.create())

    val apiPoketMoney = retrofitPoketMoneyAPI
        .client(okHttpBuilder.build())
        .build()
        .create(PoketMoneyAPI::class.java)

}