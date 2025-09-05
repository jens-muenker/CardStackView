package com.yuyakaido.android.cardstackview.internal

import androidx.recyclerview.widget.RecyclerView
import com.yuyakaido.android.cardstackview.Direction
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class CardStackStateTest {

    private lateinit var cardStackState: CardStackState

    @Before
    fun setUp() {
        cardStackState = CardStackState()
        cardStackState.width = 1000
        cardStackState.height = 2000
    }

    @Test
    fun `initial state should be Idle`() {
        assertEquals(CardStackState.Status.Idle, cardStackState.status)
    }

    @Test
    fun `next should update status`() {
        cardStackState.next(CardStackState.Status.Dragging)
        assertEquals(CardStackState.Status.Dragging, cardStackState.status)
    }

    @Test
    fun `direction should return Left when dx is negative and larger than dy`() {
        cardStackState.dx = -100
        cardStackState.dy = 50
        assertEquals(Direction.Left, cardStackState.direction)
    }

    @Test
    fun `direction should return Right when dx is positive and larger than dy`() {
        cardStackState.dx = 100
        cardStackState.dy = 50
        assertEquals(Direction.Right, cardStackState.direction)
    }

    @Test
    fun `direction should return Top when dy is negative and larger than dx`() {
        cardStackState.dx = 50
        cardStackState.dy = -100
        assertEquals(Direction.Top, cardStackState.direction)
    }

    @Test
    fun `direction should return Bottom when dy is positive and larger than dx`() {
        cardStackState.dx = 50
        cardStackState.dy = 100
        assertEquals(Direction.Bottom, cardStackState.direction)
    }

    @Test
    fun `ratio should be calculated correctly for horizontal movement`() {
        cardStackState.dx = 500 // Half of width
        cardStackState.dy = 0
        assertEquals(1.0f, cardStackState.ratio, 0.01f)
    }

    @Test
    fun `ratio should be calculated correctly for vertical movement`() {
        cardStackState.dx = 0
        cardStackState.dy = 1000 // Half of height
        assertEquals(1.0f, cardStackState.ratio, 0.01f)
    }

    @Test
    fun `ratio should be capped at 1.0`() {
        cardStackState.dx = 2000 // More than width
        cardStackState.dy = 0
        assertEquals(1.0f, cardStackState.ratio, 0.01f)
    }

    @Test
    fun `isSwipeCompleted should return true when conditions are met`() {
        cardStackState.status = CardStackState.Status.ManualSwipeAnimating
        cardStackState.topPosition = 0
        cardStackState.targetPosition = 1
        cardStackState.dx = 1500 // More than width
        assertTrue(cardStackState.isSwipeCompleted)
    }

    @Test
    fun `isSwipeCompleted should return false when not animating`() {
        cardStackState.status = CardStackState.Status.Idle
        cardStackState.topPosition = 0
        cardStackState.targetPosition = 1
        cardStackState.dx = 1500
        assertFalse(cardStackState.isSwipeCompleted)
    }

    @Test
    fun `canScrollToPosition should return true for valid position`() {
        cardStackState.status = CardStackState.Status.Idle
        cardStackState.topPosition = 0
        assertTrue(cardStackState.canScrollToPosition(1, 5))
    }

    @Test
    fun `canScrollToPosition should return false for same position`() {
        cardStackState.status = CardStackState.Status.Idle
        cardStackState.topPosition = 1
        assertFalse(cardStackState.canScrollToPosition(1, 5))
    }

    @Test
    fun `canScrollToPosition should return false for negative position`() {
        cardStackState.status = CardStackState.Status.Idle
        cardStackState.topPosition = 0
        assertFalse(cardStackState.canScrollToPosition(-1, 5))
    }

    @Test
    fun `canScrollToPosition should return false for position beyond item count`() {
        cardStackState.status = CardStackState.Status.Idle
        cardStackState.topPosition = 0
        assertFalse(cardStackState.canScrollToPosition(6, 5))
    }

    @Test
    fun `canScrollToPosition should return false when busy`() {
        cardStackState.status = CardStackState.Status.Dragging
        cardStackState.topPosition = 0
        assertFalse(cardStackState.canScrollToPosition(1, 5))
    }

    @Test
    fun `Status isBusy should return correct values`() {
        assertFalse(CardStackState.Status.Idle.isBusy)
        assertTrue(CardStackState.Status.Dragging.isBusy)
        assertTrue(CardStackState.Status.RewindAnimating.isBusy)
        assertTrue(CardStackState.Status.AutomaticSwipeAnimating.isBusy)
        assertTrue(CardStackState.Status.AutomaticSwipeAnimated.isBusy)
        assertTrue(CardStackState.Status.ManualSwipeAnimating.isBusy)
        assertTrue(CardStackState.Status.ManualSwipeAnimated.isBusy)
    }

    @Test
    fun `Status isDragging should return correct values`() {
        assertFalse(CardStackState.Status.Idle.isDragging)
        assertTrue(CardStackState.Status.Dragging.isDragging)
        assertFalse(CardStackState.Status.RewindAnimating.isDragging)
    }

    @Test
    fun `Status isSwipeAnimating should return correct values`() {
        assertFalse(CardStackState.Status.Idle.isSwipeAnimating)
        assertFalse(CardStackState.Status.Dragging.isSwipeAnimating)
        assertTrue(CardStackState.Status.ManualSwipeAnimating.isSwipeAnimating)
        assertTrue(CardStackState.Status.AutomaticSwipeAnimating.isSwipeAnimating)
    }

    @Test
    fun `Status toAnimatedStatus should return correct values`() {
        assertEquals(CardStackState.Status.ManualSwipeAnimated, CardStackState.Status.ManualSwipeAnimating.toAnimatedStatus())
        assertEquals(CardStackState.Status.AutomaticSwipeAnimated, CardStackState.Status.AutomaticSwipeAnimating.toAnimatedStatus())
        assertEquals(CardStackState.Status.Idle, CardStackState.Status.Idle.toAnimatedStatus())
    }
}