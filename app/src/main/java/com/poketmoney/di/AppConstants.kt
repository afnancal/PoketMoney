package com.poketmoney.di

/**
 * Bismillah
 * Created by Md Afnan Haseeb on 01/03/22.
 */
public class AppConstants {

    val API_STATUS_CODE_LOCAL_ERROR = 0

    val DB_NAME = "mindorks_mvvm.db"

    val NULL_INDEX = -1L

    val PREF_NAME = "mindorks_pref"

    val SEED_DATABASE_OPTIONS = "seed/options.json"

    val SEED_DATABASE_QUESTIONS = "seed/questions.json"

    val STATUS_CODE_FAILED = "failed"

    val STATUS_CODE_SUCCESS = "success"

    val TIMESTAMP_FORMAT = "ddMMyyyy_HHmmss"

    val postURL = "https://www.poketmoney.org/app/restapi/poketimageupload.php"

    // For MSG91
    val templateId = "621cc38fd2d09415c11c6af5"
    val authKey = "373014ASVGq57DNX96205f9bbP1"


    // For Offer18
    val apiAccess = "enable"
    val mid = "9192"
    val apiKey = "9192VRZUHMPDWTIFCQO"
    val secretKey = "61FBF8193F815F0FBE269B196301D392"
    val country = "IN"
    val status = "Approved"


    private fun AppConstants() {
        // This utility class is not publicly instantiable
    }

}