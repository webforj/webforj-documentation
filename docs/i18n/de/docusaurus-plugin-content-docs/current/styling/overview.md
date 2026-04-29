---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: abb693dec702e4a253cf4e1228fb2d7e
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

webforJ wird mit einem umfassenden Designsystem namens **DWC** geliefert. Es ist mehr als nur ein Thema, es ist ein strukturiertes, erweiterbares System, das die visuelle Sprache Ihrer App steuert. DWC wurde entwickelt, um Entwicklern und Designern zu helfen, schnell und selbstbewusst konsistente, markenorientierte Benutzeroberflächen zu erstellen.

Im Kern bietet DWC eine Reihe sorgfältig gestalteter CSS-Variablen (Design-Tokens), die wichtige visuelle Elemente wie Farben, Typografie, Ränder und Abstände abdecken. Diese Tokens dienen als grundlegende Bausteine für alle Komponentenstile und ermöglichen eine globale Anpassung mit minimalem Aufwand.

Um fortgeschrittenere Stile zu unterstützen, nutzt webforJ CSS Shadow Parts, wodurch interne Komponenten selektiv gestylt werden können, ohne die Kapselung zu verletzen. Dies gibt Teams die feingranulare Kontrolle darüber, wie Komponenten erscheinen, selbst in größeren Anwendungen.

DWC umfasst auch eine anpassbare Farbpalette und standardmäßig ein sauberes, helles visuelles Thema, aber jeder Aspekt kann an Ihren Marken- oder Produktstil angepasst werden.

<AISkillTip skill="webforj-styling-apps" />

## Figma Design-Kit {#figma-design-kit}

Die [DWC Figma-Bibliothek](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) ist die offizielle Design-Resource für die Erstellung moderner, geschäftsorientierter Webanwendungen. Sie enthält ein umfassendes Set von Komponenten, Typografiestilen und Farbtokens, die mit dem DWC-Designsystem übereinstimmen. Designer und Entwickler können diese Bibliothek nutzen, um visuell konsistente, benutzerfreundliche Schnittstellen mit vorhersehbarem Komponentenverhalten, präzisen Abständen und zugänglichem Farbkontrast zu erstellen.

<img src="/img/dwc.png" alt="Figma Design Kit" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>  
<!-- > ![Figma Design Kit Screenshot](./path-to-your-screenshot.png) -->

## Themen {#topics}

<DocCardList className="topics-section" />
