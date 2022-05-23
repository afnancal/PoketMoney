package com.poketmoney.ui.offerList

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poketmoney.R
import com.poketmoney.api.models.entities.OfferDetail
import com.poketmoney.databinding.CvOfferDesignBinding
import com.poketmoney.di.loadImage
import com.poketmoney.ui.details.DetailsActivity

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 11/04/22.
 */
class OfferAdapter(private val activity: OfferListActivity) :
    ListAdapter<OfferDetail, OfferAdapter.OfferViewHolder>(
        object : DiffUtil.ItemCallback<OfferDetail>() {
            override fun areItemsTheSame(oldItem: OfferDetail, newItem: OfferDetail): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: OfferDetail, newItem: OfferDetail): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    ) {
    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        return OfferViewHolder(
            parent.context.getSystemService(LayoutInflater::class.java).inflate(
                R.layout.cv_offer_design,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        CvOfferDesignBinding.bind(holder.itemView).apply {
            val offerDetails = getItem(position)

            tvHeaderOffer.text = offerDetails.offerName
            tvDescOffer.text = offerDetails.offerKpi
            btnEarnOffer.text = offerDetails.price
            ivOffer.loadImage(offerDetails.offerLogo, false)

            btnEarnOffer.setOnClickListener {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra("offerId", offerDetails.offerId)
                activity.startActivity(intent)
            }
            root.setOnClickListener {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra("offerId", offerDetails.offerId)
                activity.startActivity(intent)
            }

        }

    }
}