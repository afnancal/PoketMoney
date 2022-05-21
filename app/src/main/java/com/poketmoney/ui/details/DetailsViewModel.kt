package com.poketmoney.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poketmoney.api.PoketMoneyClient
import com.poketmoney.api.models.responses.OfferDetailsResponse
import kotlinx.coroutines.launch

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 12/04/22.
 */
class DetailsViewModel : ViewModel() {

    private val api = PoketMoneyClient.apiPoketMoney
    val offerDetailsResponse = MutableLiveData<OfferDetailsResponse>()

    fun fetchOfferDetails(mobileNo: String, offerId: String) = viewModelScope.launch {
        val response = api.getOfferDetails(mobileNo, offerId)

        response.body()?.let { offerDetailsResponse.postValue(it) }
    }

}