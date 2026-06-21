# Flash Card Image Workflow

Steps after generating and background-removing an image in Higgsfield.
For image style rules and generation prompts, see `scripts/samples/IMAGE_STYLE_PROMPT.md`.

---

## Step 1 — Update download_assets.py

Add the new CDN filename(s) to `scripts/download_assets.py`:

```python
BASE = "https://d8j0ntlcm91z4.cloudfront.net/user_3F3Lmw0hYKtRtyDDE2xxXfBnKAb/"

assets = [
    ("hf_filename.png", "emoji_category_name"),
    # add more entries here
]
```

The script crops each image to its tight bounding box (`getbbox()`) and saves as lossless WebP to `app/src/main/res/drawable/`.

## Step 2 — Run the script (on your Mac)

```bash
cd /Users/sahi3c/Claude/Projects/ToddlerLearn
python3 scripts/download_assets.py
```

CDN URLs expire within hours — run this immediately after generation.

## Step 3 — Update FlashCardsActivity.kt

Add the new Triple to the correct category list:

```kotlin
Triple("EnglishName", "తెలుగు పేరు", R.drawable.emoji_category_name)
```

---

## Notes

- The sandbox cannot download binary files from external CDNs — always run the script on your Mac
- Always crop to tight bounding box after background removal (`getbbox()`)
- Save as lossless WebP to `app/src/main/res/drawable/`
- Drawable naming: `emoji_{category}_{name}.webp` e.g. `emoji_family_amma.webp`
