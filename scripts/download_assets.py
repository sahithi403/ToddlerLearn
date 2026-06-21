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
    # Ammamma — silver-grey saree, glasses, grey-streaked hair
    ("hf_20260621_040107_efbb8979-a0bb-481b-a28d-dfbffaa6be92.png", "emoji_family_ammamma"),
    # Nannamma — orange saree, pink border, red bindi, mangalsutra
    ("hf_20260621_040108_7efd21df-408f-46f1-9b68-b98ab618a1c4.png", "emoji_family_nannamma"),
    # Pinni — light pink salwar, long wavy hair
    ("hf_20260621_040110_f80a2ad0-0e8f-40d0-83a1-31a2287b55c5.png", "emoji_family_pinni"),
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
