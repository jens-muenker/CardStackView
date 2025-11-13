package com.yuyakaido.android.cardstackview.internal

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

internal class CardStackAnimatorListener(
    private val view: View,
    private val cardStackState: CardStackState
) : AnimatorListenerAdapter() {

    override fun onAnimationEnd(animation: Animator) {
        super.onAnimationEnd(animation)
        view.alpha = 1f
        cardStackState.isLastChildOnAnimation = false
        cardStackState.isLastChildWasAnimated = true
    }

    override fun onAnimationCancel(animation: Animator) {
        super.onAnimationCancel(animation)
        view.alpha = 1f
        cardStackState.isLastChildOnAnimation = false
    }
}

