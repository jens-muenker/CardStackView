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
    fun `ManualAndAutomatic should allow both manual and automatic swiping`() {
        assertTrue(SwipeableMethod.ManualAndAutomatic.canSwipeManually())
        assertTrue(SwipeableMethod.ManualAndAutomatic.canSwipeAutomatically())
        assertTrue(SwipeableMethod.ManualAndAutomatic.canSwipe())
    }

    @Test
    fun `None should not allow any swiping`() {
        assertFalse(SwipeableMethod.None.canSwipeManually())
        assertFalse(SwipeableMethod.None.canSwipeAutomatically())
        assertFalse(SwipeableMethod.None.canSwipe())
    }

    @Test
    fun `enum values should be correct`() {
        val values = SwipeableMethod.values()
        assertEquals(4, values.size)
        assertTrue(values.contains(SwipeableMethod.Manual))
        assertTrue(values.contains(SwipeableMethod.Automatic))
        assertTrue(values.contains(SwipeableMethod.ManualAndAutomatic))
        assertTrue(values.contains(SwipeableMethod.None))
    }
}