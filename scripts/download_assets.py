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
    # Nanna — paisley shirt, dark trousers, beard, head proportion 1.15:1
    ("hf_20260621_233557_bcca0172-c443-4db7-ab08-eaa17c2de867.png", "emoji_family_nanna"),
    # Paapa — little girl, pink choli, green pavadai, bangles, bindi, head proportion 1.15:1
    ("hf_20260621_234607_afb5abe9-91a4-421d-8189-013af61980e2.png", "emoji_family_paapa"),
    # Babu — toddler boy, yellow kurta, blue shorts, barefoot, head proportion 1.15:1
    ("hf_20260621_235055_deca3d19-03c4-4707-b514-d9619209686c.png", "emoji_family_babu"),
    # Ammamma — maternal grandma, 50s, dark hair with grey streaks, grey saree navy border, glasses, head proportion 1.15:1
    ("hf_20260622_001339_7a15cf75-147f-481b-bdbd-132aec9ffc08.png", "emoji_family_ammamma"),
    # Tata — maternal grandpa, 60s, salt-and-pepper hair, moustache, glasses, blue shirt, dark trousers, head proportion 1.15:1
    ("hf_20260622_001840_fdad0daa-8ea7-4bb7-bff1-9c0d068c812f.png", "emoji_family_tata"),
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
