# FAQ - Frequently Asked Questions

Frequently asked questions and solutions.

## General

### How do I install CardStackView?

See [Getting Started](getting-started.md#installation) for the installation guide.

### What minimum Android version is supported?

CardStackView supports Android API Level 21 (Android 5.0) and higher.

### Can I use CardStackView with Java?

Yes, CardStackView is fully compatible with Java, even though it's written in Kotlin.

## Swipe & Rewind

### How do I disable swipes dynamically?

You can enable/disable swipes at runtime:

```kotlin
// Disable
manager.setSwipeableMethod(SwipeableMethod.None)
manager.setCanScrollHorizontal(false)
manager.setCanScrollVertical(false)

// Enable
manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
manager.setCanScrollHorizontal(true)
manager.setCanScrollVertical(true)
```

See also: [Issue #381](https://github.com/yuyakaido/CardStackView/issues/381)

### How do I use a custom CardStackListener to (de)activate swipes?

You can use a custom listener and enable/disable swipes in the callbacks:

```kotlin
lateinit var manager: CardStackLayoutManager

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    manager = CardStackLayoutManager(this, object : CardStackListener {
        override fun onCardDragging(direction: Direction?, ratio: Float) {}
        override fun onCardSwiped(direction: Direction?) {}
        override fun onCardRewound() {}
        override fun onCardCanceled() {}
        override fun onCardAppeared(view: View?, position: Int) {}
        override fun onCardDisappeared(view: View?, position: Int) {}
    })
    cardStackView.layoutManager = manager
}

private fun setupButtons() {
    val disable = findViewById<View>(R.id.disable_swipe_button)
    val enable = findViewById<View>(R.id.enable_swipe_button)

    disable.setOnClickListener {
        manager.setSwipeableMethod(SwipeableMethod.None)
        manager.setCanScrollHorizontal(false)
        manager.setCanScrollVertical(false)
    }
    enable.setOnClickListener {
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
    }
}
```

### How can I swipe in only one direction?

```kotlin
// Only to the right
manager.setDirections(listOf(Direction.Right))
manager.setCanScrollLeft(false)
manager.setCanScrollRight(true)

// Only to the left
manager.setDirections(listOf(Direction.Left))
manager.setCanScrollLeft(true)
manager.setCanScrollRight(false)
```

## Overlays

### My overlays are not showing

Check the following:

1. **IDs are correct**: IDs must be exact:
   - `left_overlay`
   - `right_overlay`
   - `top_overlay`
   - `bottom_overlay`

2. **Visibility is visible**: Overlay containers must have `visibility="visible"`:
   ```xml
   android:visibility="visible"
   ```

3. **Directions are enabled**: Overlays only react to enabled directions:
   ```kotlin
   // If only Direction.Left is enabled, right_overlay will never appear
   manager.setDirections(listOf(Direction.Left))
   ```

4. **Manager instance is reused**: Make sure you use the same manager instance:
   ```kotlin
   val manager = CardStackLayoutManager(context, listener).apply {
       setDirections(Direction.HORIZONTAL)
       setOverlayInterpolator(LinearInterpolator())
   }
   cardStackView.layoutManager = manager
   ```

5. **Alpha is not set manually**: Let the library control alpha values.

See also: [Overlay Troubleshooting](guides/overlays.md#troubleshooting) and [Issue #383](https://github.com/yuyakaido/CardStackView/issues/383)

### How do I change the overlay animation?

Use `setOverlayInterpolator()`:

```kotlin
manager.setOverlayInterpolator(LinearInterpolator())           // Linear
manager.setOverlayInterpolator(AccelerateInterpolator())       // Accelerating
manager.setOverlayInterpolator(DecelerateInterpolator())       // Decelerating
```

## Paging & Data

### How do I implement paging?

See [Paging Guide](guides/paging.md) for a complete guide.

**Important:** Do **not** use `notifyDataSetChanged()` for paging, as this resets the `topPosition`.

### Can I use `notifyDataSetChanged()`?

For paging: **No**. Use `DiffUtil` or `notifyItemRangeInserted()` instead.

For reloading: **Yes**, `notifyDataSetChanged()` is fine for complete reloading.

### How do I load data incrementally?

Implement `CardStackListener` and load data when the user approaches the end:

```kotlin
override fun onCardSwiped(direction: Direction?) {
    val currentPosition = manager.topPosition
    val totalItems = adapter.itemCount
    
    if (currentPosition >= totalItems - 5) {
        loadMoreData()
    }
}
```

## Configuration

### How do I change swipe sensitivity?

Adjust the `swipeThreshold`:

```kotlin
manager.setSwipeThreshold(0.1f)  // Very sensitive (10%)
manager.setSwipeThreshold(0.3f)   // Default (30%)
manager.setSwipeThreshold(0.5f)   // Less sensitive (50%)
```

### How do I remove card rotation?

Set `maxDegree` to `0`:

```kotlin
manager.setMaxDegree(0.0f)
```

### How do I change the spacing between cards?

Use `setTranslationInterval()`:

```kotlin
manager.setTranslationInterval(4.0f)   // Tight
manager.setTranslationInterval(8.0f)   // Default
manager.setTranslationInterval(12.0f)  // Wide
```

### How do I disable scaling?

Set `scaleInterval` to `1.0f`:

```kotlin
manager.setScaleInterval(1.0f)
```

## Animations

### How do I customize the swipe animation?

```kotlin
val setting = SwipeAnimationSetting.Builder()
    .setDirection(Direction.Right)
    .setDuration(Duration.Normal.duration)
    .setInterpolator(AccelerateInterpolator())
    .build()
manager.setSwipeAnimationSetting(setting)
```

See [Custom Animations](guides/custom-animations.md) for more details.

### How do I disable the fade-in animation for the last card?

Set the duration to `0`:

```kotlin
manager.setLastItemAppearingAnimationDuration(0)
```

## Manual Rewind

### How do I enable manual rewind gestures?

```kotlin
manager.setDirections(Direction.VERTICAL)
manager.setManualRewindDirections(listOf(Direction.Bottom))
```

**Important:** `manualRewindDirections` must not contain any directions that are already enabled in `setDirections()`.

### Why do I get an IllegalArgumentException with setManualRewindDirections?

This happens when a direction is enabled in both `setDirections()` and `setManualRewindDirections()`:

```kotlin
// ❌ ERROR
manager.setDirections(listOf(Direction.Left, Direction.Right))
manager.setManualRewindDirections(listOf(Direction.Left))  // IllegalArgumentException!

// ✅ CORRECT
manager.setDirections(Direction.HORIZONTAL)  // Left, Right
manager.setManualRewindDirections(listOf(Direction.Bottom))  // Bottom for rewind
```

## Carousel Style

### How do I enable carousel style?

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

See [Advanced Features](guides/advanced.md#carousel-style) for more details.

## Performance

### How do I optimize performance?

1. **Use DiffUtil**: For paging and updates
2. **Avoid heavy operations in onCardDragging**: Called very frequently
3. **Use ViewHolder pattern**: Properly implemented in adapter
4. **Avoid too many simultaneous animations**

## Troubleshooting

### Cards are not showing

1. Check if the adapter has data
2. Check if `layoutManager` is set
3. Check the layout parameters of CardStackView

### Position is reset

Avoid `notifyDataSetChanged()` for paging. Use `DiffUtil` or `notifyItemRangeInserted()` instead.

### Swipes are not working

1. Check `setSwipeableMethod()` - is it set to `None`?
2. Check `setCanScrollHorizontal()` / `setCanScrollVertical()`
3. Check `setDirections()` - are directions enabled?

## Further Help

- [Getting Started](getting-started.md) - First steps
- [Configuration](configuration.md) - All options
- [Guides](guides/) - Detailed guides
- [API Reference](api-reference.md) - Complete API documentation

If you have further questions, please open an issue on GitHub.
