---
title: Gestaltung
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Style webforJ apps with the DWC design system using CSS custom properties,
  palettes, shadow parts, and the Figma kit.
_i18n_hash: 18caef78e838347a9b6376fb635573d9
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

webforJ wird mit einem umfassenden Designsystem namens **DWC** ausgeliefert. Es ist mehr als nur ein Theme: Es ist ein strukturiertes, erweiterbares System, das die visuelle Sprache Ihrer App festlegt. DWC unterstützt Entwickler und Designer dabei, schnell und sicher konsistente, markengerechte Benutzeroberflächen zu erstellen.

Im Kern bietet DWC eine Reihe sorgfältig gestalteter CSS-Variablen (Design-Tokens), die wichtige visuelle Elemente wie Farben, Typografie, Rahmen und Abstände abdecken. Diese Tokens bilden die Grundlage für alle Komponentenstile und ermöglichen eine globale Anpassung mit minimalem Aufwand.

Für fortgeschrittenere Gestaltungsmöglichkeiten verwendet webforJ CSS Shadow Parts. Damit lassen sich interne Komponentenbereiche gezielt gestalten, ohne die Kapselung zu beeinträchtigen. So erhalten Teams eine detaillierte Kontrolle über das Erscheinungsbild von Komponenten, auch in größeren Anwendungen.

DWC umfasst außerdem eine anpassbare Farbpalette und verwendet standardmäßig ein klares, helles visuelles Theme. Alle Aspekte lassen sich jedoch an Ihre Marke oder den Stil Ihres Produkts anpassen.

<AISkillTip skill="webforj-styling-apps" />

## Figma-Designkit {#figma-design-kit}

Das [DWC Design Kit](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) ist die offizielle Figma-Ressource für das Design von webforJ-Apps. Es umfasst alle DWC-Komponenten mit ihren Themes, Größen und Zuständen sowie die Farbpaletten, Typografie-, Abstands- und Schatten-Tokens des Designs als Figma-Variablen und -Stile im hellen und dunklen Modus. Designer und Entwickler können das Kit verwenden, um visuell konsistente und benutzerfreundliche Oberflächen mit vorhersehbarem Komponentenverhalten, präzisen Abständen und zugänglichem Farbkontrast zu erstellen.

<a href="https://www.figma.com/community/file/1682060886525639971/dwc-design-kit" target="_blank" rel="noopener noreferrer">
  <img
    src="https://cdn.webforj.com/webforj-documentation/figma/dwc-design-kit-cover.jpg"
    alt="DWC Design Kit in der Figma Community"
    style={{width: '100%', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
    loading="lazy"
  />
</a>

## Themen {#topics}

<DocCardList className="topics-section" />
