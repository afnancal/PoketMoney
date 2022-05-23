package com.poketmoney.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.poketmoney.R
import com.poketmoney.ui.login.LoginActivity
import com.poketmoney.di.SharedPreference

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 10/02/22.
 */
@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private var sharedPreference: SharedPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreference = SharedPreference(this)
        val skipNow = sharedPreference!!.skipNow
        // This is used to hide the status bar and make the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // For change the status bar color
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.off_white)
        // For change the navigation bar color
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.off_white)

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            if (skipNow) {
                if (sharedPreference!!.login) {
                    // for testing purpose, off this section
                    //val intent = Intent(this, OfferListActivity::class.java)
                    val intent = Intent(this, WebViewActivity::class.java)
                    intent.putExtra("phone", sharedPreference!!.mobile)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            } else {
                val intent = Intent(this, IntroActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 4000) // 4000 is the delayed time in milliseconds.
    }
}