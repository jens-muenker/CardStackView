# Custom Animations

This guide explains how to customize swipe and rewind animations.

## Swipe Animation

### Default Swipe Animation

The default swipe animation uses the configured direction and a default duration:

```kotlin
cardStackView.swipe()
```

### Custom Swipe Animation

You can fully customize the swipe animation:

```kotlin
val setting = SwipeAnimationSetting.Builder()
    .setDirection(Direction.Right)                    // Direction
    .setDuration(Duration.Normal.duration)            // Duration
    .setInterpolator(AccelerateInterpolator())        // Interpolator
    .build()
manager.setSwipeAnimationSetting(setting)
cardStackView.swipe()
```

### Available Directions

```kotlin
Direction.Left
Direction.Right
Direction.Top
Direction.Bottom
```

### Available Duration Constants

```kotlin
Duration.Fast.duration      // Fast
Duration.Normal.duration   // Normal (default)
Duration.Slow.duration     // Slow
```

### Available Interpolators

```kotlin
LinearInterpolator()           // Linear animation
AccelerateInterpolator()       // Accelerating
DecelerateInterpolator()       // Decelerating
AccelerateDecelerateInterpolator()  // Accelerating and decelerating
OvershootInterpolator()       // With overshoot
AnticipateInterpolator()      // With anticipation
AnticipateOvershootInterpolator()  // With anticipation and overshoot
BounceInterpolator()          // With bounce effect
```

### Example: Fast Swipe to Right

```kotlin
val setting = SwipeAnimationSetting.Builder()
    .setDirection(Direction.Right)
    .setDuration(Duration.Fast.duration)
    .setInterpolator(AccelerateInterpolator())
    .build()
manager.setSwipeAnimationSetting(setting)
cardStackView.swipe()
```

### Example: Slow Swipe to Left with Bounce

```kotlin
val setting = SwipeAnimationSetting.Builder()
    .setDirection(Direction.Left)
    .setDuration(Duration.Slow.duration)
    .setInterpolator(BounceInterpolator())
    .build()
manager.setSwipeAnimationSetting(setting)
cardStackView.swipe()
```

## Rewind Animation

### Default Rewind Animation

The default rewind animation uses a default duration:

```kotlin
cardStackView.rewind()
```

### Custom Rewind Animation

You can fully customize the rewind animation:

```kotlin
val setting = RewindAnimationSetting.Builder()
    .setDirection(Direction.Bottom)                   // Direction
    .setDuration(Duration.Normal.duration)            // Duration
    .setInterpolator(DecelerateInterpolator())        // Interpolator
    .build()
manager.setRewindAnimationSetting(setting)
cardStackView.rewind()
```

### Available Directions

```kotlin
Direction.Left
Direction.Right
Direction.Top
Direction.Bottom
```

### Available Duration Constants

```kotlin
Duration.Fast.duration      // Fast
Duration.Normal.duration   // Normal (default)
Duration.Slow.duration     // Slow
```

### Available Interpolators

See [Swipe Animation](#available-interpolators) for the list of interpolators.

### Example: Fast Rewind from Bottom

```kotlin
val setting = RewindAnimationSetting.Builder()
    .setDirection(Direction.Bottom)
    .setDuration(Duration.Fast.duration)
    .setInterpolator(DecelerateInterpolator())
    .build()
manager.setRewindAnimationSetting(setting)
cardStackView.rewind()
```

### Example: Slow Rewind from Top with Bounce

```kotlin
val setting = RewindAnimationSetting.Builder()
    .setDirection(Direction.Top)
    .setDuration(Duration.Slow.duration)
    .setInterpolator(BounceInterpolator())
    .build()
manager.setRewindAnimationSetting(setting)
cardStackView.rewind()
```

## Dynamic Animations

You can change animation settings at runtime:

```kotlin
// For Like button: Swipe to Right
likeButton.setOnClickListener {
    val setting = SwipeAnimationSetting.Builder()
        .setDirection(Direction.Right)
        .setDuration(Duration.Normal.duration)
        .setInterpolator(AccelerateInterpolator())
        .build()
    manager.setSwipeAnimationSetting(setting)
    cardStackView.swipe()
}

// For Dislike button: Swipe to Left
dislikeButton.setOnClickListener {
    val setting = SwipeAnimationSetting.Builder()
        .setDirection(Direction.Left)
        .setDuration(Duration.Normal.duration)
        .setInterpolator(AccelerateInterpolator())
        .build()
    manager.setSwipeAnimationSetting(setting)
    cardStackView.swipe()
}
```

## Best Practices

1. **Consistency**: Use consistent animation settings for similar actions
2. **Performance**: Avoid overly complex interpolators with many simultaneous animations
3. **UX**: Choose duration and interpolator based on the action's importance (e.g., faster for "Skip", slower for "Like")

## Next Steps

- [Basic Usage](basic-usage.md) - More basics
- [Callbacks](callbacks.md) - React to animation events
- [Configuration](../configuration.md) - More configuration options
