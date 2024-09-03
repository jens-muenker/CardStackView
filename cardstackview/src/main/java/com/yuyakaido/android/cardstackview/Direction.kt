package com.yuyakaido.android.cardstackview

import java.util.Arrays

enum class Direction {
    Left,
    Right,
    Top,
    Bottom;

    companion object {
        @JvmField
        val HORIZONTAL: List<Direction> = listOf(Left, Right)
        val VERTICAL: List<Direction> = listOf(Top, Bottom)
        val FREEDOM: List<Direction> = listOf(*entries.toTypedArray())
    }
}
