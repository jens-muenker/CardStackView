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
        get() = when {
            abs(dy.toDouble()) < abs(dx.toDouble()) -> if (dx < 0) Direction.Left else Direction.Right
            else -> if (dy < 0) Direction.Top else Direction.Bottom
        }

    val ratio: Float
        get() {
            val absDx = abs(dx)
            val absDy = abs(dy)
            val ratio = if (absDx < absDy) {
                val halfHeight = height / 2.0f
                if (halfHeight == 0f) {
                    0f
                } else {
                    absDy / halfHeight
                }
            } else {
                val halfWidth = width / 2.0f
                if (halfWidth == 0f) {
                    0f
                } else {
                    absDx / halfWidth
                }
            }
            return min(ratio, 1.0f)
        }

    val isSwipeCompleted: Boolean
        get() = status.isSwipeAnimating && 
                topPosition < targetPosition && 
                (width < abs(dx) || height < abs(dy))

    fun canScrollToPosition(position: Int, itemCount: Int): Boolean {
        return position != topPosition && 
               position >= 0 && 
               position < itemCount && 
               !status.isBusy
    }
}
