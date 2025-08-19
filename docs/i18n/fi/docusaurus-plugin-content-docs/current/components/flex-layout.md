---
title: FlexLayout
sidebar_position: 45
_i18n_hash: bd3f6177603a98c20d4958a9c40dd49f
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ tarjoaa kehittäjille tehokkaan ja intuitiivisen tavan asettaa erilaisia sovelluksia ja komponentteja - Flex Layout. Tämä työkalupakki mahdollistaa kohteiden näyttämisen joko pystysuunnassa tai vaakasuunnassa. 

## Flex layout -ominaisuudet {#flex-layout-properties}

Flex layoutin ominaisuudet voidaan jakaa kahteen kategoriaan: ominaisuudet, jotka soveltuvat layoutin sisällä oleville kohteille, ja ominaisuudet, jotka soveltuvat itse layoutille. Flex layout, tai ylempi elementti, on laatikko/säiliö, joka voi sisältää yhden tai useamman komponentin. Kaikkea Flex Layoutin sisällä kutsutaan kohteeksi tai lapsielementiksi. Flex Layout tarjoaa joitakin voimakkaita kohdistusmahdollisuuksia, jotka voidaan saavuttaa joko säiliö- tai kohdeominaisuuksien avulla.

:::tip
webforJ:n layout-komponentti seuraa [CSS:n flexbox-layoutin](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) kaavaa. Kuitenkin, näitä työkaluja on suunniteltu käytettäväksi täysin Javassa, eikä CSS:n soveltamista Java API:n menetelmien ulkopuolella vaadita.
:::

## Säiliöominaisuudet {#container-properties}

Säiliöominaisuudet soveltuvat kaikkiin komponentteihin, jotka sijaitsevat komponenteissa, eivätkä itse layoutille. Ne eivät vaikuta vanhemman suuntaan tai sijoitteluun - vain lapsikomponentteihin.

### Suunta {#direction}

Flex Layout lisää komponentteja vierekkäin kehittäjän valitseman suunnan mukaan - joko vaaka- tai pystysuunnassa. Rakentajaa käytettäessä hyödynnä joko `horizontal()`, `horizontalReverse()`, `vertical()` tai `verticalReverse()` -menetelmiä, kun kutsut `create()`-menetelmää `FlexLayout`-objektilla konfiguroidaksesi tämän layoutin sen luomisen yhteydessä.

Vaihtoehtoisesti voit käyttää `setDirection()`-menetelmää. Vaakasuuntaiset vaihtoehdot ovat joko `FlexDirection.ROW` (vasemmalta oikealle) tai `FlexDirection.ROW_REVERSE` (oikealta vasemmalle), ja pystysuuntaiset vaihtoehdot ovat joko `FlexDirection.COLUMN` (ylhäältä alas) tai `FlexDirection.COLUMN_REVERSE` (alhaalta ylös). Tämä tehdään FlexLayout-objektilla, ei rakennettaessa.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Sijoittaminen {#positioning}

Vaakasuoraan lisätyt komponentit voidaan myös sijoittaa sekä vaakasuunnassa että pystysuunnassa. Käytä `justify()`, `align()` ja `contentAlign()` -menetelmiä Flex Layout Builderissa konfiguroidaksesi sijoittamisen, kun luot uuden Flex Layoutin.

Vaihtoehtoisesti voit käyttää itse FlexLayout-objektissa `setJustifyContent()`-menetelmää sijoittaaksesi elementtejä vaakasuunnassa ja `setAlignment()`-menetelmää määrittääksesi pystysuoran sijoittamisen. Muuttaaksesi tilaa komponenttien ympärillä poikkisektorilla (y-akseli vaakasuorassa layoutissa), käytä `setAlignContent()`-menetelmää.

:::tip
`setAlignment()`-menetelmällä voit määrittää kuinka kohteet näkyvät poikkisektorilla kokonaisuudessaan säiliössä, ja se on tehokas yksirivisten layoutien kohdalla.

`setAlignContent()`-menetelmällä voit määrittää tilan poikkisektorilla, ja se vaikuttaa vain silloin, kun layoutissa on useita rivejä.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Kääntäminen {#wrapping}

Mukauttaaksesi Flex Layout -komponenttia edelleen voit määrittää joustavan layoutin käyttäytymisen, kun lisätyt komponentit eivät enää mahdu näyttöön. Rakentajaa käytettäessä hyödynnä `nowrap()` (oletus), `wrap()` ja `wrapReverse()` -menetelmiä kääntämisen konfiguroimiseksi.

Vaihtoehtoisesti, jos layoutisi on jo olemassa, käytä `setWrap()`-menetelmää määrittääksesi, kuinka komponentit käyttäytyvät, kun ne eivät enää mahdu yhdelle riville.

### Väli {#spacing}

Voidaksesi asettaa vähimmäisvälin kohteiden välille voit asettaa gap-ominaisuuden. Se soveltuu vain erilaisten kohteiden väliin, ei ulkoreunoihin. 

Gap-ominaisuuden käyttäytyminen voidaan ajatella vähimmäisetäisyys, - tämä ominaisuus vaikuttaa vain, jos se on suurin lasketun tilan väliin. Jos kohteiden väli olisi muuten suurempi toisen lasketun ominaisuuden vuoksi, kuten `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, niin gap-ominaisuus jätetään huomiotta.

### Virta {#flow}

Flex virta, joka on yhdistelmä sekä suunta- että kääntöominaisuuksia, voidaan asettaa käyttämällä `setFlow()`-menetelmää Flex Layout -objektissa. 

:::info
Määrittääksesi tämän ominaisuuden layoutin luomisen yhteydessä, käytä oikeita suunta- ja kääntämismenetelmiä. Esimerkiksi luodaksesi pystysuoran kääntösuoran, käytä `.vertical().wrap()`-yhdistelmää.
:::

### Säiliörakentaja {#container-builder}

Seuraava demo sallii sinun rakentaa säiliön haluamillasi joustavuusominaisuuksilla, jotka voit valita eri valikoista. Tämä työkalu voidaan käyttää ei vain visuaalisen esimerkin luomiseen eri menetelmistä, vaan myös työkaluksi omien layoutien luomiseen haluamillasi ominaisuuksilla. Käytääksesi mukautettua layoutia, kopioi vain tulostuskoodi ja lisää haluamasi elementit ohjelmaasi.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>



<!-- SUURI KOODIN OSIO NÄYTETTÄVÄ KOTELLO -->
## Kohdeominaisuudet {#item-properties}

Kohdeominaisuudet eivät vaikuta Flex Layoutin lapsielementteihin, vaan itse layoutiin. Tämä on hyödyllistä, kun haluat tyylitellä tietyn Flex Layout -elementin, joka on suuremman Flex Layout -elementin lapsi riippumatta jokaisen lapsen soveltamista tyyleistä.

### Järjestys {#order}

`ItemOrder`-ominaisuus määrittää, kuinka komponentit näkyvät Flex Layoutissa, ja kun sitä käytetään Flex Layoutissa, se asettaa kohteelle kyseisen layoutin järjestysnumeron. Tämä ohittaa oletusarvoisen "lähdejärjestyksen", joka on rakennettu jokaiselle kohteelle (järjestys, jossa komponentti lisätään sen vanhempaan), ja tarkoittaa, että se näkyy ennen korkeammalla järjestysnumerolla varustettuja kohteita ja jälkeen alhaisemmalla järjestysnumerolla varustettujen kohteiden.

Tämä ominaisuus hyväksyy kokonaisluvun, joka ei sisällä yksikköä ja määrittelee joustavan kohteen suhteellisen järjestyksen säiliössä. Mitä alhaisempi arvo, sitä aikaisemmin kohde näkyy järjestyksessä. Esimerkiksi, jos kohteella on järjestysarvo 1, se näkyy ennen kohdetta, jonka järjestysarvo on 2.

:::caution
On tärkeää huomata, että järjestysominaisuus vaikuttaa vain kohteiden visuaaliseen järjestykseen säiliössä, ei niiden todelliseen sijaintiin DOM:ssa. Tämä tarkoittaa, että ruudunlukijat ja muut apuvälineet lukevat silti kohteet järjestyksessä, jossa ne esiintyvät lähdekoodissa, ei visuaalisessa järjestyksessä.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Itse kohdistaminen {#self-alignment}

Flex Layoutin itse kohdistaminen viittaa siihen, kuinka yksittäinen Flex Layout -objekti kohdistuu sen vanhemman joustavassa säiliössä poikkisektorilla, joka on kohtisuorassa pääakselia vastaan. Poikkisektorin kohdistusta ohjataan `Alignment`-ominaisuudella.

Align-self-ominaisuus määrittelee yksittäisen joustavan kohteen kohdistuksen poikkisektorilla, ohittaen oletuskohdistuksen, joka on asetettu `AlignContent`-ominaisuudella Flex Layout -objektissa. Tämä mahdollistaa yksittäisten Flex Layout -objektien kohdistamisen eri tavalla kuin muut säiliössä.

:::info
Itse kohdistaminen käyttää samoja arvoja kuin sisältökohdistus.
:::

Tämä ominaisuus on erityisen hyödyllinen, kun tarvitset kohdistaa tietyn kohteen eri tavalla kuin muut säiliössä. Katso alla olevasta esimerkistä esimerkki yksittäisen kohteen kohdistamisesta:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex-peruste {#flex-basis}

`Item Basis` on ominaisuus, jota käytetään yhdessä Flex Layoutin suunnan kanssa määrittämään joustavan kohteen alkuperäinen koko ennen kuin loput tila jakautuu.

`Item Basis` -ominaisuus määrittelee joustavan kohteen oletuskoko pääakselilla, joka on joko vaakasuora (rivisuunnassa) tai pystysuora (sarakkeessa). Tämä ominaisuus määrää joustavan kohteen leveyden tai korkeuden riippuen joustavan suuntauksen arvosta.

:::info
Oletusarvoisesti `Item Basis` -ominaisuus on asetettu automaattisesti, mikä tarkoittaa, että kohteen koko määräytyy sen sisällön mukaan. Voit kuitenkin myös määrittää tietyn koon kohteelle käyttäen erilaisia yksikköjä, kuten pikseleitä (px), em-yksikköjä (em), prosentteja (%) tai muita CSS-pituusyksiköitä.
:::

Seuraava demo sallii sinun valita yhden tai useamman laatikon ja muuttaa valittujen kohteiden `Item Basis` -arvon.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex kasvu / supistuminen {#flex-grow--shrink}

`Item Grow` ja `Item Shrink` ovat kaksi ominaisuutta, jotka toimivat yhdessä toistensa ja `Item Basis` -ominaisuuden kanssa määrittämään, kuinka joustavat kohteet kasvavat tai supistuvat täyttääkseen käytettävissä olevan tilan Flex Layout -objektissa.

`Item Grow` -ominaisuus määrittelee, kuinka paljon joustava kohde voi kasvaa suhteessa muihin säiliön kohteisiin. Se hyväksyy kokonaisarvon, joka edustaa osuuden käytettävissä olevasta tilasta, joka tulisi varata kohteelle. Esimerkiksi, jos yhdellä kohteella on `Item Grow` -arvo 1 ja toisella arvo 2, toinen kohde kasvaa kaksinkertaisesti ensimmäisen kohteen verrattuna.

`Item Shrink` -ominaisuus, puolestaan, määrittelee, kuinka paljon joustava kohde voi kutistua suhteessa muihin säiliön kohteisiin. Se hyväksyy myös kokonaisarvon, joka edustaa osuuden käytettävissä olevasta tilasta, joka tulisi varata kohteelle. Esimerkiksi, jos yhdellä kohteella on `Item Shrink` -arvo 1 ja toisella arvo 2, toinen kohde kutistuu kaksinkertaisesti ensimmäisen kohteen verrattuna.

Kun säiliössä on enemmän tilaa kuin sen sisältöä mahtuu, joustavat kohteet, joilla on `Item Grow` -arvo suurempi kuin 0, laajenevat täyttämään käytettävissä olevan tilan. Kunkin kohteen saama tila määräytyy sen `Item Grow` -arvon ja kaikkien säiliön kohteiden `Item Grow` -arvojen kokonaisuuden suhteessa.

Samoin, kun säiliöllä ei ole tarpeeksi tilaa sen sisältöjen mahtumiseksi, joustavat kohteet, joilla on `Item Shrink` -arvo suurempi kuin 0, supistuvat mahtuakseen käytettävissä olevaan tilaan. Kunkin kohteen luovuttama tila määräytyy sen `Item Shrink` -arvon ja kaikkien säiliön kohteiden `Item Shrink` -arvojen kokonaisuuden suhteessa.

## Esimerkkilomake {#example-form}
Alla oleva lomake havainnollistaa, kuinka `FlexLayout` järjestää syöttökenttiä rakenteelliseen layoutiin. 

:::tip
Jos suosittelet pystysuuntaista rakennetta, tutustu sarakkeet-layout-version tähän lomakkeeseen artikkelissa [`ColumnsLayout`](../components/columns-layout) nähdäksesi, miten se vertautuu.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
