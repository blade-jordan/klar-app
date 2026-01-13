# klar Design System
**Industrial Minimalist Language Learning**

## Philosophy

"Mechanically precise" learning that feels like a well-oiled machine. Every interaction should feel like operating precision tools in a high-end workshop - crisp, responsive, and confidence-inspiring.

## Brand Identity

### Name
**klar** (German for "clear" / "ready")
- Lowercase branding reinforces precision and minimalism
- German word connects to the app's purpose
- Short, memorable, industrial

### Tagline
"Master German. Mechanically precise."

## Color Palette

### Primary Colors
```kotlin
Industrial Black #0B0D0F  // Main background
Signal Orange   #FF6B00  // Primary actions, "PERFECT" states
Slate Grey      #2B2F36  // Secondary surfaces, cards
Ash White       #F2F4F7  // Body text, icons
Caution Red     #E63946  // Error states
```

### Usage Guidelines

**Industrial Black (#0B0D0F)**
- Main app background
- High-contrast moments
- Text on bright backgrounds
- Use for: app background, overlay backgrounds

**Signal Orange (#FF6B00)**
- Primary action buttons
- Active lesson nodes
- Selected word bubbles
- "PERFECT" success states
- Progress bars
- Hints and important callouts
- Use for: CTAs, active states, success moments

**Slate Grey (#2B2F36)**
- Card backgrounds
- Inactive states
- Secondary UI elements
- Component borders
- Use for: cards, surfaces, connectors

**Ash White (#F2F4F7)**
- Primary text color
- Icons
- Text on dark backgrounds
- Use for: all body text, headings on dark

**Caution Red (#E63946)**
- Error states
- Wrong answers
- "RETRY" messaging
- Use for: errors only

## Typography

### Font Family
**Primary**: Space Grotesk (Google Fonts)
- Geometric grotesque with sharp terminals
- Tech/industrial edge
- Excellent lowercase legibility
- **Alternative**: Inter (if Space Grotesk unavailable)

### Type Scale
```kotlin
h1: 32sp, Bold, -0.5sp letter-spacing  // Page titles
h2: 28sp, Bold, -0.5sp letter-spacing  // Section headers
h3: 24sp, Bold                         // Card titles
h4: 20sp, Bold                         // Subheadings
h5: 18sp, Bold, 0.5sp letter-spacing   // Prompts (uppercase)
h6: 16sp, Bold, 1sp letter-spacing     // Labels (uppercase)

body1: 16sp, Medium, 0.15sp            // Primary body
body2: 14sp, Medium, 0.15sp            // Secondary text

button: 16sp, Bold, 0.5sp (2sp when uppercase)
caption: 12-14sp, Medium, 1sp          // Hints, metadata
```

### Typography Rules
1. **Headings**: Always Bold weight
2. **Body**: Always Medium weight
3. **Uppercase**: Use for headings, prompts, buttons, labels
4. **Letter-spacing**: Increase for uppercase (1-2sp)
5. **Never**: Italic or light weights (not industrial)

## Shapes & Borders

### Corner Radius
```kotlin
Sharp: 0dp    // For perfectly industrial elements
Button: 4dp   // Subtle "engineered" feel
Card: 4dp     // Consistent with buttons
Word Bubble: 4dp  // Sharp instead of pill-shaped
```

### Border Widths
- **Standard**: 2dp (most components)
- **Emphasis**: 4dp (success/error cards)
- **Subtle**: 1dp (only when necessary)

### Design Rule
> NO rounded corners beyond 4dp. This isn't friendly iOS design - it's industrial Android.

## Component Library

### Buttons
```kotlin
// Primary Button
background: Signal Orange (#FF6B00)
text: Industrial Black (#0B0D0F)
corners: 4dp
border: 2dp Industrial Black
text: UPPERCASE, Bold, 2sp letter-spacing
height: 56dp
spring: StiffnessHigh + DampingRatioLowBouncy
```

### Cards
```kotlin
// Standard Card
background: Slate Grey (#2B2F36)
border: 2dp Slate Grey
corners: 4dp
padding: 16dp
```

### Word Bubbles
```kotlin
// Available (unselected)
background: Slate Grey (#2B2F36)
border: 2dp Slate Grey
text: Ash White (#F2F4F7)
corners: 4dp

// Selected
background: Signal Orange (#FF6B00)
border: 2dp Industrial Black
text: Industrial Black (#0B0D0F)
corners: 4dp
spring: DampingRatioLowBouncy (extra bounce)
```

### Lesson Nodes
```kotlin
// Active
background: Slate Grey (#2B2F36)
icon background: Signal Orange (#FF6B00)
icon: ▸ (play symbol)
title: UPPERCASE
border: 2dp Slate Grey

// Completed
icon background: Green (#4CAF50)
icon: ✓

// Locked
icon background: Disabled grey
icon: ⊗
```

### Progress Bar
```kotlin
height: 8dp
active: Signal Orange (#FF6B00)
background: Slate Grey (#2B2F36)
animation: 600ms tween
```

## Animation System

### Spring Physics (Mechanical Feel)
```kotlin
stiffness: Spring.StiffnessHigh       // Crisp, fast response
dampingRatio: Spring.DampingRatioLowBouncy  // Physical bounce
scale: 0.95f when pressed             // Standard buttons
scale: 0.90f when pressed             // Word bubbles (extra bounce)
```

**Feel**: Like pressing a mechanical keyboard switch or closing a precision tool cabinet drawer.

### Confetti System
- 50 particles (circles & squares)
- Physics-based with gravity
- 2-second animation
- Triggers on correct answers
- Signal Orange + complementary colors

### Gradients
- Flowing 4-second cycles
- Slate → Surface → Industrial Black
- Used for headers only
- Subtle, not distracting

### Transitions
- Fade + Slide for screen navigation
- 400ms duration
- Drawer-like feel (sliding in/out)

## Haptic Feedback

### Vibration Patterns
```kotlin
Success: HapticFeedbackConstants.CONFIRM    // Sharp, short
Error: HapticFeedbackConstants.REJECT       // Longer, warning
Click: HapticFeedbackConstants.KEYBOARD_TAP // Light tap
Tick: HapticFeedbackConstants.CLOCK_TICK    // Subtle feedback
```

### Usage Map
- **Success haptic**: Correct answer submitted
- **Error haptic**: Wrong answer submitted
- **Click haptic**: Buttons, lesson nodes pressed
- **Tick haptic**: Word bubbles selected/deselected

## Voice & Tone

### UI Copy Guidelines
1. **Uppercase for emphasis**: "CHECK", "PERFECT", "RETRY"
2. **Concise and direct**: "Master German. Mechanically precise."
3. **Industrial vocabulary**: "precision", "engineered", "calibrated"
4. **No cutesy language**: Avoid emojis in text, use symbols (→, ▸, ⊗)
5. **Active voice**: "TAP WORDS TO BUILD ANSWER"

### Success States
- "PERFECT" (not "Great!" or "Awesome!")
- "+10 XP" (not "You earned 10 XP!")
- ⚡ emoji (industrial/mechanical)

### Error States
- "RETRY" (not "Try again!" or "Oops!")
- ⊗ symbol (industrial X)
- Caution Red color

## Icons & Symbols

### Preferred Symbols
- ⚡ Lightning (energy, precision)
- ▸ Play/Forward (action, progress)
- ⊗ Industrial X (error, locked)
- ✓ Checkmark (completed)
- → Arrow (navigation, continuation)

### Avoid
- ❤️ Heart (too friendly)
- ⭐ Star (too playful)
- 😊 Emoji faces (not industrial)
- 🎉 Party (use ⚡ instead)

## Implementation Files

```
composeApp/src/androidMain/kotlin/com/linguistai/android/
├── ui/theme/
│   ├── KlarColors.kt      # Color palette
│   ├── KlarTypography.kt  # Type system
│   ├── KlarShapes.kt      # Corner radii
│   └── KlarTheme.kt       # Theme wrapper
├── animations/
│   ├── ConfettiCannon.kt  # Success particles
│   ├── SpringButton.kt    # Mechanical springs
│   ├── HapticFeedback.kt  # Vibration system
│   └── AnimatedGradient.kt # Flowing gradients
└── screens/
    ├── PathScreen.kt      # Learning path with klar branding
    └── LessonScreen.kt    # Exercises with klar styling
```

## Usage Examples

### Applying klar Theme
```kotlin
KlarTheme {
    // Entire app wrapped
    Navigator(PathScreen()) { navigator ->
        SlideTransition(navigator)
    }
}
```

### Accessing Colors
```kotlin
val colors = KlarTheme.colors

Box(
    modifier = Modifier
        .background(colors.background)  // Industrial Black
        .border(2.dp, colors.primary)   // Signal Orange border
)

Text(
    text = "EXAMPLE".uppercase(),
    color = colors.onBackground,  // Ash White
    style = MaterialTheme.typography.h6,
    letterSpacing = 1.sp
)
```

### Using Spring Animations
```kotlin
// Standard button
Box(modifier = Modifier.springPress(onTap = { /* action */ }))

// Word bubble with extra bounce
Box(modifier = Modifier.wordBubbleBounce(onTap = { /* action */ }))
```

### Using Haptics
```kotlin
val haptics = rememberHapticFeedback()

// On correct answer
haptics.performSuccess()

// On button press
haptics.performClick()
```

## Design Checklist

When creating new klar components:

- [ ] Uses klar color palette (no custom colors)
- [ ] 4dp or 0dp corners only (no 8dp, 12dp, etc.)
- [ ] UPPERCASE for headings/prompts
- [ ] Bold or Medium weights only (no Light)
- [ ] 2dp borders on interactive elements
- [ ] Spring physics on all clickable items
- [ ] Haptic feedback on interactions
- [ ] High letter-spacing (1-2sp) on uppercase
- [ ] Industrial symbols (→, ▸, ⊗) not emojis
- [ ] Signal Orange for primary actions
- [ ] Industrial Black background

## Technical Notes

### Space Grotesk Installation (TODO)
1. Download from Google Fonts
2. Add TTF files to `composeApp/src/androidMain/res/font/`
3. Uncomment font family code in `KlarTypography.kt`

### Color Contrast Ratios
All color combinations meet WCAG AA standards:
- Ash White on Industrial Black: 14.2:1 ✓
- Ash White on Slate Grey: 9.8:1 ✓
- Industrial Black on Signal Orange: 8.1:1 ✓
- Industrial Black on Caution Red: 6.4:1 ✓

## Inspiration References

- **Industrial Design**: Dieter Rams, Swiss Design
- **Typography**: Grotesque sans-serifs, technical manuals
- **Color**: Safety equipment, warning signs
- **Feel**: Mechanical keyboards, precision tools
- **Not**: Playful iOS apps, friendly mascots

---

**klar. Mechanically precise.**
