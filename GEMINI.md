# Linguist AI (Gemini Context)

## Project Structure
- `composeApp/`: Android application module.
- `shared/`: Shared KMP module (Android/Common).
- `gradle/libs.versions.toml`: Dependency management.

## Key Components
- `com.linguistai.shared.architecture.BaseViewModel`: Lightweight ViewModel base.
- `com.linguistai.shared.data.LessonRepository`: Interface for data access.
- `com.linguistai.android.MainActivity`: Entry point for Android app.

## Development Goals
- Keep logic in `shared` as much as possible.
- Use `libs.versions.toml` for all dependencies.
