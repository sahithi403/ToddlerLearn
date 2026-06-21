# Image Generation — ToddlerLearn

Full reference for all image generation rules, workflows, and style specs.

---

## Security Rule

Images in `scripts/samples/realLifeSamples/` are **reference-only**. They must never be uploaded to any external service, API, or image generation platform. Only text descriptions derived from these images may be used externally.

---

## MathruStyle

> 2D cel-shaded children's illustration.
>
> **Background:** Natural, scene-appropriate colors. Avoid desaturating toward a golden/sandy palette. Ground and surface textures in muted, naturalistic tones. Rocks in cool gray (#9AA5A8).
>
> **Texture:** Paper grain and sand noise overlaid at low opacity across all surfaces. No smooth gradients, no clean digital edges.
>
> **Characters:** Rounded bodies, minimal anatomy detail. Dark outlines and markings in deep navy (#1E2A3A), not pure black. Two-tone body coloring: primary body color with darker/lighter secondary tone for inner ears, around eyes, flanks, and base. One saturated accent color for accessories or markings.
>
> **Rendering:** No outlines or thin strokes — shapes defined by color contrast only. Matte finish, no gloss or specular highlights.
>
> **Composition:** Static, calm, low visual complexity.

---

## Proportion Rules (ALL characters — human and animal)

### Head-to-Body Ratio
Target: **1.15:1** — head is 15% larger than anatomically realistic. Applies to humans AND animals.

- Too small (stiff): below `1.05:1`
- ✅ Target: `1.15:1`
- Too large (chibi): above `1.3:1`

**Always include:** `"head-to-body ratio 1.15:1, head slightly larger than realistic, not chibi, not oversized"`

### Eye-to-Head Ratio (human characters)
Base: **1:4** for adults. Scale naturally with age. Never exceed 1:3.

| Age Group | Eye:Head Ratio | Prompt phrase |
|---|---|---|
| Elderly (60+) | 1:4.5 | `small realistic eyes` |
| Adult (25–55) | 1:4 | `moderately sized eyes` |
| Young adult (18–25) | 1:4 | `moderately sized eyes` |
| Child (5–12) | 1:3.5 | `slightly large expressive eyes` |
| Toddler / Baby (0–4) | 1:3 | `large expressive eyes, realistic for a toddler` |

**Always include:** `"eye size proportionate to age, not oversized"` + age-appropriate phrase above.

---

## Characters

### Bunty (rabbit)
- **Reference image:** `scripts/samples/bunty.png`
- **Body:** White (#F0EDE0) main body and chest, soft mid-gray (#A8A8A8) at inner ears, around the eyes, flanks, and base
- **Accent:** Orange-red (#E05A1A) scarf around the neck
- **Markings:** Deep navy (#1E2A3A) for eyes and nose
- **Style:** MathruStyle
- **Note:** Do not alter facial features, ear shape, body proportions, or two-tone color placement when editing or regenerating. Only background and scene context should change.

---

## Image Generation Prompt Requirements

Always include in every character generation prompt:
- `"MathruStyle 2D cel-shaded children's illustration"`
- `"isolated character on transparent background, single character centered"`
- Head-to-body ratio phrase (see Proportion Rules above)
- Eye size phrase appropriate for character's age (see table above)
- Model: `nano_banana_pro`

> For the full generation, assessment, background removal, and save workflow — see `scripts/WORKFLOW.md`

---

## Reference Samples
Located in `scripts/samples/realLifeSamples/`
