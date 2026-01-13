package com.yuyakaido.android.cardstackview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.yuyakaido.android.cardstackview.internal.CardStackDataObserver
import com.yuyakaido.android.cardstackview.internal.CardStackSnapHelper

class CardStackView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(
    context!!, attrs, defStyle
) {
    private val observer = CardStackDataObserver(this)

    init {
        initialize()
    }

    override fun setLayoutManager(manager: LayoutManager?) {
        if (manager is CardStackLayoutManager) {
            super.setLayoutManager(manager)
        } else {
            throw IllegalArgumentException("CardStackView must be set CardStackLayoutManager.")
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (layoutManager == null) {
            layoutManager = CardStackLayoutManager(context)
        }
        getAdapter()?.let { currentAdapter ->
            currentAdapter.unregisterAdapterDataObserver(observer)
            currentAdapter.onDetachedFromRecyclerView(this)
        }
        adapter?.registerAdapterDataObserver(observer)
        super.setAdapter(adapter)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val manager = layoutManager as? CardStackLayoutManager
            manager?.updateProportion(event.y)
        }
        return super.onInterceptTouchEvent(event)
    }

    fun swipe() {
        val manager = layoutManager as? CardStackLayoutManager
        manager?.let {
            smoothScrollToPosition(it.topPosition + 1)
        }
    }

    fun rewind() {
        val manager = layoutManager as? CardStackLayoutManager
        manager?.let {
            smoothScrollToPosition(it.topPosition - 1)
        }
    }

    private fun initialize() {
        CardStackSnapHelper().attachToRecyclerView(this)
        overScrollMode = OVER_SCROLL_NEVER
    }
}
