# Getting Started

This guide walks you through the first steps with CardStackView.

## Installation

### Step 1: Add Repository

Add the JitPack repository to your `build.gradle` (project level):

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add Dependency

Add the CardStackView dependency to your `build.gradle` (module level):

```gradle
dependencies {
    implementation 'com.github.jens-muenker:CardStackView:3.2.0'
}
```

## Basic Setup

### Step 1: Define Layout

Add the `CardStackView` to your layout:

```xml
<com.yuyakaido.android.cardstackview.CardStackView
    android:id="@+id/card_stack_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### Step 2: Create Adapter

Create an adapter that extends `RecyclerView.Adapter`. You can also use `ListAdapter` to reduce boilerplate code and get automatic range updates:

```kotlin
class CardStackAdapter : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    
    private var items = listOf<YourDataModel>()
    
    fun setItems(newItems: List<YourDataModel>) {
        items = newItems
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    
    override fun getItemCount() = items.size
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: YourDataModel) {
            // Bind your data to views
        }
    }
}
```

**Tip:** Use `ListAdapter` for less boilerplate and automatic diff updates:

```kotlin
class CardStackAdapter : ListAdapter<YourDataModel, CardStackAdapter.ViewHolder>(
    DiffUtil.ItemCallback<YourDataModel>() {
        override fun areItemsTheSame(oldItem: YourDataModel, newItem: YourDataModel) = 
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: YourDataModel, newItem: YourDataModel) = 
            oldItem == newItem
    }
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
            // Bind your data to views
        }
    }
}
```

### Step 3: Configure CardStackView

In your Activity or Fragment:

```kotlin
class MainActivity : AppCompatActivity() {
    
    private lateinit var cardStackView: CardStackView
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupCardStackView()
    }
    
    private fun setupCardStackView() {
        cardStackView = findViewById(R.id.card_stack_view)
        manager = CardStackLayoutManager(this)
        adapter = CardStackAdapter()
        
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        
        // Initial data
        adapter.setItems(createInitialData())
    }
    
    private fun createInitialData(): List<YourDataModel> {
        // Return your initial data
        return listOf(/* ... */)
    }
}
```

## Next Steps

- [Basic Usage](guides/basic-usage.md) - Learn how to use Swipe and Rewind
- [Configuration](configuration.md) - Customize appearance and behavior
- [Callbacks](guides/callbacks.md) - React to swipe events
- [Overlay Views](guides/overlays.md) - Add Like/Dislike indicators
