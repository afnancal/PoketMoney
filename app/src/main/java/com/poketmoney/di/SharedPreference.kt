package com.poketmoney.di

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    private val SKIP_NOW = "skipNow"
    private val OTP = "otp"
    private val LOGIN = "login"
    private val MOBILE = "mobile"
    private val MY_REFERRAL_CODE = "myReferralCode"

    private val preferences: SharedPreferences =
        context.getSharedPreferences(Context.MODE_PRIVATE.toString(), 0)

    var skipNow: Boolean
        get() = preferences.getBoolean(SKIP_NOW, false)
        set(value) = preferences.edit().putBoolean(SKIP_NOW, value).apply()

    var otp: String?
        get() = preferences.getString(OTP, "")
        set(value) = preferences.edit().putString(OTP, value).apply()

    var mobile: String?
        get() = preferences.getString(MOBILE, "")
        set(value) = preferences.edit().putString(MOBILE, value).apply()

    var myReferralCode: String?
        get() = preferences.getString(MY_REFERRAL_CODE, "")
        set(value) = preferences.edit().putString(MY_REFERRAL_CODE, value).apply()

    var login: Boolean
        get() = preferences.getBoolean(LOGIN, false)
        set(value) = preferences.edit().putBoolean(LOGIN, value).apply()

}