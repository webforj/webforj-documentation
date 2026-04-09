---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
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

webforJ ships with a comprehensive design system named **DWC**. It’s more than just a theme, it’s a structured, extensible system that governs the visual language of your app. DWC is built to help developers and designers create consistent, brand-aligned interfaces quickly and confidently.

At its core, DWC provides a set of carefully designed CSS variables (design tokens) that cover key visual elements like colors, typography, borders, and spacing. These tokens serve as the foundational building blocks for all component styles and allow global customization with minimal effort.

To support more advanced styling, webforJ leverages CSS Shadow Parts, allowing component internals to be selectively styled without breaking encapsulation. This gives teams fine-grained control over how components appear, even across larger applications.

DWC also includes a customizable color palette and defaults to a clean, light visual theme, but every aspect can be adapted to your brand or product style.

## Topics {#topics}

<DocCardList className="topics-section" />