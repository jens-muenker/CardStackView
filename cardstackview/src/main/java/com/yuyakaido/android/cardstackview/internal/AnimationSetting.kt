package com.yuyakaido.android.cardstackview.internal

import android.view.animation.Interpolator
import com.yuyakaido.android.cardstackview.Direction

interface AnimationSetting {
    fun getDirection(): Direction?
    fun getDuration(): Int
    fun getInterpolator(): Interpolator?
}
