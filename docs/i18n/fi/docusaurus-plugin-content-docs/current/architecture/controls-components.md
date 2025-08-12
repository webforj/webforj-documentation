---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 7fd4306a016d3734d34336b8136c6e11
---
webforJ-kehys on suunniteltu tarjoamaan Java API BBj-kielen DWC:n ympärille ja tarjoaa robustin arkkitehtuurin komponenttien rakentamiseen ja hallintaan.

## BBj-ohjausobjektien kartoitus webforJ-komponentteihin {#mapping-bbj-controls-to-webforj-components}
Yksi webforJ:n perusperiaatteista on BBj-ohjausobjektien sitominen webforJ-komponentteihin. Tässä arkkitehtuurissa jokaisella tuotteeseen sisältyvällä webforJ-komponentilla on yksi-yhteen-kartoitus taustalla olevaan BBj-ohjausobjektiin. Tämä kartoitus varmistaa, että Java-komponentit heijastavat BBj-vastineidensa käyttäytymistä ja ominaisuuksia saumattomasti.

Tämä läheinen vastaavuus webforJ-komponenttien ja BBj-ohjausobjektien välillä yksinkertaistaa kehitystä ja mahdollistaa Java-kehittäjien työskentelyn tutuilla käsitteillä verkkopohjaisten sovellusten rakentamisessa ilman tarpeen kirjoittaa mitään BBj-koodia.

## `DwcComponent` -pohjaluokka {#the-dwccomponent-base-class}
webforJ:n komponenttiarkkitehtuurin ytimessä on DWCComponent-pohjaluokka. Kaikki webforJ-komponentit perivät tästä luokasta. Tämä perintö antaa jokaiselle webforJ-komponentille pääsyn sen taustalla olevaan BBj-ohjausobjektiin, tarjoten suoran yhteyden Java-komponentin ja sen edustaman BBj-ohjausobjektin välillä.

On kuitenkin tärkeää huomata, että kehittäjät eivät saa laajentaa DWCComponent-luokkaa. Yrittäminen aiheuttaa aikarajoitteen poikkeuksen, joka estää tällaiset laajennukset. Tämä rajoitus on olemassa taustalla olevan BBj-ohjausobjektin eheyden ylläpitämiseksi ja varmistaaksesi, etteivät kehittäjät tahattomasti muokkaa sitä tavoilla, jotka voisivat johtaa odottamattomiin seurauksiin.

### Lopulliset luokat ja laajennusrajoitukset {#final-classes-and-extension-restrictions}
webforJ:ssä useimmat komponenttiluokat, lukuun ottamatta sisäänrakennettuja HTML-elementtejä ja kaikkia näitä laajentavia luokkia, on määritelty `final`-avainsanalla. Tämä tarkoittaa, että niitä ei ole saatavilla laajennettavaksi tai alaluokiksi. Tämä suunnittelupäätös on tarkoituksella valittu ja palvelee useita tarkoituksia:

1. **Valvonta taustalla olevan BBj-ohjausobjektin suhteen**: Kuten aiemmin mainittiin, webforJ-komponenttiluokkien laajentaminen antaisi kehittäjille hallinnan taustalla olevaan BBj-ohjausobjektiin. Komponenttien käyttäytymisen johdonmukaisuuden ja ennakoitavuuden ylläpitämiseksi tätä hallintatasoa rajoitetaan.

2. **Tahattomien muutosten estäminen**: Tekemällä komponenttiluokat `final`, vältetään tahattomat muutokset ydinkomponentteihin, mikä vähentää odottamattomien käyttäytymisten tai haavoittuvuuksien esittelyn riskiä.

3. **Koostumien käytön edistäminen**: Komponenttien toiminnallisuuden laajentamiseksi webforJ-kehys kannustaa kehittäjiä käyttämään koostumamallia. Koostumakomponentit ovat Java-luokkia, jotka sisältävät muita webforJ-komponentteja tai standardeja HTML-elementtejä. Vaikka perinteistä perintöä ei suositella, koostumakomponentit tarjoavat tavan luoda uusia, mukautettuja komponentteja, jotka kapseloivat olemassa olevia.

## Koostumakomponentit: laajentaminen koostumalla {#composite-components-extending-through-composition}
webforJ-kehyksessä koostumakomponenttien käsite on keskeisessä roolissa komponenttien toiminnallisuuden laajentamisessa. Koostumakomponentit ovat Java-luokkia, joita ei rajoita final-avainsana, mikä mahdollistaa kehittäjien luoda uusia komponentteja, jotka laajentavat yksittäisen komponentin käyttäytymistä tai yhdistävät useita komponentteja yhdeksi, koostamalla olemassa olevia komponentteja. Käyttäjiä varten on luotu luokkia, jotka helpottavat tätä käyttäytymistä. Katso `Composite`- ja `ElementComposite`-osiot nähdäksesi, kuinka koostumakomponentteja luodaan oikein.

Tämä lähestymistapa kannustaa modulaarisempaan ja joustavampaan kehitystyylin, jolloin kehittäjät voivat rakentaa räätälöityjä komponentteja, jotka täyttävät erityiset vaatimukset.
