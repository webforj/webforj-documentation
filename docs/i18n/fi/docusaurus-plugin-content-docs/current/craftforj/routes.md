---
title: Reitit
sidebar_position: 5
description: >-
  Näe kaikki käynnissä olevan webforJ-sovelluksen rekisteröidyt reitit, siirry
  niihin craftforJ:stä ja muuta niille määritettyjä käyttöoikeussääntöjä.
_i18n_title_hash: b41067229f91bece9cdfc66681cdd7a3
_i18n_description_hash: 6221e0d73e034d35b082471687f4e420
_i18n_hash: ec7a4521cd9c4328f9cd6550ebc9d90b
---
Reitit-välilehti näyttää käynnissä olevan sovelluksen reititystaulukon siinä [hierarkiassa](/docs/routing/route-hierarchy/overview), jossa reititin säilyttää sen. Aktiivinen reitti on merkitty. [Dynaamisesti](/docs/routing/routes-registration) rekisteröidyt reitit näkyvät annotaatiolla määritettyjen reittien rinnalla.

![Reittipuu, jossa aktiivinen reitti on merkitty](/img/craftforj/routes/tree.png#rounded-border)

## Reitin tiedot {#route-details}

Kun valitset reitin, näet, mitä reititin siitä tietää: reitin polun, sen taustalla olevan luokan, siihen liitetyt elinkaaren tarkkailijat ja sen määritykset. Voit avata kyseisen luokan lähdekoodikatselimessa tästä.

## Navigointi craftforJ:stä {#navigating-from-craftforj}

Voit siirtyä suoraan mihin tahansa reittiin craftforJ:stä. Parametreja käyttävillä reiteillä on kenttä kutakin parametria varten, ja polku tarkentuu sitä mukaa kun täytät kenttiä. Näin voit varmistaa etukäteen, mihin päädyt.

Tämä on oikea navigointi, joten sovelluksesi [elinkaaren tarkkailijat](/docs/routing/navigation-lifecycle/observers) suoritetaan täsmälleen samalla tavalla kuin käyttäjän navigoidessa. Myös reittipuu seuraa sovellusta, joten sovelluksessa navigointi siirtää merkinnän uuteen kohtaan.

<VideoPlayer video="craftforj/route-navigate" width={1920} height={1000} title="Route navigate" />

## Käyttöoikeussäännöt {#access-rules}

Jokaisella reitillä on siihen määritettyä [tietoturva-annotaatiota](/docs/security/annotations) kuvaava tunniste. Voit rajata reittipuun työkalupalkista julkisiin tai suojattuihin reitteihin.

Vain annotaatiot `@RolesAllowed` ja `@DenyAll` merkitsevät reitin suojatuksi. `@PermitAll` ei määritä rooleja, vaan edellyttää ainoastaan sisäänkirjautumista, joten suodatin käsittelee sen julkisena. Pidä tämä mielessä tarkistaessasi, mitkä reitit rajoittavat käyttöä roolin perusteella.

![Reittipuu, jossa jokaisella reitillä on käyttöoikeustunniste](/img/craftforj/routes/access-badge.png#rounded-border)

Voit myös muuttaa reitin käyttöoikeussääntöä craftforJ:stä. craftforJ kirjoittaa annotaation reitin luokkaan ja käynnistää sovelluksen uudelleen, joten muutos käy läpi saman tarkistuksen kuin mikä tahansa muu [lähdekoodimuutos](/docs/craftforj/source-changes). Tämä toiminto ei ole käytettävissä, jos craftforJ:llä ei ole oikeutta kirjoittaa Java-koodia.

<VideoPlayer video="craftforj/route-security" width={1920} height={1000} title="Route security" />
