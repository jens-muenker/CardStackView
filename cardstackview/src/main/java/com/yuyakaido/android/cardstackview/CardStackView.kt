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

/**
 * Eine spezialisierte RecyclerView für die Darstellung von Karten in einem Stapel.
 *
 * CardStackView bietet erweiterte Funktionen wie:
 * - Karten-Swipe-Gesten
 * - Automatisches Zurücksetzen von Scroll-Positionen bei Recycling
 * - Stack-basierte Animationen
 *
 * @property context Der Android-Context
 * @property attrs Optionale XML-Attribute
 * @property defStyle Optionaler Standard-Style
 */
class CardStackView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(
    context!!, attrs, defStyle
) {
    private val observer = CardStackDataObserver(this)
    private var externalRecyclerListener: RecyclerListener? = null
    
    /**
     * Interner RecyclerListener, der den Scroll-Zustand von recycelten Views zurücksetzt.
     *
     * Dieser Listener wird automatisch aufgerufen, wenn eine Karte recycelt wird, und
     * stellt sicher, dass alle verschachtelten scrollbaren Views (ScrollView,
     * NestedScrollView, HorizontalScrollView) auf ihre Ausgangsposition zurückgesetzt werden.
     * Dies verhindert, dass Scroll-Positionen zwischen verschiedenen Karten "durchbluten".
     *
     * Nach dem Zurücksetzen wird auch der externe RecyclerListener (falls vorhanden) aufgerufen.
     */
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

    /**
     * Setzt einen externen RecyclerListener.
     *
     * Der externe Listener wird nach dem internen Scroll-Reset-Mechanismus aufgerufen.
     * Dies ermöglicht es, eigene Recycling-Logik hinzuzufügen, während die automatische
     * Scroll-Zurücksetzung weiterhin funktioniert.
     *
     * @param listener Der externe RecyclerListener oder null
     */
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

    /**
     * Setzt den Scroll-Zustand einer View und aller verschachtelten Views rekursiv zurück.
     *
     * Diese Methode durchläuft die View-Hierarchie und setzt die Scroll-Position von allen
     * scrollbaren Views auf (0, 0) zurück. Unterstützte View-Typen sind:
     * - [ScrollView]: Vertikale Scroll-Views
     * - [NestedScrollView]: Verschachtelte vertikale Scroll-Views
     * - [HorizontalScrollView]: Horizontale Scroll-Views
     *
     * Die rekursive Verarbeitung stellt sicher, dass auch tief verschachtelte scrollbare
     * Views zurückgesetzt werden.
     *
     * **Zweck**: Verhindert, dass Scroll-Positionen beim View-Recycling zwischen verschiedenen
     * Karten übertragen werden (Scroll-State-Bleed). Ohne diese Funktion könnte eine Karte
     * mit einer bereits gescrollten Position erscheinen, wenn die View recycelt wurde.
     *
     * @param view Die View, deren Scroll-Zustand zurückgesetzt werden soll
     */
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
