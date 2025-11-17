---
sidebar_position: 3
title: Navigoinnin estäminen
_i18n_hash: a08d56654914719e12d1401d263c7956
---
Navigointia estävä lisää yhden tai useamman kerroksen hallintaa koko taustalla olevalle reititin-API:lle. Jos estäviä käsittelijöitä on läsnä, navigointi estetään seuraavasti:

Jos navigointi laukaistaan jollakin reitittimen tasolla hallitulla toiminnolla, voit suorittaa minkä tahansa tehtävän tai näyttää käyttäjälle käyttöliittymäkehotteen toiminnon vahvistamiseksi. Jokainen komponentti, joka toteuttaa `WillLeaveObserver`-sovelluksen [reitinhierarkiassa](../route-hierarchy/overview), kutsutaan. Toteuttajan on kutsuttava `accept` jatkaakseen navigointia tai `reject` estääkseen sen. Jos useampi komponentti toteuttaa `WillLeaveObserver` reitin puussa, estokäsittelijät suoritetaan peräkkäin käänteisessä järjestyksessä

:::info Käytännön esimerkki estotoiminnasta
Jos haluat nähdä, miten estäminen toimii käytännössä, tutustu [Käyttöliittymän elinkaaren tarkastajien esimerkkeihin](observers#example-handling-unsaved-changes-with-willleaveobserver)
:::

Sivutapahtumille, joita ei voida hallita suoraan, reititin ei puutu tai pakota tiettyä käyttäytymistä. Kehittäjät voivat kuitenkin edelleen kuunnella [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) -tapahtumaa tehdäksesi viimeisen yrityksen varoittaa käyttäjää tallentamattomasta datasta, jos se on tarpeen.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Selaimen takapainike {#browser-back-button}

Takapainike toimii web-sovellusten hallinnan ulkopuolella, mikä tekee sen toiminnan katkaisemisesta tai estämisestä vaikeaa kaikissa selaimissa johdonmukaisesti. Sen sijaan, että yrittäisit estää takapainiketta, on tehokkaampaa suunnitella käyttöliittymäsi/käyttökokemuksesi tavalla, joka vähentää vaikutusta. Mieti strategioita, kuten tallentaa tallentamaton data [istuntotallennukseen](../../advanced/web-storage#session-storage), jotta käyttäjä, jos hän siirtyy pois ja palaa, voi palauttaa edistymisensä turvallisesti. Tämä lähestymistapa varmistaa tietosuojan luottamatta epäluotettavaan selaimen käyttäytymiseen.
