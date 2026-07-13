---
title: FlexLayout
sidebar_position: 45
description: >-
  Arrange children in rows or columns with the FlexLayout component, controlling
  direction, justification, alignment, wrapping, and growth.
_i18n_hash: cd16392e244062d863d403e50cc56ddd
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

`FlexLayout`-komponentti järjestää lapsikomponentit riviin tai pylvääseen käyttäen CSS Flexbox -mallia. Se antaa sinulle kontrollin kohdistuksesta, välistä, kääntämisestä ja siitä, kuinka elementit kasvavat tai pienenevät täyttääkseen käytettävissä olevan tilan.

<!-- INTRO_END -->

## `FlexLayout` ominaisuudet {#flex-layout-properties}

`FlexLayout`-ominaisuudet voidaan jakaa kahteen kategoriaan: ominaisuudet, jotka vaikuttavat asettelun sisällä oleviin kohteisiin, ja ominaisuudet, jotka vaikuttavat itse asetteluun. `FlexLayout`, tai vanhelementti, on laatikko/asti, joka voi sisältää yhden tai useamman komponentin. Kaikkea `FlexLayout`:issa kutsutaan esineeksi tai lapsielementiksi. `FlexLayout` tarjoaa joitakin kohdistusmahdollisuuksia, jotka voidaan saavuttaa joko säiliö- tai esineominaisuuksien avulla.

:::tip
`FlexLayout`-komponentti seuraa [CSS:n flexbox-asettelun](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) kaavaa. Kuitenkin, `FlexLayout` on suunniteltu käytettäväksi täysin Javassa, eikä se vaadi CSS:n käyttöä Java API:in ergonomisesti tarjoamien metodien ulkopuolella.
:::

## Säiliöominaisuudet {#container-properties}

Säiliöominaisuudet vaikuttavat kaikkiin komponentteihin komponentissa eivätkä itse asetteluun. Ne eivät vaikuta vanhemman suuntaan tai sijoitteluun, vaan vain lapsikomponentteihin.

### Suunta {#direction}

`FlexLayout` lisää komponentteja vierekkäin sen suuntaan, joko vaaka- tai pystysuunnassa. Kun käytät rakentajaa, ketjuuta `horizontal()`, `horizontalReverse()`, `vertical()` tai `verticalReverse()` -menetelmät `FlexLayout.create()` -menetelmän kanssa, jotta voit konfiguroida asettelua objektin luomisen yhteydessä.

Jos haluat asettaa suunnan olemassa olevaan `FlexLayout`-objektiin, käytä `setDirection()` -menetelmää. Vaakaominaisuudet ovat `FlexDirection.ROW` (vasemmalta oikealle) tai `FlexDirection.ROW_REVERSE` (oikealta vasemmalle), ja pystysuunnassa olevat vaihtoehdot ovat `FlexDirection.COLUMN` (ylhäältä alas) tai `FlexDirection.COLUMN_REVERSE` (alas ylöspäin).

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### Sijoittaminen {#positioning}

Vaakasuunnassa lisätyt komponentit voidaan myös sijoittaa sekä vaaka- että pystysuunnassa. Käytä `justifying()`, `align()` ja `contentAlign()` -menetelmiä `FlexLayout`-rakentajasta konfiguroidaksesi sijoituksen uuden `FlexLayout`-luonnin aikana.

Vaihtoehtoisesti varsinaisessa `FlexLayout`-objektissa voit käyttää `setJustifyContent()` -menetelmää sijoittaaksesi elementtejä vaaka suunnassa ja `setAlignment()` -menetelmää pystysuoran sijoittamisen määrittämiseksi. Muuttaaksesi alueita komponenttien ympärillä poikittaissuunassa (y-akselilla vaakasuorissa asetteluissa), käytä `setAlignContent()` -menetelmää.

:::tip
`setAlignment()` -menetelmä kontrolloi, kuinka esineet näyttävät poikittaissuuntaan yhtenä kokonaisuutena säiliössä, ja se on tehokas yksirivisissä asetteluissa.

`setAlignContent()` -menetelmä hallitsee tilaa poikittaissuuntaan ja vaikuttaa vain silloin, kun asettelu sisältää useita rivejä.
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### Kääntäminen {#wrapping}

Jotta `FlexLayout`-komponenttia voitaisiin lisäksi mukauttaa, voit määrittää sen käyttäytymistä, kun komponentteja lisätään, jotka eivät enää mahdu näyttöön. Määrittääksesi tämän käyttäen rakentajaa, käytä `nowrap()` (oletus), `wrap()` ja `wrapReverse()` -menetelmiä konfiguroidaksesi kääntämistä. Jos haluat määrittää tämän olemassa olevalle `FlexLayout`-objektille, käytä `setWrap()` -menetelmää.

### Väli {#spacing}

Välin asettamiseksi vähimmäismäärää esineiden välillä voit asettaa `gap`-ominaisuuden. Se soveltaa tätä väliä vain esineiden välillä, ei ulkoreunoilla.

Gap-ominaisuuden käyttäytymistä voidaan ajatella vähimmäisetäisyytenä esineiden välillä, joten se vaikuttaa vain, jos se on suurin lasketun välin esineiden välillä. Jos esineiden väli olisi muuten suurempi toisen lasketun ominaisuuden vuoksi, kuten `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, niin gap-ominaisuutta ei oteta huomioon.

### Virta {#flow}

Flex-virta, joka on yhdistelmä sekä suuntaa että kääntöominaisuuksia, voidaan asettaa käyttämällä `setFlow()` -menetelmää `FlexLayout`-objektissa.

:::info
Jos haluat määrittää tämän ominaisuuden luotaessa asettelua, käytä oikeita suunta- ja kääntömenetelmiä. Esimerkiksi, luodaksesi pystytason kääntövirran, käytä `.vertical().wrap()` yhdistelmää.
:::

### Säiliörakentaja {#container-builder}

Seuraava demo antaa sinun rakentaa säiliön haluamillasi flex-ominaisuuksilla, jotka on valittu eri valikoista. Tämä työkalu voi olla hyödyllinen, jotta voit luoda visuaalisen esimerkin erilaisista menetelmistä, mutta voit myös luoda omia asetteluja haluamillasi ominaisuuksilla. Käytä omaa muokkaamaasi asettelua yksinkertaisesti kopioimalla tuloskoodi ja lisäämällä haluamasi elementit ohjelmassasi.

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Esineominaisuudet {#item-properties}

Esineominaisuudet eivät vaikuta `FlexLayout`-komponentin lapsielementteihin, vaan vaikuttavat itse asetteluun. Tämä on hyödyllistä yhden `FlexLayout`-elementin tyylittämisessä, joka on suuremman `FlexLayout`-elementin lapsi riippumatta kaikista lasten soveltamisista tyyleistä.

### Järjestys {#order}

`ItemOrder`-ominaisuus määrittää järjestyksen, jossa komponentit näkyvät `FlexLayout`-asettelussa, ja kun sitä käytetään `FlexLayout`:issa, se määrittää elementin tämän asettelun tietyn järjestysnumeron. Tämä ohittaa kunkin elementin oletusarvoisen "lähdejärjestyksen" (järjestyksen, jossa komponentti lisätään sen vanhempaan), ja tarkoittaa, että se tulee esiin ennen korkeammalla järjestyksellä olevia esineitä ja sen jälkeen matalammalla järjestyksellä olevia esineitä.

Tämä ominaisuus hyväksyy kokonaisluvun, joka määrittää joustavan esineen suhteellisen järjestyksen säiliössä. Mitä alhaisempi arvo, sitä aikaisemmin esine näkyy järjestyksessä. Esimerkiksi esine, jonka järjestysarvo on 1, näkyy ennen esinettä, jonka järjestysarvo on 2.

:::caution
On tärkeää huomata, että järjestysominaisuus vaikuttaa vain visuaaliseen järjestykseen esineiden säiliössä, ei niiden todelliseen asemaan DOM:ssä. Tämä tarkoittaa, että ruudunlukijat ja muut apuvälineet lukevat esineet silti niiden lähdekoodissa esiintymisjärjestyksessä, ei visuaalisessa järjestyksessä.
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### Itse kohdistus {#self-alignment}

`FlexLayout`-itsekohtautuminen viittaa siihen, kuinka yksittäinen `FlexLayout`-objekti on kohdistettu sen vanhempaan joustoon poikittaissuuntaa, joka on kohtisuorassa pääakseliin nähden. Poikittaissuuntaan kohdistus säädetään `Alignment`-ominaisuudella.

Align-self-ominaisuus määrittää, kuinka yhden joustavan esineen kohdistus poikittaissuuntaan, ohittaen oletuskohdistuksen, joka on määritetty `AlignContent`-ominaisuudella `FlexLayout`-objektissa. Tämä mahdollistaa yksittäisten `FlexLayout`-objektien kohdistamisen eri tavalla kuin muut säiliössä.

:::info
Itse kohdistus käyttää samoja arvoja kuin sisällön kohdistus.
:::

Tämä ominaisuus on erityisen hyödyllinen, kun sinun on kohdistettava tietty esine eri tavalla kuin muut esineet säiliössä. Katso alla olevaa esimerkkiä alinnotusta yksittäisestä esineestä:

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### Flex-pohja {#flex-basis}

`Item Basis` on ominaisuus, jota käytetään yhdessä `FlexLayout`-suunnan kanssa mukauttamalla joustavan esineen alkuperäinen koko ennen kuin ylimääräinen tila jaetaan.

`Item Basis` -ominaisuus määrittää joustavan esineen oletuskoolle pääakselilla, joka on joko vaaka (rivinsuunta) tai pystysuora (pylväs suunta). Tämä ominaisuus määrittää joustavan esineen leveyden tai korkeuden riippuen joustavan suunnan ominaisuudesta.

:::info
Oletuksena `Item Basis` -ominaisuus on asetettu `auto`-arvoon, mikä tarkoittaa, että esineen koko määräytyy sen sisällön mukaan. Voit kuitenkin myös asettaa tietyn koon esineelle käyttämällä erilaisia yksiköitä, kuten pikseleitä (px), em-yksiköitä (em), prosentteja (%) tai mitä tahansa CSS-pituusyksikköä.
:::

Seuraava demo antaa sinun valita yhden tai useamman laatikon ja muuttaa valittujen esineiden `Item Basis` -arvoa.

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### Flex kasvu ja kutistuminen {#flex-grow--shrink}

`Item Grow` ja `Item Shrink` ovat kaksi ominaisuutta, jotka toimivat yhdessä toistensa kanssa ja `Item Basis` -ominaisuuden kanssa määrittääkseen, kuinka joustavat esineet kasvavat tai kutistuvat täyttääkseen käytettävissä olevan tilan `FlexLayout` -objektissa.

`Item Grow` -ominaisuus määrittää, kuinka paljon joustava esine voi kasvaa suhteessa muihin säiliön esineisiin. Se hyväksyy kokonaisuuden arvon, joka edustaa osuutta käytettävissä olevasta tilasta, joka tulisi kohdistaa esineelle. Esimerkiksi, jos yhdellä esineellä on `Item Grow` -arvo 1 ja toisella arvo 2, toinen esine kasvaa kaksinkertaisesti ensimmäiseen verrattuna.

`Item Shrink` -ominaisuus puolestaan määrittää, kuinka paljon joustava esine voi kutistua suhteessa muihin säiliön esineisiin. Se hyväksyy myös kokonaisuuden arvon, joka edustaa osuutta käytettävissä olevasta tilasta, joka tulisi kohdistaa esineelle. Esimerkiksi, jos yhdellä esineellä on `Item Shrink` -arvo 1 ja toisella arvo 2, toinen esine kutistuu kaksinkertaisesti ensimmäiseen verrattuna.

Kun säiliössä on enemmän tilaa kuin mitä sen sisältö vaatii, joustavat esineet, joilla on `Item Grow` -arvo suurempi kuin 0, laajenevat täyttämään käytettävissä olevan tilan. Jokaisen esineen saama tila määräytyy sen `Item Grow` -arvon ja kaikkien säiliön esineiden yhteenlasketun `Item Grow` -arvon suhteessa.

Vastaavasti, kun säiliössä ei ole tarpeeksi tilaa mahtua sen sisältöön, joustavat esineet, joilla on `Item Shrink` -arvo suurempi kuin 0, kutistuvat mahtuakseen käytettävissä olevaan tilaan. Jokaisen esineen luopuminen määräytyy sen `Item Shrink` -arvon ja kaikkien säiliön esineiden yhteenlasketun `Item Shrink` -arvon suhteessa.

## Esimerkkilomake {#example-form}
Alla oleva lomake demonstroi, kuinka `FlexLayout` järjestää syöttökentät rakenteelliseen asetteluun.

:::tip
Jos suosittelet pylvääseen perustuvaa rakennetta, katso `ColumnsLayout`-muottoista versiota tästä lomakkeesta [`ColumnsLayout`](../components/columns-layout) -artikkelissa nähdäksesi, miten se vertautuu.
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/frontend/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
