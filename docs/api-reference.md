# API Reference

Complete API documentation for CardStackView.

## CardStackView

### Methods

#### `swipe()`

Programmatically swipe the top card.

```kotlin
cardStackView.swipe()
```

**Note:** The swipe animation is configured via `SwipeAnimationSetting`.

#### `rewind()`

Bring back the last swiped card.

```kotlin
cardStackView.rewind()
```

**Note:** The rewind animation is configured via `RewindAnimationSetting`.

#### `smoothScrollToPosition(position: Int)`

Scroll to a specific position with animation.

```kotlin
cardStackView.smoothScrollToPosition(5)
```

#### `scrollToPosition(position: Int)`

Scroll to a specific position without animation.

```kotlin
cardStackView.scrollToPosition(5)
```

### Properties

#### `layoutManager: LayoutManager`

The layout manager for `CardStackView`. Must be an instance of `CardStackLayoutManager`.

```kotlin
cardStackView.layoutManager = CardStackLayoutManager(context)
```

#### `adapter: Adapter<*>`

The adapter for `CardStackView`. Must be a `RecyclerView.Adapter` instance.

```kotlin
cardStackView.adapter = YourAdapter()
```

---

## CardStackLayoutManager

### Constructor

```kotlin
CardStackLayoutManager(
    context: Context,
    listener: CardStackListener = CardStackListener.DEFAULT
)
```

**Parameters:**
- `context`: The context
- `listener`: Optional `CardStackListener` (default: `CardStackListener.DEFAULT`)

### Properties

#### `topPosition: Int`

Position of the top card (get/set).

```kotlin
val currentPosition = manager.topPosition
manager.topPosition = 5
```

#### `cardStackListener: CardStackListener`

The `CardStackListener` for events.

```kotlin
manager.cardStackListener = object : CardStackListener {
    // ...
}
```

#### `cardStackSetting: CardStackSetting`

Access to internal settings (read-only).

```kotlin
val setting = manager.cardStackSetting
```

### Configuration Methods

#### Stack Layout

```kotlin
fun setStackLayout(stackLayout: StackLayout)
```

Sets the stack layout (`Overlay` or `Linear`).

```kotlin
manager.setStackLayout(StackLayout.Overlay)
manager.setStackLayout(StackLayout.Linear)
```

#### Stack Style

```kotlin
fun setStackStyle(stackStyle: CardStackStyle)
```

Sets the stack style (`Stack` or `Carousel`).

```kotlin
manager.setStackStyle(CardStackStyle.Stack)
manager.setStackStyle(CardStackStyle.Carousel)
```

#### Carousel Setting

```kotlin
fun setCarouselSetting(carouselSetting: CarouselSetting)
```

Sets the carousel settings.

```kotlin
val carousel = CarouselSetting(
    orientation = CarouselOrientation.Horizontal,
    scaleMultiplier = 0.18f,
    minScale = 0.65f,
    tiltAngle = 8f
)
manager.setCarouselSetting(carousel)
```

#### Stack From

```kotlin
fun setStackFrom(stackFrom: StackFrom)
```

Sets the stack-from direction.

```kotlin
manager.setStackFrom(StackFrom.None)
manager.setStackFrom(StackFrom.Bottom)
```

#### Visible Count

```kotlin
fun setVisibleCount(visibleCount: Int)
```

Sets the number of visible cards (min: 1).

```kotlin
manager.setVisibleCount(3)
```

#### Translation Interval

```kotlin
fun setTranslationInterval(translationInterval: Float)
```

Sets spacing between cards in dp (min: 0.0f).

```kotlin
manager.setTranslationInterval(8.0f)
```

#### Scale Interval

```kotlin
fun setScaleInterval(scaleInterval: Float)
```

Sets the scale factor (min: 0.0f).

```kotlin
manager.setScaleInterval(0.95f)
```

#### Swipe Threshold

```kotlin
fun setSwipeThreshold(swipeThreshold: Float)
```

Sets the swipe threshold (0.0f - 1.0f).

```kotlin
manager.setSwipeThreshold(0.3f)
```

#### Max Degree

```kotlin
fun setMaxDegree(maxDegree: Float)
```

Sets the maximum rotation angle in degrees (-360.0f to 360.0f).

```kotlin
manager.setMaxDegree(20.0f)
```

#### Directions

```kotlin
fun setDirections(directions: List<Direction>)
```

Sets the allowed swipe directions.

```kotlin
manager.setDirections(Direction.HORIZONTAL)
manager.setDirections(Direction.VERTICAL)
manager.setDirections(Direction.FREEDOM)
```

#### Manual Rewind Directions

```kotlin
fun setManualRewindDirections(directions: List<Direction>)
```

Sets directions for manual rewind gestures.

```kotlin
manager.setManualRewindDirections(listOf(Direction.Bottom))
```

**Note:** Directions must not overlap with those passed to `setDirections()`.

#### Can Scroll Horizontal

```kotlin
fun setCanScrollHorizontal(canScrollHorizontal: Boolean)
```

Enables/disables horizontal scrolling.

```kotlin
manager.setCanScrollHorizontal(true)
```

#### Can Scroll Vertical

```kotlin
fun setCanScrollVertical(canScrollVertical: Boolean)
```

Enables/disables vertical scrolling.

```kotlin
manager.setCanScrollVertical(true)
```

#### Can Scroll Left

```kotlin
fun setCanScrollLeft(canScrollLeft: Boolean)
```

Enables/disables scrolling to the left.

```kotlin
manager.setCanScrollLeft(true)
```

#### Can Scroll Right

```kotlin
fun setCanScrollRight(canScrollRight: Boolean)
```

Enables/disables scrolling to the right.

```kotlin
manager.setCanScrollRight(true)
```

#### Can Scroll Up

```kotlin
fun setCanScrollUp(canScrollUp: Boolean)
```

Enables/disables scrolling up.

```kotlin
manager.setCanScrollUp(true)
```

#### Can Scroll Down

```kotlin
fun setCanScrollDown(canScrollDown: Boolean)
```

Enables/disables scrolling down.

```kotlin
manager.setCanScrollDown(true)
```

#### Swipeable Method

```kotlin
fun setSwipeableMethod(swipeableMethod: SwipeableMethod)
```

Sets the swipeable method.

```kotlin
manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
manager.setSwipeableMethod(SwipeableMethod.Automatic)
manager.setSwipeableMethod(SwipeableMethod.Manual)
manager.setSwipeableMethod(SwipeableMethod.None)
```

#### Swipe Animation Setting

```kotlin
fun setSwipeAnimationSetting(swipeAnimationSetting: SwipeAnimationSetting)
```

Sets the swipe animation configuration.

```kotlin
val setting = SwipeAnimationSetting.Builder()
    .setDirection(Direction.Right)
    .setDuration(Duration.Normal.duration)
    .setInterpolator(AccelerateInterpolator())
    .build()
manager.setSwipeAnimationSetting(setting)
```

#### Rewind Animation Setting

```kotlin
fun setRewindAnimationSetting(rewindAnimationSetting: RewindAnimationSetting)
```

Sets the rewind animation configuration.

```kotlin
val setting = RewindAnimationSetting.Builder()
    .setDirection(Direction.Bottom)
    .setDuration(Duration.Normal.duration)
    .setInterpolator(DecelerateInterpolator())
    .build()
manager.setRewindAnimationSetting(setting)
```

#### Overlay Interpolator

```kotlin
fun setOverlayInterpolator(overlayInterpolator: Interpolator)
```

Sets the interpolator for overlay animations.

```kotlin
manager.setOverlayInterpolator(LinearInterpolator())
manager.setOverlayInterpolator(AccelerateInterpolator())
```

#### Last Item Appearing Animation Duration

```kotlin
fun setLastItemAppearingAnimationDuration(duration: Int)
```

Sets the duration of the fade-in animation for the last card in milliseconds (min: 0).

```kotlin
manager.setLastItemAppearingAnimationDuration(150)
manager.setLastItemAppearingAnimationDuration(0)  // Disable
```

---

## CardStackListener

Interface for event callbacks.

### Methods

#### `onCardDragging(direction: Direction?, ratio: Float)`

Called while the card is being dragged.

```kotlin
override fun onCardDragging(direction: Direction?, ratio: Float) {
    // direction: Direction (Left, Right, Top, Bottom)
    // ratio: Progress from 0.0f to 1.0f
}
```

#### `onCardSwiped(direction: Direction?)`

Called when the card has been swiped.

```kotlin
override fun onCardSwiped(direction: Direction?) {
    // direction: Swipe direction
}
```

#### `onCardRewound()`

Called when a card has been brought back.

```kotlin
override fun onCardRewound() {
    // Rewind event
}
```

#### `onCardCanceled()`

Called when the swipe has been canceled.

```kotlin
override fun onCardCanceled() {
    // Cancel event
}
```

#### `onCardAppeared(view: View?, position: Int)`

Called when a card appears.

```kotlin
override fun onCardAppeared(view: View?, position: Int) {
    // view: Card view
    // position: Adapter position
}
```

#### `onCardDisappeared(view: View?, position: Int)`

Called when a card disappears.

```kotlin
override fun onCardDisappeared(view: View?, position: Int) {
    // view: Card view
    // position: Adapter position
}
```

---

## Enums

### Direction

```kotlin
enum class Direction {
    Left,
    Right,
    Top,
    Bottom;
    
    companion object {
        val HORIZONTAL: List<Direction> = listOf(Left, Right)
        val VERTICAL: List<Direction> = listOf(Top, Bottom)
        val FREEDOM: List<Direction> = listOf(Left, Right, Top, Bottom)
    }
}
```

### StackFrom

```kotlin
enum class StackFrom {
    None,
    Top,
    TopAndLeft,
    TopAndRight,
    Bottom,
    BottomAndLeft,
    BottomAndRight,
    Left,
    Right
}
```

### StackLayout

```kotlin
enum class StackLayout {
    Overlay,  // Cards overlap
    Linear    // Cards are arranged sequentially
}
```

### CardStackStyle

```kotlin
enum class CardStackStyle {
    Stack,     // Classic stack
    Carousel   // Carousel style
}
```

### CarouselOrientation

```kotlin
enum class CarouselOrientation {
    Horizontal,
    Vertical
}
```

### SwipeableMethod

```kotlin
enum class SwipeableMethod {
    AutomaticAndManual,  // Programmatic and manual swipes
    Automatic,           // Programmatic only
    Manual,              // Manual only
    None                 // Disabled
}
```

---

## Builder Classes

### SwipeAnimationSetting.Builder

```kotlin
val setting = SwipeAnimationSetting.Builder()
    .setDirection(Direction.Right)
    .setDuration(Duration.Normal.duration)
    .setInterpolator(AccelerateInterpolator())
    .build()
```

### RewindAnimationSetting.Builder

```kotlin
val setting = RewindAnimationSetting.Builder()
    .setDirection(Direction.Bottom)
    .setDuration(Duration.Normal.duration)
    .setInterpolator(DecelerateInterpolator())
    .build()
```

### CarouselSetting

```kotlin
val carousel = CarouselSetting(
    orientation = CarouselOrientation.Horizontal,
    scaleMultiplier = 0.18f,
    minScale = 0.65f,
    tiltAngle = 8f
)
```

---

## Duration

Predefined duration constants:

```kotlin
Duration.Fast.duration      // Fast
Duration.Normal.duration    // Normal
Duration.Slow.duration      // Slow
```

---

## See Also

- [Getting Started](getting-started.md) - First steps
- [Configuration](configuration.md) - Detailed configuration options
- [Guides](guides/) - Guides for specific features
