package com.poketmoney.ui.referral

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poketmoney.R
import com.poketmoney.api.models.entities.Referral
import com.poketmoney.databinding.ReferItemDesignBinding
import com.poketmoney.di.loadImage

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 11/04/22.
 */
class ReferralAdapter :
    ListAdapter<Referral, ReferralAdapter.ReferralViewHolder>(
        object : DiffUtil.ItemCallback<Referral>() {
            override fun areItemsTheSame(oldItem: Referral, newItem: Referral): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Referral, newItem: Referral): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    ) {
    inner class ReferralViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferralViewHolder {
        return ReferralViewHolder(
            parent.context.getSystemService(LayoutInflater::class.java).inflate(
                R.layout.refer_item_design,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReferralAdapter.ReferralViewHolder, position: Int) {
        ReferItemDesignBinding.bind(holder.itemView).apply {
            val referral = getItem(position)

            if (referral.userImage != "")
                ivProfileReferItem.loadImage(referral.userImage, true)
            tvNameReferItem.text = referral.firstName + " " + referral.lastName
            tvEarningReferItem.text = "Earning: ₹" + referral.earningAmount
            tvReceived1ReferItem.text = "₹" + referral.receivedAmount
        }

    }
}