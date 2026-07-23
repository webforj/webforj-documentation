---
sidebar_position: 6
title: Documentation
description: Write and organize webforJ documentation pages, demos, links, assets, and examples consistently.
hide_giscus_comments: true
---

Documentation contributions are made in the [webforJ documentation repository](https://github.com/webforj/webforj-documentation). They should help readers complete real work with accurate explanations and runnable examples. Start from nearby pages and keep new content consistent with the section where it belongs.

## Repository layout {#repository-layout}

Common documentation paths include:

| Path | Purpose |
| --- | --- |
| `docs/docs` | Main documentation pages |
| `docs/cookbook` | Task-focused cookbook recipes |
| `docs/blog` | Blog posts and release posts |
| `docs/static` | Images, videos, generated indexes, and other static site files |
| `src/main/java/com/webforj/samples` | Java demo views rendered by documentation examples |
| `src/test/java/com/webforj/samples` | Integration tests for sample views |

Translations live under `docs/i18n`. Don't edit translated files for an English content change unless the issue specifically asks for translation work.

## Page structure {#page-structure}

Create new pages as Markdown or Markdown with JSX files in the relevant folder. Include front matter with at least:

```md
---
title: Page Title
description: A concise search-friendly summary of the page.
---
```

Use `sidebar_position` when the order matters. Use `_category_.json` only when creating or changing a section.

Overview pages commonly use `DocCardList` to link to child pages:

```mdx
## Topics {#topics}

<DocCardList className="topics-section" />
```

## Links and headings {#links-and-headings}

Prefer relative links for pages in the same documentation area and absolute `/docs/...` links when crossing sections. Keep heading anchors stable if other pages may link to them:

```md
## Pull request checklist {#pull-request-checklist}
```

Avoid changing existing anchors unless you also update every link that points to them.

## Code examples and demos {#code-examples-and-demos}

Use runnable examples when the reader needs to see behavior. Most interactive examples use `ComponentDemo` with a route and source files:

```mdx
<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>
```

When adding a demo:

- Keep Java files under the existing sample package structure.
- Use names that match the documented component or feature.
- Include only the code needed to demonstrate the concept.
- Confirm every `files` entry points to a tracked source file.
- Confirm the `path` loads the intended `@Route` while the sample app is running.
- Add or update an integration test for meaningful interactive behavior.
- Verify the docs page and sample route together.

`ComponentDemo` loads source panels from the repository's `main` branch. A new source file won't appear in the local source panel until that file is available remotely. Confirm the repository-relative path during review, and verify source panels locally for files that already exist on `main`.

## Assets {#assets}

Put inspectable screenshots and images under `docs/static/img` in a folder that matches the topic. Use descriptive file names and useful `alt` text.

Avoid adding decorative images when a code sample, diagram, or short explanation would be clearer.

## Writing style {#writing-style}

Write in direct, practical language. Explain what the feature does, when to use it, and what tradeoffs matter. Avoid unverifiable claims, vague marketing language, and examples that rely on behavior outside the current webforJ version.

Before submitting, compare the page with nearby docs for title casing, code block style, admonition use, and link patterns.

When documenting a framework change, verify the behavior against the corresponding framework implementation and tests. Cross-link the documentation pull request with its framework issue or pull request so both changes can be reviewed together.
