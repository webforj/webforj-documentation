---
sidebar_position: 1
title: Testaus
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

# webforJ:n testaus

webforJ-sovellusten testaus yhdistää yksikkö-, frontend- ja päästä päähän (E2E) -testauksen. Kukin niistä palvelee omaa tarkoitustaan sovelluksen vakauden ja luotettavuuden varmistamisessa.

## Yksikkötestaus {#unit-testing}

Yksikkötestauksessa yksittäisiä komponentteja tai taustalogiikkaa testataan erillään muusta sovelluksesta. Noudattamalla Java-testauksen vakiintuneita käytäntöjä, kuten käyttämällä [JUnit](https://junit.org/junit5/)-kehystä, kehittäjät voivat validoida sovelluksen tietyn logiikan ja varmistaa, että kukin ”yksikkö” toimii odotetusti.

Näkymien ja komponenttien testaamiseen [TestBed](./testbed/overview) mahdollistaa testauksen ilman selainta tavallisissa JUnit-testeissä, ilman selainta tai palvelinta. Testissä lisätään näkymä sivulle, käytetään sitä käyttäjän tavoin ja tarkistetaan tulos Javasta käsin.

## Frontend-testaus {#frontend-testing}

Frontend-testauksessa testataan projektin [frontend-paketointityökalulla](/docs/managing-resources/bundler/overview) luomia lähteitä. [Bun](https://bun.sh/)-testiajo suorittaa ne samassa koontiprosessissa kuin Java-testit, joten TypeScript-komponentti tai pala asiakaspuolen logiikkaa testataan samalla tavalla kuin taustajärjestelmä. Katso [Frontend-testaus](./frontend-testing).

## Päästä päähän -testaus (E2E) {#end-to-end-e2e-testing}

Päästä päähän -testauksella validoidaan käyttäjien kokemus webforJ-sovelluksissa, jotka luovat dynaamisia, yksisivuisia verkkokäyttöliittymiä. Näissä testeissä simuloidaan käyttäjien toimia ja tarkistetaan koko sovelluksen ominaisuudet.

Työkaluilla, kuten [**Selenium**](https://www.selenium.dev/) ja [**Playwright**](https://playwright.dev/java/docs/intro), voit:

- Automatisoida selaimen käyttöä simuloimalla painikkeiden napsautuksia ja lomakkeiden lähettämistä.
- Varmistaa dynaamisten käyttöliittymäkomponenttien yhdenmukaisen hahmonnuksen ja vuorovaikutteisuuden.
- Varmistaa, että toiminta pysyy yhdenmukaisena eri selaimissa ja laitteissa.

## Testausstrategioiden yhdistäminen {#combining-testing-strategies}

Yhdistämällä yksikkö- ja E2E-testauksen voit:

1. **Eristää ongelmat**: havaita ja ratkaista komponenttitason virheet varhaisessa vaiheessa yksikkötestauksen avulla.
2. **Varmistaa luotettavuuden**: validoida käyttäjän koko käyttökokemus ja järjestelmäintegraatiot E2E-testauksella.

## Aiheet {#topics}

<DocCardList className="topics-section" />
