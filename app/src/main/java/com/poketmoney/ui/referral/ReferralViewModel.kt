package com.poketmoney.ui.referral

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poketmoney.api.PoketMoneyClient
import com.poketmoney.api.models.responses.ReferralResponse
import kotlinx.coroutines.launch

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 18/04/22.
 */
class ReferralViewModel : ViewModel() {

    private val api = PoketMoneyClient.apiPoketMoney
    val infoListResponse = MutableLiveData<ReferralResponse>()

    fun fetchMyReferral(mobileNo: String) = viewModelScope.launch {
        val response = api.getReferralList(mobileNo)

        response.body()?.let { infoListResponse.postValue(it) }
    }
}