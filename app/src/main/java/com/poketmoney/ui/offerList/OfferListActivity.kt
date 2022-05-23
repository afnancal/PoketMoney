package com.poketmoney.ui.offerList

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.poketmoney.R
import com.poketmoney.databinding.ActivityOfferListBinding
import com.poketmoney.utils.CommonUtils
import com.poketmoney.di.NetworkUtils
import com.poketmoney.di.SharedPreference

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 11/04/22.
 */
class OfferListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfferListBinding
    private lateinit var categorySlug: String

    private lateinit var progressDialog: Dialog
    private var sharedPreference: SharedPreference? = null
    private lateinit var offerListViewModel: OfferListViewModel
    private lateinit var offerAdapter: OfferAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offer_list)

        // toolbar
        val toolbar: Toolbar = binding.includeToolbarOfferList.toolbar
        setSupportActionBar(toolbar)
        // add back arrow to toolbar
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        categorySlug = intent.getStringExtra("categorySlug").toString()
        sharedPreference = SharedPreference(this)
        progressDialog = CommonUtils().showLoadingDialog(this)


        offerListViewModel = ViewModelProvider(this).get(OfferListViewModel::class.java)
        offerAdapter = OfferAdapter(this@OfferListActivity)

        binding.rvOfferList.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvOfferList.adapter = offerAdapter

        callingAPI()
        offerListViewModel.infoListResponse.observe({ lifecycle }) {
            binding.includeToolbarOfferList.tvTitleToolbar.text = it[0].categoryName
            offerAdapter.submitList(it[0].offerDetails)
            CommonUtils().hideLoading(progressDialog)
        }
    }

    private fun callingAPI() {
        progressDialog.show()
        if (NetworkUtils().isNetworkConnected(this)) {
            // fetch the data from the server
            offerListViewModel.fetchMyFeed(sharedPreference!!.mobile!!, categorySlug)
        } else {
            Snackbar.make(
                binding.rlContainerOfferList,
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