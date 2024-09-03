package com.yuyakaido.android.cardstackview.internal

import androidx.recyclerview.widget.RecyclerView
import com.yuyakaido.android.cardstackview.Direction
import kotlin.math.abs
import kotlin.math.min

class CardStackState {
    var status: Status = Status.Idle
    var width: Int = 0
    var height: Int = 0
    var dx: Int = 0
    var dy: Int = 0
    var topPosition: Int = 0
    var targetPosition: Int = RecyclerView.NO_POSITION
    var proportion: Float = 0.0f

    enum class Status {
        Idle,
        Dragging,
        RewindAnimating,
        AutomaticSwipeAnimating,
        AutomaticSwipeAnimated,
        ManualSwipeAnimating,
        ManualSwipeAnimated;

        val isBusy: Boolean
            get() = this != Idle

        val isDragging: Boolean
            get() = this == Dragging

        val isSwipeAnimating: Boolean
            get() = this == ManualSwipeAnimating || this == AutomaticSwipeAnimating

        fun toAnimatedStatus(): Status {
            return when (this) {
                ManualSwipeAnimating -> ManualSwipeAnimated
                AutomaticSwipeAnimating -> AutomaticSwipeAnimated
                else -> Idle
            }
        }
    }

    fun next(state: Status) {
        this.status = state
    }

    val direction: Direction
        get() = if (abs(dy.toDouble()) < abs(dx.toDouble())) {
            if (dx < 0.0f) {
                Direction.Left
            } else {
                Direction.Right
            }
        } else {
            if (dy < 0.0f) {
                Direction.Top
            } else {
                Direction.Bottom
            }
        }

    val ratio: Float
        get() {
            val absDx = abs(dx.toDouble()).toInt()
            val absDy = abs(dy.toDouble()).toInt()
            val ratio = if (absDx < absDy) {
                absDy / (height / 2.0f)
            } else {
                absDx / (width / 2.0f)
            }
            return min(ratio.toDouble(), 1.0).toFloat()
        }

    val isSwipeCompleted: Boolean
        get() {
            if (status.isSwipeAnimating) {
                if (topPosition < targetPosition) {
                    if (width < abs(dx.toDouble()) || height < abs(dy.toDouble())) {
                        return true
                    }
                }
            }
            return false
        }

    fun canScrollToPosition(position: Int, itemCount: Int): Boolean {
        if (position == topPosition) {
            return false
        }
        if (position < 0) {
            return false
        }
        if (itemCount < position) {
            return false
        }
        if (status.isBusy) {
            return false
        }
        return true
    }
}
