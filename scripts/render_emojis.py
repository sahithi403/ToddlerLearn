#!/usr/bin/env python3
"""
Render emoji characters to WebP files for bundling in the Android app.
Applies saturation boost to match the app's visual style.

Usage:
    python3 scripts/render_emojis.py

Output:
    app/src/main/res/drawable/emoji_<name>.webp
"""

import os
from PIL import Image, ImageDraw, ImageFont, ImageEnhance, ImageFilter

# ── Configuration ─────────────────────────────────────────────────────────────

EMOJI_FONT = "/System/Library/Fonts/Apple Color Emoji.ttc"
OUTPUT_DIR = os.path.join(os.path.dirname(__file__), "../app/src/main/res/drawable")
SIZE = 240          # canvas size in pixels
FONT_SIZE = 160     # emoji font size
SATURATION = 0.7    # slightly desaturated for a soft, painterly feel
WEBP_QUALITY = 90

# ── Emoji Data ─────────────────────────────────────────────────────────────────
# Format: (emoji, drawable_name)
# drawable_name must be lowercase letters, numbers, underscores only

EMOJIS = [
    # మనుషులు (People)
    ("👶", "people_baby"),
    ("👧", "people_girl"),
    ("👦", "people_boy"),
    ("👩", "people_mother"),
    ("👨", "people_father"),
    ("👵", "people_grandmother"),
    ("👴", "people_grandfather"),
    ("👩‍⚕️", "people_doctor"),
    ("👨‍🏫", "people_teacher"),
    ("👩‍🍳", "people_cook"),
    ("👮", "people_police"),
    ("👩‍🌾", "people_farmer"),

    # వస్తువులు (Objects)
    ("🏠", "objects_house"),
    ("🚗", "objects_car"),
    ("📚", "objects_book"),
    ("✏️", "objects_pencil"),
    ("🎒", "objects_bag"),
    ("⚽", "objects_ball"),
    ("🍎", "objects_apple"),
    ("🌸", "objects_flower"),
    ("☀️", "objects_sun"),
    ("🌙", "objects_moon"),
    ("⭐", "objects_star"),
    ("🌧️", "objects_rain"),

    # జంతువులు (Animals)
    ("🐶", "animals_dog"),
    ("🐱", "animals_cat"),
    ("🐮", "animals_cow"),
    ("🐷", "animals_pig"),
    ("🐸", "animals_frog"),
    ("🦁", "animals_lion"),
    ("🐘", "animals_elephant"),
    ("🐧", "animals_penguin"),
    ("🦊", "animals_fox"),
    ("🐰", "animals_rabbit"),
    ("🐻", "animals_bear"),
    ("🦋", "animals_butterfly"),

    # రంగులు (Colors)
    ("🔴", "colors_red"),
    ("🟠", "colors_orange"),
    ("🟡", "colors_yellow"),
    ("🟢", "colors_green"),
    ("🔵", "colors_blue"),
    ("🟣", "colors_purple"),
    ("⚫", "colors_black"),
    ("⚪", "colors_white"),
    ("🟤", "colors_brown"),
    ("🩷", "colors_pink"),
    ("🩵", "colors_light_blue"),
    ("🟩", "colors_dark_green"),

    # ఆకారాలు (Shapes)
    ("⭕", "shapes_circle"),
    ("🔷", "shapes_diamond"),
    ("🔺", "shapes_triangle"),
    ("⬛", "shapes_square"),
    ("🔶", "shapes_hexagon"),
    ("⭐", "shapes_star"),
    ("❤️", "shapes_heart"),
    ("🔘", "shapes_oval"),
    ("📐", "shapes_rectangle"),
    ("🌙", "shapes_crescent"),
    ("💠", "shapes_crystal"),
    ("🔣", "shapes_cross"),
]

# ── Rendering ──────────────────────────────────────────────────────────────────

def render_emoji(emoji: str, name: str, font: ImageFont.FreeTypeFont):
    img = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Center the emoji
    bbox = draw.textbbox((0, 0), emoji, font=font)
    w = bbox[2] - bbox[0]
    h = bbox[3] - bbox[1]
    x = (SIZE - w) / 2 - bbox[0]
    y = (SIZE - h) / 2 - bbox[1]

    draw.text((x, y), emoji, font=font, embedded_color=True)

    # Boost saturation
    rgb = img.convert("RGB")
    enhanced = ImageEnhance.Color(rgb).enhance(SATURATION)

    # Restore alpha
    r, g, b = enhanced.split()
    _, _, _, a = img.split()
    emoji_layer = Image.merge("RGBA", (r, g, b, a))

    return emoji_layer


def main():
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    try:
        font = ImageFont.truetype(EMOJI_FONT, FONT_SIZE)
    except Exception as e:
        print(f"❌ Could not load emoji font: {e}")
        return

    print(f"Rendering {len(EMOJIS)} emojis to {OUTPUT_DIR}\n")
    skipped = 0

    for emoji, name in EMOJIS:
        out_path = os.path.join(OUTPUT_DIR, f"emoji_{name}.webp")
        if os.path.exists(out_path):
            print(f"  skip  emoji_{name}.webp (already exists)")
            skipped += 1
            continue
        try:
            img = render_emoji(emoji, name, font)
            img.save(out_path, "WEBP", quality=WEBP_QUALITY)
            print(f"  ✓  emoji_{name}.webp")
        except Exception as e:
            print(f"  ✗  emoji_{name} — {e}")

    print(f"\nDone. {len(EMOJIS) - skipped} rendered, {skipped} skipped.")


if __name__ == "__main__":
    main()
