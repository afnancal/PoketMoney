package com.poketmoney.utils

import android.text.TextUtils
import android.util.Patterns

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 19/02/22.
 */
class Validation {

    /**
     * Validation of Phone Number
     */
    fun isValidPhoneNumber(target: CharSequence): Boolean {
        val firstLetter = target[0].toString().toInt()
        if (target.length < 10) {
            return false
        } else if (firstLetter == 6 || firstLetter == 7 || firstLetter == 8 || firstLetter == 9) {
            return Patterns.PHONE.matcher(target).matches()
        }
        return false
    }

    /**
     * Validation of Email Address
     */
    fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    /**
     * Validation of Pincode
     */
    fun isValidPincode(target: CharSequence): Boolean {
        return target.length >= 6
    }

    /**
     * Validation of Pincode
     */
    fun isValidOTP(target: CharSequence): Boolean {
        return target.length >= 4
    }

}