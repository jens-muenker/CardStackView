package com.yuyakaido.android.cardstackview

import android.view.View
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*

class CardStackListenerTest {

    @Test
    fun `DEFAULT listener should not throw exceptions`() {
        val defaultListener = CardStackListener.DEFAULT
        val mockView = mock(View::class.java)
        
        // These should not throw any exceptions
        defaultListener.onCardDragging(Direction.Left, 0.5f)
        defaultListener.onCardSwiped(Direction.Right)
        defaultListener.onCardRewound()
        defaultListener.onCardCanceled()
        defaultListener.onCardAppeared(mockView, 0)
        defaultListener.onCardDisappeared(mockView, 0)
    }

    @Test
    fun `custom listener should be called with correct parameters`() {
        val mockListener = mock(CardStackListener::class.java)
        val mockView = mock(View::class.java)
        
        mockListener.onCardDragging(Direction.Top, 0.3f)
        mockListener.onCardSwiped(Direction.Bottom)
        mockListener.onCardRewound()
        mockListener.onCardCanceled()
        mockListener.onCardAppeared(mockView, 1)
        mockListener.onCardDisappeared(mockView, 1)
        
        verify(mockListener).onCardDragging(Direction.Top, 0.3f)
        verify(mockListener).onCardSwiped(Direction.Bottom)
        verify(mockListener).onCardRewound()
        verify(mockListener).onCardCanceled()
        verify(mockListener).onCardAppeared(mockView, 1)
        verify(mockListener).onCardDisappeared(mockView, 1)
    }
}