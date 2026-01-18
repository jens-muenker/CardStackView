# Overlay Views

Overlay Views are indicators displayed during swiping (e.g., Like/Dislike icons).

## Overview

| Direction | Layout ID | Usage |
|:---------:|:---------:|:------|
| Left | `left_overlay` | Dislike/No |
| Right | `right_overlay` | Like/Yes |
| Top | `top_overlay` | Additional option |
| Bottom | `bottom_overlay` | Additional option |

## Setup

### Step 1: Add Overlay Views to Layout

Add `FrameLayout`s (or other Views) with the exact IDs to your card item layout:

```xml
<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <!-- Your Card Content -->
    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/card_image" />
    
    <!-- Overlay Views -->
    <FrameLayout
        android:id="@+id/left_overlay"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#80FF0000"
        android:visibility="visible">
        
        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:src="@drawable/ic_dislike" />
    </FrameLayout>
    
    <FrameLayout
        android:id="@+id/right_overlay"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#8000FF00"
        android:visibility="visible">
        
        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:src="@drawable/ic_like" />
    </FrameLayout>
</FrameLayout>
```

**Important:**
- IDs must be exact: `left_overlay`, `right_overlay`, `top_overlay`, `bottom_overlay`
- Overlay containers must have `visibility="visible"`
- Let the library control alpha values - don't set them manually

### Step 2: Configure Overlay Interpolator (optional)

You can customize the interpolator for the alpha animation:

```kotlin
manager.setOverlayInterpolator(LinearInterpolator())           // Linear
manager.setOverlayInterpolator(AccelerateInterpolator())       // Accelerating
manager.setOverlayInterpolator(DecelerateInterpolator())       // Decelerating
```

Default is `LinearInterpolator()`.

### Step 3: Enable Directions

Overlays only react to enabled directions:

```kotlin
// For horizontal overlays
manager.setDirections(Direction.HORIZONTAL)

// For vertical overlays
manager.setDirections(Direction.VERTICAL)

// For all overlays
manager.setDirections(Direction.FREEDOM)
```

## Examples

### Like/Dislike Overlays

```xml
<!-- item_card.xml -->
<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <!-- Card Content -->
    <ImageView ... />
    
    <!-- Dislike Overlay (Left) -->
    <FrameLayout
        android:id="@+id/left_overlay"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#80FF0000"
        android:visibility="visible">
        <ImageView
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:layout_gravity="center"
            android:src="@drawable/ic_close_white" />
    </FrameLayout>
    
    <!-- Like Overlay (Right) -->
    <FrameLayout
        android:id="@+id/right_overlay"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#8000FF00"
        android:visibility="visible">
        <ImageView
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:layout_gravity="center"
            android:src="@drawable/ic_favorite_white" />
    </FrameLayout>
</FrameLayout>
```

```kotlin
// In your Activity/Fragment
manager.setDirections(Direction.HORIZONTAL)
manager.setOverlayInterpolator(LinearInterpolator())
```

### Vertical Overlays (Top/Bottom)

```xml
<!-- Top Overlay -->
<FrameLayout
    android:id="@+id/top_overlay"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#800000FF"
    android:visibility="visible">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="Super Like"
        android:textColor="@android:color/white"
        android:textSize="24sp" />
</FrameLayout>

<!-- Bottom Overlay -->
<FrameLayout
    android:id="@+id/bottom_overlay"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#80FFFF00"
    android:visibility="visible">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="Pass"
        android:textColor="@android:color/black"
        android:textSize="24sp" />
</FrameLayout>
```

```kotlin
manager.setDirections(Direction.VERTICAL)
```

## Troubleshooting

### Overlays Not Appearing

If your overlays don't appear, check the following:

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

5. **Alpha is not set manually**: Let the library control alpha values. Don't set them in your adapter or layout.

### Overlays Appear Too Early/Late

Adjust the interpolator:

```kotlin
// Appear earlier
manager.setOverlayInterpolator(AccelerateInterpolator())

// Appear later
manager.setOverlayInterpolator(DecelerateInterpolator())
```

## Best Practices

1. **Semi-transparent backgrounds**: Use semi-transparent colors for better visibility:
   ```xml
   android:background="#80FF0000"  <!-- 50% transparent red -->
   ```

2. **Centered icons**: Center icons/text in overlays:
   ```xml
   android:layout_gravity="center"
   ```

3. **Consistent sizes**: Use consistent sizes for overlay content

## Next Steps

- [Basic Usage](basic-usage.md) - Swipe and Rewind
- [Callbacks](callbacks.md) - React to swipe events
- [Configuration](../configuration.md) - More options
