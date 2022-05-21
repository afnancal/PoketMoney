package com.poketmoney.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.poketmoney.R

/**
 * Bismillah
 * Created by Md Afnan Haseeb on 01/03/22.
 */
class ViewUtils {

    private fun ViewUtils() {
        // This class is not publicly instantiable
    }

    fun changeIconDrawableToGray(context: Context?, drawable: Drawable?) {
        if (drawable != null) {
            drawable.mutate()
            drawable.setColorFilter(
                ContextCompat.getColor(context!!, R.color.grey),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }

}