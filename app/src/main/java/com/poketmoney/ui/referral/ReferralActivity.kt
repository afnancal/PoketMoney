package com.poketmoney.ui.referral

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.poketmoney.R
import com.poketmoney.databinding.ActivityReferralBinding
import com.poketmoney.extensions.NetworkUtils
import com.poketmoney.extensions.SharedPreference
import com.poketmoney.utils.CommonUtils


/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 18/04/22.
 */
class ReferralActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReferralBinding

    private lateinit var progressDialog: Dialog
    private var sharedPreference: SharedPreference? = null
    private lateinit var referralViewModel: ReferralViewModel
    private lateinit var referralAdapter: ReferralAdapter

    private lateinit var copyUrl: String
    private lateinit var referUrl: String
    private var referralIncome: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_referral)

        // toolbar
        val toolbar: Toolbar = binding.includeToolbarReferralActivity.toolbar
        setSupportActionBar(toolbar)
        // add back arrow to toolbar
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        binding.includeToolbarReferralActivity.tvTitleToolbar.text = "Referral"

        sharedPreference = SharedPreference(this)
        progressDialog = CommonUtils().showLoadingDialog(this)


        referralViewModel = ViewModelProvider(this).get(ReferralViewModel::class.java)
        referralAdapter = ReferralAdapter()

        binding.rvReferralActivity.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvReferralActivity.adapter = referralAdapter

        callingAPI()
        referralViewModel.infoListResponse.observe({ lifecycle }) {
            copyUrl = it.copyurl
            referUrl = it.referurl
            referralIncome = it.referralIncome
            referralAdapter.submitList(it.info)
            if (it.info.isNotEmpty()) {
                binding.rlReferImgReferralActivity.visibility = View.GONE
                binding.llCountReferralActivity.visibility = View.VISIBLE
                binding.rvReferralActivity.visibility = View.VISIBLE

                binding.tvCountReferralActivity.text = it.info.size.toString()
                binding.tvIncomeReferralActivity.text = "₹" + it.referralIncome
            }
            binding.rlMainReferralActivity.visibility = View.VISIBLE
            CommonUtils().hideLoading(progressDialog)
        }
        binding.tvCodeReferralActivity.text = sharedPreference!!.myReferralCode
        binding.rlCopyReferralActivity.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", copyUrl)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(applicationContext, "text copied", Toast.LENGTH_LONG).show()
        }
        binding.rlReferReferralActivity.setOnClickListener {
            val body: String =
                HtmlCompat.fromHtml(referUrl, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            CommonUtils().shareText("Poket Money", body, this@ReferralActivity)
        }
    }

    private fun callingAPI() {
        progressDialog.show()
        if (NetworkUtils().isNetworkConnected(this)) {
            // fetch the data from the server
            referralViewModel.fetchMyReferral(sharedPreference!!.mobile!!)
        } else {
            Snackbar.make(
                binding.rlContainerReferralActivity,
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