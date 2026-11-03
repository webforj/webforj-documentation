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

# Testen met webforJ

Testen in webforJ-apps combineert unit-, frontend- en end-to-endtests (E2E), die elk een eigen doel dienen om de app stabiel en betrouwbaar te houden.

## Unittests {#unit-testing}

Unittests zijn gericht op het afzonderlijk controleren van individuele componenten of backendlogica. Door standaard Java-testpraktijken te volgen, zoals het gebruik van [JUnit](https://junit.org/junit5/), kunnen ontwikkelaars specifieke applogica valideren en controleren of elke ‘unit’ naar verwachting werkt.

Voor views en componenten maakt de [TestBed](./testbed/overview) browserloos testen mogelijk in gewone JUnit-tests, zonder browser of server. Een test voegt een view toe aan de pagina, voert er acties op uit zoals een gebruiker dat zou doen en controleert het resultaat vanuit Java.

## Frontendtests {#frontend-testing}

Frontendtests omvatten de bronnen die een project zelf schrijft met de [frontendbundler](/docs/managing-resources/bundler/overview). De test-runner van [Bun](https://bun.sh/) voert deze uit als onderdeel van dezelfde build als de Java-tests, zodat een TypeScript-component of een stukje clientlogica op dezelfde manier wordt gecontroleerd als de backend. Zie [Frontendtests](./frontend-testing).

## End-to-endtests (E2E) {#end-to-end-e2e-testing}

End-to-endtests valideren wat gebruikers ervaren in webforJ-apps, die dynamische webinterfaces met één pagina genereren. Deze tests simuleren gebruikersinteracties en controleren de functies van de volledige app.

Met tools zoals [**Selenium**](https://www.selenium.dev/) en [**Playwright**](https://playwright.dev/java/docs/intro) kun je:

- Browserinteracties automatiseren door klikken op knoppen en het verzenden van formulieren te simuleren.
- Controleren of dynamische UI-componenten consistent worden weergegeven en interactief zijn.
- Bevestigen dat het gedrag consistent blijft in verschillende browsers en op verschillende apparaten.

## Teststrategieën combineren {#combining-testing-strategies}

Door unit- en E2E-tests te combineren:

1. **Problemen isoleren**: detecteer en verhelp bugs op componentniveau vroegtijdig met unittests.
2. **Betrouwbaarheid bevestigen**: valideer volledige gebruikersroutes en systeemintegraties met E2E-tests.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
