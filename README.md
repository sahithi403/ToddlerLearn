# 🌟 Toddler Learn — Android App

A tap-and-learn game for toddlers. Kids tap colorful cards to hear the name of animals, colors, and shapes spoken aloud.

## Features
- 3 categories: Animals, Colors, Shapes
- 12 items per category with emoji + large text
- Text-to-Speech: tap a card and hear the name spoken
- Bounce animation on every tap
- Bright, toddler-friendly UI

---

## How to Open in Android Studio

### Requirements
- [Android Studio](https://developer.android.com/studio) (Hedgehog 2023.1.1 or newer)
- Android SDK 34
- Java 8+

### Steps

1. Open Android Studio
2. Click **File → Open** (or "Open" on the welcome screen)
3. Navigate to this folder (`Android App`) and click **OK**
4. Wait for Gradle sync to finish (first time takes a few minutes)
5. Connect an Android phone via USB, or create an emulator:
   - **Device Manager → Create Device → Pixel 6 → API 34**
6. Click the green **Run ▶** button

---

## Project Structure

```
app/
  src/main/
    java/com/example/toddlerlearn/
      MainActivity.kt         ← Main screen logic
      LearnItemAdapter.kt     ← RecyclerView adapter for the card grid
      LearnItem.kt            ← Data model
    res/
      layout/
        activity_main.xml     ← Main screen layout
        item_learn_card.xml   ← Individual card layout
      values/
        colors.xml
        strings.xml
        themes.xml
    AndroidManifest.xml
```

---

## Customizing

**Add more animals/colors/shapes:** In `MainActivity.kt`, find the `animals`, `colors`, or `shapes` lists and add more `"emoji" to "Label"` pairs.

**Change card colors:** Edit `res/values/colors.xml` — the `card_1` through `card_9` entries control card backgrounds.

**Change the voice speed/pitch:** In `MainActivity.kt`, after `tts.language = Locale.US`, add:
```kotlin
tts.setSpeechRate(0.8f)   // slower = more toddler-friendly
tts.setPitch(1.2f)        // higher = friendlier voice
```
