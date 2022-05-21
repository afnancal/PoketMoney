package com.poketmoney.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.poketmoney.R
import com.poketmoney.databinding.ActivityIntroBinding
import com.poketmoney.extensions.SharedPreference
import com.poketmoney.ui.login.LoginActivity
import com.poketmoney.ui.slider.SliderAdapter
import com.poketmoney.ui.slider.SliderItems

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 10/02/22.
 */
class IntroActivity : AppCompatActivity() {

    private lateinit var databinding: ActivityIntroBinding
    var sharedPreference: SharedPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        sharedPreference = SharedPreference(this)
        //databinding.tvHeadIntro.text = "How IT WORKS?"
        databinding.btnSkipIntro.setOnClickListener {
            sharedPreference!!.skipNow = true
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val imageList: ArrayList<SliderItems> = ArrayList()
        imageList.add(SliderItems(R.drawable.intro_1))
        imageList.add(SliderItems(R.drawable.intro_2))
        imageList.add(SliderItems(R.drawable.intro_3))
        imageList.add(SliderItems(R.drawable.intro_4))
        setImageInSlider(imageList, databinding.viewPagerImageSlider)
    }

    private fun setImageInSlider(images: ArrayList<SliderItems>, viewPager2: ViewPager2) {
        viewPager2.adapter = SliderAdapter(images, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(90))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleX = 0.85f + r * 0.50f
            page.scaleY = 0.85f + r * 0.45f
        }
        viewPager2.setPageTransformer(compositePageTransformer)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    // you are on the first page
                }
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3500)
            }
        })
        /*val adapter = DotIndicatorPager2Adapter()
        databinding.viewPagerImageSlider.adapter = adapter
        val zoomOutPageTransformer = ZoomOutPageTransformer()
        databinding.viewPagerImageSlider.setPageTransformer { page, position ->
            zoomOutPageTransformer.transformPage(page, position)
        }*/
        databinding.dotsIndicator.setViewPager2(viewPager2)
    }

    //class scope
    val handler = Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        override fun run() {
            //handler.removeCallbacksAndMessages(null)
            //make sure you cancel the previous task in case you scheduled one that has not run yet
            //do your thing
            databinding.viewPagerImageSlider.currentItem =
                databinding.viewPagerImageSlider.currentItem + 1
            handler.postDelayed(this, 3500)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3500)
    }

}