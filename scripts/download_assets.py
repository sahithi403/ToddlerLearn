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
    # Objects
    ("hf_20260613_015753_3837f06d-fbbc-44c3-aa82-c29fa46577c4.png", "emoji_objects_house"),
    ("hf_20260613_015754_876c03e2-0911-49b2-b586-02f7fe8a83ba.png", "emoji_objects_book"),
    ("hf_20260613_015755_bc4d45bc-8a0a-45ca-ad27-54cf18175277.png", "emoji_objects_ball"),
    ("hf_20260613_015757_387b1194-503b-44c8-974d-ba0fdc984043.png", "emoji_objects_flower"),
    ("hf_20260613_015759_df7ccf7e-80d1-4b1b-8c4e-77ea59d1ba3b.png", "emoji_objects_sun"),
    ("hf_20260613_015800_e367ed09-74b1-48c0-a994-b4964fc3bacb.png", "emoji_objects_moon"),
    ("hf_20260613_015802_3e35039f-254f-420a-b1f7-4b09318249c8.png", "emoji_objects_star"),
    ("hf_20260613_015803_42bdca74-9044-40d2-b6c3-57458b63bc72.png", "emoji_objects_rain"),
    # Animals
    ("hf_20260613_015805_53fcfc0e-1268-4ee7-b9a6-72d72e9875b7.png", "emoji_animals_pig"),
    ("hf_20260613_015807_20ed14d3-a3b5-4d0a-92c3-cb18882de1a8.png", "emoji_animals_frog"),
    ("hf_20260613_015808_ecb4c871-e407-4bd1-947f-394c8b73b0d6.png", "emoji_animals_lion"),
    ("hf_20260613_015810_50eb628f-dda4-457d-ae5f-8415bc9a43e2.png", "emoji_animals_elephant"),
    ("hf_20260613_015811_f1caed24-cabb-4ac5-8115-e633747013fc.png", "emoji_animals_fox"),
    ("hf_20260613_015812_d9f5b284-4646-4177-812e-02b4a6debd3a.png", "emoji_animals_bear"),
    ("hf_20260613_015814_e5521245-cf65-46bf-bac4-45b99f8797b8.png", "emoji_animals_butterfly"),
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
