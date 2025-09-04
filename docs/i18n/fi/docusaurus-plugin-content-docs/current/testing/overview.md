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

# webforJ Testaus

Testaus webforJ-sovelluksissa sisältää yhdistelmän yksikkö- ja päätepisteitä (E2E) testauksesta, jonka avulla varmistetaan vakaa ja luotettava sovellus. Jokaisella testauksen tyypillä on oma tarkoituksensa sovelluksen laadun ylläpitämisessä.

## Yksikkötestaus {#unit-testing}

Yksikkötestaus keskittyy yksittäisten komponenttien tai taustalogikan tarkistamiseen eristyksissä. Noudata standardeja Java-testauksen käytäntöjä, kuten [JUnit](https://junit.org/junit5/), jotta kehittäjät voivat tehokkaasti validoida tiettyä sovelluslogiikkaa ja varmistaa, että jokainen "yksikkö" toimii odotetusti.

## Päätepisteet (E2E) testaus {#end-to-end-e2e-testing}

Päätepisteiden testaus on tärkeää käyttäjäkokemuksen validoimiseksi webforJ-sovelluksissa, jotka luovat dynaamisia, yhden sivun verkkoliittymiä. Nämä testit simuloivat käyttäjän vuorovaikutuksia ja vahvistavat koko sovelluksen ominaisuudet.

Käyttämällä työkaluja kuten [**Selenium**](https://www.selenium.dev/) ja [**Playwright**](https://playwright.dev/java/docs/intro), voit:

- Automatisoida selaimen vuorovaikutuksia, kuten painikkeiden klikkauksia ja lomakkeiden lähettämistä.
- Vahvistaa dynaamisten käyttöliittymäkomponenttien johdonmukaisen renderöinnin ja vuorovaikutuksen.
- Varmistaa käyttäytymisen johdonmukaisuuden eri selaimissa ja laitteissa.

## Testausstrategioiden yhdistäminen {#combining-testing-strategies}

Yhdistämällä yksikkö- ja E2E-testauksen:

1. **Eristä ongelmat**: Havaita ja ratkaista komponenttikohtaiset virheet aikaisessa vaiheessa yksikkötestauksella.
2. **Varmista luotettavuus**: Vahvistaa täydelliset käyttäjäreitit ja järjestelmäintegraatiot E2E-testauksella.

## Aiheet {#topics}

<DocCardList className="topics-section" />
