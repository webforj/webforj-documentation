---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
description: Style webforJ apps with the DWC design system using CSS custom properties, palettes, shadow parts, and the Figma kit.
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

webforJ ships with a comprehensive design system named **DWC**. It's more than just a theme, it's a structured, extensible system that governs the visual language of your app. DWC is built to help developers and designers create consistent, brand-aligned interfaces quickly and confidently.

At its core, DWC provides a set of carefully designed CSS variables (design tokens) that cover key visual elements like colors, typography, borders, and spacing. These tokens serve as the foundational building blocks for all component styles and allow global customization with minimal effort.

To support more advanced styling, webforJ uses CSS Shadow Parts, allowing component internals to be selectively styled without breaking encapsulation. This gives teams fine-grained control over how components appear, even across larger applications.

DWC also includes a customizable color palette and defaults to a clean, light visual theme, but every aspect can be adapted to your brand or product style.

<AISkillTip skill="webforj-styling-apps" />

## Figma design kit {#figma-design-kit}

The [DWC Design Kit](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) is the official Figma resource for designing webforJ apps. It covers every DWC component with its themes, expanses, and states, along with the color palettes, typography, spacing, and shadow tokens of the design system as Figma variables and styles in both light and dark mode. Designers and developers can use the kit to build visually consistent, user-friendly interfaces with predictable component behavior, precise spacing, and accessible color contrast.

<iframe
  title="DWC Design Kit"
  src="https://embed.figma.com/design/xZVIDRnF7FJ3Dibb5At2lU/DWC-Design-Kit?node-id=6707-254&embed-host=webforj-docs"
  style={{width: '100%', aspectRatio: '16 / 10', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
  loading="lazy"
  allowFullScreen
/>

## Topics {#topics}

<DocCardList className="topics-section" />