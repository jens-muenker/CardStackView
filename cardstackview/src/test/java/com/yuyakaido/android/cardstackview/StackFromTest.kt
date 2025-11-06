package com.yuyakaido.android.cardstackview

import org.junit.Test
import org.junit.Assert.*

class StackFromTest {

    @Test
    fun `enum values should be correct`() {
        val values = StackFrom.values()
        assertEquals(9, values.size)
        assertTrue(values.contains(StackFrom.None))
        assertTrue(values.contains(StackFrom.Top))
        assertTrue(values.contains(StackFrom.TopAndLeft))
        assertTrue(values.contains(StackFrom.TopAndRight))
        assertTrue(values.contains(StackFrom.Bottom))
        assertTrue(values.contains(StackFrom.BottomAndLeft))
        assertTrue(values.contains(StackFrom.BottomAndRight))
        assertTrue(values.contains(StackFrom.Left))
        assertTrue(values.contains(StackFrom.Right))
    }
}