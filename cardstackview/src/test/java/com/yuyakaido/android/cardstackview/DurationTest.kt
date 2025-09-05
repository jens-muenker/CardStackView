package com.yuyakaido.android.cardstackview

import org.junit.Test
import org.junit.Assert.*

class DurationTest {

    @Test
    fun `duration values should be correct`() {
        assertEquals(100, Duration.Fast.duration)
        assertEquals(200, Duration.Normal.duration)
        assertEquals(500, Duration.Slow.duration)
    }

    @Test
    fun `fromVelocity should return correct duration for low velocity`() {
        assertEquals(Duration.Slow, Duration.fromVelocity(500))
        assertEquals(Duration.Slow, Duration.fromVelocity(999))
    }

    @Test
    fun `fromVelocity should return correct duration for medium velocity`() {
        assertEquals(Duration.Normal, Duration.fromVelocity(1000))
        assertEquals(Duration.Normal, Duration.fromVelocity(4999))
    }

    @Test
    fun `fromVelocity should return correct duration for high velocity`() {
        assertEquals(Duration.Fast, Duration.fromVelocity(5000))
        assertEquals(Duration.Fast, Duration.fromVelocity(10000))
    }

    @Test
    fun `enum values should be correct`() {
        val values = Duration.values()
        assertEquals(3, values.size)
        assertTrue(values.contains(Duration.Fast))
        assertTrue(values.contains(Duration.Normal))
        assertTrue(values.contains(Duration.Slow))
    }
}