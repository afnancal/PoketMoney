package com.poketmoney.ui.login

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poketmoney.api.PoketMoneyClient
import com.poketmoney.api.models.responses.LoginResponse
import com.poketmoney.api.models.responses.ResendOtpResponse
import com.poketmoney.di.AppConstants
import com.poketmoney.di.SharedPreference
import com.poketmoney.utils.CommonUtils
import kotlinx.coroutines.launch

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 01/03/22.
 */
class LoginViewModel : ViewModel() {

    private var sharedPreference: SharedPreference? = null
    private val apiOtp = PoketMoneyClient.apiMSG91
    private val otpResp = MutableLiveData<String?>()
    val resendOtpResp = MutableLiveData<ResendOtpResponse>()

    private val api = PoketMoneyClient.apiPoketMoney
    val loginResponse = MutableLiveData<LoginResponse>()

    fun fetchUserInfo(mobileNo: String) = viewModelScope.launch {
        val response = api.getLoginInfoByMobile(mobileNo)

        response.body()?.let { loginResponse.postValue(it) }
    }

    fun sendOtp(mobileNo: String, activity: Activity) = viewModelScope.launch {
        sharedPreference = SharedPreference(activity)
        val otp = CommonUtils().generateOTP()

        sharedPreference!!.otp = otp
        val response =
            apiOtp.sendOtpToMobile(
                AppConstants().templateId,
                mobileNo,
                AppConstants().authKey,
                otp
            )

        response.body()?.type.let { otpResp.postValue(it) }
    }

    fun reSendOtp(mobileNo: String) = viewModelScope.launch {
        val response =
            apiOtp.reSendOtpToMobile(
                AppConstants().authKey,
                "text",
                mobileNo
            )

        response.body()?.let { resendOtpResp.postValue(it) }
    }

}