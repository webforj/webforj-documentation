---
title: Styling
hide_table_of_contents: true
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

## Figma Design Kit

The [DWC Figma library](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) is the official design resource for creating modern, enterprise-grade web applications. It includes a comprehensive set of components, typography styles, and color tokens that align with the DWC design system. With this library, designers and developers can build visually consistent, user-friendly interfaces that balance functionality with a refined user experience.

<img src="/img/dwc.png" alt="Figma Design Kit" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>  
<!-- > ![Figma Design Kit Screenshot](./path-to-your-screenshot.png) -->

## Topics

<DocCardList className="topics-section" />