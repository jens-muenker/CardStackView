package com.yuyakaido.android.cardstackview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.widget.NestedScrollView
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
    private var externalRecyclerListener: RecyclerListener? = null
    internal val scrollStateRecyclerListener = RecyclerListener { holder ->
        resetScrollState(holder.itemView)
        externalRecyclerListener?.onViewRecycled(holder)
    }

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
        if (getAdapter() != null) {
            getAdapter()!!.unregisterAdapterDataObserver(observer)
            getAdapter()!!.onDetachedFromRecyclerView(this)
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

    override fun setRecyclerListener(listener: RecyclerListener?) {
        externalRecyclerListener = listener
    }

    fun swipe() {
        if (layoutManager is CardStackLayoutManager) {
            val manager = layoutManager as CardStackLayoutManager?
            smoothScrollToPosition(manager!!.topPosition + 1)
        }
    }

    fun rewind() {
        if (layoutManager is CardStackLayoutManager) {
            val manager = layoutManager as CardStackLayoutManager?
            smoothScrollToPosition(manager!!.topPosition - 1)
        }
    }

    private fun initialize() {
        CardStackSnapHelper().attachToRecyclerView(this)
        overScrollMode = OVER_SCROLL_NEVER
        super.setRecyclerListener(scrollStateRecyclerListener)
    }

    private fun resetScrollState(view: View) {
        when (view) {
            is ScrollView -> view.scrollTo(0, 0)
            is NestedScrollView -> view.scrollTo(0, 0)
            is HorizontalScrollView -> view.scrollTo(0, 0)
        }
        if (view is ViewGroup) {
            for (index in 0 until view.childCount) {
                resetScrollState(view.getChildAt(index))
            }
        }
    }
}
