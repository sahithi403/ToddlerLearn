# ToddlerLearn — Project Context

## What this app is
A tap-and-learn Android app for toddlers. Kids tap colorful cards to hear animals, colors, and shapes spoken aloud via Text-to-Speech.

## Tech Stack
- Language: Kotlin
- Build: Gradle 8.5, AGP 8.2.0, JDK 21
- Min SDK: 24, Target SDK: 34
- Key libraries: RecyclerView, Material Design, Android TTS

## Project Structure
- `app/src/main/java/com/example/toddlerlearn/` — source code
- `app/src/main/res/layout/` — XML layouts
- `app/src/main/res/values/` — colors, strings, themes

## Known Setup Notes
- Gradle wrapper: 8.5 (supports JDK 21)
- App icon uses `@android:drawable/sym_def_app_icon` (no custom mipmap icons yet)
- `local.properties` and `.gradle/`, `.idea/` are gitignored

## Branches
- `main` — stable
- `helloWorld` — development branch
