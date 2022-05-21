package com.poketmoney.ui.slider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.makeramen.roundedimageview.RoundedImageView
import com.poketmoney.R
import com.poketmoney.ui.slider.SliderAdapter.SliderViewHolder

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 15/02/22.
 */
class SliderAdapter(sliderItems: MutableList<SliderItems>, viewPager2: ViewPager2) :
    RecyclerView.Adapter<SliderViewHolder>() {


    private val sliderItems: List<SliderItems>
    private val viewPager2: ViewPager2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        //return sliderItems.size();
        return 4 // for 4 tabs as well as dot indicator
    }

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: RoundedImageView = itemView.findViewById(R.id.imageSlide)
        fun setImage(sliderItems: SliderItems) {

            //use glide or picasso in case you get image from internet
            imageView.setImageResource(sliderItems.image)
        }

    }

    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

    init {
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager2
    }
}