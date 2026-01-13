package com.yuyakaido.android.cardstackview

/**
 * Defines how stacked cards are positioned relative to each other.
 *
 * [Overlay] keeps the existing Tinder-style overlapping behavior.
 * [Linear] places cards sequentially (e.g., like a vertical RecyclerView) and
 * uses [CardStackLayoutManager.setTranslationInterval] as the spacing between cards.
 */
enum class StackLayout {
    Overlay,
    Linear,
}
