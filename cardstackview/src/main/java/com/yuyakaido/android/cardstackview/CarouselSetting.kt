package com.yuyakaido.android.cardstackview

import androidx.annotation.FloatRange

enum class CarouselOrientation {
    Vertical,
    Horizontal
}

data class CarouselSetting @JvmOverloads constructor(
    val orientation: CarouselOrientation = CarouselOrientation.Vertical,
    @param:FloatRange(from = 0.0) val scaleMultiplier: Float = 0.15f,
    @param:FloatRange(from = 0.0, to = 1.0) val minScale: Float = 0.6f,
    @param:FloatRange(from = 0.0, to = 90.0) val tiltAngle: Float = 8f
) {
    init {
        require(scaleMultiplier >= 0f) { "Carousel scaleMultiplier must be greater than or equal to 0." }
        require(minScale in 0f..1f) { "Carousel minScale must be between 0.0 and 1.0." }
        require(tiltAngle in 0f..90f) { "Carousel tiltAngle must be within 0 - 90 degrees." }
    }
}
