package com.yuyakaido.android.cardstackview.internal

import android.content.Context

object DisplayUtil {
    fun dpToPx(context: Context, dp: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }
}
