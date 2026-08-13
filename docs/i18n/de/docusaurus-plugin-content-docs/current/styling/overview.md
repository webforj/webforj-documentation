---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Style webforJ apps with the DWC design system using CSS custom properties,
  palettes, shadow parts, and the Figma kit.
_i18n_hash: 40e7755b35318ea88eb990c6b6dbd240
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

webforJ wird mit einem umfassenden Designsystem namens **DWC** geliefert. Es ist mehr als nur ein Thema, es ist ein strukturiertes, erweiterbares System, das die visuelle Sprache Ihrer App regelt. DWC wurde entwickelt, um Entwicklern und Designern zu helfen, konsistente, markenorientierte Schnittstellen schnell und mit Zuversicht zu erstellen.

Im Kern bietet DWC eine Reihe sorgfältig gestalteter CSS-Variablen (Design-Tokens), die wichtige visuelle Elemente wie Farben, Typografie, Grenzen und Abstände abdecken. Diese Tokens dienen als grundlegende Bausteine für alle Komponentenstile und ermöglichen eine globale Anpassung mit minimalem Aufwand.

Um fortgeschrittenere Stile zu unterstützen, nutzt webforJ CSS Shadow Parts, die es ermöglichen, interne Komponenten selektiv zu stylen, ohne die Kapselung zu brechen. Dies gibt Teams eine feinkörnige Kontrolle darüber, wie Komponenten aussehen, selbst in größeren Anwendungen.

DWC umfasst auch eine anpassbare Farbpalette und wird standardmäßig mit einem klaren, hellen visuellen Thema geliefert, aber jeder Aspekt kann an Ihren Marken- oder Produktstil angepasst werden.

<AISkillTip skill="webforj-styling-apps" />

## Figma Design-Kit {#figma-design-kit}

Die [DWC Figma-Bibliothek](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) ist die offizielle Designressource für die Erstellung moderner, unternehmensgerechter Webanwendungen. Sie umfasst eine umfassende Sammlung von Komponenten, typografischen Stilen und Farb-Tokens, die mit dem DWC-Designsystem übereinstimmen. Designer und Entwickler können diese Bibliothek nutzen, um visuell konsistente, benutzerfreundliche Schnittstellen mit vorhersehbarem Komponentenverhalten, präzisen Abständen und barrierefreiem Farbkontrast zu erstellen.

<img src="/img/dwc.png" alt="Figma Design-Kit" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>
<!-- > ![Figma Design-Kit Screenshot](./path-to-your-screenshot.png) -->

## Themen {#topics}

<DocCardList className="topics-section" />
