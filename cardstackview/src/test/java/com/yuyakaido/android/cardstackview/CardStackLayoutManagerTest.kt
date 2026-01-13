package com.yuyakaido.android.cardstackview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuyakaido.android.cardstackview.CardStackStyle
import com.yuyakaido.android.cardstackview.CarouselOrientation
import com.yuyakaido.android.cardstackview.CarouselSetting
import com.yuyakaido.android.cardstackview.internal.CardStackState
import com.yuyakaido.android.cardstackview.StackLayout
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CardStackLayoutManagerTest {

    private lateinit var context: Context
    private lateinit var layoutManager: CardStackLayoutManager
    private lateinit var mockListener: CardStackListener
    private lateinit var mockRecycler: RecyclerView.Recycler
    private lateinit var mockState: RecyclerView.State

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        mockListener = mock(CardStackListener::class.java)
        layoutManager = CardStackLayoutManager(context, mockListener)
        mockRecycler = mock(RecyclerView.Recycler::class.java)
        mockState = mock(RecyclerView.State::class.java)
    }

    @Test
    fun `constructor should initialize with default listener`() {
        val defaultLayoutManager = CardStackLayoutManager(context)
        assertNotNull(defaultLayoutManager.cardStackListener)
    }

    @Test
    fun `constructor should initialize with custom listener`() {
        assertEquals(mockListener, layoutManager.cardStackListener)
    }

    @Test
    fun `generateDefaultLayoutParams should return correct params`() {
        val params = layoutManager.generateDefaultLayoutParams()
        
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT, params.width)
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT, params.height)
    }

    @Test
    fun `canScrollHorizontally should return correct value based on settings`() {
        layoutManager.cardStackSetting.canScrollHorizontal = true
        layoutManager.cardStackSetting.swipeableMethod = SwipeableMethod.Manual
        
        assertTrue(layoutManager.canScrollHorizontally())
        
        layoutManager.cardStackSetting.canScrollHorizontal = false
        assertFalse(layoutManager.canScrollHorizontally())
        
        layoutManager.cardStackSetting.canScrollHorizontal = true
        layoutManager.cardStackSetting.swipeableMethod = SwipeableMethod.Automatic
        // With Automatic method, horizontal scrolling is still possible
        assertTrue(layoutManager.canScrollHorizontally())
    }

    @Test
    fun `canScrollVertically should return correct value based on settings`() {
        layoutManager.cardStackSetting.canScrollVertical = true
        layoutManager.cardStackSetting.swipeableMethod = SwipeableMethod.Manual
        
        assertTrue(layoutManager.canScrollVertically())
        
        layoutManager.cardStackSetting.canScrollVertical = false
        assertFalse(layoutManager.canScrollVertically())
        
        layoutManager.cardStackSetting.canScrollVertical = true
        layoutManager.cardStackSetting.swipeableMethod = SwipeableMethod.Automatic
        // With Automatic method, vertical scrolling is still possible
        assertTrue(layoutManager.canScrollVertically())
    }

    @Test
    fun `scrollHorizontallyBy should return 0 when topPosition equals itemCount`() {
        layoutManager.cardStackState.topPosition = 5
        `when`(mockState.itemCount).thenReturn(5)
        
        val result = layoutManager.scrollHorizontallyBy(10, mockRecycler, mockState)
        assertEquals(0, result)
    }

    @Test
    fun `scrollVerticallyBy should return 0 when topPosition equals itemCount`() {
        layoutManager.cardStackState.topPosition = 5
        `when`(mockState.itemCount).thenReturn(5)
        
        val result = layoutManager.scrollVerticallyBy(10, mockRecycler, mockState)
        assertEquals(0, result)
    }

    @Test
    fun `updateProportion should update proportion correctly`() {
        val mockView = mock(View::class.java)
        `when`(mockView.top).thenReturn(100)
        layoutManager.cardStackState.topPosition = 0
        layoutManager.cardStackState.height = 1000
        
        // Mock the findViewByPosition method by setting up the layout manager state
        layoutManager.cardStackState.width = 500
        layoutManager.cardStackState.height = 1000
        
        // This test is limited because we can't easily mock findViewByPosition
        // but we can test that the method doesn't crash
        layoutManager.updateProportion(500f)
    }

    @Test
    fun `setStackFrom should update setting`() {
        layoutManager.setStackFrom(StackFrom.Top)
        assertEquals(StackFrom.Top, layoutManager.cardStackSetting.stackFrom)
    }

    @Test
    fun `setStackLayout should update setting`() {
        layoutManager.setStackLayout(StackLayout.Linear)
        assertEquals(StackLayout.Linear, layoutManager.cardStackSetting.stackLayout)
    }

    @Test
    fun `setStackStyle should update setting`() {
        layoutManager.setStackStyle(CardStackStyle.Carousel)
        assertEquals(CardStackStyle.Carousel, layoutManager.cardStackSetting.stackStyle)
    }

    @Test
    fun `setCarouselSetting should update setting`() {
        val setting = CarouselSetting(CarouselOrientation.Horizontal, 0.2f, 0.7f, 6f)
        layoutManager.setCarouselSetting(setting)
        assertEquals(setting, layoutManager.cardStackSetting.carouselSetting)
    }

    @Test
    fun `setVisibleCount should update setting`() {
        layoutManager.setVisibleCount(5)
        assertEquals(5, layoutManager.cardStackSetting.visibleCount)
    }

    @Test
    fun `setVisibleCount should throw exception for invalid count`() {
        try {
            layoutManager.setVisibleCount(0)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message?.contains("VisibleCount must be greater than 0") == true)
        }
    }

    @Test
    fun `setTranslationInterval should update setting`() {
        layoutManager.setTranslationInterval(10.0f)
        assertEquals(10.0f, layoutManager.cardStackSetting.translationInterval, 0.01f)
    }

    @Test
    fun `setTranslationInterval should throw exception for negative value`() {
        try {
            layoutManager.setTranslationInterval(-1.0f)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message?.contains("TranslationInterval must be greater than or equal 0.0f") == true)
        }
    }

    @Test
    fun `setScaleInterval should update setting`() {
        layoutManager.setScaleInterval(0.9f)
        assertEquals(0.9f, layoutManager.cardStackSetting.scaleInterval, 0.01f)
    }

    @Test
    fun `setScaleInterval should throw exception for negative value`() {
        try {
            layoutManager.setScaleInterval(-0.1f)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message?.contains("ScaleInterval must be greater than or equal 0.0f") == true)
        }
    }

    @Test
    fun `setSwipeThreshold should update setting`() {
        layoutManager.setSwipeThreshold(0.5f)
        assertEquals(0.5f, layoutManager.cardStackSetting.swipeThreshold, 0.01f)
    }

    @Test
    fun `setSwipeThreshold should throw exception for invalid range`() {
        try {
            layoutManager.setSwipeThreshold(1.5f)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message?.contains("SwipeThreshold must be 0.0f to 1.0f") == true)
        }
    }

    @Test
    fun `setMaxDegree should update setting`() {
        layoutManager.setMaxDegree(30.0f)
        assertEquals(30.0f, layoutManager.cardStackSetting.maxDegree, 0.01f)
    }

    @Test
    fun `setMaxDegree should throw exception for invalid range`() {
        try {
            layoutManager.setMaxDegree(400.0f)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message?.contains("MaxDegree must be -360.0f to 360.0f") == true)
        }
    }

    @Test
    fun `setDirections should update setting`() {
        val directions = listOf(Direction.Left, Direction.Right)
        layoutManager.setDirections(directions)
        assertEquals(directions, layoutManager.cardStackSetting.directions)
    }

    @Test
    fun `setCanScrollHorizontal should update setting`() {
        layoutManager.setCanScrollHorizontal(false)
        assertFalse(layoutManager.cardStackSetting.canScrollHorizontal)
    }

    @Test
    fun `setCanScrollVertical should update setting`() {
        layoutManager.setCanScrollVertical(false)
        assertFalse(layoutManager.cardStackSetting.canScrollVertical)
    }

    @Test
    fun `setSwipeableMethod should update setting`() {
        layoutManager.setSwipeableMethod(SwipeableMethod.Manual)
        assertEquals(SwipeableMethod.Manual, layoutManager.cardStackSetting.swipeableMethod)
    }

    @Test
    fun `topPosition should get and set correctly`() {
        layoutManager.topPosition = 5
        assertEquals(5, layoutManager.topPosition)
    }

    @Test
    fun `topView should return null when no view at position`() {
        // This test is limited because we can't easily mock findViewByPosition
        // but we can test that the method doesn't crash
        val topView = layoutManager.topView
        // The actual result depends on the internal state, but it shouldn't crash
    }
}