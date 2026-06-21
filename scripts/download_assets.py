"""
Download, crop and save flash card assets to drawable folder.
Run immediately — URLs expire within hours.

Usage: python3 scripts/download_assets.py
"""
import requests
from PIL import Image
from io import BytesIO

BASE = "https://d8j0ntlcm91z4.cloudfront.net/user_3F3Lmw0hYKtRtyDDE2xxXfBnKAb/"

assets = [
    # Family — v2 (fixed poses, proportions, hair)
    ("hf_20260621_033708_38cffe03-1d0f-4765-99b4-5a8f58be7ae8.png", "emoji_family_amma"),
    ("hf_20260621_033709_7ca7a276-97bd-4fa3-a4d2-4da8aae0edba.png", "emoji_family_papa"),
    ("hf_20260621_033711_be9a93ed-8ac0-43b1-9a41-97872a18feac.png", "emoji_family_thatha"),
]

for filename, name in assets:
    url = BASE + filename
    print(f"Downloading {name}...")
    r = requests.get(url)
    r.raise_for_status()
    img = Image.open(BytesIO(r.content)).convert("RGBA")
    bbox = img.getbbox()
    cropped = img.crop(bbox)
    out = f"app/src/main/res/drawable/{name}.webp"
    cropped.save(out, "WEBP", lossless=True)
    print(f"  Saved {out} ({cropped.size[0]}x{cropped.size[1]}px)")

print("\nAll done!")
