package com.yuyakaido.android.cardstackview.internal

import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.RewindAnimationSetting
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import com.yuyakaido.android.cardstackview.SwipeableMethod

class CardStackSetting {
    var stackFrom: StackFrom = StackFrom.None
    var visibleCount: Int = 3
    var translationInterval: Float = 8.0f
    var scaleInterval: Float = 0.95f // 0.0f - 1.0f
    @JvmField
    var swipeThreshold: Float = 0.3f // 0.0f - 1.0f
    var maxDegree: Float = 20.0f
    @JvmField
    var directions: List<Direction> = Direction.HORIZONTAL
    var canScrollHorizontal: Boolean = true
    var canScrollVertical: Boolean = true
    var swipeableMethod: SwipeableMethod = SwipeableMethod.AutomaticAndManual
    @JvmField
    var swipeAnimationSetting: SwipeAnimationSetting = SwipeAnimationSetting.Builder().build()
    @JvmField
    var rewindAnimationSetting: RewindAnimationSetting = RewindAnimationSetting.Builder().build()
    var overlayInterpolator: Interpolator = LinearInterpolator()
}
