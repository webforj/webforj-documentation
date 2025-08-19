---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 4e709dc1db793a4ae1ed6f944375b512
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

webforJ wird mit einem umfassenden Designsysten namens **DWC** ausgeliefert. Es ist mehr als nur ein Thema; es ist ein strukturiertes, erweiterbares System, das die visuelle Sprache Ihrer App steuert. DWC wurde entwickelt, um Entwicklern und Designern zu helfen, konsistente, markenorientierte Benutzeroberflächen schnell und selbstbewusst zu erstellen.

Im Kern bietet DWC eine Reihe sorgfältig gestalteter CSS-Variablen (Design-Tokens), die wichtige visuelle Elemente wie Farben, Typografie, Rahmen und Abstände abdecken. Diese Tokens dienen als grundlegende Bausteine für alle Komponentenstile und ermöglichen eine globale Anpassung mit minimalem Aufwand.

Um fortschrittlichere Stile zu unterstützen, nutzt webforJ CSS Shadow Parts, wodurch die internen Komponenten selektiv stilisiert werden können, ohne die Kapselung zu brechen. Dies gibt den Teams eine feinkörnige Kontrolle darüber, wie Komponenten erscheinen, selbst in größeren Anwendungen.

DWC umfasst außerdem eine anpassbare Farbpalette und standardmäßig ein sauberes, helles visuelles Thema, aber jeder Aspekt kann an den Stil Ihrer Marke oder Ihres Produkts angepasst werden.

## Figma Design Kit {#figma-design-kit}

Die [DWC Figma-Bibliothek](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) ist die offizielle Designressource zur Erstellung moderner, unternehmensgerechter Webanwendungen. Sie enthält eine umfassende Reihe von Komponenten, Typografiestilen und Farb-Tokens, die mit dem DWC-Designsystem übereinstimmen. Mit dieser Bibliothek können Designer und Entwickler visuell konsistente, benutzerfreundliche Oberflächen erstellen, die Funktionalität mit einer raffinierten Benutzererfahrung in Einklang bringen.

<img src="/img/dwc.png" alt="Figma Design Kit" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

## Themen {#topics}

<DocCardList className="topics-section" />
