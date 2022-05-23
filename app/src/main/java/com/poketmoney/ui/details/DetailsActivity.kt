package com.poketmoney.ui.details

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.poketmoney.databinding.ActivityDetailsBinding
import com.poketmoney.di.NetworkUtils
import com.poketmoney.di.SharedPreference
import com.poketmoney.di.loadImage
import com.poketmoney.utils.CommonUtils


/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 06/04/22.
 */
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var progressDialog: Dialog
    private lateinit var offerId: String
    private lateinit var shareUrl: String

    private lateinit var detailsViewModel: DetailsViewModel
    private var sharedPreference: SharedPreference? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar
        val toolbar: Toolbar = binding.includeToolbarDetails.toolbar
        setSupportActionBar(toolbar)
        // add back arrow to toolbar
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        offerId = intent.getStringExtra("offerId").toString()
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        sharedPreference = SharedPreference(this)
        progressDialog = CommonUtils().showLoadingDialog(this)

        callingAPI()
        detailsViewModel.offerDetailsResponse.observe({ lifecycle }) {
            binding.includeToolbarDetails.tvTitleToolbar.text = it.info[0].offerName
            binding.tvProfit2Details.text = "                        " + it.info[0].offerKpi

            binding.tvCSMobile.text = it.info[0].offerName
            binding.ivBannerDetails.loadImage(it.info[0].offerCreatives, false)

            binding.includeHowWorkWebViewOffer.tvTitleWebViewOffer.text = "How to Works"
            binding.includeHowWorkWebViewOffer.webViewOffer.loadData(
                it.info[0].howWorks,
                "text/html",
                "UTF-8"
            )
            binding.includeHowWorkWebViewOffer.webViewOffer.setBackgroundColor(Color.parseColor("#fffaeb"))

            binding.includeTermsWebViewOffer.tvTitleWebViewOffer.text = "Terms & Conditions"
            binding.includeTermsWebViewOffer.webViewOffer.loadData(
                it.info[0].offerTerms,
                "text/html",
                "UTF-8"
            )
            binding.includeTermsWebViewOffer.webViewOffer.setBackgroundColor(Color.parseColor("#fffaeb"))

            binding.includeDescWebViewOffer.tvTitleWebViewOffer.text = "Description"
            //binding.includeDescWebViewOffer.webViewOffer.settings.javaScriptEnabled = true
            binding.includeDescWebViewOffer.webViewOffer.loadData(
                it.info[0].description,
                "text/html",
                "UTF-8"
            )
            binding.includeDescWebViewOffer.webViewOffer.setBackgroundColor(Color.parseColor("#fffaeb"))

            binding.includeFeatureWebViewOffer.tvTitleWebViewOffer.text = "Features"
            binding.includeFeatureWebViewOffer.webViewOffer.loadData(
                it.info[0].feature,
                "text/html",
                "UTF-8"
            )
            binding.includeFeatureWebViewOffer.webViewOffer.setBackgroundColor(Color.parseColor("#fffaeb"))

            binding.includeInstructionWebViewOffer.tvTitleWebViewOffer.text = "Instructions"
            binding.includeInstructionWebViewOffer.webViewOffer.loadData(
                it.info[0].instruction,
                "text/html",
                "UTF-8"
            )
            binding.includeInstructionWebViewOffer.webViewOffer.setBackgroundColor(
                Color.parseColor(
                    "#fffaeb"
                )
            )

            binding.includeProcessWebViewOffer.tvTitleWebViewOffer.text = "Process"
            binding.includeProcessWebViewOffer.webViewOffer.loadData(
                it.info[0].process,
                "text/html",
                "UTF-8"
            )
            binding.includeProcessWebViewOffer.webViewOffer.setBackgroundColor(Color.parseColor("#fffaeb"))

            binding.includeDocumentsWebViewOffer.tvTitleWebViewOffer.text = "Documentation"
            binding.includeDocumentsWebViewOffer.webViewOffer.loadData(
                it.info[0].documents,
                "text/html",
                "UTF-8"
            )
            binding.includeDocumentsWebViewOffer.webViewOffer.setBackgroundColor(Color.parseColor("#fffaeb"))

            binding.includeFaqsWebViewOffer.tvTitleWebViewOffer.text = "FAQs"
            binding.includeFaqsWebViewOffer.webViewOffer.loadData(
                it.info[0].faq,
                "text/html",
                "UTF-8"
            )
            binding.includeFaqsWebViewOffer.webViewOffer.setBackgroundColor(Color.parseColor("#fffaeb"))

            shareUrl = it.info[0].shareUrl
            binding.rlMainDetails.visibility = View.VISIBLE
            binding.btnShareDetails.visibility = View.VISIBLE
            CommonUtils().hideLoading(progressDialog)
        }

        binding.btnShareDetails.setOnClickListener {
            /*val text =
                "<html>\n" +
                        "<body>\n" +
                        "<p>Poket Money is one of India’s fastest-growing brokers. A fast, reliable, and easy-to-use trading platform with paperless account opening. Open your Demat account now!</p>\n" +
                        "<p>Check the below link to know our offers - </p>\n" +
                        "<a herf=\"#\" >$url</a>\n" +
                        "</body>\n" +
                        "</html>"*/
            val body: String =
                HtmlCompat.fromHtml(shareUrl, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            CommonUtils().shareText("Poket Money", body, this@DetailsActivity)
        }
    }

    private fun callingAPI() {
        progressDialog.show()
        if (NetworkUtils().isNetworkConnected(this)) {
            // fetch the data from the server
            detailsViewModel.fetchOfferDetails(sharedPreference!!.mobile!!, offerId)
        } else {
            Snackbar.make(
                binding.rlContainerDetails,
                "Please connect to the internet!",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}