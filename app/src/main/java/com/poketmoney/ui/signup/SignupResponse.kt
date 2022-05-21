package com.poketmoney.ui.signup

import com.poketmoney.api.models.responses.Offer18SignupResponse


/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 07/03/22.
 */
interface SignupResponse {

    fun onSuccess(value: Offer18SignupResponse)

    fun onError(throwable: Throwable)

}