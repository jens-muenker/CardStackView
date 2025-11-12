package com.yuyakaido.android.cardstackview.internal

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*

class DisplayUtilTest {

    @Test
    fun `dpToPx should convert dp to pixels correctly`() {
        val mockContext = mock(Context::class.java)
        val mockResources = mock(Resources::class.java)
        val mockDisplayMetrics = mock(DisplayMetrics::class.java)
        
        mockDisplayMetrics.density = 2.0f
        `when`(mockContext.resources).thenReturn(mockResources)
        `when`(mockResources.displayMetrics).thenReturn(mockDisplayMetrics)
        
        val result = DisplayUtil.dpToPx(mockContext, 10.0f)
        assertEquals(20, result)
    }

    @Test
    fun `dpToPx should handle fractional density correctly`() {
        val mockContext = mock(Context::class.java)
        val mockResources = mock(Resources::class.java)
        val mockDisplayMetrics = mock(DisplayMetrics::class.java)
        
        mockDisplayMetrics.density = 1.5f
        `when`(mockContext.resources).thenReturn(mockResources)
        `when`(mockResources.displayMetrics).thenReturn(mockDisplayMetrics)
        
        val result = DisplayUtil.dpToPx(mockContext, 8.0f)
        assertEquals(12, result) // 8 * 1.5 = 12.0, rounded to 12
    }

    @Test
    fun `dpToPx should handle zero dp`() {
        val mockContext = mock(Context::class.java)
        val mockResources = mock(Resources::class.java)
        val mockDisplayMetrics = mock(DisplayMetrics::class.java)
        
        mockDisplayMetrics.density = 2.0f
        `when`(mockContext.resources).thenReturn(mockResources)
        `when`(mockResources.displayMetrics).thenReturn(mockDisplayMetrics)
        
        val result = DisplayUtil.dpToPx(mockContext, 0.0f)
        assertEquals(0, result)
    }

    @Test
    fun `dpToPx should handle negative dp`() {
        val mockContext = mock(Context::class.java)
        val mockResources = mock(Resources::class.java)
        val mockDisplayMetrics = mock(DisplayMetrics::class.java)
        
        mockDisplayMetrics.density = 2.0f
        `when`(mockContext.resources).thenReturn(mockResources)
        `when`(mockResources.displayMetrics).thenReturn(mockDisplayMetrics)
        
        val result = DisplayUtil.dpToPx(mockContext, -5.0f)
        assertEquals(-10, result)
    }
}