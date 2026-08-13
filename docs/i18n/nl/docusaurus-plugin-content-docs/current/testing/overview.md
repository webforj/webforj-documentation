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

# webforJ Testen

Testen in webforJ-apps combineert unit-, frontend- en end-to-end (E2E) testen, waarbij elk een specifieke rol vervult in het behouden van de stabiliteit en betrouwbaarheid van de app.

## Unit testing {#unit-testing}

Unit testing richt zich op het verifiëren van individuele componenten of backend-logica in isolatie. Door standaarden van Java-testpraktijken te volgen, zoals het gebruik van [JUnit](https://junit.org/junit5/), kunnen ontwikkelaars specifieke app-logica efficiënt valideren en ervoor zorgen dat elke "eenheid" functioneert zoals verwacht.

## Frontend testing {#frontend-testing}

Frontend testing omvat de bronnen die een projectautheur gebruikt met de [frontend bundler](/docs/managing-resources/bundler/overview). De [Bun](https://bun.sh/) test runner voert deze uit als onderdeel van dezelfde build die de Java-tests uitvoert, zodat een TypeScript-component of een stuk clientlogica op dezelfde manier wordt geverifieerd als de backend. Zie [Frontend testing](./frontend-testing).

## End-to-End (E2E) testing {#end-to-end-e2e-testing}

End-to-end testing is belangrijk voor het valideren van de gebruikerservaring in webforJ-apps, die dynamische, single-page webinterfaces genereren. Deze tests simuleren gebruikersinteracties en verifiëren de functies van de hele app.

Met tools zoals [**Selenium**](https://www.selenium.dev/) en [**Playwright**](https://playwright.dev/java/docs/intro) kun je:

- Automatiseren van browserinteracties, zoals het klikken op knoppen en het indienen van formulieren.
- Controleren op consistente weergave en interactiviteit van dynamische UI-componenten.
- Zorgen voor gedragsconsistentie over verschillende browsers en apparaten.

## Combineren van teststrategieën {#combining-testing-strategies}

Door unit- en E2E-testing te combineren:

1. **Isoleren van Problemen**: Detecteer en los bugs op componentniveau vroegtijdig op met unit testing.
2. **Zorg voor Betrouwbaarheid**: Valideer volledige gebruikersreizen en systeemintegraties met E2E testing.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
