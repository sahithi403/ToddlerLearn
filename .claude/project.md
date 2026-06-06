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

## Visual Design

### Colors
| Element | Color |
|---|---|
| App background | `#F2EDE6` |
| Header | `#3D5A5E` |
| Accent | `#D95F2B` |
| Card text | `#1A1A1A` |
| Card 1 — sage green | `#D6E4D0` |
| Card 2 — dusty teal | `#D8E4E2` |
| Card 3 — warm sand | `#E4D8CC` |
| Card 4 — dusty blue | `#D0DAE4` |
| Card 5 — blush | `#E0D4CC` |
| Card 6 — seafoam | `#CCD8D2` |
| Card 7 — linen | `#E0D8C8` |
| Card 8 — lavender | `#D4D0E0` |
| Card 9 — mint | `#CCD8D0` |
| Nav: మనుషులు | `#6B4E3D` |
| Nav: వస్తువులు | `#4A6741` |
| Nav: జంతువులు | `#7A5C3A` |
| Nav: రంగులు | `#3A5F72` |
| Nav: ఆకారాలు | `#5E4A6B` |

### Emoji Rendering
- Pre-rendered at dev time using `scripts/render_emojis.py` → saved as WebP to `app/src/main/res/drawable/`
- Run script whenever new emojis are added: `python3 scripts/render_emojis.py`
- Script skips files that already exist — delete them first to force re-render
- Canvas: 240x240px, font size 160sp, Apple Color Emoji font
- **Saturation: `0.7`** — slightly desaturated for a soft, muted feel (do NOT boost above 1.0, emojis become cartoonish)
- Output format: WebP, quality 90
- Naming convention: `emoji_{category}_{name}.webp` e.g. `emoji_animals_dog.webp`
- Each `LearnItem` holds a `drawableResId: Int` pointing to the pre-rendered file
- Adapter loads via `ivEmoji.setImageResource(item.drawableResId)` — no runtime rendering

## Branches
- `main` — stable
- `helloWorld` — development branch
