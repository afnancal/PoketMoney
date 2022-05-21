package com.poketmoney.ui.offerList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poketmoney.api.models.entities.OfferDetail
import com.poketmoney.api.models.responses.Info
import com.poketmoney.data.OfferRepo
import kotlinx.coroutines.launch

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 11/04/22.
 */
class OfferListViewModel : ViewModel() {

    val infoListResponse = MutableLiveData<List<Info>>()
    //val info: LiveData<List<Info>> = infoListResponse

    fun fetchMyFeed(mobileNo: String, categorySlug: String) = viewModelScope.launch {
        OfferRepo.getOfferList(mobileNo, categorySlug)?.let {
            infoListResponse.postValue(it)
        }
    }

}