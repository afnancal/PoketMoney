package com.poketmoney.data

import com.poketmoney.api.PoketMoneyClient

object OfferRepo {

    private val api = PoketMoneyClient.apiPoketMoney

    suspend fun getOfferList(mobileNo: String, categorySlug: String) =
        api.getOfferList(mobileNo, categorySlug).body()?.info
}