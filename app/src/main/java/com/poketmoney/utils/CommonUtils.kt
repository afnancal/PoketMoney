package com.poketmoney.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.poketmoney.R
import com.poketmoney.extensions.AppConstants
import com.poketmoney.extensions.SharedPreference
import com.poketmoney.ui.login.LoginActivity
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.InetAddress
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.*


/**
 * Bismillah
 * Created by Md Afnan Haseeb on 01/03/22.
 */
class CommonUtils {

    private var sharedPreference: SharedPreference? = null

    @SuppressLint("all")
    fun getDeviceId(context: Context): String? {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }


    fun getTimestamp(): String? {
        return SimpleDateFormat(AppConstants().TIMESTAMP_FORMAT, Locale.US).format(Date())
    }


    /**
     * Function for Generate 4 digit OTP String
     * @return
     */
    fun generateOTP(): String {
        val randomPin = (Math.random() * 9000).toInt() + 1000
        return randomPin.toString()
    }


    fun showLoadingDialog(context: Context): Dialog {
        val progressDialog = Dialog(context)
        //progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

    fun hideLoading(progressDialog: Dialog) {
        if (progressDialog.isShowing) {
            progressDialog.cancel()
        }
    }


    fun hideKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun showAlertDialog(activity: Activity) {
        sharedPreference = SharedPreference(activity)
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(activity)
        alertDialog.setTitle("Logout Alert!")
        alertDialog.setMessage("Are you sure, you want to logout?")
        alertDialog.setPositiveButton(
            "yes"
        ) { _, _ ->
            sharedPreference!!.mobile = ""
            sharedPreference!!.myReferralCode = ""
            sharedPreference!!.login = false
            val intent = Intent(activity, LoginActivity::class.java)
            // set the new task and clear flags
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
            activity.finish()
        }
        alertDialog.setNegativeButton(
            "No"
        ) { _, _ -> }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()

        val buttonBackground: Button = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
        buttonBackground.setTextColor(Color.DKGRAY)
        //buttonBackground.textSize = 16F

        val buttonBackground1: Button = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        buttonBackground1.setTextColor(Color.GREEN)
        //buttonBackground1.textSize = 16F
    }


    fun shareText(subject: String?, body: String?, activity: Activity) {
        val txtIntent = Intent(Intent.ACTION_SEND)
        txtIntent.type = "text/plain"
        txtIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        txtIntent.putExtra(Intent.EXTRA_TEXT, body)
        activity.startActivity(Intent.createChooser(txtIntent, "Share via:"))
    }


    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }


    /**
     * Get IP address from first non-localhost interface
     * @param useIPv4   true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    fun getIPAddress(useIPv4: Boolean): String {
        try {
            val interfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress) {
                        val sAddr: String? = addr.hostAddress
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        val isIPv4 = sAddr?.indexOf(':')!! < 0
                        if (useIPv4) {
                            if (isIPv4) return sAddr
                        } else {
                            if (!isIPv4) {
                                val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                return if (delim < 0) sAddr.uppercase(Locale.getDefault()) else sAddr.substring(
                                    0,
                                    delim
                                ).uppercase(Locale.getDefault())
                            }
                        }
                    }
                }
            }
        } catch (ignored: Exception) {
        } // for now eat exceptions
        return ""
    }

}