---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Style webforJ apps with the DWC design system using CSS custom properties,
  palettes, shadow parts, and the Figma kit.
_i18n_hash: bacf450dadef59e4496e78d465a1e44d
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

webforJ wird mit einem umfassenden Designsystem namens **DWC** ausgeliefert. Es ist mehr als nur ein Thema; es ist ein strukturiertes, erweiterbares System, das die visuelle Sprache Ihrer App regelt. DWC wurde entwickelt, um Entwicklern und Designern zu helfen, schnell und selbstbewusst konsistente, markenorientierte Benutzeroberflächen zu erstellen.

Im Kern bietet DWC eine Reihe sorgfältig gestalteter CSS-Variablen (Design-Tokens), die wichtige visuelle Elemente wie Farben, Typografie, Rahmen und Abstände abdecken. Diese Tokens dienen als grundlegende Bausteine für alle Komponentenstile und ermöglichen eine globale Anpassung mit minimalem Aufwand.

Um fortgeschrittenere Stile zu unterstützen, verwendet webforJ CSS Shadow Parts, die es ermöglichen, interne Komponenten selektiv zu gestalten, ohne die Kapselung zu brechen. Dies gibt Teams eine feinkörnige Kontrolle darüber, wie Komponenten erscheinen, selbst in größeren Anwendungen.

DWC enthält auch eine anpassbare Farbpalette und setzt standardmäßig ein sauberes, helles visuelles Thema, aber jeder Aspekt kann an Ihren Marken- oder Produktstil angepasst werden.

<AISkillTip skill="webforj-styling-apps" />

## Figma-Designkit {#figma-design-kit}

Das [DWC Design Kit](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) ist die offizielle Figma-Ressource für das Design von webforJ-Apps. Es deckt jede DWC-Komponente mit ihren Themen, Ausdehnungen und Zuständen ab, zusammen mit den Farbpaletten, der Typografie, den Abständen und den Schatten-Tokens des Designsystems als Figma-Variablen und -Stile in sowohl hellen als auch dunklen Modi. Designer und Entwickler können das Kit verwenden, um visuell konsistente, benutzerfreundliche Benutzeroberflächen mit vorhersehbarem Komponentenverhalten, präzisen Abständen und zugänglichem Farbkontrast zu erstellen.

<iframe
  title="DWC Design Kit"
  src="https://embed.figma.com/design/xZVIDRnF7FJ3Dibb5At2lU/DWC-Design-Kit?node-id=6707-254&embed-host=webforj-docs"
  style={{width: '100%', aspectRatio: '16 / 10', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
  loading="lazy"
  allowFullScreen
/>

## Themen {#topics}

<DocCardList className="topics-section" />
