package com.poketmoney.ui.signup

import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poketmoney.api.PoketMoneyClient
import com.poketmoney.api.models.entities.User
import com.poketmoney.api.models.responses.LoginResponse
import com.poketmoney.api.models.responses.Offer18SignupResponse
import com.poketmoney.api.models.responses.SignupResponse
import com.poketmoney.di.AppConstants
import com.poketmoney.utils.CommonUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 02/03/22.
 */
class SignupViewModel : ViewModel() {

    private val apiOffer18 = PoketMoneyClient.apiOffer18
    //val offer18SignupResponse = MutableLiveData<Offer18SignupResponse>()

    private val apiPoketMoney = PoketMoneyClient.apiPoketMoney
    val referralCodeResponse = MutableLiveData<LoginResponse>()
    val signupResponse = MutableLiveData<SignupResponse>()

    fun offer18Signup(
        fName: String,
        lName: String,
        email: String,
        mobile: String,
        zip: String,
        @Nullable callbacks: com.poketmoney.ui.signup.SignupResponse
    ) =
        viewModelScope.launch {
            val response = apiOffer18.offer18Signup(
                AppConstants().apiAccess,
                AppConstants().mid,
                AppConstants().apiKey,
                AppConstants().secretKey,
                fName,
                lName,
                CommonUtils().getIPAddress(true),   // if true means ipv4, false -> ipv6
                email,
                mobile,
                zip,
                AppConstants().country,
                AppConstants().status
            )

            //response.body()?.let { offer18SignupResponse.postValue(it) }
            response.enqueue(object : Callback<Offer18SignupResponse> {
                override fun onResponse(
                    call: Call<Offer18SignupResponse>,
                    response: Response<Offer18SignupResponse>
                ) {
                    //Log.e("Afnan", "response 33: " + Gson().toJson(response.body()))
                    callbacks.onSuccess(response.body()!!)
                }

                override fun onFailure(call: Call<Offer18SignupResponse>, t: Throwable) {
                    Log.e("Afnan", "onFailure: $t")
                    callbacks.onError(t)
                }

            })
        }


    fun checkReferralCode(referralCode: String) = viewModelScope.launch {
        val response = apiPoketMoney.getReferralCheck(referralCode)

        response.body()?.let { referralCodeResponse.postValue(it) }
    }

    fun signupUser(userCreds: User) = viewModelScope.launch {
        val response = apiPoketMoney.signupUser(userCreds)

        response.body()?.let { signupResponse.postValue(it) }
    }

}