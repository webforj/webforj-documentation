---
sidebar_position: 10
title: BBj Controls and webforJ Components
description: >-
  See how webforJ components map one-to-one to BBj controls through the
  DwcComponent base class and why composition replaces inheritance.
_i18n_hash: 83f48323774737067ddd9a6bebb0373d
---
webforJ-kehys on suunniteltu tarjoamaan Java API BBj-kielen DWC:n ympärille ja tarjoaa vahvan arkkitehtuurin komponenttien rakentamiseen ja hallintaan.

## BBj-ohjainten kartoitus webforJ-komponentteihin {#mapping-bbj-controls-to-webforj-components}
Yksi webforJ:n perustavanlaatuisista periaatteista on BBj-ohjainten sitominen webforJ-komponentteihin. Tässä arkkitehtuurissa jokaisella webforJ-komponentilla, joka toimitetaan tuotteen mukana, on yksi-yhteen kartoitus taustalla olevan BBj-ohjaimen kanssa. Tämä kartoitus varmistaa, että Java-komponentit heijastavat saumatonta käyttäytymistä ja ominaisuuksia, joita niiden BBj-vastaavilla on.

Tämä läheinen vastaavuus webforJ-komponenttien ja BBj-ohjainten välillä yksinkertaistaa kehitystä ja mahdollistaa Java-kehittäjien työskentelyn tuttujen käsitteiden parissa web-pohjaisten sovellusten rakentamisessa ilman tarvetta kirjoittaa mitään BBj-koodia.

## `DwcComponent` -perusluokka {#the-dwccomponent-base-class}
webforJ:n komponenttien arkkitehtuurin ytimessä on DWCComponent-perusluokka. Kaikki webforJ-komponentit perivät tämän luokan. Tämä perintö antaa jokaiselle webforJ-komponentille pääsyn sen taustalla olevaan BBj-ohjaimeen, tarjoten suoran yhteyden Java-komponentin ja sitä edustavan BBj-ohjaimen välille.

On kuitenkin tärkeää huomata, että kehittäjät eivät voi laajentaa DWCComponent-luokkaa enempää. Tämän tekeminen aiheuttaa suoritusajan poikkeuksen, joka kieltää tällaiset laajennukset. Tämä rajoitus on olemassa sen varmistamiseksi, että taustalla olevan BBj-ohjaimen eheyteen ei puututa, ja että kehittäjät eivät vahingossa manipuloi sitä tavoilla, jotka voisivat johtaa odottamattomiin seurauksiin.

### Lopulliset luokat ja laajennusrajoitukset {#final-classes-and-extension-restrictions}
webforJ:ssä useimmat komponenttiluokat, lukuun ottamatta sisäänrakennettuja HTML-elementtejä ja näitä laajentavia luokkia, on määritelty `final`. Tämä tarkoittaa, että niitä ei voi laajentaa tai aliluokitella. Tämä suunnittelupäätös on tarkoituksellinen ja sillä on useita tavoitteita:

1. **Valvonta taustalla olevasta BBj-ohjaimesta**: Kuten aiemmin mainittiin, webforJ-komponenttiluokkien laajentaminen antaisi kehittäjille hallintaa taustalla olevasta BBj-ohjaimesta. Komponenttikäytöksen johdonmukaisuuden ja ennustettavuuden ylläpitämiseksi tätä hallintatasoa rajoitetaan.

2. **Haitallisten muutosten estäminen**: Komponenttiluokkien määrittäminen `final` estää tahattomat muutokset ydinkomponenteille, mikä vähentää odottamattomien käyttäytymismallien tai haavoittuvuuksien syntymisen riskiä.

3. **Koostumien käytön edistäminen**: Komponenttien toiminnallisuuden laajentamiseksi webforJ-kehys kannustaa kehittäjiä käyttämään koosteita. Koostekomponentit ovat Java-luokkia, jotka sisältävät muita webforJ-komponentteja tai standardeja HTML-elementtejä. Vaikka perinteinen perintö on kielletty, koostekomponentit tarjoavat tavan luoda uusia, räätälöityjä komponentteja, jotka kapseloivat olemassa olevia.

## Koostekomponentit: laajentaminen koostamisen kautta {#composite-components-extending-through-composition}
webforJ-kehyksessä koostekomponenttien käsite näyttelee keskeistä roolia komponenttitoiminnallisuuden laajentamisessa. Koostekomponentit ovat Java-luokkia, joita `final`-avainsana ei rajoita, mikä sallii kehittäjien luoda uusia komponentteja, jotka laajentavat yhden komponentin käyttäytymistä tai yhdistävät useita komponentteja yhdeksi koostamalla olemassa olevia komponentteja. Tätä käyttäytymistä helpottavia luokkia on luotu kehittäjien käyttöön. Katso `Composite`- ja `ElementComposite`-osiot nähdäksemme, kuinka koostekomponentteja voidaan luoda oikein.

Tämä lähestymistapa kannustaa moduulimaisempaan ja joustavampaan kehitystyylin, mahdollistaen kehittäjien rakentaa räätälöityjä komponentteja, jotka täyttävät erityiset vaatimukset.
