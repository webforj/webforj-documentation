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

# webforJ Testaus

Testaus webforJ-sovelluksissa yhdistää yksikkö-, frontend- ja päättäväinen (E2E) testaus, joista jokainen palvelee erityistä tarkoitusta sovelluksen vakauden ja luotettavuuden ylläpitämisessä.

## Yksikkötestaus {#unit-testing}

Yksikkötestaus keskittyy yksittäisten komponenttien tai taustalogiikan tarkistamiseen eristyksissä. Noudata standardeja Java-testauksen käytäntöjä, kuten [JUnit](https://junit.org/junit5/), jotta kehittäjät voivat tehokkaasti validoida sovelluksen logiikkaa ja varmistaa, että jokainen "yksikkö" toimii odotetusti.

## Frontend-testaus {#frontend-testing}

Frontend-testaus kattaa projektin kirjalliset lähteet [frontend-bundlerin](/docs/managing-resources/bundler/overview) avulla. [Bun](https://bun.sh/) -testisuorittaja ajaa ne osana samaa rakennetta, joka suorittaa Java-testit, joten TypeScript-komponentti tai osa asiakaslogiikkaa vahvistetaan samalla tavalla kuin backend. Katso [Frontend-testaus](./frontend-testing).

## Päättäväinen (E2E) testaus {#end-to-end-e2e-testing}

Päättäväinen testaus on tärkeää käyttäjäkokemuksen validoimiseksi webforJ-sovelluksissa, jotka luovat dynaamisia, yksisivuisia verkkoliittymiä. Nämä testit simuloivat käyttäjävuorovaikutuksia ja vahvistavat koko sovelluksen ominaisuuksia.

Käytä työkaluja kuten [**Selenium**](https://www.selenium.dev/) ja [**Playwright**](https://playwright.dev/java/docs/intro), jotta voit:

- Automatisoida selaimen vuorovaikutuksia, kuten painikkeen napsautuksia ja lomakkeiden lähetyksiä.
- Vahvistaa dynaamisten käyttöliittymäkomponenttien johdonmukaisen renderöinnin ja vuorovaikutteisuuden.
- Varmistaa käyttäytymisen johdonmukaisuuden eri selaimissa ja laitteissa.

## Testausstrategioiden yhdistäminen {#combining-testing-strategies}

Yhdistämällä yksikkö- ja E2E-testauksen:

1. **Eristä ongelmat**: Tunnista ja ratkaise komponenttitason virheitä varhaisessa vaiheessa yksikkötestauksen avulla.
2. **Varmista luotettavuus**: Validoi täydelliset käyttäjäpolut ja järjestelmäintegraatiot E2E-testauksen avulla.

## Aiheet {#topics}

<DocCardList className="topics-section" />
