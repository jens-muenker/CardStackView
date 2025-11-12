package com.yuyakaido.android.cardstackview.internal

import android.content.Context

object DisplayUtil {
    fun dpToPx(context: Context, dp: Float): Int {
        val density = context.resources.displayMetrics.density
        val px = dp * density
        // Symmetric rounding for negatives: add 0.5 for positive, subtract 0.5 for negative
        return if (px >= 0f) (px + 0.5f).toInt() else (px - 0.5f).toInt()
    }
}
