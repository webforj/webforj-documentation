---
sidebar_position: 1
title: Testing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 9fb95791bbb594c57b7ff481ed4fe47b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# webforJ-Testen

Das Testen in webforJ-Apps umfasst eine Kombination aus Unit- und End-to-End (E2E)-Tests, um eine stabile und zuverlässige App sicherzustellen. Jede Testart hat ihren eigenen Zweck zur Aufrechterhaltung der App-Qualität.

## Unit-Tests {#unit-testing}

Unit-Tests konzentrieren sich darauf, einzelne Komponenten oder Backend-Logik isoliert zu überprüfen. Durch die Befolgung der gängigen Java-Testpraktiken, wie der Verwendung von [JUnit](https://junit.org/junit5/), können Entwickler spezifische App-Logik effizient validieren und sicherstellen, dass jede "Einheit" wie erwartet funktioniert.

## End-to-End (E2E)-Tests {#end-to-end-e2e-testing}

End-to-End-Tests sind wichtig, um die Benutzererfahrung in webforJ-Apps zu validieren, die dynamische, einseitige Webschnittstellen generieren. Diese Tests simulieren Benutzerinteraktionen und überprüfen die Funktionen der gesamten App.

Mit Tools wie [**Selenium**](https://www.selenium.dev/) und [**Playwright**](https://playwright.dev/java/docs/intro) können Sie:

- Browserinteraktionen automatisieren, wie z. B. Button-Klicks und Formularübermittlungen.
- Konsistente Darstellung und Interaktivität dynamischer UI-Komponenten überprüfen.
- Sicherstellen, dass das Verhalten auf verschiedenen Browsern und Geräten konsistent ist.

## Kombination von Teststrategien {#combining-testing-strategies}

Durch die Kombination von Unit- und E2E-Tests:

1. **Probleme isolieren**: Erkennen und Beheben von komponentenbezogenen Fehlern frühzeitig mit Unit-Tests.
2. **Zuverlässigkeit gewährleisten**: Validieren Sie vollständige Benutzerreisen und Systemintegrationen mit E2E-Tests.

## Themen {#topics}

<DocCardList className="topics-section" />
