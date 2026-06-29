---
sidebar_position: 1
title: Testing
description: >-
  Combine JUnit unit tests with Selenium or Playwright end-to-end tests to
  validate webforJ components, logic, and full user journeys.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 3c566f2e9edf3bf00e984a01e0b2f049
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# webforJ-Testen

Das Testen in webforJ-Apps kombiniert Unit-, Frontend- und End-to-End (E2E) -Tests, die jeweils einen besonderen Zweck haben, um die App stabil und zuverlässig zu halten.

## Unit-Tests {#unit-testing}

Unit-Tests konzentrieren sich darauf, einzelne Komponenten oder Backend-Logik isoliert zu überprüfen. Durch die Einhaltung standardmäßiger Java-Testpraktiken, wie z.B. der Verwendung von [JUnit](https://junit.org/junit5/), können Entwickler spezifische App-Logik effizient validieren und sicherstellen, dass jede "Einheit" wie erwartet funktioniert.

## Frontend-Tests {#frontend-testing}

Frontend-Tests decken die Quellen ab, die ein Projektautor mit dem [Frontend-Bundler](/docs/managing-resources/bundler/overview) erstellt. Der [Bun](https://bun.sh/) Testläufer führt sie als Teil des gleichen Builds aus, der die Java-Tests ausführt, sodass eine TypeScript-Komponente oder ein Stück Client-Logik auf die gleiche Weise wie das Backend überprüft wird. Siehe [Frontend-Tests](./frontend-testing).

## End-to-End (E2E) -Tests {#end-to-end-e2e-testing}

End-to-End-Tests sind wichtig, um die Benutzererfahrung in webforJ-Apps zu validieren, die dynamische, einseitige Weboberflächen generieren. Diese Tests simulieren Benutzerinteraktionen und überprüfen die Funktionen der gesamten App.

Mit Werkzeugen wie [**Selenium**](https://www.selenium.dev/) und [**Playwright**](https://playwright.dev/java/docs/intro) können Sie:

- Browserinteraktionen automatisieren, wie z.B. Schaltflächenklicks und Formularübermittlungen.
- Konsistente Darstellung und Interaktivität von dynamischen UI-Komponenten überprüfen.
- Sicherstellen, dass das Verhalten über verschiedene Browser und Geräte hinweg konsistent ist.

## Kombination von Teststrategien {#combining-testing-strategies}

Durch die Kombination von Unit- und E2E-Tests:

1. **Probleme isolieren**: Komponentenspezifische Fehler frühzeitig mit Unit-Tests erkennen und beheben.
2. **Zuverlässigkeit sicherstellen**: Vollständige Benutzerreisen und Systemintegrationen mit E2E-Tests validieren.

## Themen {#topics}

<DocCardList className="topics-section" />
