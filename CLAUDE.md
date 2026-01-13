# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
**klar** is an Android-first German language learning application built with Kotlin Multiplatform (KMP). The app features an industrial minimalist design system with high-contrast "safety" colors, mechanical spring animations, and thumb-friendly navigation. The goal is to deliver Duolingo-level polish with a distinctive industrial aesthetic.

## Brand Identity: klar Design System

### Industrial Minimalist Aesthetic
- **Philosophy**: "Mechanically precise" learning with high-contrast, safety-focused design
- **Feel**: Like a well-oiled machine or precision tool cabinet
- **Typography**: Geometric grotesque (Space Grotesk recommended) with uppercase industrial caps
- **Shapes**: Sharp 4dp corners for "engineered" feel

### Color Palette
Located in `composeApp/src/androidMain/kotlin/com/linguistai/android/ui/theme/KlarColors.kt`:
- **Industrial Black** (#0B0D0F): Main background
- **Signal Orange** (#FF6B00): Primary actions, active states, "PERFECT" moments
- **Slate Grey** (#2B2F36): Secondary surfaces, cards
- **Ash White** (#F2F4F7): Body text, icons
- **Caution Red** (#E63946): Error states

### Mechanical Spring Physics
All buttons use:
- `stiffness = Spring.StiffnessHigh`
- `dampingRatio = Spring.DampingRatioLowBouncy`
- Creates physical "click" and "snap" like precision tools

## Project Structure
- **:composeApp**: Android application module
  - `ui/theme/`: klar design system (KlarColors, KlarTypography, KlarShapes, KlarTheme)
  - `animations/`: Premium feedback system (confetti, springs, haptics, gradients)
  - `screens/`: PathScreen and LessonScreen with klar branding
  - `navigation/`: Voyager transitions
  - Namespace: `com.linguistai.android`

- **:shared**: KMP shared module
  - `architecture/`: BaseViewModel for KMP
  - `data/`: Lesson data model matching JSON schema
  - `di/`: Koin dependency injection modules
  - Namespace: `com.linguistai.shared`

## Architecture
- **Pattern**: MVVM (Model-View-ViewModel)
- **Design System**: Industrial Minimalist (klar theme)
- **UI**: Jetpack Compose with KlarTheme wrapper
- **Navigation**: Voyager with fade-and-slide transitions
- **Animations**: Native Compose (Canvas confetti, spring physics, animated gradients)
- **Haptics**: Contextual vibration feedback (success, error, click, tick)
- **DI**: Koin for dependency injection
- **Database**: Room KMP (planned)
- **Coroutines**: kotlinx.coroutines for async operations

## Key Technical Details
- **Min SDK**: 24 (Android 7.0)
- **Target/Compile SDK**: 36
- **Kotlin**: 2.3.0
- **Compose Multiplatform**: 1.9.3
- **JVM Target**: 1.8
- **Java**: 21 (LTS)

## Dependencies
Version catalog in `gradle/libs.versions.toml`:
- Voyager (navigation): 1.1.0-beta03
- Koin (DI): 4.0.0
- Room: 2.8.4
- Kotlinx Coroutines: 1.10.2

## Build Commands

### Build the entire project
```bash
./gradlew build
```

### Build specific modules
```bash
./gradlew :composeApp:build
./gradlew :shared:build
```

### Install klar app on device/emulator
```bash
./gradlew :composeApp:installDebug
```

### Run the klar app
```bash
./gradlew :composeApp:installDebug && adb shell am start -n com.linguistai.android/.MainActivity
```

### Clean build
```bash
./gradlew clean
```

## Premium Feedback Animation System

### Confetti Cannon
- 50 physics-based particles (circles & squares)
- Realistic gravity, velocity, rotation
- Triggers on correct answers
- Located: `animations/ConfettiCannon.kt`

### Spring Physics
- All buttons scale to 0.95f with mechanical bounce
- Word bubbles have extra bounce (0.90f)
- Located: `animations/SpringButton.kt`
- Usage: `.springPress()` or `.wordBubbleBounce()` modifiers

### Haptic Feedback
- **Success**: Short sharp (correct answers)
- **Error**: Long press (wrong answers)
- **Click**: Light tap (buttons)
- **Tick**: Subtle (word selection)
- Located: `animations/HapticFeedback.kt`
- Usage: `rememberHapticFeedback()` then call `.performSuccess()`, etc.

### Animated Gradients
- Flowing gradients (4-second cycles)
- Pulse effects for headers
- Located: `animations/AnimatedGradient.kt`
- Usage: `.animatedGradient()`, `.pulseGradient()` modifiers

## Lesson Data Model

Matches JSON schema with enums and proper types:
```kotlin
enum class ExerciseType {
    MULTIPLE_CHOICE, WORD_BUBBLES, LISTEN_AND_TYPE
}

enum class Level {
    A1, A2, B1, B2, C1, C2
}

data class Exercise(
    val id: String,
    val type: ExerciseType,
    val prompt: String,
    val audioPhrase: String?,
    val correctAnswer: String,
    val options: List<String>,
    val hint: String?,
    val xpReward: Int = 10
)

data class Lesson(
    val lessonId: String,
    val level: Level,
    val title: String,
    val exercises: List<Exercise>
)
```

## Design System Usage

### Using klar Theme
```kotlin
KlarTheme {
    // Your composables
    val colors = KlarTheme.colors

    Text(
        text = "EXAMPLE",
        color = colors.primary, // Signal Orange
        style = MaterialTheme.typography.h6
    )
}
```

### klar Design Patterns
- **Uppercase text** for headings (industrial feel)
- **Sharp 4dp corners** for all cards and buttons
- **Signal Orange** for active/primary actions
- **Industrial Black** background
- **2dp borders** on components
- **High letter-spacing** (1-2sp) for uppercase text

## Future Enhancements
- Add Space Grotesk font files to `res/font/`
- Implement Listen-and-Type exercise type
- Add sound effects (success chime, error buzz)
- Streak counters with animations
- Level-up celebration animations
- Achievement badges
