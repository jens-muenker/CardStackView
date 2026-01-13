package com.yuyakaido.android.cardstackview.internal

import android.view.animation.LinearInterpolator
import com.yuyakaido.android.cardstackview.CardStackStyle
import com.yuyakaido.android.cardstackview.CarouselOrientation
import com.yuyakaido.android.cardstackview.CarouselSetting
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.StackLayout
import com.yuyakaido.android.cardstackview.SwipeableMethod
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class CardStackSettingTest {

    private lateinit var cardStackSetting: CardStackSetting

    @Before
    fun setUp() {
        cardStackSetting = CardStackSetting()
    }

    @Test
    fun `default values should be correct`() {
        assertEquals(StackFrom.None, cardStackSetting.stackFrom)
        assertEquals(CardStackStyle.Stack, cardStackSetting.stackStyle)
        assertEquals(CarouselSetting(), cardStackSetting.carouselSetting)
        assertEquals(3, cardStackSetting.visibleCount)
        assertEquals(8.0f, cardStackSetting.translationInterval, 0.01f)
        assertEquals(StackLayout.Overlay, cardStackSetting.stackLayout)
        assertEquals(0.95f, cardStackSetting.scaleInterval, 0.01f)
        assertEquals(0.3f, cardStackSetting.swipeThreshold, 0.01f)
        assertEquals(20.0f, cardStackSetting.maxDegree, 0.01f)
        assertEquals(Direction.HORIZONTAL, cardStackSetting.directions)
        assertTrue(cardStackSetting.canScrollHorizontal)
        assertTrue(cardStackSetting.canScrollVertical)
        assertEquals(SwipeableMethod.AutomaticAndManual, cardStackSetting.swipeableMethod)
        assertNotNull(cardStackSetting.swipeAnimationSetting)
        assertNotNull(cardStackSetting.rewindAnimationSetting)
        assertTrue(cardStackSetting.overlayInterpolator is LinearInterpolator)
    }

    @Test
    fun `stackFrom should be settable`() {
        cardStackSetting.stackFrom = StackFrom.Top
        assertEquals(StackFrom.Top, cardStackSetting.stackFrom)
    }

    @Test
    fun `stackLayout should be settable`() {
        cardStackSetting.stackLayout = StackLayout.Linear
        assertEquals(StackLayout.Linear, cardStackSetting.stackLayout)
    }

    @Test
    fun `stackStyle should be settable`() {
        cardStackSetting.stackStyle = CardStackStyle.Carousel
        assertEquals(CardStackStyle.Carousel, cardStackSetting.stackStyle)
    }

    @Test
    fun `carouselSetting should be settable`() {
        val setting = CarouselSetting(CarouselOrientation.Horizontal, 0.2f, 0.7f, 6f)
        cardStackSetting.carouselSetting = setting
        assertEquals(setting, cardStackSetting.carouselSetting)
    }

    @Test
    fun `visibleCount should be settable`() {
        cardStackSetting.visibleCount = 5
        assertEquals(5, cardStackSetting.visibleCount)
    }

    @Test
    fun `translationInterval should be settable`() {
        cardStackSetting.translationInterval = 10.0f
        assertEquals(10.0f, cardStackSetting.translationInterval, 0.01f)
    }

    @Test
    fun `scaleInterval should be settable`() {
        cardStackSetting.scaleInterval = 0.9f
        assertEquals(0.9f, cardStackSetting.scaleInterval, 0.01f)
    }

    @Test
    fun `swipeThreshold should be settable`() {
        cardStackSetting.swipeThreshold = 0.5f
        assertEquals(0.5f, cardStackSetting.swipeThreshold, 0.01f)
    }

    @Test
    fun `maxDegree should be settable`() {
        cardStackSetting.maxDegree = 30.0f
        assertEquals(30.0f, cardStackSetting.maxDegree, 0.01f)
    }

    @Test
    fun `directions should be settable`() {
        val customDirections = listOf(Direction.Left, Direction.Right)
        cardStackSetting.directions = customDirections
        assertEquals(customDirections, cardStackSetting.directions)
    }

    @Test
    fun `canScrollHorizontal should be settable`() {
        cardStackSetting.canScrollHorizontal = false
        assertFalse(cardStackSetting.canScrollHorizontal)
    }

    @Test
    fun `canScrollVertical should be settable`() {
        cardStackSetting.canScrollVertical = false
        assertFalse(cardStackSetting.canScrollVertical)
    }

    @Test
    fun `swipeableMethod should be settable`() {
        cardStackSetting.swipeableMethod = SwipeableMethod.Manual
        assertEquals(SwipeableMethod.Manual, cardStackSetting.swipeableMethod)
    }

    @Test
    fun `overlayInterpolator should be settable`() {
        val customInterpolator = LinearInterpolator()
        cardStackSetting.overlayInterpolator = customInterpolator
        assertEquals(customInterpolator, cardStackSetting.overlayInterpolator)
    }
}