# Flash Card Image Workflow

For image style rules and generation prompts, see `scripts/samples/IMAGE_STYLE_PROMPT.md`.

---

## Automated Image Generation Workflow

Follow these steps in order. Steps marked **AUTO** require no user input. Steps marked **MANUAL** require one user action.

### Step 1 — Upload reference (editing existing characters only) · MANUAL
If adjusting an existing drawable (e.g. fixing proportions), the current file must be uploaded to Higgsfield first. The sandbox cannot reach external upload URLs, so give the user this command and wait for confirmation:
```bash
curl -X PUT -H "Content-Type: image/webp" --data-binary @/Users/sahi3c/Claude/Projects/ToddlerLearn/app/src/main/res/drawable/<name>.webp '<presigned_upload_url>'
```
Then call `media_confirm`. Skip this step for brand-new characters.

### Step 2 — Generate image · AUTO
- Use `nano_banana_pro`
- Always include MathruStyle prompt phrases and proportion rules from `IMAGE_STYLE_PROMPT.md`
- **Always refine from the existing image** — pass the current drawable (uploaded in Step 1) or previous job ID as `medias` reference. Never start from scratch unless the user explicitly asks to.
- Prompt should describe only what needs to change, not the full character from scratch
- After submitting: tell the user "Generating — let me know when the image appears in Higgsfield and whether it looks good." Do NOT call `job_display` while waiting.
- When user confirms it's done: call `job_display` exactly once to get the CDN URL
- Immediately give user this curl command and wait for them to confirm before proceeding:
  ```bash
  curl -s "<cdn_raw_url>" -o /Users/sahi3c/Claude/Projects/ToddlerLearn/scripts/samples/<name>_check.png
  ```

### Step 3 — Self-assess against MathruStyle rules · AUTO
Once the file is saved, use `Read` to view it and check:
- Head:body ratio ~1.15:1 (not chibi, not stiff)
- Eye:head ratio appropriate for age (see IMAGE_STYLE_PROMPT.md table)
- MathruStyle: cel-shaded, navy outlines, paper grain texture, matte finish
- No smooth gradients, no photorealistic shading

Always save to the workspace — never `/tmp` — so it can be read directly.
Only proceed to Step 4/5 after this assessment passes.

### Step 4 — Iterate if needed · AUTO (max 3 rounds)
- If image does not meet rules: refine using the previous job as reference, targeted prompt describing only what needs to change
- Do NOT restart from scratch — always build on the previous result
- After 3 failed iterations: stop and ask the user for direction

### Step 5 — Background removal + user confirmation simultaneously · SEMI-AUTO
Kick off background removal immediately after self-assessment passes:
```
remove_background(media_id=<job_id>, media_type="image")
```
While it runs, show the assessed image and ask: "Does this look good? Background removal is running in parallel — let me know if you want changes, otherwise I'll save it when done."
- If user approves or no response: use the bg-removed result when ready
- If user requests changes: go back to Step 4 with the current job as reference

### Step 6 — Save to app · MANUAL (one command)
Update `scripts/download_assets.py` with the bg-removed CDN filename and drawable name, then tell user:
```bash
cd /Users/sahi3c/Claude/Projects/ToddlerLearn
python3 scripts/download_assets.py
```
The script crops to bounding box and saves lossless WebP to `app/src/main/res/drawable/`.

### Step 7 — Update FlashCardsActivity.kt · AUTO
If replacing an existing drawable: nothing needed (same filename).
If adding a new character: add the Triple to the correct category list:
```kotlin
Triple("EnglishName", "తెలుగు పేరు", R.drawable.emoji_category_name)
```

---

## Summary of manual interventions
| Step | What user does |
|---|---|
| Step 1 (edits only) | Run one `curl` upload command |
| Step 2 | Run one `curl` download command for assessment |
| Step 6 | Run `python3 scripts/download_assets.py` |

Everything else is automated.

---

## Notes
- CDN URLs expire within hours — run download script immediately
- Sandbox cannot reach external CDNs — all downloads must run on user's Mac
- Drawable naming: `emoji_{category}_{name}.webp` e.g. `emoji_family_amma.webp`
- Always crop to tight bounding box after background removal (`getbbox()`)
- Save as lossless WebP
