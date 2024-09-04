![Logo](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-logo.png)

[![](https://jitpack.io/v/jens-muenker/CardStackView.svg)](https://jitpack.io/#jens-muenker/CardStackView) [![](https://jitpack.io/v/jens-muenker/CardStackView/month.svg)](https://jitpack.io/#jens-muenker/CardStackView)

# Overview

This repository is a fork of <a href="https://github.com/yuyakaido/CardStackView">CardStackView</a>. I fixed some bugs, updated the dependencies and gradle, and converted the code to Kotlin. In addition, in the changelogs you can see all other features I added.

![Example](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-overview.gif)

# Usage

1. Include the library as a local library project in your build.gradle:

    ```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	...
	
	dependencies {
	        implementation 'com.github.jens-muenker:CardStackView:3.0.0'
	}
    ```


2. Add a [CardStackView](cardstackview/src/main/java/com/yuyakaido/android/cardstackview/CardStackView.java) to your layout
	```xml
	<com.yuyakaido.android.cardstackview.CardStackView
	            android:id="@+id/card_stack_view"
	            android:layout_width="match_parent"
	            android:layout_height="0dp"
	            android:layout_weight="1" />
	```

3. Implement an adapter for your stack view. This should be a ([RecyclerView.Adapter](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter)). There is a [Sample CardStackAdapter](sample/src/main/java/com/yuyakaido/android/cardstackview/sample/CardStackAdapter.kt) in this project. You may also want to use a [ListAdapter](https://developer.android.com/reference/android/support/v7/recyclerview/extensions/ListAdapter) for less boilerplate code, and automatically handled range updates (especially helpful if your data updates as the user interacts).

4. Wire up your view with your Adapter:
	```kotlin
	val cardStackView = findViewById<CardStackView>(R.id.card_stack_view)
	cardStackView.layoutManager = CardStackLayoutManager()
	cardStackView.adapter = CardStackAdapter()
	```

# Features

## Manual Swipe

![ManualSwipe](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-manual-swipe.gif)

## Automatic Swipe

![AutomaticSwipe](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-automatic-swipe.gif)

```kotlin
CardStackView.swipe()
```

You can set custom swipe animation.

```kotlin
val setting = SwipeAnimationSetting.Builder()
        .setDirection(Direction.Right)
        .setDuration(Duration.Normal.duration)
        .setInterpolator(AccelerateInterpolator())
        .build()
CardStackLayoutManager.setSwipeAnimationSetting(setting)
CardStackView.swipe()
```

## Cancel

Manual swipe is canceled when the card is dragged less than threshold.

![Cancel](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-cancel.gif)

## Rewind

![Rewind](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-rewind.gif)

```kotlin
CardStackView.rewind()
```

You can set custom rewind animation.

```kotlin
val setting = RewindAnimationSetting.Builder()
        .setDirection(Direction.Bottom)
        .setDuration(Duration.Normal.duration)
        .setInterpolator(DecelerateInterpolator())
        .build()
CardStackLayoutManager.setRewindAnimationSetting(setting)
CardStackView.rewind()
```

## Overlay View

| Value | Sample |
| :----: | :----: |
| Left | ![Overlay-Left](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-overlay-left.png) |
| Right | ![Overlay-Right](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-overlay-right.png) |

Put overlay view in your item layout of RecyclerView.

```xml
<FrameLayout
    android:id="@+id/left_overlay"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Set your left overlay -->

</FrameLayout>
```

| Value | Layout ID |
| :----: | :----: |
| Left | left_overlay |
| Right | right_overlay |
| Top | top_overlay |
| Bottom | bottom_overlay |

## Overlay Interpolator

You can set own interpolator to define the rate of change of alpha.

```kotlin
CardStackLayoutManager.setOverlayInterpolator(LinearInterpolator())
```

## Paging

You can implement paging by using following two ways.

1. Use [DiffUtil](https://developer.android.com/reference/android/support/v7/util/DiffUtil).
2. Call [RecyclerView.Adapter.notifyItemRangeInserted](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter#notifyItemRangeInserted(int,%20int)) manually.

**Caution**

You should **NOT** call `RecyclerView.Adapter.notifyDataSetChanged` for paging because this method will reset top position and maybe occur a perfomance issue.

## Reloading

You can implement reloading by calling `RecyclerView.Adapter.notifyDataSetChanged`.

## Stack From

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | None | ![StackFrom-None](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-stack-from-none.png) |
|  | Top | ![StackFrom-Top](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-stack-from-top.png) |
| | Bottom | ![StackFrom-Bottom](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-stack-from-bottom.png) |
| | Left | ![StackFrom-Left](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-stack-from-left.png) |
| | Right | ![StackFrom-Right](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-stack-from-right.png) |

```kotlin
CardStackLayoutManager.setStackFrom(StackFrom.None)
```

## Visible Count

| Default | Value | Sample |
| :----: | :----: | :----: |
| | 2 | ![VisibleCount-2](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-visible-count-2.png) |
| ✅ | 3 | ![VisibleCount-3](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-visible-count-3.png) |
| | 4 | ![VisibleCount-4](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-visible-count-4.png) |

```kotlin
CardStackLayoutManager.setVisibleCount(3)
```

## Translation Interval

| Default | Value | Sample |
| :----: | :----: | :----: |
| | 4dp | ![TranslationInterval-4dp](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-translation-interval-4dp.png) |
| ✅ | 8dp | ![TranslationInterval-8dp](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-translation-interval-8dp.png) |
| | 12dp | ![TranslationInterval-12dp](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-translation-interval-12dp.png) |

```kotlin
CardStackLayoutManager.setTranslationInterval(8.0f)
```

## Scale Interval

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | 95% | ![ScaleInterval-95%](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-scale-interval-95.png) |
| | 90% | ![ScaleInterval-90%](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-scale-interval-90.png) |

```kotlin
CardStackLayoutManager.setScaleInterval(0.95f)
```

## Max Degree

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | 20° | ![MaxDegree-20](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-max-degree-20.png) |
| | 0° | ![MaxDegree-0](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-max-degree-0.png) |

```kotlin
CardStackLayoutManager.setMaxDegree(20.0f)
```

## Swipe Direction

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | Horizontal | ![SwipeDirection-Horizontal](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipe-direction-horizontal.gif) |
| | Vertical | ![SwipeDirection-Vertical](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipe-direction-vertical.gif) |
| | Freedom | ![SwipeDirection-Freedom](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipe-direction-freedom.gif) |

```kotlin
CardStackLayoutManager.setDirections(Direction.HORIZONTAL)
```

## Swipe Threshold

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | 30% | ![SwipeThreshold-30%](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipe-threshold-30.gif) |
| | 10% | ![SwipeThreshold-10%](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipe-threshold-10.gif) |

```kotlin
CardStackLayoutManager.setSwipeThreshold(0.3f)
```

## Swipe Restriction

| CanScrollHorizontal | CanScrollVertical | Sample |
| :----: | :----: | :----: |
| true | true | ![SwipeRestriction-NoRestriction](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipe-restriction-no-restriction.gif) |
| true | false | ![SwipeRestriction-CanScrollHorizontalOnly](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipe-restriction-can-scroll-horizontal-only.gif) |
| false | true | ![SwipeRestriction-CanScrollVerticalOnly](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipe-restriction-can-scroll-vertical-only.gif) |
| false | false | ![SwipeRestriction-CannotSwipe](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipe-restriction-cannot-swipe.gif) |

```kotlin
CardStackLayoutManager.setCanScrollHorizontal(true)
CardStackLayoutManager.setCanScrollVertical(true)
```

## Swipeable Method

| Default | Value | Sample |
| :----: | :----: | :----: |
| ✅ | AutomaticAndManual | ![SwipeableMethod-AutomaticAndManual](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipeable-method-automatic-and-manual.gif) |
| | Automatic | ![SwipwableMethod-Automatic](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipeable-method-automatic.gif) |
| | Manual | ![SwipwableMethod-Manual](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipeable-method-manual.gif) |
| | None | ![SwipwableMethod-None](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-swipeable-method-none.gif) |

```kotlin
CardStackLayoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
```

# Public Interfaces

## Basic usages

| Method | Description |
| :---- | :---- |
| CardStackView.swipe() | You can swipe once by calling this method. |
| CardStackView.rewind() | You can rewind once by calling this method. |
| CardStackLayoutManager.getTopPosition() | You can get position displayed on top. |
| CardStackLayoutManager.setStackFrom(StackFrom stackFrom) | You can set StackFrom. |
| CardStackLayoutManager.setTranslationInterval(float translationInterval) | You can set TranslationInterval. |
| CardStackLayoutManager.setScaleInterval(float scaleInterval) | You can set ScaleInterval. |
| CardStackLayoutManager.setSwipeThreshold(float swipeThreshold) | You can set SwipeThreshold. |
| CardStackLayoutManager.setMaxDegree(float maxDegree) | You can set MaxDegree. |
| CardStackLayoutManager.setDirections(List<Direction> directions) | You can set Direction. |
| CardStackLayoutManager.setCanScrollHorizontal(boolean canScrollHorizontal) | You can set CanScrollHorizontal. |
| CardStackLayoutManager.setCanScrollVertical(boolean canScrollVertical) | You can set CanScrollVertical. |
| CardStackLayoutManager.setSwipeAnimationSetting(SwipeAnimationSetting swipeAnimationSetting) | You can set SwipeAnimationSetting. |
| CardStackLayoutManager.setRewindAnimationSetting(RewindAnimationSetting rewindAnimationSetting) | You can set RewindAnimationSetting. |

## Advanced usages

| Method | Description |
| :---- | :---- |
| CardStackView.smoothScrollToPosition(int position) | You can scroll any position with animation. |
| CardStackView.scrollToPosition(int position) | You can scroll any position without animation. |

# Callbacks

| Method | Description |
| :---- | :---- |
| CardStackListener.onCardDragging(Direction direction, float ratio) | This method is called while the card is dragging. |
| CardStackListener.onCardSwiped(Direction direction) | This method is called when the card is swiped. |
| CardStackListener.onCardRewound() | This method is called when the card is rewinded. |
| CardStackListener.onCardCanceled() | This method is called when the card is dragged less than threshold. |
| CardStackListener.onCardAppeared(View view, int position) | This method is called when the card appeared. |
| CardStackListener.onCardDisappeared(View view, int position) | This method is called when the card disappeared. |

# Changelog

**3.0.0** - Based on <a href="https://github.com/krokyze/uCrop-n-Edit">uCrop'n'Edit</a> 2.2.8 and <a href="https://github.com/Yalantis/uCrop">uCrop</a> 2.2.8:
- updated to newest gradle version
- updated libarys to newest versions
- translated code to kotlin

# License

This software is licensed under the Apache License, Version 2.0. See the <a href="https://www.apache.org/licenses/LICENSE-2.0">LICENSE</a> file for details.

    Copyright 2024, Jens Münker

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

For <a href="https://github.com/yuyakaido/CardStackView">CardStackView</a>:

```
Copyright 2018 yuyakaido

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
