package com.poketmoney.ui.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.poketmoney.R

class DotIndicatorPager2Adapter : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return object : ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.slide_item_container, parent, false)
        ) {}
    }

    override fun getItemCount() = 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Empty
    }

}