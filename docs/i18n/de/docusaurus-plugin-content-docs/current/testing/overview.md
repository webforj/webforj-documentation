---
sidebar_position: 1
title: Testen
description: >-
  Combine JUnit unit tests with Selenium or Playwright end-to-end tests to
  validate webforJ components, logic, and full user journeys.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 58ac266506a221d71e22c723189aa08f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# webforJ-Tests

Tests in webforJ-Apps umfassen Unit-, Frontend- und End-to-End-Tests (E2E), die jeweils einen eigenen Zweck erfüllen und dazu beitragen, die App stabil und zuverlässig zu halten.

## Unit-Tests {#unit-testing}

Unit-Tests konzentrieren sich darauf, einzelne Komponenten oder die Backend-Logik isoliert zu überprüfen. Mit standardmäßigen Java-Testverfahren wie [JUnit](https://junit.org/junit5/) können Entwickler:innen bestimmte App-Logik validieren und prüfen, ob jede „Einheit“ wie erwartet funktioniert.

Für Views und Komponenten ermöglicht das [TestBed](./testbed/overview) browserloses Testen in einfachen JUnit-Tests – ganz ohne Browser oder Server. Ein Test fügt der Seite eine View hinzu, interagiert mit ihr wie ein Benutzer und überprüft das Ergebnis in Java.

## Frontend-Tests {#frontend-testing}

Frontend-Tests decken die Quellen ab, die ein Projekt mit dem [Frontend-Bundler](/docs/managing-resources/bundler/overview) erstellt. Der [Bun](https://bun.sh/)-Test-Runner führt sie als Teil desselben Builds aus, in dem auch die Java-Tests laufen. So wird eine TypeScript-Komponente oder ein Stück Client-Logik genauso überprüft wie das Backend. Weitere Informationen findest du unter [Frontend-Tests](./frontend-testing).

## End-to-End-Tests (E2E) {#end-to-end-e2e-testing}

End-to-End-Tests prüfen, was Benutzer:innen in webforJ-Apps erleben, die dynamische Single-Page-Weboberflächen erzeugen. Diese Tests simulieren Benutzerinteraktionen und überprüfen die Funktionen der gesamten App.

Mit Tools wie [**Selenium**](https://www.selenium.dev/) und [**Playwright**](https://playwright.dev/java/docs/intro) kannst du:

- Browserinteraktionen automatisieren, indem du Klicks auf Schaltflächen und das Absenden von Formularen simulierst.
- Eine konsistente Darstellung und Interaktivität dynamischer UI-Komponenten überprüfen.
- Sicherstellen, dass das Verhalten über verschiedene Browser und Geräte hinweg konsistent bleibt.

## Teststrategien kombinieren {#combining-testing-strategies}

Durch die Kombination von Unit- und E2E-Tests kannst du:

1. **Probleme isolieren**: Fehler auf Komponentenebene mithilfe von Unit-Tests frühzeitig erkennen und beheben.
2. **Zuverlässigkeit sicherstellen**: Vollständige Benutzerabläufe und Systemintegrationen mit E2E-Tests validieren.

## Themen {#topics}

<DocCardList className="topics-section" />
