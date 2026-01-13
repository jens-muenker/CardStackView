package com.yuyakaido.android.cardstackview

import android.content.Context
import android.graphics.PointF
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
import com.yuyakaido.android.cardstackview.CardStackStyle
import com.yuyakaido.android.cardstackview.CarouselOrientation
import com.yuyakaido.android.cardstackview.CarouselSetting
import com.yuyakaido.android.cardstackview.internal.CardStackSetting
import com.yuyakaido.android.cardstackview.internal.CardStackSmoothScroller
import com.yuyakaido.android.cardstackview.internal.CardStackState
import com.yuyakaido.android.cardstackview.internal.DisplayUtil

class CardStackLayoutManager @JvmOverloads constructor(
    private val context: Context,
    listener: CardStackListener = CardStackListener.DEFAULT
) : RecyclerView.LayoutManager(), ScrollVectorProvider {
    var cardStackListener: CardStackListener = CardStackListener.DEFAULT
    val cardStackSetting: CardStackSetting = CardStackSetting()
    val cardStackState: CardStackState = CardStackState()

    init {
        this.cardStackListener = listener
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onLayoutChildren(recycler: Recycler, s: RecyclerView.State) {
        update(recycler)
        if (s.didStructureChange()) {
            val topView = topView
            if (topView != null) {
                cardStackListener.onCardAppeared(this.topView, cardStackState.topPosition)
            }
        }
    }

    override fun canScrollHorizontally(): Boolean {
        return cardStackSetting.swipeableMethod.canSwipe() && cardStackSetting.canScrollHorizontal
    }

    override fun canScrollVertically(): Boolean {
        return cardStackSetting.swipeableMethod.canSwipe() && cardStackSetting.canScrollVertical
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, s: RecyclerView.State): Int {
        // Use provided RecyclerView.State for accurate item count in tests/not-attached scenarios
        if (cardStackState.topPosition == s.itemCount) {
            return 0
        }

        when (cardStackState.status) {
            CardStackState.Status.Idle -> if (cardStackSetting.swipeableMethod.canSwipeManually()) {
                if (!isManualHorizontalScrollAllowed(dx)) {
                    return 0
                }
                cardStackState.dx -= dx
                update(recycler)
                return dx
            }

            CardStackState.Status.Dragging -> if (cardStackSetting.swipeableMethod.canSwipeManually()) {
                if (!isManualHorizontalScrollAllowed(dx)) {
                    return 0
                }
                cardStackState.dx -= dx
                update(recycler)
                return dx
            }

            CardStackState.Status.RewindAnimating -> {
                cardStackState.dx -= dx
                update(recycler)
                return dx
            }

            CardStackState.Status.AutomaticSwipeAnimating -> if (cardStackSetting.swipeableMethod.canSwipeAutomatically()) {
                cardStackState.dx -= dx
                update(recycler)
                return dx
            }

            CardStackState.Status.AutomaticSwipeAnimated -> {}
            CardStackState.Status.ManualSwipeAnimating -> if (cardStackSetting.swipeableMethod.canSwipeManually()) {
                if (!isManualHorizontalScrollAllowed(dx)) {
                    return 0
                }
                cardStackState.dx -= dx
                update(recycler)
                return dx
            }

            CardStackState.Status.ManualSwipeAnimated -> {}

        }
        return 0
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler, s: RecyclerView.State): Int {
        // Use provided RecyclerView.State for accurate item count in tests/not-attached scenarios
        if (cardStackState.topPosition == s.itemCount) {
            return 0
        }

        when (cardStackState.status) {
            CardStackState.Status.Idle -> if (cardStackSetting.swipeableMethod.canSwipeManually()) {
                if (!isManualVerticalScrollAllowed(dy)) {
                    return 0
                }
                cardStackState.dy -= dy
                update(recycler)
                return dy
            }

            CardStackState.Status.Dragging -> if (cardStackSetting.swipeableMethod.canSwipeManually()) {
                if (!isManualVerticalScrollAllowed(dy)) {
                    return 0
                }
                cardStackState.dy -= dy
                update(recycler)
                return dy
            }

            CardStackState.Status.RewindAnimating -> {
                cardStackState.dy -= dy
                update(recycler)
                return dy
            }

            CardStackState.Status.AutomaticSwipeAnimating -> if (cardStackSetting.swipeableMethod.canSwipeAutomatically()) {
                cardStackState.dy -= dy
                update(recycler)
                return dy
            }

            CardStackState.Status.AutomaticSwipeAnimated -> {}
            CardStackState.Status.ManualSwipeAnimating -> if (cardStackSetting.swipeableMethod.canSwipeManually()) {
                if (!isManualVerticalScrollAllowed(dy)) {
                    return 0
                }
                cardStackState.dy -= dy
                update(recycler)
                return dy
            }

            CardStackState.Status.ManualSwipeAnimated -> {}

        }
        return 0
    }

    override fun onScrollStateChanged(s: Int) {
        when (s) {
            RecyclerView.SCROLL_STATE_IDLE -> if (cardStackState.targetPosition == RecyclerView.NO_POSITION) {
                // Processing when Swipe is completed
                cardStackState.next(CardStackState.Status.Idle)
                cardStackState.targetPosition = RecyclerView.NO_POSITION
            } else if (cardStackState.topPosition == cardStackState.targetPosition) {
                // Processing when Rewind is completed
                cardStackState.next(CardStackState.Status.Idle)
                cardStackState.targetPosition = RecyclerView.NO_POSITION
            } else {
                // Handling of two or more cards swiped at the same time
                if (cardStackState.topPosition < cardStackState.targetPosition) {
                    // When the first card is swiped, SCROLL_STATE_IDLE is played once.
                    // The next animation is run at that timing to make it look like a series of swipes.
                    smoothScrollToNext(cardStackState.targetPosition)
                } else {
                    // As in the case of Next, run the next animation at the timing when the processing of the first piece is completed.
                    smoothScrollToPrevious(cardStackState.targetPosition)
                }
            }

            RecyclerView.SCROLL_STATE_DRAGGING -> if (cardStackSetting.swipeableMethod.canSwipeManually()) {
                cardStackState.next(CardStackState.Status.Dragging)
            }

            RecyclerView.SCROLL_STATE_SETTLING -> {}
        }
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
        return null
    }

    override fun scrollToPosition(position: Int) {
        if (cardStackSetting.swipeableMethod.canSwipeAutomatically()) {
            if (cardStackState.canScrollToPosition(position, itemCount)) {
                cardStackState.topPosition = position
                requestLayout()
            }
        }
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        s: RecyclerView.State,
        position: Int
    ) {
        if (cardStackSetting.swipeableMethod.canSwipeAutomatically()) {
            if (cardStackState.canScrollToPosition(position, itemCount)) {
                smoothScrollToPosition(position)
            }
        }
    }

    fun updateProportion(y: Float) {
        if (topPosition < itemCount) {
            val view = findViewByPosition(topPosition)
            if (view != null) {
                val half = height / 2.0f
                if (half == 0f) {
                    cardStackState.proportion = 0.0f
                } else {
                    cardStackState.proportion = -(y - half - view.top) / half
                }
            }
        }
    }

    private fun update(recycler: Recycler) {
        cardStackState.width = width
        cardStackState.height = height

        if (cardStackState.isSwipeCompleted) {
            // Overview
            // When a swipe is completed, the swiped View must be deleted from the cache,
            // or the swiped card will be displayed at the next refresh If the swiped card
            // is displayed, the data source is correct, only the view is out of date.
            //
            // Reproduction Procedure
            // 1. `removeAndRecycleView(getTopView(), recycler);` Comment out
            // 2. VisibleCount=1 and set paging to occur when the last card is swiped
            // 3. Display only one card on the screen (let this card be A)
            // 4. Swipe A
            // 5. Display only one card on the screen (let this card be B)
            // 6. After paging is complete, B should be displayed, but A appears on the screen
            topView?.let { removeAndRecycleView(it, recycler) }

            val direction = cardStackState.direction

            cardStackState.next(cardStackState.status.toAnimatedStatus())
            cardStackState.topPosition++
            cardStackState.dx = 0
            cardStackState.dy = 0
            if (cardStackState.topPosition == cardStackState.targetPosition) {
                cardStackState.targetPosition = RecyclerView.NO_POSITION
            }

            /* Event notification via Handler is done to avoid the following error:
             *
             * 2019-03-31 18:44:29.744 8496-8496/com.yuyakaido.android.cardstackview.sample E/AndroidRuntime: FATAL EXCEPTION: main
             *     Process: com.yuyakaido.android.cardstackview.sample, PID: 8496
             *     java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling com.yuyakaido.android.cardstackview.CardStackView{9d8ff78 VFED..... .F....ID 0,0-1080,1353 #7f080027 app:id/card_stack_view}, adapter:com.yuyakaido.android.cardstackview.sample.CardStackAdapter@e0b8651, layout:com.yuyakaido.android.cardstackview.CardStackLayoutManager@17b0eb6, context:com.yuyakaido.android.cardstackview.sample.MainActivity@fe550ca
             *         at android.support.v7.widget.RecyclerView.assertNotInLayoutOrScroll(RecyclerView.java:2880)
             *         at android.support.v7.widget.RecyclerView$RecyclerViewDataObserver.onItemRangeInserted(RecyclerView.java:5300)
             *         at android.support.v7.widget.RecyclerView$AdapterDataObservable.notifyItemRangeInserted(RecyclerView.java:12022)
             *         at android.support.v7.widget.RecyclerView$Adapter.notifyItemRangeInserted(RecyclerView.java:7214)
             *         at android.support.v7.util.AdapterListUpdateCallback.onInserted(AdapterListUpdateCallback.java:42)
             *         at android.support.v7.util.BatchingListUpdateCallback.dispatchLastEvent(BatchingListUpdateCallback.java:61)
             *         at android.support.v7.util.DiffUtil$DiffResult.dispatchUpdatesTo(DiffUtil.java:852)
             *         at android.support.v7.util.DiffUtil$DiffResult.dispatchUpdatesTo(DiffUtil.java:802)
             *         at com.yuyakaido.android.cardstackview.sample.MainActivity.paginate(MainActivity.kt:164)
             *         at com.yuyakaido.android.cardstackview.sample.MainActivity.onCardSwiped(MainActivity.kt:50)
             *         at com.yuyakaido.android.cardstackview.CardStackLayoutManager.update(CardStackLayoutManager.java:277)
             *         at com.yuyakaido.android.cardstackview.CardStackLayoutManager.scrollHorizontallyBy(CardStackLayoutManager.java:92)
             *         at android.support.v7.widget.RecyclerView.scrollStep(RecyclerView.java:1829)
             *         at android.support.v7.widget.RecyclerView$ViewFlinger.run(RecyclerView.java:5067)
             *         at android.view.Choreographer$CallbackRecord.run(Choreographer.java:911)
             *         at android.view.Choreographer.doCallbacks(Choreographer.java:723)
             *         at android.view.Choreographer.doFrame(Choreographer.java:655)
             *         at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:897)
             *         at android.os.Handler.handleCallback(Handler.java:789)
             *         at android.os.Handler.dispatchMessage(Handler.java:98)
             *         at android.os.Looper.loop(Looper.java:164)
             *         at android.app.ActivityThread.main(ActivityThread.java:6541)
             *         at java.lang.reflect.Method.invoke(Native Method)
             *         at com.android.internal.os.Zygote$MethodAndArgsCaller.run(Zygote.java:240)
             *         at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:767)
             */
            Handler(Looper.getMainLooper()).post {
                cardStackListener.onCardSwiped(direction)
                topView?.let { view ->
                    cardStackListener.onCardAppeared(view, cardStackState.topPosition)
                }
            }
        }

        detachAndScrapAttachedViews(recycler)

        val parentTop = paddingTop
        val parentLeft = paddingLeft
        val parentRight = width - paddingLeft
        val parentBottom = height - paddingBottom
        var i = cardStackState.topPosition
        while (i < cardStackState.topPosition + cardStackSetting.visibleCount && i < itemCount) {
            val child = recycler.getViewForPosition(i)
            addView(child, 0)
            measureChildWithMargins(child, 0, 0)
            layoutDecoratedWithMargins(child, parentLeft, parentTop, parentRight, parentBottom)

            resetTranslation(child)
            resetScale(child)
            resetRotation(child)
            resetOverlay(child)

            if (i == cardStackState.topPosition) {
                updateTranslation(child)
                resetScale(child)
                updateRotation(child)
                updateOverlay(child)
            } else {
                val currentIndex = i - cardStackState.topPosition
                if (cardStackSetting.stackStyle == CardStackStyle.Carousel) {
                    updateCarousel(child, currentIndex)
                } else {
                    updateTranslation(child, currentIndex)
                    updateScale(child, currentIndex)
                }
                resetOverlay(child)
            }
            i++
        }

        if (cardStackState.status.isDragging) {
            cardStackListener.onCardDragging(cardStackState.direction, cardStackState.ratio)
        }
    }

    private fun updateTranslation(view: View) {
        view.translationX = cardStackState.dx.toFloat()
        view.translationY = cardStackState.dy.toFloat()
    }

    private fun updateTranslation(view: View, index: Int) {
        when (cardStackSetting.stackLayout) {
            StackLayout.Linear -> updateLinearTranslation(view, index)
            StackLayout.Overlay -> updateOverlayTranslation(view, index)
        }
    }

    private fun updateOverlayTranslation(view: View, index: Int) {
        val nextIndex = index - 1
        val translationPx = DisplayUtil.dpToPx(context, cardStackSetting.translationInterval)
        val currentTranslation = (index * translationPx).toFloat()
        val nextTranslation = (nextIndex * translationPx).toFloat()
        val targetTranslation =
            currentTranslation - (currentTranslation - nextTranslation) * cardStackState.ratio
        when (cardStackSetting.stackFrom) {
            StackFrom.None -> {}
            StackFrom.Top -> view.translationY = -targetTranslation
            StackFrom.TopAndLeft -> {
                view.translationY = -targetTranslation
                view.translationX = -targetTranslation
            }

            StackFrom.TopAndRight -> {
                view.translationY = -targetTranslation
                view.translationX = targetTranslation
            }

            StackFrom.Bottom -> view.translationY = targetTranslation
            StackFrom.BottomAndLeft -> {
                view.translationY = targetTranslation
                view.translationX = -targetTranslation
            }

            StackFrom.BottomAndRight -> {
                view.translationY = targetTranslation
                view.translationX = targetTranslation
            }

            StackFrom.Left -> view.translationX = -targetTranslation
            StackFrom.Right -> view.translationX = targetTranslation
        }
    }

    private fun updateLinearTranslation(view: View, index: Int) {
        val nextIndex = index - 1
        if (nextIndex < 0) {
            return
        }
        if (cardStackSetting.stackFrom == StackFrom.None) {
            updateOverlayTranslation(view, index)
            return
        }

        val spacingPx = DisplayUtil.dpToPx(context, cardStackSetting.translationInterval)
        val spacing = spacingPx.toFloat()
        val decoratedHeight = getDecoratedMeasuredHeight(view)
        val decoratedWidth = getDecoratedMeasuredWidth(view)
        val verticalStep = decoratedHeight + spacing
        val horizontalStep = decoratedWidth + spacing

        val currentVerticalTranslation = (index * verticalStep)
        val nextVerticalTranslation = (nextIndex * verticalStep)
        val targetVertical = currentVerticalTranslation -
            (currentVerticalTranslation - nextVerticalTranslation) * cardStackState.ratio

        val currentHorizontalTranslation = (index * horizontalStep)
        val nextHorizontalTranslation = (nextIndex * horizontalStep)
        val targetHorizontal = currentHorizontalTranslation -
            (currentHorizontalTranslation - nextHorizontalTranslation) * cardStackState.ratio

        when (cardStackSetting.stackFrom) {
            StackFrom.Top -> view.translationY = -targetVertical
            StackFrom.Bottom -> view.translationY = targetVertical
            StackFrom.Left -> view.translationX = -targetHorizontal
            StackFrom.Right -> view.translationX = targetHorizontal
            StackFrom.TopAndLeft -> {
                view.translationY = -targetVertical
                view.translationX = -targetHorizontal
            }

            StackFrom.TopAndRight -> {
                view.translationY = -targetVertical
                view.translationX = targetHorizontal
            }

            StackFrom.BottomAndLeft -> {
                view.translationY = targetVertical
                view.translationX = -targetHorizontal
            }

            StackFrom.BottomAndRight -> {
                view.translationY = targetVertical
                view.translationX = targetHorizontal
            }

            StackFrom.None -> {
                // Already handled above.
            }
        }
    }

    private fun resetTranslation(view: View) {
        view.translationX = 0.0f
        view.translationY = 0.0f
    }

    private fun updateScale(view: View, index: Int) {
        val nextIndex = index - 1
        val currentScale = 1.0f - index * (1.0f - cardStackSetting.scaleInterval)
        val nextScale = 1.0f - nextIndex * (1.0f - cardStackSetting.scaleInterval)
        val targetScale = currentScale + (nextScale - currentScale) * cardStackState.ratio
        when (cardStackSetting.stackFrom) {
            StackFrom.None -> {
                view.scaleX = targetScale
                view.scaleY = targetScale
            }

            StackFrom.Top -> view.scaleX = targetScale
            StackFrom.TopAndLeft -> view.scaleX = targetScale
            StackFrom.TopAndRight -> view.scaleX = targetScale
            StackFrom.Bottom -> view.scaleX = targetScale
            StackFrom.BottomAndLeft -> view.scaleX = targetScale
            StackFrom.BottomAndRight -> view.scaleX = targetScale
            StackFrom.Left ->                 // TODO: Should handle ScaleX
                view.scaleY = targetScale

            StackFrom.Right ->                 // TODO: Should handle ScaleX
                view.scaleY = targetScale
        }
    }

    private fun resetScale(view: View) {
        view.scaleX = 1.0f
        view.scaleY = 1.0f
    }

    private fun updateCarousel(view: View, index: Int) {
        val carousel = cardStackSetting.carouselSetting
        val distance = (index.toFloat() - cardStackState.ratio).coerceAtLeast(0f)
        val maxDepth = (cardStackSetting.visibleCount - 1).coerceAtLeast(1)
        val normalizedDepth = (distance / maxDepth.toFloat()).coerceIn(0f, 1f)
        val scale =
            (1.0f - carousel.scaleMultiplier * distance).coerceAtLeast(carousel.minScale)
        view.scaleX = scale
        view.scaleY = scale

        val translationIntervalPx =
            DisplayUtil.dpToPx(context, cardStackSetting.translationInterval).toFloat()
        val intervalShift = translationIntervalPx * distance
        val sizeShift = if (carousel.orientation == CarouselOrientation.Vertical) {
            view.measuredHeight * (1.0f - scale) / 2.0f
        } else {
            view.measuredWidth * (1.0f - scale) / 2.0f
        }
        val direction = resolveCarouselDirectionSign(carousel.orientation)
        val translation = direction * (intervalShift + sizeShift)

        if (carousel.orientation == CarouselOrientation.Vertical) {
            view.translationY = translation
            view.translationX = 0.0f
        } else {
            view.translationX = translation
            view.translationY = 0.0f
        }

        val rotation = direction * carousel.tiltAngle * normalizedDepth
        view.rotation = rotation
    }

    private fun resolveCarouselDirectionSign(orientation: CarouselOrientation): Float {
        return when (orientation) {
            CarouselOrientation.Vertical -> when (cardStackSetting.stackFrom) {
                StackFrom.Top, StackFrom.TopAndLeft, StackFrom.TopAndRight -> -1f
                else -> 1f
            }

            CarouselOrientation.Horizontal -> when (cardStackSetting.stackFrom) {
                StackFrom.Left, StackFrom.TopAndLeft, StackFrom.BottomAndLeft -> -1f
                else -> 1f
            }
        }
    }

    private fun updateRotation(view: View) {
        if (width == 0) {
            view.rotation = 0.0f
            return
        }
        val degree =
            cardStackState.dx * cardStackSetting.maxDegree / width * cardStackState.proportion
        view.rotation = degree
    }

    private fun resetRotation(view: View) {
        view.rotation = 0.0f
        view.rotationX = 0.0f
        view.rotationY = 0.0f
    }

    private fun updateOverlay(view: View) {
        val overlays = mapOf(
            Direction.Left to R.id.left_overlay,
            Direction.Right to R.id.right_overlay,
            Direction.Top to R.id.top_overlay,
            Direction.Bottom to R.id.bottom_overlay
        )
        
        // Reset all overlays
        listOf(
            R.id.left_overlay,
            R.id.right_overlay,
            R.id.top_overlay,
            R.id.bottom_overlay
        ).forEach { id ->
            view.findViewById<View>(id)?.alpha = 0.0f
        }
        
        val direction = cardStackState.direction
        val alpha = cardStackSetting.overlayInterpolator.getInterpolation(cardStackState.ratio)
        
        overlays[direction]?.let { overlayId ->
            view.findViewById<View>(overlayId)?.alpha = alpha
        }
    }

    private fun resetOverlay(view: View) {
        listOf(
            R.id.left_overlay,
            R.id.right_overlay,
            R.id.top_overlay,
            R.id.bottom_overlay
        ).forEach { id ->
            view.findViewById<View>(id)?.alpha = 0.0f
        }
    }

    private fun smoothScrollToPosition(position: Int) {
        if (cardStackState.topPosition < position) {
            smoothScrollToNext(position)
        } else {
            smoothScrollToPrevious(position)
        }
    }

    private fun smoothScrollToNext(position: Int) {
        cardStackState.proportion = 0.0f
        cardStackState.targetPosition = position
        val scroller =
            CardStackSmoothScroller(CardStackSmoothScroller.ScrollType.AutomaticSwipe, this)
        scroller.targetPosition = cardStackState.topPosition
        startSmoothScroll(scroller)
    }

    private fun smoothScrollToPrevious(position: Int) {
        val topView = topView
        if (topView != null) {
            cardStackListener.onCardDisappeared(this.topView, cardStackState.topPosition)
        }

        cardStackState.proportion = 0.0f
        cardStackState.targetPosition = position
        cardStackState.topPosition--
        val scroller =
            CardStackSmoothScroller(CardStackSmoothScroller.ScrollType.AutomaticRewind, this)
        scroller.targetPosition = cardStackState.topPosition
        startSmoothScroll(scroller)
    }

    val topView: View?
        get() = findViewByPosition(cardStackState.topPosition)

    var topPosition: Int
        get() = cardStackState.topPosition
        set(topPosition) {
            cardStackState.topPosition = topPosition
        }

    fun setStackFrom(stackFrom: StackFrom) {
        cardStackSetting.stackFrom = stackFrom
    }

    fun setStackLayout(stackLayout: StackLayout) {
        cardStackSetting.stackLayout = stackLayout
    }

    fun setStackStyle(stackStyle: CardStackStyle) {
        cardStackSetting.stackStyle = stackStyle
    }

    fun setCarouselSetting(carouselSetting: CarouselSetting) {
        cardStackSetting.carouselSetting = carouselSetting
    }

    fun setVisibleCount(@IntRange(from = 1) visibleCount: Int) {
        require(visibleCount >= 1) { "VisibleCount must be greater than 0." }
        cardStackSetting.visibleCount = visibleCount
    }

    fun setTranslationInterval(@FloatRange(from = 0.0) translationInterval: Float) {
        require(!(translationInterval < 0.0f)) { "TranslationInterval must be greater than or equal 0.0f" }
        cardStackSetting.translationInterval = translationInterval
    }

    fun setScaleInterval(@FloatRange(from = 0.0) scaleInterval: Float) {
        require(!(scaleInterval < 0.0f)) { "ScaleInterval must be greater than or equal 0.0f." }
        cardStackSetting.scaleInterval = scaleInterval
    }

    fun setSwipeThreshold(@FloatRange(from = 0.0, to = 1.0) swipeThreshold: Float) {
        require(!(swipeThreshold < 0.0f || 1.0f < swipeThreshold)) { "SwipeThreshold must be 0.0f to 1.0f." }
        cardStackSetting.swipeThreshold = swipeThreshold
    }

    fun setMaxDegree(@FloatRange(from = (-360.0f).toDouble(), to = 360.0) maxDegree: Float) {
        require(!(maxDegree < -360.0f || 360.0f < maxDegree)) { "MaxDegree must be -360.0f to 360.0f" }
        cardStackSetting.maxDegree = maxDegree
    }

    fun setDirections(directions: List<Direction>) {
        cardStackSetting.directions = directions
    }

    fun setCanScrollHorizontal(canScrollHorizontal: Boolean) {
        cardStackSetting.canScrollHorizontal = canScrollHorizontal
    }

    fun setCanScrollVertical(canScrollVertical: Boolean) {
        cardStackSetting.canScrollVertical = canScrollVertical
    }

    fun setCanScrollLeft(canScrollLeft: Boolean) {
        cardStackSetting.canScrollLeft = canScrollLeft
    }

    fun setCanScrollRight(canScrollRight: Boolean) {
        cardStackSetting.canScrollRight = canScrollRight
    }

    fun setCanScrollUp(canScrollUp: Boolean) {
        cardStackSetting.canScrollUp = canScrollUp
    }

    fun setCanScrollDown(canScrollDown: Boolean) {
        cardStackSetting.canScrollDown = canScrollDown
    }

    fun setSwipeableMethod(swipeableMethod: SwipeableMethod) {
        cardStackSetting.swipeableMethod = swipeableMethod
    }

    fun setSwipeAnimationSetting(swipeAnimationSetting: SwipeAnimationSetting) {
        cardStackSetting.swipeAnimationSetting = swipeAnimationSetting
    }

    fun setRewindAnimationSetting(rewindAnimationSetting: RewindAnimationSetting) {
        cardStackSetting.rewindAnimationSetting = rewindAnimationSetting
    }

    fun setOverlayInterpolator(overlayInterpolator: Interpolator) {
        cardStackSetting.overlayInterpolator = overlayInterpolator
    }

    private fun isManualHorizontalScrollAllowed(dx: Int): Boolean {
        if (!cardStackSetting.canScrollHorizontal) {
            return false
        }
        if (dx == 0) {
            return true
        }
        return when {
            dx > 0 -> cardStackSetting.canScrollLeft
            dx < 0 -> cardStackSetting.canScrollRight
            else -> true
        }
    }

    private fun isManualVerticalScrollAllowed(dy: Int): Boolean {
        if (!cardStackSetting.canScrollVertical) {
            return false
        }
        if (dy == 0) {
            return true
        }
        return when {
            dy > 0 -> cardStackSetting.canScrollUp
            dy < 0 -> cardStackSetting.canScrollDown
            else -> true
        }
    }
}
