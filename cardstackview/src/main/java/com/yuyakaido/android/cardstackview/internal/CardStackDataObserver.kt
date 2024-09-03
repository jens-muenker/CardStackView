package com.yuyakaido.android.cardstackview.internal

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import kotlin.math.min

class CardStackDataObserver(private val recyclerView: RecyclerView) : AdapterDataObserver() {
    override fun onChanged() {
        val manager = cardStackLayoutManager
        manager.topPosition = 0
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        // Do nothing
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        // Do nothing
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        // Do nothing
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        // TopPosition may need to be adjusted if the element is deleted
        // Specifically, if all elements are deleted, or if elements before
        // TopPosition are deleted, adjustments are necessary
        val manager = cardStackLayoutManager
        val topPosition = manager.topPosition
        if (manager.itemCount == 0) {
            // If all elements are deleted
            manager.topPosition = 0
        } else if (positionStart < topPosition) {
            // If an element before TopPosition is deleted
            val diff = topPosition - positionStart
            manager.topPosition =
                min((topPosition - diff).toDouble(), (manager.itemCount - 1).toDouble())
                    .toInt()
        }
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        val manager = cardStackLayoutManager
        manager.removeAllViews()
    }

    private val cardStackLayoutManager: CardStackLayoutManager
        get() {
            val manager = recyclerView.layoutManager
            if (manager is CardStackLayoutManager) {
                return manager
            }
            throw IllegalStateException("CardStackView must be set CardStackLayoutManager.")
        }
}
