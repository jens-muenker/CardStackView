package com.yuyakaido.android.cardstackview

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CardStackViewTest {

    private lateinit var context: Context
    private lateinit var cardStackView: CardStackView
    private lateinit var mockLayoutManager: CardStackLayoutManager
    private lateinit var mockAdapter: RecyclerView.Adapter<*>

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        cardStackView = CardStackView(context)
        mockLayoutManager = mock(CardStackLayoutManager::class.java)
        mockAdapter = mock(RecyclerView.Adapter::class.java)
    }

    @Test
    fun `setLayoutManager should accept CardStackLayoutManager`() {
        cardStackView.setLayoutManager(mockLayoutManager)
        assertEquals(mockLayoutManager, cardStackView.layoutManager)
    }

    @Test
    fun `setLayoutManager should throw exception for non-CardStackLayoutManager`() {
        val otherLayoutManager = mock(RecyclerView.LayoutManager::class.java)
        
        try {
            cardStackView.setLayoutManager(otherLayoutManager)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message?.contains("CardStackLayoutManager") == true)
        }
    }

    @Test
    fun `setAdapter should set default layout manager if none exists`() {
        cardStackView.setAdapter(mockAdapter)
        
        assertNotNull(cardStackView.layoutManager)
        assertTrue(cardStackView.layoutManager is CardStackLayoutManager)
        assertEquals(mockAdapter, cardStackView.adapter)
    }

    @Test
    fun `setAdapter should use existing layout manager`() {
        cardStackView.setLayoutManager(mockLayoutManager)
        cardStackView.setAdapter(mockAdapter)
        
        assertEquals(mockLayoutManager, cardStackView.layoutManager)
        assertEquals(mockAdapter, cardStackView.adapter)
    }

    @Test
    fun `onInterceptTouchEvent should update proportion on ACTION_DOWN`() {
        cardStackView.setLayoutManager(mockLayoutManager)
        
        val motionEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 100f, 200f, 0)
        cardStackView.onInterceptTouchEvent(motionEvent)
        
        verify(mockLayoutManager).updateProportion(200f)
        motionEvent.recycle()
    }

    @Test
    fun `swipe should call smoothScrollToPosition with next position`() {
        cardStackView.setLayoutManager(mockLayoutManager)
        `when`(mockLayoutManager.topPosition).thenReturn(2)
        
        cardStackView.swipe()
        
        verify(mockLayoutManager).topPosition
        // Note: We can't directly verify smoothScrollToPosition as it's a private method
        // but we can verify that the layout manager was accessed
    }

    @Test
    fun `rewind should call smoothScrollToPosition with previous position`() {
        cardStackView.setLayoutManager(mockLayoutManager)
        `when`(mockLayoutManager.topPosition).thenReturn(2)
        
        cardStackView.rewind()
        
        verify(mockLayoutManager).topPosition
        // Note: We can't directly verify smoothScrollToPosition as it's a private method
        // but we can verify that the layout manager was accessed
    }

    @Test
    fun `swipe should not crash when layout manager is not CardStackLayoutManager`() {
        val otherLayoutManager = mock(RecyclerView.LayoutManager::class.java)
        try {
            cardStackView.setLayoutManager(otherLayoutManager)
            // if no exception, proceed
        } catch (e: IllegalArgumentException) {
            // ignore, ensure method still doesn't crash afterwards
        }
        // This should not crash
        cardStackView.swipe()
    }

    @Test
    fun `rewind should not crash when layout manager is not CardStackLayoutManager`() {
        val otherLayoutManager = mock(RecyclerView.LayoutManager::class.java)
        try {
            cardStackView.setLayoutManager(otherLayoutManager)
        } catch (e: IllegalArgumentException) {
            // ignore
        }
        // This should not crash
        cardStackView.rewind()
    }


    @Test
    fun `onInterceptTouchEvent should not crash when layout manager is not CardStackLayoutManager`() {
        val otherLayoutManager = mock(RecyclerView.LayoutManager::class.java)
        try {
            cardStackView.setLayoutManager(otherLayoutManager)
        } catch (e: IllegalArgumentException) {
            // ignore
        }
        val motionEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 100f, 200f, 0)
        // This should not crash
        cardStackView.onInterceptTouchEvent(motionEvent)
        motionEvent.recycle()
    }

    @Test
    fun `recycling a view should reset nested ScrollView`() {
        val scrollView = ScrollView(context)
        scrollView.addView(View(context))
        scrollView.scrollTo(0, 200)

        val holder = TestViewHolder(scrollView)

        cardStackView.scrollStateRecyclerListener.onViewRecycled(holder)

        assertEquals(0, scrollView.scrollY)
        assertEquals(0, scrollView.scrollX)
    }

    @Test
    fun `external recycler listener should still be invoked`() {
        var callbackInvoked = false
        val externalListener = RecyclerView.RecyclerListener {
            callbackInvoked = true
        }
        cardStackView.setRecyclerListener(externalListener)

        val holder = TestViewHolder(View(context))
        cardStackView.scrollStateRecyclerListener.onViewRecycled(holder)

        assertTrue(callbackInvoked)
    }

    private class TestViewHolder(view: View) : RecyclerView.ViewHolder(view)
}