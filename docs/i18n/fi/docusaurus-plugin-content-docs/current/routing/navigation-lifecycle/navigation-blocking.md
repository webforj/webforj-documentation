---
sidebar_position: 3
title: Navigation Blocking
description: >-
  Intercept navigation with WillLeaveObserver veto handlers and the beforeunload
  event to guard unsaved changes.
_i18n_hash: 0deeb3e0583fdd425fe2a604ee1e9164
---
Navigoinnin estäminen lisää yhden tai useamman hallintakerroksen koko taustalla olevaan reititin-API:in. Jos estäviä käsittelijöitä on läsnä, navigointi estetään seuraavasti:

Jos navigointi laukaistaan jollain reitittimen tasolla kontrolloidulla toiminnolla, voit suorittaa minkä tahansa tehtävän tai näyttää käyttäjälle käyttöliittymäkyselyn vahvistaaksesi toiminnon. Jokainen komponentti, joka toteuttaa `WillLeaveObserver` [reitinhierarkiassa](../route-hierarchy/overview), kutsutaan. Toteuttajan on kutsuttava `accept` jatkaakseen navigointia tai `reject` estääkseen sen. Jos useat komponentit toteuttavat `WillLeaveObserver` reitin puussa, estokäyttäjät suoritetaan järjestyksessä käänteisessä järjestyksessä.

:::info Käytännön esimerkki estämisen käsittelystä
Näyttääksesi, kuinka estäminen toimii käytännössä, katso [Käyttöliittymäelävaiheita esimerkkejä](observers#example-handling-unsaved-changes-with-willleaveobserver)
:::

Sivutapahtumissa, joita ei voida suoraan hallita, reititin ei sekoitu tai pakota tiettyä käyttäytymistä. Kehittäjät voivat silti kuunnella [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) -tapahtumaa viimeisenä yrityksenä varoittaa käyttäjää tallentamattomista tiedoista tarvittaessa.

```java
PageEventOptions options = new PageEventOptions();
options.setCode("""
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Selaimen takaisinpainike {#browser-back-button}

Takaisinpainike toimii web-sovellusten kontrollin ulkopuolella, mikä tekee sen toiminnan katkaisemisesta haastavaa kaikissa selaimissa johdonmukaisesti. Sen sijaan, että yrittäisit estää takaisinpainiketta, on tehokkaampaa suunnitella käyttöliittymäsi/käyttäjäkokemuksesi siten, että sen vaikutusta vähennetään. Harkitse strategioita, kuten tallentamattomien tietojen tallentamista [istuntotallennukseen](../../advanced/web-storage#session-storage), jotta jos käyttäjä siirtyy pois ja palaa, heidän edistymisensä palautuu turvallisesti. Tämä lähestymistapa varmistaa tietosuojan ilman, että luotetaan epäluotettavaan selaimen käyttäytymiseen.
