---
title: FlexLayout
sidebar_position: 45
_i18n_hash: ddb7d5ef1e583af6e3a7072d91329c7b
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

`FlexLayout` -komponentti järjestää lapsikomponentit riviin tai sarakkeeseen käyttämällä CSS Flexbox -mallia. Se antaa sinulle hallintaa kohdistamisesta, välistä, kääntöön ja siitä, kuinka kohteet kasvavat tai kutistuvat täyttämään käytettävissä olevan tilan.

<!-- INTRO_END -->

## `FlexLayout` ominaisuudet {#flex-layout-properties}

`FlexLayout` -ominaisuudet voidaan jakaa kahteen kategoriaan: ominaisuudet, jotka koskevat elementtejä, jotka ovat asettuneet asetteluun, ja ominaisuudet, jotka koskevat itse asettelua. `FlexLayout`, eli vanhelementti, on laatikko/asti, joka voi sisältää yhden tai useamman komponentin. Kaikkea `FlexLayoutin` sisällä kutsutaan kohteeksi tai lapsielementiksi. `FlexLayout` tarjoaa joitakin kohdistusmahdollisuuksia, jotka voidaan saavuttaa joko astiapropertyjen tai kohdepropertyjen avulla.

:::tip
`FlexLayout` -komponentti noudattaa [CSS:n flexbox-asettelu](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) -mallia. Kuitenkin, `FlexLayout` on tarkoitettu käytettäväksi kokonaan Javassa, eikä se vaadi CSS:n käyttöä Java API -menetelmien ulkopuolella.
:::

## Astia ominaisuudet {#container-properties}

Astiaominaisuudet kohdistuvat kaikkiin komponentteihin asettelun sisällä, eivätkä itse asetteluun. Ne eivät vaikuta vanhemman suuntaan tai sijoitteluun, vain lapsikomponentteihin sisällä.

### Suunta {#direction}

`FlexLayout` lisää komponentteja vierekkäin sen suunnan mukaan, joko vaaka- tai pystysuunnassa. Käytettäessä rakentajaa, yhdistä `horizontal()`, `horizontalReverse()`, `vertical()`, tai `verticalReverse()` -menetelmät `FlexLayout.create()` -menetelmään asettelun määrittämiseksi objektin luomisen yhteydessä.

Asettaaksesi suunnan olemassa olevalle `FlexLayout` -kohteelle, käytä `setDirection()` -menetelmää. Vaaka-suuntavaihtoehdot ovat `FlexDirection.ROW` (vasemmalta oikealle) tai `FlexDirection.ROW_REVERSE` (oikealta vasemmalle), ja pystysuuntavaihtoehdot ovat `FlexDirection.COLUMN` (ylhäältä alas) tai `FlexDirection.COLUMN_REVERSE` (alhaalta ylös). 

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Sijoittaminen {#positioning}

Vaakasuoraan lisättyjä komponentteja voidaan myös sijoittaa sekä vaaka- että pystysuunnassa. Käytä `FlexLayout` -rakentajan `justify()`, `align()` ja `contentAlign()` -menetelmiä sijoittelun määrittämiseksi uuden `FlexLayout` -asettelun luomisen yhteydessä.

Vaihtoehtoisesti itse `FlexLayout` -kohteessa voit käyttää `setJustifyContent()` -menetelmää kohteiden sijoittamiseen vaaka suunnassa ja `setAlignment()` -menetelmää pystysuuntaiseen sijoitteluun. Muuttaaksesi alueen ympärillä komponentteja poikkisuunnassa (y-akselilla vaakasuorissa asetteluissa), käytä `setAlignContent()` -menetelmää.

:::tip
`setAlignment()` -menetelmä hallitsee, kuinka kohteet tulevat näkyviin poikkisuunnassa kokonaisuutena astiassa, ja se on tehokas yksirivisissä asetteluissa.

`setAlignContent()` -menetelmä hallitsee tilaa poikkisuunnassa, ja se vaikuttaa vain silloin, kun asettelu sisältää useita rivejä.
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Kääntäminen {#wrapping}

`FlexLayout` -komponentin mukauttamiseksi voit määrittää sen käyttäytymisen, kun komponentit lisätään, jotka eivät enää mahdu näyttöön. Määrittääksesi tämän käyttäen rakentajaa, käytä `nowrap()` (oletus), `wrap()`, ja `wrapReverse()` -menetelmiä kääntämiseksi. Määrittääksesi tämän olemassa olevalle `FlexLayout` -kohteelle, käytä `setWrap()` -menetelmää.

### Väli {#spacing}

Kohteiden väli-ominaisuuden määrittämiseksi voit asettaa `gap` -ominaisuuden. Se soveltaa tätä väliä vain kohteiden välillä, ei ulkoreunoilla. 

Väliomaisuuden käyttäytyminen voidaan ymmärtää minimietäisyytenä, joten se tulee voimaan vain, jos se on suurin lasketun tilan väli kohteiden välillä. Jos tilan väli kohteiden välillä olisi muuten suurempi toisen lasketun ominaisuuden vuoksi, kuten `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, niin väliomaisuus ohitetaan.

### Virta {#flow}

Flex-virta, joka on yhdistelmä sekä suunta että kääntöominaisuudet, voidaan asettaa käyttämällä `setFlow()` -menetelmää `FlexLayout` -kohteessa. 

:::info
Määrittääksesi tämän ominaisuuden asettelun luomisen yhteydessä, käytä oikeita suunta- ja kääntömenetelmiä. Esimerkiksi, luodaksesi pystysuoran kääntövirran, käytä `.vertical().wrap()` yhdistelmää.
:::

### Astiarakentaja {#container-builder}

Seuraava demo mahdollistaa astian rakentamisen halutuilla flex-ominaisuuksilla, jotka on valittu eri valikoista. Tätä työkalua voidaan käyttää ei vain luomaan visuaalista esimerkkiä eri menetelmistä, vaan myös luomaan omia asetteluja haluamillasi ominaisuuksilla. Käyttääksesi mukautettua asettelua, kopioi vain tuotokoodin ja lisää haluamasi elementit käytettäväksi ohjelmassasi.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Kohde ominaisuudet {#item-properties}

Kohdeominaisuudet eivät vaikuta mihinkään lapsielementteihin `FlexLayout` -yhteydessä, vaan vaikuttavat itse asetteluun. Tämä on hyödyllistä, kun haluat muotoilla yksittäisen `FlexLayout` -elementin, joka on suuremman `FlexLayout` -elementin lapsi eristyksissä tyyleistä, jotka koskevat kaikkia lapsia.

### Järjestys {#order}

`ItemOrder` -ominaisuus määrittää järjestyksen, jossa komponentit näytetään `FlexLayout` -sivustolla, ja kun sitä käytetään `FlexLayout` -kohteessa, se määrittää kohteelle erityisen järjestysnumeron. Tämä ohittaa jokaisen kohteen oletus "lähdejärjestyksen", ja tarkoittaa, että se näytetään ennen kohteita, joilla on korkeampi järjestys, ja jälkeen kohteita, joilla on matalampi järjestys.

Tämä ominaisuus hyväksyy yksittäisen kokonaislukuarvon, joka määrittää flex-kohteen suhteellisen järjestyksen astiassa. Mitä matalampi arvo, sitä aikaisemmin kohde ilmestyy järjestyksessä. Esimerkiksi, kohde, jonka järjestysarvo on 1, ilmestyy ennen kohdetta, jonka järjestysarvo on 2.

:::caution
On tärkeää huomata, että järjestysominaisuus vaikuttaa vain kohteiden näkyvään järjestykseen astiassa, ei niiden todelliseen sijaintiin DOM:ssa. Tämä tarkoittaa, että ruudunlukijat ja muut apuvälineet lukevat edelleen kohteet järjestyksessä, jossa ne ilmestyvät lähdekoodissa, eivät näkyvässä järjestyksessä.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Itsensä kohdistaminen {#self-alignment}

`FlexLayout`'n itsensä kohdistaminen viittaa siihen, kuinka yksittäinen `FlexLayout` -kohde kohdistuu sen vanhempaan flex-astiaan poikkisuunnassa, joka on kohtisuorassa pääakselia vastaan. Poikkisuunnan kohdistamista ohjataan `Alignment` -ominaisuudella.

Align-self -ominaisuus määrittää yksittäisen flex-kohteen kohdistamisen poikkisuunnassa, ohittaen oletuskohdistamisen, joka on asetettu `AlignContent` -ominaisuuden mukaan `FlexLayout` -kohteessa. Tämä mahdollistaa yksittäisten `FlexLayout` -kohteiden kohdistamisen eri tavalla kuin muut astiassa olevat.

:::info
Itsensä kohdistaminen käyttää samoja arvoja kuin sisällön kohdistaminen.
:::

Tämä ominaisuus on erityisen hyödyllinen, kun sinun on kohdistettava tietty kohde eri tavalla kuin muut kohteet astiassa. Katso alla oleva esimerkki yksittäisen kohteen kohdistamisesta:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex-basis {#flex-basis}

`Item Basis` on ominaisuus, jota käytetään yhdessä `FlexLayout`'n suunnan kanssa määrittämään flex-kohteen aloituskoko ennen kuin jäljelle jäävä tila jaetaan.

`Item Basis` -ominaisuus määrittää flex-kohteen oletuskoko pääakselin suuntaan, joka on joko vaakasuora (rivin suunta) tai pystysuora (sarakkeen suunta). Tämä ominaisuus asettaa flex-kohteen leveyden tai korkeuden riippuen flex-suunta -ominaisuuden arvosta.

:::info
Oletuksena `Item Basis` -ominaisuus asetetaan `auto`, mikä tarkoittaa, että kohteen koko määräytyy sen sisällön mukaan. Voit kuitenkin myös asettaa tarkasti koon kohteelle käyttämällä erilaisia yksiköitä, kuten pikseleitä (px), em-yksiköitä (em), prosenttia (%) tai mitä tahansa muuta CSS-pituusyksikköä.
:::

Seuraava demo mahdollistaa valita yksi tai useampi laatikko ja muuttaa `Item Basis` -arvoa valituille kohteille.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex-kasvu ja -kutistus {#flex-grow--shrink}

`Item Grow` ja `Item Shrink` ovat kaksi ominaisuutta, jotka työskentelevät yhdessä toistensa sekä `Item Basis` -ominaisuuden kanssa määrittääkseen, kuinka flex-kohteet kasvavat tai kutistuvat täyttämään käytettävissä olevan tilan `FlexLayout` -kohteessa.

`Item Grow` -ominaisuus määrittää, kuinka paljon flex-kohde voi kasvaa suhteessa muihin kohteisiin astiassa. Se ottaa yksikköarvon, joka edustaa osaa käytettävissä olevasta tilasta, joka tulisi varata kohteelle. Esimerkiksi, jos yhdellä kohteella on `Item Grow` -arvo 1 ja toisella arvo 2, toinen kohde kasvaa kaksi kertaa enemmän kuin ensimmäinen.

`Item Shrink` -ominaisuus puolestaan määrittää, kuinka paljon flex-kohde voi kutistua suhteessa muihin kohteisiin astiassa. Se ottaa myös yksikköarvon, joka edustaa osaa käytettävissä olevasta tilasta, joka tulisi varata kohteelle. Esimerkiksi, jos yhdellä kohteella on `Item Shrink` -arvo 1 ja toisella arvo 2, toinen kohde kutistuu kaksi kertaa enemmän kuin ensimmäinen.

Kun astiassa on enemmän tilaa kuin on tarpeen sen sisältöjen mahtumiseksi, flex-kohteet, joilla on `Item Grow` -arvo suurempi kuin 0, laajenevat täyttämään käytettävissä olevan tilan. Kukin kohteen saama tila määräytyy sen `Item Grow` -arvon ja kaikkien astiassa olevien kohteiden yhteenlasketun `Item Grow` -arvon suhteessa.

Vastaavasti, kun astiassa ei ole tarpeeksi tilaa sen sisältöjen mahtumiseksi, flex-kohteet, joilla on `Item Shrink` -arvo suurempi kuin 0, kutistuvat mahtumaan käytettävissä olevaan tilaan. Kukin kohteen luopuma tila määräytyy sen `Item Shrink` -arvon ja kaikkien astiassa olevien kohteiden yhteenlasketun `Item Shrink` -arvon suhteessa.

## Esimerkkilomake {#example-form}
Alla oleva lomake havainnollistaa, kuinka `FlexLayout` järjestää syötteet rakenteelliseksi asetteluksi.

:::tip
Jos suosittelet sarakkeisiin perustuvaa rakennetta, tutustu lomakkeen `ColumnsLayout` -versioon [`ColumnsLayout`](../components/columns-layout) artikkelissa nähdäksesi, miten se vertautuu.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
