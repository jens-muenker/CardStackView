package com.yuyakaido.android.cardstackview

import org.junit.Test
import org.junit.Assert.*

class DirectionTest {

    @Test
    fun `HORIZONTAL should contain Left and Right`() {
        assertEquals(2, Direction.HORIZONTAL.size)
        assertTrue(Direction.HORIZONTAL.contains(Direction.Left))
        assertTrue(Direction.HORIZONTAL.contains(Direction.Right))
    }

    @Test
    fun `VERTICAL should contain Top and Bottom`() {
        assertEquals(2, Direction.VERTICAL.size)
        assertTrue(Direction.VERTICAL.contains(Direction.Top))
        assertTrue(Direction.VERTICAL.contains(Direction.Bottom))
    }

    @Test
    fun `FREEDOM should contain all directions`() {
        assertEquals(4, Direction.FREEDOM.size)
        assertTrue(Direction.FREEDOM.contains(Direction.Left))
        assertTrue(Direction.FREEDOM.contains(Direction.Right))
        assertTrue(Direction.FREEDOM.contains(Direction.Top))
        assertTrue(Direction.FREEDOM.contains(Direction.Bottom))
    }

    @Test
    fun `enum values should be correct`() {
        val values = Direction.values()
        assertEquals(4, values.size)
        assertTrue(values.contains(Direction.Left))
        assertTrue(values.contains(Direction.Right))
        assertTrue(values.contains(Direction.Top))
        assertTrue(values.contains(Direction.Bottom))
    }
}