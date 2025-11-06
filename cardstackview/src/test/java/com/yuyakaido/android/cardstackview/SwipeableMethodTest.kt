package com.yuyakaido.android.cardstackview

import org.junit.Test
import org.junit.Assert.*

class SwipeableMethodTest {

    @Test
    fun `Manual should allow manual swiping`() {
        assertTrue(SwipeableMethod.Manual.canSwipeManually())
        assertFalse(SwipeableMethod.Manual.canSwipeAutomatically())
        assertTrue(SwipeableMethod.Manual.canSwipe())
    }

    @Test
    fun `Automatic should allow automatic swiping`() {
        assertFalse(SwipeableMethod.Automatic.canSwipeManually())
        assertTrue(SwipeableMethod.Automatic.canSwipeAutomatically())
        assertTrue(SwipeableMethod.Automatic.canSwipe())
    }

    @Test
    fun `AutomaticAndManual should allow both manual and automatic swiping`() {
        assertTrue(SwipeableMethod.AutomaticAndManual.canSwipeManually())
        assertTrue(SwipeableMethod.AutomaticAndManual.canSwipeAutomatically())
        assertTrue(SwipeableMethod.AutomaticAndManual.canSwipe())
    }

    @Test
    fun `Manual should allow only manual swiping`() {
        assertTrue(SwipeableMethod.Manual.canSwipeManually())
        assertFalse(SwipeableMethod.Manual.canSwipeAutomatically())
        assertTrue(SwipeableMethod.Manual.canSwipe())
    }

    @Test
    fun `enum values should be correct`() {
        val values = SwipeableMethod.values()
        assertEquals(3, values.size)
        assertTrue(values.contains(SwipeableMethod.Manual))
        assertTrue(values.contains(SwipeableMethod.Automatic))
        assertTrue(values.contains(SwipeableMethod.AutomaticAndManual))
    }
}