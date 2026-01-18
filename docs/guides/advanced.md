# Advanced Features

This guide explains advanced features like Manual Rewind Gestures, Carousel Style, and more.

## Manual Rewind Gestures

You can configure gestures that trigger a rewind when dragging, instead of removing the current card.

### Setup

```kotlin
// Enable vertical swipes
manager.setDirections(Direction.VERTICAL)

// Configure Bottom swipe as rewind gesture
manager.setManualRewindDirections(listOf(Direction.Bottom))
```

When the user swipes down, the previous card is brought back (if available), instead of removing the current card.

### Example: Horizontal with Rewind

```kotlin
// Swipe horizontally (Left/Right)
manager.setDirections(Direction.HORIZONTAL)

// Left swipe brings back previous card
manager.setManualRewindDirections(listOf(Direction.Left))
```

### Important Notes

- `manualRewindDirections` must **not** contain any directions that are already enabled in `setDirections(...)`
- If you try to use an already enabled direction, the LayoutManager throws an `IllegalArgumentException`
- This ensures swipe and rewind gestures remain distinct

### Example: Incorrect Configuration

```kotlin
// ❌ ERROR - Direction.Left is both in setDirections and setManualRewindDirections
manager.setDirections(listOf(Direction.Left, Direction.Right))
manager.setManualRewindDirections(listOf(Direction.Left))  // IllegalArgumentException!
```

### Example: Correct Configuration

```kotlin
// ✅ CORRECT - Different directions
manager.setDirections(Direction.HORIZONTAL)  // Left, Right
manager.setManualRewindDirections(listOf(Direction.Bottom))  // Bottom for rewind
```

## Carousel Style

The Carousel Style provides a carousel-inspired effect with curves and tilt.

### Setup

```kotlin
val carousel = CarouselSetting(
    orientation = CarouselOrientation.Horizontal,  // or Vertical
    scaleMultiplier = 0.18f,                        // Scale multiplier
    minScale = 0.65f,                              // Minimum scale
    tiltAngle = 8f                                  // Tilt angle in degrees
)

manager.setStackStyle(CardStackStyle.Carousel)
manager.setCarouselSetting(carousel)
manager.setStackFrom(StackFrom.Bottom)
manager.setTranslationInterval(16f)
```

### CarouselSetting Parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| `orientation` | `CarouselOrientation` | `Horizontal` or `Vertical` |
| `scaleMultiplier` | `Float` | Scale multiplier (0.0f - 1.0f) |
| `minScale` | `Float` | Minimum scale for back cards (0.0f - 1.0f) |
| `tiltAngle` | `Float` | Tilt angle in degrees |

### Example: Horizontal Carousel

```kotlin
val carousel = CarouselSetting(
    orientation = CarouselOrientation.Horizontal,
    scaleMultiplier = 0.18f,
    minScale = 0.65f,
    tiltAngle = 8f
)

manager.setStackStyle(CardStackStyle.Carousel)
manager.setCarouselSetting(carousel)
manager.setStackFrom(StackFrom.Bottom)
manager.setTranslationInterval(16f)
```

### Example: Vertical Carousel

```kotlin
val carousel = CarouselSetting(
    orientation = CarouselOrientation.Vertical,
    scaleMultiplier = 0.18f,
    minScale = 0.65f,
    tiltAngle = 10f
)

manager.setStackStyle(CardStackStyle.Carousel)
manager.setCarouselSetting(carousel)
manager.setStackFrom(StackFrom.Bottom)
manager.setTranslationInterval(16f)
```

### Back to Stack Style

```kotlin
manager.setStackStyle(CardStackStyle.Stack)
```

## Scroll to Position

You can programmatically scroll to a specific position:

### With Animation

```kotlin
cardStackView.smoothScrollToPosition(5)
```

### Without Animation

```kotlin
cardStackView.scrollToPosition(5)
```

### Set Top Position

```kotlin
manager.topPosition = 5
```

## Dynamic Configuration

You can change the configuration at runtime:

```kotlin
// Switch between horizontal and vertical swipes
fun switchToHorizontal() {
    manager.setDirections(Direction.HORIZONTAL)
    manager.setCanScrollHorizontal(true)
    manager.setCanScrollVertical(false)
}

fun switchToVertical() {
    manager.setDirections(Direction.VERTICAL)
    manager.setCanScrollHorizontal(false)
    manager.setCanScrollVertical(true)
}
```

## Change Swipeable Method at Runtime

```kotlin
// Temporarily disable swipes
fun disableSwipes() {
    manager.setSwipeableMethod(SwipeableMethod.None)
}

// Re-enable swipes
fun enableSwipes() {
    manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
}
```

## Example: Toggle between Carousel and Stack

```kotlin
private var isCarouselEnabled = false

fun toggleCarouselStyle() {
    isCarouselEnabled = !isCarouselEnabled
    
    if (isCarouselEnabled) {
        val carousel = CarouselSetting(
            orientation = CarouselOrientation.Vertical,
            scaleMultiplier = 0.18f,
            minScale = 0.65f,
            tiltAngle = 10f
        )
        manager.setStackStyle(CardStackStyle.Carousel)
        manager.setCarouselSetting(carousel)
    } else {
        manager.setStackStyle(CardStackStyle.Stack)
    }
    
    manager.requestLayout()
}
```

## Example: Complete Advanced Configuration

```kotlin
val manager = CardStackLayoutManager(this, listener).apply {
    // Stack Style
    setStackStyle(CardStackStyle.Carousel)
    setCarouselSetting(
        CarouselSetting(
            orientation = CarouselOrientation.Vertical,
            scaleMultiplier = 0.18f,
            minScale = 0.65f,
            tiltAngle = 10f
        )
    )
    
    // Stack Layout
    setStackLayout(StackLayout.Linear)
    setStackFrom(StackFrom.Bottom)
    setTranslationInterval(16f)
    
    // Swipe
    setDirections(Direction.VERTICAL)
    setManualRewindDirections(listOf(Direction.Left))
    setSwipeThreshold(0.25f)
    
    // Overlays
    setOverlayInterpolator(AccelerateInterpolator())
    
    // Animations
    setLastItemAppearingAnimationDuration(200)
}
```

## Best Practices

1. **Performance**: Don't change configurations too frequently at runtime
2. **UX**: Inform users about changes (e.g., when swipes are disabled)
3. **Consistency**: Use consistent configurations for similar features
4. **Testing**: Test all configuration combinations thoroughly

## Next Steps

- [Configuration](../configuration.md) - All available options
- [Basic Usage](basic-usage.md) - Basic features
- [Callbacks](callbacks.md) - Event handling
