---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 929625ea8b8335de7326ecb067dca773
---
webforJ-kehyksen tarkoituksena on tarjota Java API BBj-kielen DWC:n ympärillä, ja se tarjoaa vahvan arkkitehtuurin komponenttien rakentamiseen ja hallintaan.

## Mapping BBj-ohjaimia webforJ-komponentteihin {#mapping-bbj-controls-to-webforj-components}
Yksi webforJ:n perusperiaatteista on BBj-ohjainten sitominen webforJ-komponentteihin. Tässä arkkitehtuurissa jokaisella webforJ-komponentilla, joka toimitetaan tuotteen mukana, on yksi-yhteen -suhde taustalla olevan BBj-ohjaimen kanssa. Tämä mappaus varmistaa, että Java-komponentit peilaavat BBj-vastineidensa käyttäytymistä ja ominaisuuksia saumattomasti.

Tämä tiivis vastaavuus webforJ-komponenttien ja BBj-ohjainten välillä yksinkertaistaa kehitystä ja mahdollistaa Java-kehittäjien työskentelemisen tutuilla käsitteillä web-pohjaisten sovellusten rakentamisessa ilman tarvetta kirjoittaa mitään BBj-koodia.

## `DwcComponent` perusluokka {#the-dwccomponent-base-class}
webforJ:n komponenttiarkkitehtuurin ytimessä on DWCComponent-perusluokka. Kaikki webforJ-komponentit perivät tämän luokan. Tämä perintö antaa jokaiselle webforJ-komponentille pääsyn sen taustalla olevaan BBj-ohjaimeen, ja se tarjoaa suoran linkin Java-komponentin ja BBj-ohjaimen välillä, jota se edustaa.

On kuitenkin tärkeää huomata, että kehittäjillä on rajoituksia DWCComponent-luokan laajentamiselle. Yrittäminen laajentaa tätä luokkaa johtaa ajonaikaiseen poikkeukseen, joka estää tällaiset laajennukset. Tämä rajoitus on olemassa, jotta säilytetään taustalla olevan BBj-ohjaimen eheys ja varmistetaan, että kehittäjät eivät tahattomasti manipuloisi sitä tavoilla, jotka voisivat johtaa odottamattomiin seurauksiin.

### Loppuluokat ja laajennusrajoitukset {#final-classes-and-extension-restrictions}
webforJ:ssä useimmat komponenttiluokat, lukuun ottamatta sisäänrakennettuja HTML-elementtejä ja näistä laajentavia luokkia, on määritelty `final`-avainsanalla. Tämä tarkoittaa, että niitä ei voi laajentaa tai aliluokkia. Tämä suunnittelupäätös on tarkoituksellinen ja palvelee useita tarkoituksia:

1. **Kontrolli taustalla olevaan BBj-ohjaimeen**: Kuten mainittiin aiemmin, webforJ-komponenttiluokkien laajentaminen antaisi kehittäjille hallintaa taustalla olevasta BBj-ohjaimesta. Komponenttikäyttäytymisen johdonmukaisuuden ja ennakoitavuuden ylläpitämiseksi tätä hallintaa rajoitetaan.

2. **Tahattomien muutosten estäminen**: Komponenttiluokkien määrittäminen `final`-avainsanalla estää tahattomat muutokset ydinkomponenteille, mikä vähentää odottamattomien käyttäytymis- tai haavoittuvuusriskien syntyä.

3. **Compositejen käytön edistäminen**: Komponenttien toiminnallisuuden laajentamiseksi webforJ-kehys kannustaa kehittäjiä käyttämään koosteen lähestymistapaa. Koostekomponentit ovat Java-luokkia, jotka sisältävät muita webforJ-komponentteja tai standardeja HTML-elementtejä. Vaikka perinteinen perintö on ei-toivottua, koostekomponentit tarjoavat tavan luoda uusia, räätälöityjä komponentteja, jotka kapseloivat olemassa olevia.

## Koostekomponentit: laajennus koostumisen kautta {#composite-components-extending-through-composition}
webforJ-kehyksessä koostekomponenttien käsite näyttelee keskeistä roolia komponenttitoiminnallisuuden laajentamisessa. Koostekomponentit ovat Java-luokkia, joita ei rajoita final-avainsana, mikä mahdollistaa kehittäjien luoda uusia komponentteja, jotka laajentavat yhden komponentin käyttäytymistä tai yhdistävät useita komponentteja yhdeksi, koostamalla olemassa olevia komponentteja. Luokkia, jotka helpottavat tätä toimintaa, on luotu kehittäjien käyttöön. Katso `Composite`- ja `ElementComposite`-osioista, miten luoda oikein koostekomponentteja.

Tämä lähestymistapa kannustaa modulaarisempaan ja joustavampaan kehitystyylin, mikä mahdollistaa kehittäjien rakentaa räätälöityjä komponentteja, jotka täyttävät erityiset vaatimukset.
