---
title: FlexLayout
sidebar_position: 45
_i18n_hash: cf7ba76f1e13488c6fa3a419ba6ceaca
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

`FlexLayout` -komponentti asettaa lapsikomponentteja riviin tai sarakkeeseen CSS Flexbox -mallin avulla. Se antaa sinulle hallinnan kohdistuksessa, väleissä, kääntämisessä ja siinä, miten elementit kasvavat tai kutistuvat täyttääkseen käytettävissä olevan tilan.

<!-- INTRO_END -->

## `FlexLayout` ominaisuudet {#flex-layout-properties}

`FlexLayout`-ominaisuudet voidaan jakaa kahteen kategoriaan: ominaisuudet, jotka koskevat elementtejä, jotka sijaitsevat asettelun sisällä, ja ominaisuudet, jotka koskevat itse asettelua. `FlexLayout` tai vanhelementti on laatikko/säiliö, joka voi sisältää yhden tai useamman komponentin. Kaikkea `FlexLayout`:n sisällä kutsutaan item- tai lapsielemetiksi. `FlexLayout` tarjoaa joitain kohdistusmahdollisuuksia, jotka voidaan saavuttaa joko säiliö- tai item-ominaisuuksien avulla.

:::tip
`FlexLayout` komponentti seuraa [CSS:n flexbox-asettelun](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) kaaviota. Kuitenkin, `FlexLayout` on suunniteltu käytettäväksi täysin Javassa, eikä se vaadi CSS:n käyttöä Java API -menetelmien ulkopuolella.
:::

## Säiliöominaisuudet {#container-properties}

Säiliöominaisuudet vaikuttavat kaikkiin komponentteihin, jotka sijaitsevat komponentin sisällä, eivätkä itse asetteluun. Ne eivät vaikuta vanhemman suuntaan tai sijaintiin, vaan vain lapsikomponentteihin sisällä.

### Suunta {#direction}

`FlexLayout` lisää komponentteja vierekkäin sen suunnan mukaan, joka voi olla vaaka- tai pystysuuntainen. Rakentajaa käytetään ketjuttamalla `horizontal()`, `horizontalReverse()`, `vertical()` tai `verticalReverse()` menetelmiä `FlexLayout.create()` -menetelmän kanssa, jotta asettelua voidaan määrittää objektin luontivaiheessa.

Asettaaksesi suunnan olemassa olevaan `FlexLayout` -objektiin, käytä `setDirection()` -menetelmää. Vaakasuunnat ovat `FlexDirection.ROW` (vasemmalta oikealle) tai `FlexDirection.ROW_REVERSE` (oikealta vasemmalle), ja pystysuunnat ovat `FlexDirection.COLUMN` (ylhäältä alas) tai `FlexDirection.COLUMN_REVERSE` (alhaalta ylös). 

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### Sijoittaminen {#positioning}

Vaakasuoraan lisättyjä komponentteja voidaan myös sijoittaa sekä vaaka- että pystysuunnassa. Käytä `justify()`, `align()` ja `contentAlign()` -menetelmiä `FlexLayout`-rakentajassa määrittääksesi sijoittamisen, kun luot uuden `FlexLayout`-objektin.

Vaihtoehtoisesti, varsinaisessa `FlexLayout` -objektissa voit käyttää `setJustifyContent()` -menetelmää sijoittaaksesi elementtejä vaakasuoraan ja `setAlignment()` -menetelmää pystysijoituksen määrittämiseksi. Muuttaaksesi aluetta komponenttien ympärillä poikkisuunnassa (y-akseli vaakasuorissa asetteluissa), käytä `setAlignContent()` -menetelmää.

:::tip
`setAlignment()` -menetelmä ohjaa, kuinka elementit näyttäytyvät poikkisuunnassa kokonaisuutena säiliössä, ja se on tehokas yksirivisissä asetteluissa.

`setAlignContent()` -menetelmä ohjaa tilaa poikkisuunnassa, ja se astuu voimaan vain, kun asettelulla on useita rivejä.
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### Kääntäminen {#wrapping}

`FlexLayout` -komponentin käyttäytymistä voidaan muokata, kun komponentteja lisätään, jotka eivät enää mahdu näyttöön. Voit määrittää tämän rakennettaessa `nowrap()` (oletus), `wrap()` ja `wrapReverse()` -menetelmillä. Olemassa olevaan `FlexLayout` -objektiin voidaan määrittää tämä käyttämällä `setWrap()` -menetelmää.

### Väli {#spacing}

Välin asettamiseksi elementtien välille voit määrittää `gap` -ominaisuuden. Se soveltuu vain elementtien väliin, ei ulkoreunoille. 

Gap-ominaisuuden toimintaa voidaan ajatella vähimmäisetäisyytenä, joten se astuu voimaan vain, jos se on suurin laskettu etäisyys elementtien välillä. Jos elementtien välinen etäisyys olisi muutoin suurempi toisen lasketun ominaisuuden vuoksi, kuten `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, silloin gap-ominaisuutta ei oteta huomioon.

### Virta {#flow}

Flex-virta, joka on yhdistelmä suunta- ja wrap-ominaisuuksia, voidaan asettaa käyttämällä `setFlow()` -menetelmää `FlexLayout` -objektissa. 

:::info
Voit määrittää tämän ominaisuuden luotaessa asettelua käyttämällä oikeita suunta- ja wrap-menetelmiä. Esimerkiksi, luodaksesi pystysuuntaisen kääntövirran, käytä `.vertical().wrap()` -yhdistelmää.
:::

### Säiliörakentaja {#container-builder}

Seuraava demo mahdollistaa säiliön rakentamisen valituista flex-ominaisuuksista eri valikoista. Tätä työkalua voidaan käyttää paitsi visuaalisen esimerkin luomiseen eri menetelmistä myös omien asettelujen luomiseen haluamistasi ominaisuuksista. Käytä mukautettua asettelua kopioimalla vain tuotokoodia ja lisäämällä haluamasi elementit käyttöön ohjelmassasi.

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>

<!-- ISOSSA KOODIPALAA NÄYTETÄÄN SÄILIÖ -->
## Item-ominaisuudet {#item-properties}

Item-ominaisuudet eivät vaikuta mihinkään lapsielementteihin `FlexLayout`:ssa, vaan vaikuttavat itse asetteluun. Tämä on hyödyllistä, kun haluat tyylitellä yksittäistä `FlexLayout`-elementtiä, joka on suuremman `FlexLayout`-elementin lapsi eristyksissä muista lapsista sovellettavista tyyleistä.

### Järjestys {#order}

`ItemOrder` -ominaisuus määrittää järjestyksen, jossa komponentit näytetään `FlexLayout`:ssa, ja käytettäessä `FlexLayout`-objektissa antaa itemille tämän asettelun spesifisen järjestysnumeron. Tämä ohittaa oletus "lähdejärjestyksen", joka on rakennettu jokaiselle itemille (se järjestys, jossa komponentti lisätään sen vanhempaan), ja tarkoittaa, että se näytetään ennen korkeamman järjestyksen omaavia itemtejä ja alempiarvoisten järjestyksellä.

Tämä ominaisuus hyväksyy kokonaisluvun, joka määrittää joustavan itemin suhteellisen järjestyksen säiliössä. Mitä pienempi arvo, sitä aikaisemmin itemi ilmestyy järjestyksessä. Esimerkiksi item, jolla on järjestysarvo 1, ilmestyy ennen itemiä, jolla on järjestysarvo 2.

:::caution
On tärkeää huomata, että järjestysominaisuus vaikuttaa vain elementtien visuaaliseen järjestykseen säiliössä, ei niiden todelliseen sijaintiin DOM:issa. Tämä tarkoittaa, että näytönlukijat ja muut apuvälineet lukevat silti elementit siinä järjestyksessä, johon ne ilmestyvät lähdekoodissa, eikä visuaalisessa järjestyksessä.
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### Itse kohdistaminen {#self-alignment}

`FlexLayout` -tekijän itse kohdistaminen viittaa siihen, miten yksittäinen `FlexLayout` -objekti on kohdistettu sen vanhempaan joustaviin säiliöihin poikkisuunnassa, joka on pystysuoraa pääakselia vastaan. Poikkisuuntainen kohdistus ohjataan `Alignment` -ominaisuudella.

Align-self-ominaisuus määrittää yksittäisen joustavan itemin kohdistuksen poikkisuunnassa, ohittaen `AlignContent` -ominaisuuden asetetun oletuskohtauksen `FlexLayout` -objektissa. Tämä sallii yksittäisten `FlexLayout` -objektien kohdistamisen eri tavalla kuin muut säiliössä.

:::info
Itsekohdistamisessa käytetään samoja arvoja kuin sisällön kohdistamisessa.
:::

Tämä ominaisuus on erityisen hyödyllinen, kun sinun on kohdistettava tietty item eri tavalla kuin muut itemit säiliössä. Katso alla olevaa esimerkkiä yksittäisen kohdistetun itemin kohdistamisesta:

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### Flex-perusta {#flex-basis}

`Item Basis` on ominaisuus, jota käytetään yhdessä `FlexLayout` -suunnan kanssa asettaaksesi joustavan itemin alkukoon ennen kuin mitään jäljellä olevaa tilaa jaetaan.

`Item Basis` -ominaisuus määrittää joustavan itemin oletuskooltaan pääakselilla, joka voi olla vaaka (rivin suunta) tai pystysuunnassa (sarakkeen suunta). Tämä ominaisuus asettaa joustavan itemin leveyden tai korkeuden riippuen joustavan suuntaparametrin arvosta.

:::info
Oletusarvoisesti `Item Basis` -ominaisuus on asetettu `autoksi`, mikä tarkoittaa, että itemin koko määräytyy sen sisällön mukaan. Voit kuitenkin myös määrittää itemille tietyn koon käyttämällä erilaisia yksiköitä, kuten pikseleitä (px), em(-yksiköitä) (em), prosentteja (%) tai muitakin CSS-pituusmittayksiköitä.
:::

Seuraava demo mahdollistaa yhden tai useamman laatikon valinnan ja `Item Basis` -arvon muuttamisen valituille itemeille.

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### Flex kasvu ja kutistus {#flex-grow--shrink}

`Item Grow` ja `Item Shrink` ovat kaksi ominaisuutta, jotka toimivat yhdessä ja `Item Basis` -ominaisuuden kanssa määrittämään, miten joustavat itemit kasvavat tai kutistuvat täyttääkseen käytettävissä olevan tilan `FlexLayout` -objektissa.

`Item Grow` -ominaisuus määrittää, kuinka paljon joustava item voi kasvaa suhteessa muihin itemeihin säiliössä. Se ottaa yksikköä ilman arvoa, joka edustaa osuutta käytettävissä olevasta tilasta, joka tulee varata itemille. Esimerkiksi, jos yhdellä itemillä on `Item Grow` -arvo 1 ja toisella arvo 2, toinen item kasvaa kaksinkertaisesti ensimmäiseen verrattuna.

`Item Shrink` -ominaisuus puolestaan määrittää, kuinka paljon joustava item voi kutistua suhteessa muihin itemeihin säiliössä. Se ottaa myös yksikköä ilman arvoa, joka edustaa osuutta käytettävissä olevasta tilasta, joka tulee varata itemille. Esimerkiksi, jos yhdellä itemillä on `Item Shrink` -arvo 1 ja toisella arvo 2, toinen item kutistuu kaksinkertaisesti ensimmäiseen verrattuna.

Kun säiliöllä on enemmän tilaa kuin sen sisältöä varten on tarpeen, joustavat itemit, joiden `Item Grow` -arvo on suurempi kuin 0, laajenevat täyttämään käytettävissä olevan tilan. Tavoitteena on käyttää suhde `Item Grow` -arvoa suhteessa kaikkien säiliöelementtien kokonaisarvoon.

Vastaavasti, kun säiliöllä ei ole tarpeeksi tilaa mukaansa sisältöään, joustavat itemit, joiden `Item Shrink` -arvo on suurempi kuin 0, kutistuvat mahtuakseen käytettävissä olevaan tilaan. Tavoitteena on käyttää suhde `Item Shrink` -arvoa suhteessa kaikkien säiliöelementtien kokonaisarvoon.

## Esimerkkilomake {#example-form}
Alla oleva lomake osoittaa, kuinka `FlexLayout` järjestää syöttökentät rakenteelliseen asetteluun. 

:::tip
Jos preferoit sarakepohjaista rakennetta, tarkista lomakkeen `ColumnsLayout` -versio [`ColumnsLayout`](../components/columns-layout) artikkelissa nähdäksesi kuinka se vertautuu.
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/resources/static/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
