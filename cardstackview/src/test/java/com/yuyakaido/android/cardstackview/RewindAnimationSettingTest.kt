package com.yuyakaido.android.cardstackview

import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import org.junit.Test
import org.junit.Assert.*

class RewindAnimationSettingTest {

    @Test
    fun `default builder should create correct settings`() {
        val setting = RewindAnimationSetting.Builder().build()
        
        assertEquals(Direction.Bottom, setting.direction)
        assertEquals(Duration.Normal.duration, setting.duration)
        assertTrue(setting.interpolator is DecelerateInterpolator)
    }

    @Test
    fun `builder should allow setting custom direction`() {
        val setting = RewindAnimationSetting.Builder()
            .setDirection(Direction.Top)
            .build()
        
        assertEquals(Direction.Top, setting.direction)
    }

    @Test
    fun `builder should allow setting custom duration`() {
        val setting = RewindAnimationSetting.Builder()
            .setDuration(500)
            .build()
        
        assertEquals(500, setting.duration)
    }

    @Test
    fun `builder should allow setting custom interpolator`() {
        val customInterpolator = LinearInterpolator()
        val setting = RewindAnimationSetting.Builder()
            .setInterpolator(customInterpolator)
            .build()
        
        assertEquals(customInterpolator, setting.interpolator)
    }

    @Test
    fun `builder should allow chaining`() {
        val customInterpolator = LinearInterpolator()
        val setting = RewindAnimationSetting.Builder()
            .setDirection(Direction.Left)
            .setDuration(300)
            .setInterpolator(customInterpolator)
            .build()
        
        assertEquals(Direction.Left, setting.direction)
        assertEquals(300, setting.duration)
        assertEquals(customInterpolator, setting.interpolator)
    }

    @Test
    fun `getDirection should return correct direction`() {
        val setting = RewindAnimationSetting.Builder()
            .setDirection(Direction.Right)
            .build()
        
        assertEquals(Direction.Right, setting.direction)
    }

    @Test
    fun `getDuration should return correct duration`() {
        val setting = RewindAnimationSetting.Builder()
            .setDuration(1000)
            .build()
        
        assertEquals(1000, setting.duration)
    }

    @Test
    fun `getInterpolator should return correct interpolator`() {
        val customInterpolator = LinearInterpolator()
        val setting = RewindAnimationSetting.Builder()
            .setInterpolator(customInterpolator)
            .build()
        
        assertEquals(customInterpolator, setting.interpolator)
    }
}