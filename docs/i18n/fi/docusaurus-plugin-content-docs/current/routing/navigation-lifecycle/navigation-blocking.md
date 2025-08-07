---
sidebar_position: 3
title: Navigation Blocking
_i18n_hash: c0d79c6ce266eb4b9f9fd28915dcc380
---
Navigoinnin esto lisää yhden tai useamman ohjaustason koko taustalla olevaan reitittimeen. Jos mitään estäviä käsittelijöitä on läsnä, navigointi estetään seuraavasti:

Jos navigointi laukaistaan jollain reitittimen tasolla ohjatulla asiatapahtumalla, voit suorittaa tehtäviä tai näyttää käyttöliittymäilmoituksen käyttäjälle toimenpiteen vahvistamiseksi. Jokainen komponentti, joka toteuttaa `WillLeaveObserver` [reitinhierarkiassa](../route-hierarchy/overview), kutsutaan. Toteuttajan on kutsuttava `accept` jatkaakseen navigointia tai `reject` estääkseen sen. Jos useampi komponentti toteuttaa `WillLeaveObserver` reitin puussa, estokäsittelyt suoritetaan peräkkäin käänteisessä järjestyksessä.

:::info Käytännön esimerkki estokäsittelystä  
Katso, kuinka estäminen toimii käytännössä, viittaamalla [Käyttöliittymäelämän havainnoijien esimerkit](observers#example-handling-unsaved-changes-with-willleaveobserver)  
:::

Sivutapahtumille, joita ei voida hallita suoraan, reititin ei häiritse eikä pakota erityistä toimintaa. Kehittäjät voivat silti kuunnella [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) -tapahtumaa tehdäkseen viimeisen yrityksen varoittaa käyttäjää tallentamattomista tiedoista, jos se on tarpeen.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Selaimen takaisinpainike {#browser-back-button}

Takaisinpainike toimii verkkosovellusten hallinnan ulkopuolella, mikä tekee sen toiminnan keskeyttämisestä tai estämisestä haastavaa kaikissa selaimissa johdonmukaisesti. Sen sijaan, että yrittäisit estää takaisinpainiketta, on tehokkaampaa suunnitella UI/UX siten, että vaikutuksia vähennetään. Harkitse strategioita, kuten tallentamalla tallentamattomat tiedot [istuntomuistiin](../../advanced/web-storage#session-storage), jotta jos käyttäjä navigoi pois ja palaa takaisin, heidän edistymisensä palautuu turvallisesti. Tämä lähestymistapa takaa tietosuojan ilman epävakaaseen selaimen toimintaan luottamista.
