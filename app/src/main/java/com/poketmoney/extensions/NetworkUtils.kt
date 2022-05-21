package com.poketmoney.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.android.volley.Response
import com.android.volley.toolbox.Volley

/**
 * Bismillah
 * Created by Md Afnan Haseeb on 01/03/22.
 */
class NetworkUtils {

    private fun NetworkUtils() {
        // This class is not publicly instantiable
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            /*val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting*/
            val capability = cm.getNetworkCapabilities(cm.activeNetwork)
            return capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        }
        return false
    }

    fun uploadImage(imageData: ByteArray, imgFileName: String, activity: Activity) {
        val request = object : VolleyFileUploadRequest(
            Method.POST,
            AppConstants().postURL,
            Response.Listener {
                println("response is: $it")
            },
            Response.ErrorListener {
                println("error is: $it")
            }
        ) {
            override fun getByteData(): MutableMap<String, FileDataPart> {
                val params = HashMap<String, FileDataPart>()
                params["image"] = FileDataPart(imgFileName, imageData, "jpg")
                return params
            }
        }
        Volley.newRequestQueue(activity).add(request)
    }

}