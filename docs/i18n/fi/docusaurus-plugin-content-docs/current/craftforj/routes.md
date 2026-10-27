---
title: Reitit
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: 8a8c4099d3bd0d4ff988038cee6a5c15
---
Väylät-välilehti näyttää käynnissä olevan sovelluksen reittitaulukon [hierarkiassa](/docs/routing/route-hierarchy/overview), jonka reititin pitää hallussaan, ja aktiivinen reitti on merkitty. Dynaamisesti rekisteröidyt reitit näkyvät yhdessä merkittyjen kanssa [dynaamisesti](/docs/routing/routes-registration).

![Reittipuu aktiivinen reitti merkittynä](/img/craftforj/routes/tree.png#rounded-border)

## Reitin tiedot {#route-details}

Reitin valitseminen näyttää, mitä reititin tietää siitä, mukaan lukien sen polku, sen takana oleva luokka, siihen liitetyt elinkaaren tarkkailijat ja sen konfigurointi. Voit avata tämän luokan lähdekatsojassa täältä.

## Navigointi craftforJ:stä {#navigating-from-craftforj}

Voit navigoida mihin tahansa reittiin suoraan craftforJ:stä. Parametreja ottavat reitit tarjoavat kentän jokaiselle niistä ja ratkaisevat polun, kun täytät ne, joten voit vahvistaa, mihin saavut ennen kuin siirryt.

Tämäntyyppinen navigointi on todellista navigointia, joten sovelluksesi [elinkaaren tarkkailijat](/docs/routing/navigation-lifecycle/observers) toimivat täsmälleen kuten käyttäjälle. Puu seuraa myös sovellusta, joten navigointi sovelluksessa itsessään siirtää merkkiä.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4" type="video/mp4" />
  </video>
</div>

## Käyttösäännöt {#access-rules}

Jokaisella reitillä on merkki [turvallisuusannotaatiosta](/docs/security/annotations), joka on sille deklaroitu, ja voit rajoittaa puuta julkisiin tai suojattuihin reitteihin työkalupalkista.

Vain `@RolesAllowed` ja `@DenyAll` lasketaan suojatuiksi. `@PermitAll` ei nimeä rooleja ja vaatii vain, että joku on kirjautunut sisään, joten suodatin käsittelee sitä julkisena. Pidä tämä mielessä tarkistaessasi, mitkä reitit rajoittavat pääsyä roolin mukaan.

![Reittipuu käyttömerkinnällä jokaisella reitillä](/img/craftforj/routes/access-badge.png#rounded-border)

Voit myös muuttaa reitin käyttöoikeussääntöä craftforJ:stä. CraftforJ kirjoittaa annotaation reitin luokkaan ja sovellus käynnistää uudelleen, joten muutos käy läpi saman tarkastuksen kuin mikä tahansa muu [lähteen muutos](/docs/craftforj/source-changes). Vaihtoehto ei ole käytettävissä, kun craftforJ:lle ei sallita Javaan kirjoittamista.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4" type="video/mp4" />
  </video>
</div>
