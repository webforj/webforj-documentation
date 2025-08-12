---
sidebar_position: 1
title: Testing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 34ef676ce8690df9732e6bee7af56206
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# webforJ Testing

Die Tests in webforJ-Apps umfassen eine Kombination aus Unit- und End-to-End (E2E)-Tests, um eine stabile und zuverlässige App sicherzustellen. Jede Art von Test erfüllt einen bestimmten Zweck bei der Aufrechterhaltung der App-Qualität.

## Unit-Tests {#unit-testing}

Unit-Tests konzentrieren sich darauf, einzelne Komponenten oder Backend-Logik isoliert zu überprüfen. Durch die Befolgung standardmäßiger Java-Testpraktiken, wie beispielsweise der Verwendung von [JUnit](https://junit.org/junit5/), können Entwickler spezifische App-Logik effizient validieren und sicherstellen, dass jede "Einheit" wie erwartet funktioniert.

## End-to-End (E2E)-Tests {#end-to-end-e2e-testing}

End-to-End-Tests sind wichtig, um die Benutzererfahrung in webforJ-Apps zu validieren, die dynamische, einseitige Webschnittstellen generieren. Diese Tests simulieren Benutzerinteraktionen und überprüfen die Funktionen der gesamten App.

Mit Tools wie [**Selenium**](https://www.selenium.dev/) und [**Playwright**](https://playwright.dev/java/docs/intro) können Sie:

- Browserinteraktionen automatisieren, wie z.B. Schaltflächenklicks und Formularübermittlungen.
- Konsistente Darstellung und Interaktivität dynamischer UI-Komponenten überprüfen.
- Verhaltenskonsistenz in verschiedenen Browsern und Geräten sicherstellen.

## Kombination von Teststrategien {#combining-testing-strategies}

Durch die Kombination von Unit- und E2E-Tests:

1. **Probleme isolieren**: Komponentenbezogene Fehler frühzeitig mit Unit-Tests erkennen und beheben.
2. **Zuverlässigkeit gewährleisten**: Vollständige Benutzerreisen und Systemintegrationen mit E2E-Tests validieren.

## Themen {#topics}

<DocCardList className="topics-section" />
