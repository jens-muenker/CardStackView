package com.yuyakaido.android.cardstackview

import org.junit.Assert.assertEquals
import org.junit.Test

class CarouselSettingTest {

    @Test
    fun `default values should be vertical`() {
        val setting = CarouselSetting()
        assertEquals(CarouselOrientation.Vertical, setting.orientation)
        assertEquals(0.15f, setting.scaleMultiplier)
        assertEquals(0.6f, setting.minScale)
        assertEquals(8f, setting.tiltAngle)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `negative scale multiplier should throw`() {
        CarouselSetting(scaleMultiplier = -0.1f)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `minScale outside range should throw`() {
        CarouselSetting(minScale = 1.5f)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `tilt angle outside range should throw`() {
        CarouselSetting(tiltAngle = 120f)
    }
}
