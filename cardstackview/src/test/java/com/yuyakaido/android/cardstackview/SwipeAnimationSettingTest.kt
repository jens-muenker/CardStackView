package com.yuyakaido.android.cardstackview

import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import org.junit.Test
import org.junit.Assert.*

class SwipeAnimationSettingTest {

    @Test
    fun `default builder should create correct settings`() {
        val setting = SwipeAnimationSetting.Builder().build()
        
        assertEquals(Direction.Right, setting.getDirection())
        assertEquals(Duration.Normal.duration, setting.getDuration())
        assertTrue(setting.getInterpolator() is AccelerateInterpolator)
    }

    @Test
    fun `builder should allow setting custom direction`() {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Left)
            .build()
        
        assertEquals(Direction.Left, setting.getDirection())
    }

    @Test
    fun `builder should allow setting custom duration`() {
        val setting = SwipeAnimationSetting.Builder()
            .setDuration(500)
            .build()
        
        assertEquals(500, setting.getDuration())
    }

    @Test
    fun `builder should allow setting custom interpolator`() {
        val customInterpolator = LinearInterpolator()
        val setting = SwipeAnimationSetting.Builder()
            .setInterpolator(customInterpolator)
            .build()
        
        assertEquals(customInterpolator, setting.getInterpolator())
    }

    @Test
    fun `builder should allow chaining`() {
        val customInterpolator = LinearInterpolator()
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Top)
            .setDuration(300)
            .setInterpolator(customInterpolator)
            .build()
        
        assertEquals(Direction.Top, setting.getDirection())
        assertEquals(300, setting.getDuration())
        assertEquals(customInterpolator, setting.getInterpolator())
    }

    @Test
    fun `getDirection should return correct direction`() {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Bottom)
            .build()
        
        assertEquals(Direction.Bottom, setting.getDirection())
    }

    @Test
    fun `getDuration should return correct duration`() {
        val setting = SwipeAnimationSetting.Builder()
            .setDuration(1000)
            .build()
        
        assertEquals(1000, setting.getDuration())
    }

    @Test
    fun `getInterpolator should return correct interpolator`() {
        val customInterpolator = LinearInterpolator()
        val setting = SwipeAnimationSetting.Builder()
            .setInterpolator(customInterpolator)
            .build()
        
        assertEquals(customInterpolator, setting.getInterpolator())
    }
}