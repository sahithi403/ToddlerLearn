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
    # Papa — longer hair, headband, bindi added
    ("hf_20260621_034200_1044fa6a-c90a-451d-a2a1-6f2e3029a20b.png", "emoji_family_papa"),
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
