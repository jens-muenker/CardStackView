# Callbacks

This guide explains how to react to events with `CardStackListener`.

## Overview

`CardStackListener` provides callbacks for all important events:

| Callback | When is it called? |
|----------|-------------------|
| `onCardDragging` | While the card is being dragged |
| `onCardSwiped` | When the card has been swiped |
| `onCardRewound` | When a card has been brought back |
| `onCardCanceled` | When the swipe has been canceled |
| `onCardAppeared` | When a card appears |
| `onCardDisappeared` | When a card disappears |

## Setup

### Step 1: Implement Listener

Implement `CardStackListener` in your Activity or Fragment:

```kotlin
class MainActivity : AppCompatActivity(), CardStackListener {
    
    override fun onCardDragging(direction: Direction?, ratio: Float) {
        // Called while dragging
    }
    
    override fun onCardSwiped(direction: Direction?) {
        // Called when the card has been swiped
    }
    
    override fun onCardRewound() {
        // Called when a card has been brought back
    }
    
    override fun onCardCanceled() {
        // Called when the swipe has been canceled
    }
    
    override fun onCardAppeared(view: View?, position: Int) {
        // Called when a card appears
    }
    
    override fun onCardDisappeared(view: View?, position: Int) {
        // Called when a card disappears
    }
}
```

### Step 2: Pass Listener to Manager

Pass the listener when creating the manager:

```kotlin
val manager = CardStackLayoutManager(this, this)  // this = CardStackListener
cardStackView.layoutManager = manager
```

Or set it later:

```kotlin
manager.cardStackListener = this
```

## Callback Details

### onCardDragging

Called continuously while dragging:

```kotlin
override fun onCardDragging(direction: Direction?, ratio: Float) {
    // direction: The direction being dragged (Left, Right, Top, Bottom)
    // ratio: Progress from 0.0f (start) to 1.0f (threshold reached)
    
    when (direction) {
        Direction.Left -> {
            // Show dislike indicator based on ratio
            updateDislikeIndicator(ratio)
        }
        Direction.Right -> {
            // Show like indicator based on ratio
            updateLikeIndicator(ratio)
        }
        else -> {}
    }
}
```

**Usage:**
- Update UI elements based on swipe progress
- Show/hide overlays dynamically
- Play sound effects

### onCardSwiped

Called when a card has been successfully swiped:

```kotlin
override fun onCardSwiped(direction: Direction?) {
    val currentPosition = manager.topPosition
    val totalItems = adapter.itemCount
    
    when (direction) {
        Direction.Right -> {
            // Like action
            handleLike(currentPosition)
        }
        Direction.Left -> {
            // Dislike action
            handleDislike(currentPosition)
        }
        Direction.Top -> {
            // Super Like action
            handleSuperLike(currentPosition)
        }
        Direction.Bottom -> {
            // Pass action
            handlePass(currentPosition)
        }
        null -> {}
    }
    
    // Paging: Load more data if needed
    if (currentPosition >= totalItems - 5) {
        loadMoreData()
    }
}
```

**Usage:**
- Perform actions based on swipe direction
- Save preferences
- Load more data (paging)
- Update backend

### onCardRewound

Called when a card has been brought back:

```kotlin
override fun onCardRewound() {
    val currentPosition = manager.topPosition
    
    // Undo last action
    undoLastAction(currentPosition)
    
    Log.d("CardStack", "Card at position $currentPosition was rewound")
}
```

**Usage:**
- Undo last action
- Update statistics
- Logging

### onCardCanceled

Called when the swipe has been canceled (card was not dragged beyond threshold):

```kotlin
override fun onCardCanceled() {
    val currentPosition = manager.topPosition
    
    // Reset UI elements
    resetIndicators()
    
    Log.d("CardStack", "Swipe canceled at position $currentPosition")
}
```

**Usage:**
- Reset UI elements
- Hide temporary overlays
- Logging

### onCardAppeared

Called when a card appears (e.g., after swipe or rewind):

```kotlin
override fun onCardAppeared(view: View?, position: Int) {
    // Load data for the new card
    val item = adapter.getItem(position)
    loadCardData(item)
    
    // Update UI
    updateCardInfo(item)
    
    Log.d("CardStack", "Card appeared at position $position")
}
```

**Usage:**
- Load additional data for the card
- Update UI elements
- Analytics tracking

### onCardDisappeared

Called when a card disappears (e.g., after swipe):

```kotlin
override fun onCardDisappeared(view: View?, position: Int) {
    val item = adapter.getItem(position)
    
    // Cleanup
    cleanupCardData(item)
    
    Log.d("CardStack", "Card disappeared at position $position")
}
```

**Usage:**
- Cleanup resources
- Stop running animations
- Analytics tracking

## Complete Example

```kotlin
class MainActivity : AppCompatActivity(), CardStackListener {
    
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupCardStackView()
    }
    
    private fun setupCardStackView() {
        val cardStackView = findViewById<CardStackView>(R.id.card_stack_view)
        manager = CardStackLayoutManager(this, this)
        adapter = CardStackAdapter()
        
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
    }
    
    override fun onCardDragging(direction: Direction?, ratio: Float) {
        when (direction) {
            Direction.Right -> updateLikeIndicator(ratio)
            Direction.Left -> updateDislikeIndicator(ratio)
            else -> {}
        }
    }
    
    override fun onCardSwiped(direction: Direction?) {
        val position = manager.topPosition
        
        when (direction) {
            Direction.Right -> handleLike(position)
            Direction.Left -> handleDislike(position)
            else -> {}
        }
        
        // Paging
        if (position >= adapter.itemCount - 5) {
            loadMoreData()
        }
    }
    
    override fun onCardRewound() {
        undoLastAction(manager.topPosition)
    }
    
    override fun onCardCanceled() {
        resetIndicators()
    }
    
    override fun onCardAppeared(view: View?, position: Int) {
        val item = adapter.getItem(position)
        updateCardInfo(item)
    }
    
    override fun onCardDisappeared(view: View?, position: Int) {
        // Cleanup if needed
    }
    
    private fun updateLikeIndicator(ratio: Float) {
        // Update UI
    }
    
    private fun updateDislikeIndicator(ratio: Float) {
        // Update UI
    }
    
    private fun handleLike(position: Int) {
        // Handle like action
    }
    
    private fun handleDislike(position: Int) {
        // Handle dislike action
    }
    
    private fun undoLastAction(position: Int) {
        // Undo action
    }
    
    private fun resetIndicators() {
        // Reset UI
    }
    
    private fun updateCardInfo(item: YourDataModel) {
        // Update UI
    }
    
    private fun loadMoreData() {
        // Load more data
    }
}
```

## Custom Listener

You can also create a separate listener:

```kotlin
val listener = object : CardStackListener {
    override fun onCardDragging(direction: Direction?, ratio: Float) {}
    override fun onCardSwiped(direction: Direction?) {}
    override fun onCardRewound() {}
    override fun onCardCanceled() {}
    override fun onCardAppeared(view: View?, position: Int) {}
    override fun onCardDisappeared(view: View?, position: Int) {}
}

val manager = CardStackLayoutManager(this, listener)
```

## Best Practices

1. **Performance**: Avoid heavy operations in `onCardDragging` (called very frequently)
2. **UI Updates**: Perform UI updates in `onCardDragging` to provide visual feedback
3. **Business Logic**: Perform business logic in `onCardSwiped`, not in `onCardDragging`
4. **Cleanup**: Use `onCardDisappeared` for cleanup operations

## Next Steps

- [Basic Usage](basic-usage.md) - Swipe and Rewind
- [Paging](paging.md) - Dynamic data loading
- [Overlay Views](overlays.md) - Like/Dislike indicators
