# klar - TODO List

## 🔴 Critical (Must Fix)

### Fix App Crash on Answer Click
- [ ] Investigate crash logs when user clicks answer in lesson
- [ ] Debug LessonViewModel state management
- [ ] Test fix on device
- [ ] Verify all exercise types work correctly

### Implement App Icon & Splash Screen
- [ ] Use `/assets/icon.png` for app launcher icon
- [ ] Create adaptive icon (foreground + background)
- [ ] Implement splash screen with `/assets/splash.png`
- [ ] Update AndroidManifest.xml with proper icon references
- [ ] Test on different Android versions

## 🟡 High Priority

### Audio Implementation
- [ ] Implement LISTEN_AND_TYPE exercise type
- [ ] Add audio playback for German phrases
- [ ] Record/source audio for all exercises
- [ ] Add audio player controls (play, pause, replay)
- [ ] Add volume indicators

### Additional Exercise Types
- [ ] Fill-in-the-blank exercises
- [ ] Matching exercises (German to English)
- [ ] Pronunciation practice with recording
- [ ] Conjugation drills

### User Progress & Persistence
- [ ] Implement Room database for lesson progress
- [ ] Save completed lessons locally
- [ ] Track XP across sessions
- [ ] Add streak counter
- [ ] Implement level unlocking system

### Settings Screen
- [ ] Dark mode toggle (already industrial, but add pure black option)
- [ ] Audio settings (volume, speed)
- [ ] Haptic feedback toggle
- [ ] Animation intensity settings
- [ ] Clear progress option

## 🟢 Medium Priority

### UI/UX Enhancements
- [ ] Add loading animations for lesson transitions
- [ ] Implement lesson completion screen with stats
- [ ] Add achievement badges (industrial themed)
- [ ] Create level-up celebration animation
- [ ] Add tooltip system for first-time users

### Content Expansion
- [ ] Add more A1 level lessons (currently 3)
- [ ] Create A2 level content
- [ ] Add vocabulary practice mode
- [ ] Implement daily challenges
- [ ] Add grammar explanations

### Performance Optimization
- [ ] Profile app performance
- [ ] Optimize Compose recompositions
- [ ] Reduce APK size
- [ ] Implement lazy loading for lessons
- [ ] Add ProGuard rules for release builds

### Testing
- [ ] Add unit tests for ViewModels
- [ ] Add unit tests for repository
- [ ] Add UI tests for critical paths
- [ ] Test on low-end devices (min SDK 24)
- [ ] Test on tablets and foldables

## 🔵 Low Priority / Future Enhancements

### Social Features
- [ ] Add user profiles
- [ ] Leaderboards for XP
- [ ] Share progress on social media
- [ ] Challenge friends system

### Advanced Features
- [ ] Offline mode with all content
- [ ] Text-to-speech for pronunciation help
- [ ] Speech recognition for pronunciation practice
- [ ] Spaced repetition system
- [ ] Flashcard mode

### Platform Expansion
- [ ] iOS version (KMP already supports it)
- [ ] Web version with Compose for Web
- [ ] Desktop version (Windows/Mac/Linux)

### Backend Integration
- [ ] User authentication
- [ ] Cloud sync for progress
- [ ] Analytics integration
- [ ] Push notifications for streak reminders
- [ ] Dynamic content updates

### Monetization (If Applicable)
- [ ] In-app purchases for premium content
- [ ] Ad integration (non-intrusive)
- [ ] Subscription model research
- [ ] Premium features planning

## 📝 Technical Debt

### Architecture Improvements
- [ ] Migrate to AGP 9.0 compatible structure (per gradle warnings)
- [ ] Add ViewModelProvider for proper lifecycle management
- [ ] Implement proper navigation args with Voyager
- [ ] Add analytics/crash reporting

### Code Quality
- [ ] Add KDoc documentation for public APIs
- [ ] Set up CI/CD with GitHub Actions
- [ ] Add pre-commit hooks (ktlint, detekt)
- [ ] Create contribution guidelines

### Documentation
- [ ] Write comprehensive README.md
- [ ] Add architecture diagrams
- [ ] Create developer setup guide
- [ ] Document design system usage
- [ ] Add screenshots/demo video

## ✅ Completed

- [x] Initial app structure with KMP
- [x] Industrial minimalist design system (klar theme)
- [x] MVVM architecture with PathViewModel and LessonViewModel
- [x] Physics-first spring animations
- [x] Space Grotesk font integration
- [x] Multiple Choice exercise type
- [x] Word Bubbles exercise type
- [x] Confetti celebration animation
- [x] Haptic feedback system
- [x] Koin dependency injection
- [x] Voyager navigation with spring transitions
- [x] Package renaming to com.klar.android
- [x] GitHub repository setup
- [x] Brand consistency fixes (all colors industrial)
- [x] Error handling in ViewModels
- [x] LazyColumn key optimization

---

**Last Updated:** 2026-01-13
**Current Version:** 1.0 (Initial Release)
**Next Milestone:** Fix crash + implement icon/splash → v1.0.1
