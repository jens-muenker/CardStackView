# Configuration

All available configuration options for `CardStackLayoutManager`.

## Stack Layout

Choose whether cards overlap each other (`StackLayout.Overlay`, default) or are arranged sequentially like a vertical/horizontal RecyclerView (`StackLayout.Linear`). In Linear mode, `translationInterval` becomes the spacing between cards.

```kotlin
manager.setStackLayout(StackLayout.Overlay)  // Default
manager.setStackLayout(StackLayout.Linear)    // Sequential arrangement
```

**Example for Linear:**
```kotlin
manager.setStackLayout(StackLayout.Linear)
manager.setStackFrom(StackFrom.Bottom)   // Cards rise from bottom
manager.setTranslationInterval(12f)      // Spacing in dp between items
manager.setScaleInterval(1.0f)           // Optional: keep card sizes identical
```

## Stack Style

Choose between classic Stack (`CardStackStyle.Stack`, default) and Carousel Style (`CardStackStyle.Carousel`).

### Carousel Style

For a carousel-inspired effect:

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

## Stack From

Determines from which side the cards are stacked.

| Value | Description |
|-------|-------------|
| `StackFrom.None` | Default - no offset |
| `StackFrom.Top` | From top |
| `StackFrom.Bottom` | From bottom |
| `StackFrom.Left` | From left |
| `StackFrom.Right` | From right |
| `StackFrom.TopAndLeft` | From top and left |
| `StackFrom.TopAndRight` | From top and right |
| `StackFrom.BottomAndLeft` | From bottom and left |
| `StackFrom.BottomAndRight` | From bottom and right |

```kotlin
manager.setStackFrom(StackFrom.None)
```

## Visible Count

Number of visible cards in the stack.

| Default | Value | Description |
|:-------:|:-----:|-------------|
| ✅ | `3` | Default - 3 cards visible |
| | `2` | 2 cards visible |
| | `4` | 4 cards visible |

```kotlin
manager.setVisibleCount(3)
```

## Translation Interval

Spacing between cards in dp. In Linear mode, this is the actual spacing, in Overlay mode it's the offset.

| Default | Value | Description |
|:-------:|:-----:|-------------|
| | `4dp` | Tight spacing |
| ✅ | `8dp` | Default |
| | `12dp` | Wide spacing |

```kotlin
manager.setTranslationInterval(8.0f)
```

## Scale Interval

Scaling factor for cards in the stack (0.0f - 1.0f). `1.0f` means no scaling.

| Default | Value | Description |
|:-------:|:-----:|-------------|
| ✅ | `0.95f` | 95% size for back cards |
| | `0.90f` | 90% size for back cards |
| | `1.0f` | No scaling |

```kotlin
manager.setScaleInterval(0.95f)
```

## Max Degree

Maximum rotation angle of cards in degrees (-360° to 360°).

| Default | Value | Description |
|:-------:|:-----:|-------------|
| ✅ | `20°` | Default - slight rotation |
| | `0°` | No rotation |

```kotlin
manager.setMaxDegree(20.0f)
```

## Swipe Direction

Directions in which swiping is allowed.

| Default | Value | Description |
|:-------:|:-----:|-------------|
| ✅ | `Direction.HORIZONTAL` | Left and Right |
| | `Direction.VERTICAL` | Top and Bottom |
| | `Direction.FREEDOM` | All directions |

```kotlin
manager.setDirections(Direction.HORIZONTAL)
manager.setDirections(Direction.VERTICAL)
manager.setDirections(Direction.FREEDOM)
```

## Swipe Threshold

Threshold for swipe (0.0f - 1.0f). If the card is dragged beyond this value, it will be swiped.

| Default | Value | Description |
|:-------:|:-----:|-------------|
| ✅ | `0.3f` | 30% - Default |
| | `0.1f` | 10% - Very sensitive |
| | `0.5f` | 50% - Less sensitive |

```kotlin
manager.setSwipeThreshold(0.3f)
```

## Swipe Restriction

Restriction of swipe directions.

### Horizontal/Vertical Restriction

```kotlin
manager.setCanScrollHorizontal(true)   // Allow horizontal
manager.setCanScrollVertical(true)     // Allow vertical
```

### Individual Directions

```kotlin
manager.setCanScrollLeft(true)    // Allow left
manager.setCanScrollRight(true)   // Allow right
manager.setCanScrollUp(true)      // Allow up
manager.setCanScrollDown(true)    // Allow down
```

## Swipeable Method

Determines how swiping can be performed.

| Default | Value | Description |
|:-------:|:-----:|-------------|
| ✅ | `AutomaticAndManual` | Programmatic code and gestures |
| | `Automatic` | Programmatic code only |
| | `Manual` | Gestures only |
| | `None` | Disabled |

```kotlin
manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
```

## Manual Rewind Directions

Directions that trigger a rewind when dragging (see [Advanced Features](guides/advanced.md#manual-rewind-gestures)).

```kotlin
manager.setDirections(Direction.VERTICAL)
manager.setManualRewindDirections(listOf(Direction.Bottom))
```

**Important:** `manualRewindDirections` must not contain any directions that are already enabled in `setDirections(...)`.

## Overlay Interpolator

Interpolator for the alpha animation of overlay views.

```kotlin
manager.setOverlayInterpolator(LinearInterpolator())
manager.setOverlayInterpolator(AccelerateInterpolator())
manager.setOverlayInterpolator(DecelerateInterpolator())
```

## Last Item Appearing Animation

Duration of the fade-in animation for the last card when new data is added (in milliseconds).

```kotlin
manager.setLastItemAppearingAnimationDuration(150)  // Default: 150ms
manager.setLastItemAppearingAnimationDuration(0)    // Disabled
```

## Swipe Animation Setting

Settings for the swipe animation (see [Custom Animations](guides/custom-animations.md#swipe-animation)).

```kotlin
val setting = SwipeAnimationSetting.Builder()
    .setDirection(Direction.Right)
    .setDuration(Duration.Normal.duration)
    .setInterpolator(AccelerateInterpolator())
    .build()
manager.setSwipeAnimationSetting(setting)
```

## Rewind Animation Setting

Settings for the rewind animation (see [Custom Animations](guides/custom-animations.md#rewind-animation)).

```kotlin
val setting = RewindAnimationSetting.Builder()
    .setDirection(Direction.Bottom)
    .setDuration(Duration.Normal.duration)
    .setInterpolator(DecelerateInterpolator())
    .build()
manager.setRewindAnimationSetting(setting)
```

## Top Position

Current position of the top card (get/set).

```kotlin
val currentPosition = manager.topPosition
manager.topPosition = 5  // Jump to position 5
```

## Complete Configuration Example

```kotlin
val manager = CardStackLayoutManager(this, listener).apply {
    // Stack Layout
    setStackLayout(StackLayout.Overlay)
    setStackStyle(CardStackStyle.Stack)
    setStackFrom(StackFrom.None)
    
    // Visibility
    setVisibleCount(3)
    setTranslationInterval(8.0f)
    setScaleInterval(0.95f)
    setMaxDegree(20.0f)
    
    // Swipe
    setDirections(Direction.HORIZONTAL)
    setSwipeThreshold(0.3f)
    setCanScrollHorizontal(true)
    setCanScrollVertical(true)
    setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
    
    // Overlays
    setOverlayInterpolator(LinearInterpolator())
    
    // Animations
    setLastItemAppearingAnimationDuration(150)
}

cardStackView.layoutManager = manager
```
