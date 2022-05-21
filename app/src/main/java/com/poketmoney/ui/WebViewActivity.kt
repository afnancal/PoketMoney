package com.poketmoney.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.poketmoney.R
import com.poketmoney.databinding.ActivityWebviewBinding
import com.poketmoney.extensions.SharedPreference
import com.poketmoney.ui.details.DetailsActivity
import com.poketmoney.ui.offerList.OfferListActivity
import com.poketmoney.ui.referral.ReferralActivity
import com.poketmoney.utils.CommonUtils

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 10/02/22.
 */
class WebViewActivity : AppCompatActivity() {

    private lateinit var databinding: ActivityWebviewBinding
    private var sharedPreference: SharedPreference? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_webview)

        sharedPreference = SharedPreference(this)
        sharedPreference!!.login = true
        val mobileNo = intent.getStringExtra("phone")
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        databinding.webView.webViewClient = WebViewClient()

        // this will enable the javascript settings
        databinding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        databinding.webView.settings.setSupportZoom(true)

        // Set web view client
        databinding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                super.shouldOverrideUrlLoading(view, request)
                val url: String = request?.url.toString()
                when {
                    url.contains("https://poketmoney.org/app/logout.php") -> {
                        CommonUtils().showAlertDialog(this@WebViewActivity)
                    }
                    url.contains("https://poketmoney.org/app/offers.php") -> {
                        val bits: List<String> = url.split("category=")
                        val lastOne = bits[bits.size - 1]
                        val intent = Intent(this@WebViewActivity, OfferListActivity::class.java)
                        intent.putExtra("categorySlug", lastOne)
                        startActivity(intent)
                    }
                    url.contains("https://poketmoney.org/app/offer-details.php") -> {
                        val bits: List<String> = url.split("offerid=")
                        val lastOne = bits[bits.size - 1]
                        val intent = Intent(this@WebViewActivity, DetailsActivity::class.java)
                        intent.putExtra("offerId", lastOne)
                        startActivity(intent)
                    }
                    url.contains("https://poketmoney.org/app/referral.php") -> {
                        val intent = Intent(this@WebViewActivity, ReferralActivity::class.java)
                        startActivity(intent)
                    }
                    else -> {
                        /*val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(i)*/
                        view?.loadUrl(url)
                    }
                }
                return true
            }
        }

        // this will load the url of the website
        databinding.webView.loadUrl("https://poketmoney.org/app/index.php?mobile=$mobileNo")
    }

    // if you press Back button this code will work
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (databinding.webView.canGoBack())
            databinding.webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}
