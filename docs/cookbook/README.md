# webforJ Cookbook

A collection of focused, copy-paste Java code recipes for common webforJ tasks.  
This document explains how the cookbook is built and how to add new recipes.

---

## Architecture overview

```
docs/
├── cookbook/                        ← Recipe source files (MDX)
│   ├── index.mdx                    ← Landing page — renders CookbookIndex
│   ├── recipe-template.mdx          ← Contributor template (excluded from build)
│   ├── css/
│   │   └── inject-inline-css.mdx
│   ├── routing/
│   │   ├── navigate-programmatically.mdx
│   │   └── read-query-parameter.mdx
│   └── ...
├── tools/
│   ├── generate-cookbook-index.mjs  ← Build-time index generator
│   └── validate-cookbook.mjs        ← CI frontmatter validator
├── static/
│   └── cookbook-index.json          ← Generated artifact (committed to repo)
└── src/components/Cookbook/
    ├── CookbookIndex.jsx             ← Search + filter UI
    ├── CookbookCard.jsx              ← Individual recipe card
    └── CookbookIndex.module.css     ← Scoped styles (DWC theme tokens)
```

### How it works

1. **Build time** — `generate-cookbook-index.mjs` scans every recipe `.mdx` file,
   reads its frontmatter, and writes `static/cookbook-index.json`.  
   The script runs automatically via the `prebuild` and `prestart` npm hooks.

2. **Runtime** — `CookbookIndex.jsx` fetches `cookbook-index.json` in the browser
   and renders a card grid with three AND-combined client-side filters:
   - **Free-text search** — matches title, description, and tags
   - **Tag buttons** — filter to recipes with a specific tag
   - **Difficulty select** — filter by beginner / intermediate / advanced

3. **Routing** — the cookbook is served by a second `@docusaurus/plugin-content-docs`
   instance (id `cookbook`, `routeBasePath: 'cookbook'`).  Each recipe `.mdx` file
   becomes a standalone page at `/cookbook/<category>/<slug>`.

4. **CI validation** — `.github/workflows/validate-cookbook.yml` runs
   `validate-cookbook.mjs --changed` on every PR that touches `docs/cookbook/`.
   The validator checks frontmatter schema; any error blocks the merge.

---

## Adding a new recipe

### 1 — Copy the template

```bash
cp docs/cookbook/recipe-template.mdx docs/cookbook/<category>/<your-recipe-slug>.mdx
```

Use an existing category folder (`css`, `routing`, `table`, `forms`, `layout`) or
create a new one with a `_category_.json` file:

```json
{ "label": "My Category", "position": 6 }
```

### 2 — Fill in the frontmatter

```yaml
---
title: "Short, descriptive phrase"
description: "One sentence explaining what this recipe does. Max 160 characters."
tags: [css, styling]
components: []
difficulty: beginner
---
```

| Field | Required | Rules |
|---|---|---|
| `title` | ✅ | Short phrase; no "How to" prefix |
| `description` | ✅ | One sentence, ≤ 160 characters |
| `tags` | ✅ | Array; values must come from the allowed list (see below) |
| `components` | ✅ | Array of component names, e.g. `[Button, TextField]`; use `[]` if not component-specific |
| `difficulty` | ✅ | `beginner` \| `intermediate` \| `advanced` |

**Allowed tag values:**

```
css, styling, routing, parameters, table, forms, layout,
navigation, data-binding, validation, components, dialog,
notification, grid, list, tree, toolbar, upload, chart,
theme, i18n, security, spring, performance, testing
```

> To add a new tag, update the `ALLOWED_TAGS` set in `tools/validate-cookbook.mjs`
> and the `tags` list in `recipe-template.mdx` in the same PR.

### 3 — Write the recipe body

Keep it minimal: a short 1–2 sentence intro (if the code alone isn't self-explanatory)
followed by one Java code block.

````mdx
Brief context sentence if needed.

```java
// Your verified, compilable Java code here
```

Optional follow-up sentence explaining a variation or edge case.
````

**Rules:**
- At least one fenced Java code block is required.
- Code must compile — test it locally before opening a PR.
- No Kotlin — Java only.

### 4 — Regenerate the index locally

```bash
cd docs
node tools/generate-cookbook-index.mjs
```

This rewrites `static/cookbook-index.json`.  Commit this file alongside your recipe.

### 5 — Validate

```bash
cd docs
node tools/validate-cookbook.mjs
```

All recipes must report ✅ before the PR can be merged.

---

## Modifying the allowed tag list

1. Add the new value to `ALLOWED_TAGS` in `tools/validate-cookbook.mjs`.
2. Add it to the `tags` comment block in `recipe-template.mdx`.
3. If it maps to a new category, add `_category_.json` in the new folder.
4. Regenerate the index and commit.

---

## Modifying the UI components

| File | What it controls |
|---|---|
| `src/components/Cookbook/CookbookIndex.jsx` | Fetches index, renders search/filter controls and card grid |
| `src/components/Cookbook/CookbookCard.jsx` | Single recipe card layout (title, description, tags, difficulty badge) |
| `src/components/Cookbook/CookbookIndex.module.css` | All scoped styles; uses `--dwc-*` and `--ifm-*` CSS tokens so both light and dark modes work automatically |

Styles use the DWC design-token system (`--dwc-color-primary`, `--dwc-color-default`,
`--dwc-surface-2`, etc.) and follow the same conventions as `BlogFilterBar.module.css`.

---

## File exclusions

The following files are excluded from the Docusaurus build via the `exclude` option
in `docusaurus.config.js` and will never appear in the sidebar or as served pages:

- `recipe-template.mdx` — contributor template
- `README.md` — this file
