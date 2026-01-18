# Basic Usage

This guide explains the basic features of CardStackView: Swipe, Rewind, and Cancel.

## Swipe

### Manual Swipe

Users can swipe cards by dragging in the configured direction:

![ManualSwipe](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-manual-swipe.gif)

Swipe directions are configured via `setDirections()`:

```kotlin
manager.setDirections(Direction.HORIZONTAL)  // Left/Right
manager.setDirections(Direction.VERTICAL)     // Top/Bottom
manager.setDirections(Direction.FREEDOM)      // All directions
```

### Automatic Swipe

You can swipe cards programmatically:

```kotlin
cardStackView.swipe()
```

![AutomaticSwipe](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-automatic-swipe.gif)

### Custom Swipe Animation

You can customize the swipe animation:

```kotlin
val setting = SwipeAnimationSetting.Builder()
    .setDirection(Direction.Right)
    .setDuration(Duration.Normal.duration)
    .setInterpolator(AccelerateInterpolator())
    .build()
manager.setSwipeAnimationSetting(setting)
cardStackView.swipe()
```

See [Custom Animations](custom-animations.md) for more details.

## Cancel

If a card is dragged less than the configured threshold, the swipe is canceled and the card springs back:

![Cancel](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-cancel.gif)

The threshold is configured via `setSwipeThreshold()`:

```kotlin
manager.setSwipeThreshold(0.3f)  // 30% - Default
```

See [Configuration](../configuration.md#swipe-threshold) for more details.

## Rewind

Rewind brings back the last swiped card:

![Rewind](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-rewind.gif)

### Programmatic

```kotlin
cardStackView.rewind()
```

### Custom Rewind Animation

You can customize the rewind animation:

```kotlin
val setting = RewindAnimationSetting.Builder()
    .setDirection(Direction.Bottom)
    .setDuration(Duration.Normal.duration)
    .setInterpolator(DecelerateInterpolator())
    .build()
manager.setRewindAnimationSetting(setting)
cardStackView.rewind()
```

See [Custom Animations](custom-animations.md) for more details.

### Manual Rewind Gestures

You can also configure gestures for rewind. See [Advanced Features](advanced.md#manual-rewind-gestures).

## Swipeable Method

You can control how swiping can be performed:

```kotlin
manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)  // Both
manager.setSwipeableMethod(SwipeableMethod.Automatic)            // Code only
manager.setSwipeableMethod(SwipeableMethod.Manual)              // Gestures only
manager.setSwipeableMethod(SwipeableMethod.None)                // Disabled
```

See [Configuration](../configuration.md#swipeable-method) for more details.

## Next Steps

- [Callbacks](callbacks.md) - React to swipe events
- [Custom Animations](custom-animations.md) - Customize animations
- [Overlay Views](overlays.md) - Add Like/Dislike indicators
