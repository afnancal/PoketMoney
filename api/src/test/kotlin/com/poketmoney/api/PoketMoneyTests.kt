package com.poketmoney.api

import com.poketmoney.api.models.entities.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import kotlin.random.Random

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 28/02/22.
 */
class PoketMoneyTests {


    @Test
    fun `POST Offer18 Signup`() {
        runBlocking {
            val respTest = PoketMoneyClient.apiOffer18.offer18Signup(
                apiAccess = "enable",
                mid = "9192",
                apiKey = "9192VRZUHMPDWTIFCQO",
                secretKey = "61FBF8193F815F0FBE269B196301D392",
                fName = "Tes",
                lName = "In",
                signupIP = "192.168.1.30",
                email = "tes@t.com",
                mobile = "9632587411",
                zip = "700073",
                country = "IN",
                status = "Approved"
            )
            //assertNotNull(respTest.body()?.status)
        }
    }

    @Test
    fun `Send OTP To Mobile`() {
        runBlocking {
            val sendOtpToMobile =
                PoketMoneyClient.apiMSG91.sendOtpToMobile(
                    templateId = "621cc38fd2d09415c11c6af5",
                    mobile = "919477992616",
                    authKey = "373014ASVGq57DNX96205f9bbP1",
                    otp = "${Random.nextInt(9990, 9999)}"
                )
            assertNotNull(sendOtpToMobile.body()?.type)
        }
    }

    @Test
    fun `ReSend OTP To Mobile`() {
        runBlocking {
            val reSendOtpToMobile =
                PoketMoneyClient.apiMSG91.reSendOtpToMobile(
                    authKey = "373014ASVGq57DNX96205f9bbP1",
                    retryType = "text",
                    mobile = "919477992616"
                )
            assertNotNull(reSendOtpToMobile.body()?.type)
        }
    }


    @Test
    fun `POST users - create user`() {
        val userCreds = User(
            phoneNumber = "9876543217",
            firstName = "rand_user_${Random.nextInt(99, 999)}",
            lastName = "rand_user_${Random.nextInt(99, 999)}",
            emailId = "test${Random.nextInt(999, 9999)}@test.com",
            userImage = "image${Random.nextInt(999, 9999)}.jpg",
            pinCode = "${Random.nextInt(99999, 999999)}",
            fullAddress = "rand_user_${Random.nextInt(99, 999)}",
            referralCode = "POK${Random.nextInt(999, 9999)}NEY",
            affiliateId = Random.nextInt(99999, 999999),
            apiAccess = "enable",
            apiKey = "Random${Random.nextInt(999, 9999)}KEY"
        )
        runBlocking {
            val resp = PoketMoneyClient.apiPoketMoney.signupUser(userCreds)
            //assertEquals(userCreds.phoneNumber, resp.body()?.status)
            assertNotNull(resp.body()?.status)
        }
    }

    @Test
    fun `GET Login info By Mobile`() {
        runBlocking {
            val loginInfoByMobile =
                PoketMoneyClient.apiPoketMoney.getLoginInfoByMobile(phoneNo = "9876543210")
            assertNotNull(loginInfoByMobile.body()?.status)
        }
    }

}