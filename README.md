![Logo](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-logo.png)

[![](https://jitpack.io/v/jens-muenker/CardStackView.svg)](https://jitpack.io/#jens-muenker/CardStackView)
[![](https://jitpack.io/v/jens-muenker/CardStackView/month.svg)](https://jitpack.io/#jens-muenker/CardStackView)

# CardStackView

A modern Android library for swipeable card stacks with extensive customization options. This library is a fork of [yuyakaido/CardStackView](https://github.com/yuyakaido/CardStackView) with bug fixes, Kotlin migration, and enhanced features.

![Example](https://github.com/yuyakaido/images/blob/master/CardStackView/sample-overview.gif)

## ✨ Features

- 🎯 **Easy Integration** - Based on RecyclerView
- 👆 **Manual & Automatic Swiping** - Flexible control via gestures or programmatic code
- 🔄 **Rewind Function** - Bring cards back with animation
- 🎨 **Versatile Customization** - Layouts, animations, overlays, styles
- 📱 **Carousel & Stack Styles** - Different display modes (Overlay, Linear, Carousel)
- 🎭 **Overlay Views** - Like/Dislike indicators during swiping
- ⚙️ **Extensive Configuration** - All aspects customizable (thresholds, directions, animations)
- 🔧 **Kotlin-first** - Fully written in Kotlin

[→ All Features in Detail](docs/configuration.md)

## 🚀 Quick Start

### Installation

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.jens-muenker:CardStackView:3.1.0'
}
```

### Minimal Example

**Layout (XML):**
```xml
<com.yuyakaido.android.cardstackview.CardStackView
    android:id="@+id/card_stack_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

**Code (Kotlin):**
```kotlin
val cardStackView = findViewById<CardStackView>(R.id.card_stack_view)
val manager = CardStackLayoutManager(this)
cardStackView.layoutManager = manager
cardStackView.adapter = YourAdapter()
```

[→ Detailed Getting Started Guide](docs/getting-started.md)

## 📚 Documentation

### Basics
- [Getting Started](docs/getting-started.md) - First steps and installation
- [Configuration](docs/configuration.md) - All available settings and options

### Guides
- [Basic Usage](docs/guides/basic-usage.md) - Swipe, Rewind, Cancel
- [Custom Animations](docs/guides/custom-animations.md) - Customize swipe and rewind animations
- [Overlay Views](docs/guides/overlays.md) - Implement Like/Dislike indicators
- [Paging](docs/guides/paging.md) - Dynamic data loading
- [Callbacks](docs/guides/callbacks.md) - Event handling with CardStackListener
- [Advanced Features](docs/guides/advanced.md) - Manual Rewind, Carousel Style, etc.

### Reference
- [API Reference](docs/api-reference.md) - Complete API documentation
- [FAQ](docs/faq.md) - Frequently asked questions and solutions

## 🎯 Main Features Overview

| Feature | Description | Link |
|---------|-------------|------|
| **Swipe** | Manual & automatic swiping in all directions | [→](docs/guides/basic-usage.md#swipe) |
| **Rewind** | Bring cards back with animation | [→](docs/guides/basic-usage.md#rewind) |
| **Overlays** | Like/Dislike indicators during swiping | [→](docs/guides/overlays.md) |
| **Animations** | Custom Swipe/Rewind animations | [→](docs/guides/custom-animations.md) |
| **Stack Styles** | Overlay, Linear, Carousel | [→](docs/configuration.md#stack-layout) |
| **Paging** | Dynamic data loading | [→](docs/guides/paging.md) |
| **Callbacks** | Event handling for all actions | [→](docs/guides/callbacks.md) |

## 💡 Support

If this library is helpful to you or your apps, please consider supporting its development:

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://buymeacoffee.com/jens.muenker)

## 📝 Changelog

### 3.1.0
- Improved code quality
- Added unit tests
- Updated Gradle & dependencies
- Configurable fade-in animation for last card

### 3.0.0
- Updated Gradle & dependencies
- Migrated code to Kotlin

[→ Full Changelog](CHANGELOG.md)

## 📄 License

Apache License 2.0 - See [LICENSE](LICENSE) for details.

```
Copyright 2025, Jens Münker

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

**Original CardStackView:**
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

---

**Forked from** [yuyakaido/CardStackView](https://github.com/yuyakaido/CardStackView) | **Developed by** Jens Münker
