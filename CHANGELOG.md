# Changelog

All notable changes to this project will be documented in this file.

## [3.2.0]

### Added
- Manual rewind gesture support - configure gestures to bring back previous cards ([#294](https://github.com/yuyakaido/CardStackView/issues/294), [PR #10](https://github.com/jens-muenker/CardStackView/pull/10))
- Per-direction swipe control - enable/disable swiping for individual directions (Left, Right, Up, Down) ([#260](https://github.com/yuyakaido/CardStackView/issues/260), [PR #9](https://github.com/jens-muenker/CardStackView/pull/9))
- Carousel card stack style - carousel-inspired presentation with curves and tilt ([#310](https://github.com/yuyakaido/CardStackView/issues/310), [PR #8](https://github.com/jens-muenker/CardStackView/pull/8))
- StackLayout enum for linear stacking - sequential card arrangement option ([PR #6](https://github.com/jens-muenker/CardStackView/pull/6))
- Configurable fade-in animation for the last card when new data appears ([PR #11](https://github.com/jens-muenker/CardStackView/pull/11))
- Complete documentation overhaul with comprehensive guides
- Getting Started guide with step-by-step instructions
- Complete configuration documentation
- Detailed guides for all features (Basic Usage, Custom Animations, Overlays, Paging, Callbacks, Advanced Features)
- Comprehensive API reference
- Extensive FAQ section
- FAQ section for custom CardStackListener swipe control ([#381](https://github.com/yuyakaido/CardStackView/issues/381), [PR #3](https://github.com/jens-muenker/CardStackView/pull/3))
- Overlay troubleshooting guide ([#383](https://github.com/yuyakaido/CardStackView/issues/383), [PR #2](https://github.com/jens-muenker/CardStackView/pull/2))

### Improved
- Restructured documentation with detailed guides in `docs/` directory
- Improved README with better structure and navigation
- All documentation translated to English
- Code quality and structure

### Fixed
- Division by zero in ratio calculation ([#387](https://github.com/yuyakaido/CardStackView/issues/387), [PR #4](https://github.com/jens-muenker/CardStackView/pull/4))

## [3.1.0]

### Added
- Configurable fade-in animation for the last card when new data appears
- Unit tests for better code quality and stability

### Improved
- Code quality and structure
- Documentation (README and detailed docs)

### Updated
- Gradle to latest version
- Dependencies to latest versions

## [3.0.0]

### Changed
- Complete migration to Kotlin
- Code structure refactored

### Updated
- Gradle to latest version
- Dependencies to latest versions

### Fixed
- Various bugs from the original repository

---

**Note:** This project is a fork of [yuyakaido/CardStackView](https://github.com/yuyakaido/CardStackView). All changes before version 3.0.0 are part of the original project.
