---
title: Konfigurointi
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: f7e73354e4d9548e79122405c21ae275
---
craftforJ on konfiguroitu tiedostossa `webforj.conf`. Ominaisuuksien nimet ovat samat [Spring](/docs/integrations/spring/overview) -dokumentaatiossa, joten määritä ne tiedostoon `application.properties`, jos konfiguraatiosi sijaitsee siellä.

## Vaatimukset {#required-properties}

| Ominaisuus | Tyyppi | Oletus | Kuvaus |
|------------|--------|--------|--------|
| **`webforj.debug`** | Boolean | `false` | Aktivoi virheenkorjaustilan. craftforJ vaatii sen |
| **`webforj.devtools.craftforj.enabled`** | Boolean | `false` | Aktivoi craftforJ:n |

Molemmat ominaisuudet on aktivoitava. Katso [Turvallisuus](/docs/craftforj/security#two-required-settings) syistä siihen, miksi craftforJ vaatii kahta asetusta eikä vain yhtä.

## Pääsy {#access}

| Ominaisuus | Tyyppi | Oletus | Kuvaus |
|------------|--------|--------|--------|
| **`webforj.devtools.craftforj.hosts-allowed`** | Lista tai Merkkijono | vain loopback | Asiakasosoitteet, joiden on sallittu käyttää sovellusta ajavaa konetta |

Oletusarvoisesti vain selain, joka on samalla koneella sovelluksen kanssa, voi käyttää craftforJ:ta. Muiden koneiden sallimiseksi luettelo niiden osoitteista. Merkintä, joka päättyy `*`:n, vastaa esijuurta, ja yksittäinen `*` poistaa rajoituksen kokonaan.

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning Villikortti sallii kenelle tahansa, joka voi saavuttaa sovelluksesi
craftforJ lukee ja kirjoittaa projektisi lähteitä. Käytä `*` vain verkossa, jossa tiedät varmasti, kuka voi saavuttaa portin, kuten säilössä, jota käytät vain sinä. Älä käytä sitä jaetulla verkolla.
:::

## Projektin juuri {#project-root}

| Ominaisuus | Tyyppi | Oletus | Kuvaus |
|------------|--------|--------|--------|
| **`webforj.devtools.project-root`** | Merkkijono | havaittu | Hakemisto, jossa lähteesi sijaitsevat |

craftforJ määrittää, mistä projektisi on, sen perusteella, kuinka sovellus käynnistettiin. Epätavalliset projektirakenteet ja jotkut säilöasetukset estävät tämän havaitsemisen. Jos [Sovellustiedot](/docs/craftforj/app-info) ilmoittaa väärän projektin juuren, määritä se täällä.

```ini title="webforj.conf"
webforj.devtools.project-root = "/home/me/projects/my-app"
```

Springissä määritä sama avain tiedostoon `application.properties`.

:::info Nimensämuutos 26.03
Tämä asetus oli aiemmin `webforj.devtools.craftforj.project-root`. Vanha avain toimii edelleen varmuusmenettelynä, mutta se on vanhentunut ja lokittaa varoituksen käytettäessä. Jos molemmat avaimet on asetettu, `webforj.devtools.project-root` on etusijalla.
:::

## Ominaisuusliput {#feature-flags}

Jokainen näistä on oletusarvoisesti aktivoitu. Yhden pois kytkeminen kaventaa, mitä craftforJ:lle on sallittu tehdä.

| Ominaisuus | Pois kytkeminen poistaa |
|------------|-------------------------|
| **`webforj.devtools.craftforj.source-changes`** | Ominaisuuden muutosten kirjoittaminen Javaan ja reitin käyttöoikeuden muuttaminen |
| **`webforj.devtools.craftforj.stylesheet-changes`** | Teemojen ja tyylien tallentaminen tyylitiedostoosi |
| **`webforj.devtools.craftforj.ai.enabled`** | AI-avustaja |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | Avustajan kirjoittama Java omasta aloitteestaan |

Lipukkeen poistaminen estää ominaisuuden käytön kaikilta, jotka käyttävät sovellusta. craftforJ:n asetukset ovat kehittäjäkohtaisia ja voivat vain kaventaa edelleen, joten kehittäjä ei voi kytkeä ominaisuutta päälle, jonka sovellus on kytkenyt pois.

:::info Poissulkemasi ominaisuudet pysyvät näkyvissä
Kun lippu on pois päältä, ohjaus pysyy craftforJ:ssä ja merkitään tuetuksi yhdistetyn sovelluksen toimesta.
:::

:::warning Tuotannossa
Älä aseta `webforj.devtools.craftforj.enabled` -asetusta. Katso [Turvallisuus](/docs/craftforj/security#in-production) koko tarkistuslistan osalta.
:::
