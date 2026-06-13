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

## Image Assets — Usage Rules
- Images in `scripts/samples/realLifeSamples/` are **reference-only**. They must never be uploaded to any external service, API, or image generation platform.
- Only text descriptions derived from these images may be used externally (e.g. style prompts, color specs).

## Image Generation Styles

### MathruStyle
> 2D cel-shaded children's illustration.
>
> **Background:** Natural, scene-appropriate colors — whatever environment is depicted should use realistic, true-to-life colors (e.g. greens for nature, blues for water, warm tones for indoor scenes). Avoid desaturating or shifting the environment toward a golden/sandy palette. Ground and surface textures in muted, naturalistic tones. Rocks in cool gray (#9AA5A8).
>
> **Texture:** Paper grain and sand noise overlaid at low opacity across all surfaces. No smooth gradients, no clean digital edges.
>
> **Characters:** Naturally proportioned with realistic anatomy. Large expressive eyes with colored iris. Two-tone body coloring: a primary body color with a darker or lighter secondary tone for inner ears, around the eyes, flanks, and base. One saturated accent color for accessories or markings. Dark outlines and markings in deep navy (#1E2A3A), not pure black.
>
> **Rendering:** No outlines or thin strokes — shapes defined by color contrast only. Matte finish, no gloss or specular highlights.
>
> **Composition:** Static, calm, low visual complexity.
>
> Reference samples: `scripts/samples/realLifeSamples/`


## Characters

### Bunty (rabbit)
- **Reference image:** `scripts/samples/bunty.png`
- **Body:** White (#F0EDE0) main body and chest, soft mid-gray (#A8A8A8) at inner ears, around the eyes, flanks, and base
- **Accent:** Orange-red (#E05A1A) scarf around the neck
- **Markings:** Deep navy (#1E2A3A) for eyes and nose
- **Style:** MathruStyle
- **Note:** Do not alter facial features, ear shape, body proportions, or the two-tone color placement when editing or re-generating Bunty. Only background and scene context should change between images.

## Branches
- `main` — stable
- `helloWorld` — development branch
## Card Layout
- All cards use a **fixed height of 200dp** (`item_learn_card.xml`) — ensures every row is the same size
- ImageView inside uses `match_parent` width and `weight=1` height — fills available card space
- `scaleType="fitCenter"` — scales image to fit within the card, maintaining aspect ratio
- No per-item dynamic sizing; `LearnItem` only needs `drawableResId`, no `imageSizeDp`

## Special Assets
- **Bunty** (`emoji_animals_bunty.webp`): background removed via `rembg[cpu]`, then tightly cropped to bounding box using Pillow. Original 240×240px → cropped to 80×141px (portrait). Tight crop ensures Bunty fills the card width at runtime.
- Crop command: `from PIL import Image; img = Image.open(...).convert('RGBA'); img.crop(img.getbbox()).save(..., lossless=True)`
