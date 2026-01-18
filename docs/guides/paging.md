# Paging

This guide explains how to implement dynamic data loading (paging).

## Overview

Paging allows you to load data incrementally as the user swipes through cards. This is especially useful for large datasets or when loading data from an API.

## Implementation

### Method 1: With DiffUtil (Recommended)

`DiffUtil` is the recommended method as it automatically calculates differences between old and new data and only performs necessary updates:

```kotlin
class CardStackAdapter : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    
    private var items = listOf<YourDataModel>()
    
    fun setItems(newItems: List<YourDataModel>) {
        val oldItems = items
        items = newItems
        
        val callback = YourDiffCallback(oldItems, newItems)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)
    }
    
    // ... Rest of adapter implementation
}

class YourDiffCallback(
    private val oldList: List<YourDataModel>,
    private val newList: List<YourDataModel>
) : DiffUtil.Callback() {
    
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }
    
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
```

### Method 2: With ListAdapter

`ListAdapter` uses `DiffUtil` internally and reduces boilerplate:

```kotlin
class CardStackAdapter : ListAdapter<YourDataModel, CardStackAdapter.ViewHolder>(
    YourDiffItemCallback()
) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: YourDataModel) {
            // Bind data
        }
    }
}

class YourDiffItemCallback : DiffUtil.ItemCallback<YourDataModel>() {
    override fun areItemsTheSame(oldItem: YourDataModel, newItem: YourDataModel) =
        oldItem.id == newItem.id
    
    override fun areContentsTheSame(oldItem: YourDataModel, newItem: YourDataModel) =
        oldItem == newItem
}
```

Then simply:

```kotlin
adapter.submitList(newItems)
```

### Method 3: Manually with notifyItemRangeInserted

If you need more control:

```kotlin
fun addItems(newItems: List<YourDataModel>) {
    val startPosition = items.size
    items = items + newItems
    notifyItemRangeInserted(startPosition, newItems.size)
}
```

## Paging with Callbacks

Implement `CardStackListener` and load new data when the user approaches the end:

```kotlin
class MainActivity : AppCompatActivity(), CardStackListener {
    
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter
    private var isLoading = false
    
    override fun onCardSwiped(direction: Direction?) {
        val currentPosition = manager.topPosition
        val totalItems = adapter.itemCount
        
        // Load more data when 5 cards before the end
        if (currentPosition >= totalItems - 5 && !isLoading) {
            loadMoreData()
        }
    }
    
    private fun loadMoreData() {
        isLoading = true
        
        // Load data (e.g., from API)
        loadDataFromApi { newItems ->
            // Add new items
            val currentItems = adapter.getItems()
            adapter.setItems(currentItems + newItems)
            isLoading = false
        }
    }
}
```

## Example: Complete Paging Implementation

```kotlin
class MainActivity : AppCompatActivity(), CardStackListener {
    
    private lateinit var cardStackView: CardStackView
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter
    private var isLoading = false
    private var currentPage = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupCardStackView()
        loadInitialData()
    }
    
    private fun setupCardStackView() {
        cardStackView = findViewById(R.id.card_stack_view)
        manager = CardStackLayoutManager(this, this)
        adapter = CardStackAdapter()
        
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
    }
    
    private fun loadInitialData() {
        loadData(0) { items ->
            adapter.setItems(items)
            currentPage = 1
        }
    }
    
    override fun onCardSwiped(direction: Direction?) {
        val currentPosition = manager.topPosition
        val totalItems = adapter.itemCount
        
        // Load more data when 5 cards before the end
        if (currentPosition >= totalItems - 5 && !isLoading) {
            loadMoreData()
        }
    }
    
    private fun loadMoreData() {
        if (isLoading) return
        
        isLoading = true
        loadData(currentPage) { newItems ->
            val currentItems = adapter.getItems()
            adapter.setItems(currentItems + newItems)
            currentPage++
            isLoading = false
        }
    }
    
    private fun loadData(page: Int, callback: (List<YourDataModel>) -> Unit) {
        // Simulate API call
        viewModelScope.launch {
            val items = repository.loadPage(page)
            callback(items)
        }
    }
    
    // ... other callback methods
}
```

## Important Notes

### ❌ Don't Use: notifyDataSetChanged

**Avoid** `notifyDataSetChanged()` for paging:

```kotlin
// ❌ BAD - Resets position
adapter.notifyDataSetChanged()
```

**Why?**
- Resets the `topPosition`
- Can cause performance issues
- Leads to unexpected behavior

### ✅ Use: DiffUtil or notifyItemRangeInserted

```kotlin
// ✅ GOOD - With DiffUtil
adapter.setItems(newItems)

// ✅ GOOD - Manually
adapter.notifyItemRangeInserted(startPosition, count)
```

## Best Practices

1. **Proactive Loading**: Load data before the user reaches the end (e.g., 5 cards before the end)
2. **Loading States**: Show a loading indicator while loading
3. **Error Handling**: Handle errors when loading data
4. **Caching**: Cache loaded data to avoid unnecessary API calls

## Example with Loading State

```kotlin
override fun onCardSwiped(direction: Direction?) {
    val currentPosition = manager.topPosition
    val totalItems = adapter.itemCount
    
    if (currentPosition >= totalItems - 5 && !isLoading) {
        showLoadingIndicator()
        loadMoreData()
    }
}

private fun loadMoreData() {
    isLoading = true
    loadData(currentPage) { newItems ->
        val currentItems = adapter.getItems()
        adapter.setItems(currentItems + newItems)
        currentPage++
        isLoading = false
        hideLoadingIndicator()
    }
}
```

## Next Steps

- [Callbacks](callbacks.md) - More details on event handling
- [Basic Usage](basic-usage.md) - Swipe and Rewind
- [Configuration](../configuration.md) - More options
