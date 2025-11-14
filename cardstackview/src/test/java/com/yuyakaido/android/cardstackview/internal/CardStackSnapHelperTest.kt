package com.yuyakaido.android.cardstackview.internal

import android.view.View
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CardStackSnapHelperTest {

    private lateinit var snapHelper: CardStackSnapHelper
    private lateinit var layoutManager: CardStackLayoutManager
    private lateinit var targetView: View
    private lateinit var setting: CardStackSetting
    private lateinit var state: CardStackState

    @Before
    fun setUp() {
        snapHelper = CardStackSnapHelper()
        layoutManager = mock(CardStackLayoutManager::class.java)
        targetView = mock(View::class.java)
        setting = CardStackSetting()
        state = CardStackState()

        `when`(layoutManager.cardStackSetting).thenReturn(setting)
        `when`(layoutManager.cardStackState).thenReturn(state)
        `when`(layoutManager.topPosition).thenReturn(1)
        `when`(layoutManager.findViewByPosition(anyInt())).thenReturn(targetView)
    }

    @Test
    fun `calculateDistanceToFinalSnap should trigger manual rewind when configured`() {
        setting.directions = Direction.VERTICAL
        setting.manualRewindDirections = listOf(Direction.Bottom)

        state.topPosition = 1
        state.width = 100
        state.height = 100
        state.dx = 0
        state.dy = 100

        `when`(targetView.translationX).thenReturn(0f)
        `when`(targetView.translationY).thenReturn(100f)
        `when`(targetView.width).thenReturn(100)
        `when`(targetView.height).thenReturn(100)

        snapHelper.calculateDistanceToFinalSnap(layoutManager, targetView)

        verify(layoutManager).rewindFromDrag()
    }
}
