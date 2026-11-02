---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: b276ce8df35b91ed18f07c8a686fb688
---
Reitit-välilehti näyttää käynnissä olevan sovelluksen reititystaulun [hierarkiassa](/docs/routing/route-hierarchy/overview), jossa reitit, joiden aktiivinen reitti on merkitty. Dynaamisesti rekisteröidyt reitit [dynamically](/docs/routing/routes-registration) näkyvät rinnakkain merkittyjen reittien kanssa.

![Reitit, joissa aktiivinen reitti on merkitty](/img/craftforj/routes/tree.png#rounded-border)

## Reititiedot {#route-details}

Reitin valitseminen näyttää, mitä reitittimellä on tiedossaan, mukaan lukien sen polku, sen taustalla oleva luokka, siihen liitetyt elinkaaren tarkkailijat sekä sen konfiguraatio. Voit avata tämän luokan lähdekatseluohjelmassa täältä.

## Navigointi craftforJ:stä {#navigating-from-craftforj}

Voit navigoida suoraan mihin tahansa reittiin craftforJ:stä. Parametreja ottavat reitit tarjoavat kentän jokaista parametria varten ja tarkastavat polun, kun täytät kenttiä, jotta voit vahvistaa, mihin päädyt ennen etenemistä.

Tämäntyyppinen navigointi on todellista navigointia, joten sovelluksesi [elinkaaren tarkkailijat](/docs/routing/navigation-lifecycle/observers) suoritetaan aivan kuten käyttäjälle. Puu seuraa myös sovellusta, joten navigointi itse sovelluksessa siirtää merkkiä.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Käyttöoikeussäännöt {#access-rules}

Jokaisella reitillä on badge [turvallisuusmerkinnästä](/docs/security/annotations), joka on siihen julistettu, ja voit rajata puuta julkisiin tai suojattuihin reitteihin työkalupalkista.

Vain `@RolesAllowed` ja `@DenyAll` lasketaan suojatuiksi. `@PermitAll` ei määritä rooleja ja vaatii vain, että joku on kirjautunut sisään, joten suodatin käsittelee sen julkisena. Pidä tämä mielessä tarkistaessasi, mitkä reitit rajoittavat käyttöoikeuksia roolin mukaan.

![Reitit, joissa käyttöoikeusbadge on jokaisessa reitissä](/img/craftforj/routes/access-badge.png#rounded-border)

Voit myös muuttaa reitin käyttöoikeussääntöä craftforJ:stä. craftforJ kirjoittaa merkinnän reitin luokkaan ja sovellus käynnistyy uudelleen, joten muutos käy läpi saman tarkastuksen kuin mikä tahansa muu [lähdemuutos](/docs/craftforj/source-changes). Vaihtoehto ei ole käytettävissä, kun craftforJ:lle ei sallita Javaan kirjoittamista.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4#t=0.5" type="video/mp4" />
  </video>
</div>
