package com.yuyakaido.android.cardstackview

enum class Duration(@JvmField val duration: Int) {
    Fast(100),
    Normal(200),
    Slow(500);

    companion object {
        @JvmStatic
        fun fromVelocity(velocity: Int): Duration {
            if (velocity < 1000) {
                return Slow
            } else if (velocity < 5000) {
                return Normal
            }
            return Fast
        }
    }
}
