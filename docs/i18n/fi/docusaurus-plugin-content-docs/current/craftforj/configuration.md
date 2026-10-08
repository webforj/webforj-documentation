---
title: Määritykset
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: 025fb1766af8cbcb741f6f353bdf6523
---
craftforJ on määritetty `webforj.conf` -tiedostossa. Ominaisuuksien nimet ovat samat [Spring](/docs/integrations/spring/overview) -dokumentaatiossa, joten määritä ne `application.properties` -tiedostoon, jos se on paikka, jossa kokoonpanosi sijaitsee.

## Vaatimukset {#required-properties}

| Ominaisuus | Tyyppi | Oletus | Kuvaus |
|------------|--------|--------|--------|
| **`webforj.debug`** | Boolean | `false` | Ota virheenkorjaustila käyttöön. craftforJ vaatii sen |
| **`webforj.devtools.craftforj.enabled`** | Boolean | `false` | Ota craftforJ käyttöön |

Molemmat ominaisuudet on otettava käyttöön. Katso [Turvallisuus](/docs/craftforj/security#two-required-settings) miksi craftforJ vaatii kahta asetusta yhden sijaan.

## Pääsy {#access}

| Ominaisuus | Tyyppi | Oletus | Kuvaus |
|------------|--------|--------|--------|
| **`webforj.devtools.craftforj.hosts-allowed`** | Lista tai Merkkijono | vain loopback | Asiakasosoitteet, joita sallitaan koneen ulkopuolella, jolla sovellus suoritetaan |

Oletuksena vain selaimen on sallittu tavoittaa craftforJ, kun se on samassa koneessa kuin sovellus. Muiden koneiden sallimiseksi luettele niiden osoitteet. Merkintä, joka päättyy `*` vastaa prefiksiä, ja yksittäinen `*` poistaa rajoituksen kokonaan.

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning Wildcard sallii kenelle tahansa, joka voi tavoittaa sovelluksesi
craftforJ lukee ja kirjoittaa projektisi lähdekoodit. Käytä `*` vain verkossa, jossa olet varma, kuka voi tavoittaa portin, kuten säiliössä, jota käytät vain sinä. Älä koskaan käytä sitä jaetulla verkolla.
:::

## Projektin juuri {#project-root}

| Ominaisuus | Tyyppi | Oletus | Kuvaus |
|------------|--------|--------|--------|
| **`webforj.devtools.craftforj.project-root`** | Merkkijono | havaittu | Hakemisto, jossa lähteesi sijaitsevat |

craftforJ määrittää, mikä projektisi on sovelluksen käynnistämistavasta. Poikkeukselliset projektirakenteet ja jotkut säiliöasetukset rajoittavat tämän havaitsemista. Jos [Sovellustiedot](/docs/craftforj/app-info) ilmoittaa väärästä projektin juuresta, aseta se tänne.

## Ominaisuusliput {#feature-flags}

Jokainen näistä on oletusarvoisesti käytössä. Yhden pois kytkeminen rajoittaa sitä, mitä craftforJ saa tehdä.

| Ominaisuus | Pois kytkeminen poistaa |
|------------|-------------------------|
| **`webforj.devtools.craftforj.source-changes`** | Ominaisuuksien kirjoittaminen takaisin Javaan ja reitin käyttöoikeuden muuttaminen |
| **`webforj.devtools.craftforj.stylesheet-changes`** | Teemojen ja tyylien tallentaminen tyylitiedostoosi |
| **`webforj.devtools.craftforj.ai.enabled`** | AI-avustaja |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | Avustaja kirjoittaa omaa Javaansa |

Lipun pois kytkeminen kytkee ominaisuuden pois päältä kaikilta, jotka käyttävät kyseistä sovellusta. craftforJ-asetukset ovat kehittäjäkohtaisia ja voivat vain rajoittaa edelleen, joten kehittäjä ei voi kytkeä ominaisuutta takaisin päälle, jonka sovellus on pois päältä kytkenyt.

:::info Ominaisuudet, jotka poistat käytöstä, pysyvät näkyvinä
Kun lippu on pois päältä, ohjaus pysyy craftforJ:ssa ja sitä merkitään tuettamattomaksi yhdistettyyn sovellukseen.
:::

:::warning Tuotannossa
Jätä `webforj.devtools.craftforj.enabled` asettamatta. Katso [Turvallisuus](/docs/craftforj/security#in-production) täydelliseksi tarkistuslistaksi.
:::
