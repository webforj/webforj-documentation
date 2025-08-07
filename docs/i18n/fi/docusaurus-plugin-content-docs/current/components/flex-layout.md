---
title: FlexLayout
sidebar_position: 45
_i18n_hash: df051e46de48f07807bf0dc3bcaa641a
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ tarjoaa kehittäjille tehokkaan ja intuitiivisen tavan se järjestää erilaisia sovelluksia ja komponentteja - Flex Layout. Tämä työkalusarja mahdollistaa kohteiden näyttämisen joko pystysuorassa tai vaakasuorassa.

## Flex layout -ominaisuudet {#flex-layout-properties}

Flex layoutin ominaisuudet voidaan jakaa kahteen kategoriaan: ominaisuudet, jotka koskevat layoutin sisällä olevia kohteita, ja ominaisuudet, jotka koskevat itse layoutia. Flex layout, tai vanhelementti, on laatikko/säiliö, joka voi sisältää yhden tai useamman komponentin. Kaikkea Flex Layoutin sisällä kutsutaan kohteeksi tai lapsielemeniksi. Flex Layout tarjoaa joitakin vahvoja keskittymiskykyjä, jotka voidaan saavuttaa sekä säiliö- että kohdeominaisuuksien avulla.

:::tip
webforJ:n layout-komponentti seuraa [CSS:n flexbox-layoutin](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) mallia. Kuitenkin, näitä työkaluja on tarkoitus käyttää täysin Javassa, eikä CSS:n käyttäminen Java API -menetelmien ulkopuolella vaadita.
:::

## Säiliöominaisuudet {#container-properties}

Säiliöominaisuudet koskevat kaikkia komponentteja säiliön sisällä, eivätkä itse layoutia. Ne eivät vaikuta vanhemman suuntaan tai sijoitteluun - vain lapsikomponenttien sisällä.

### Suunta {#direction}

Flex Layout lisää komponentteja vierekkäin kehittäjän valitsemassa suunnassa - joko vaakasuunnassa tai pystysuunnassa. Kun käytät rakennusohjelmaa, käytä joko `horizontal()`, `horizontalReverse()`, `vertical()` tai `verticalReverse()` -menetelmiä, kun kutsut `create()`-menetelmää `FlexLayout`-oliolla tämän layoutin määrittämiseksi objektin luomisen yhteydessä.

Vaihtoehtoisesti käytä `setDirection()`-menetelmää. Vaakasuorat vaihtoehdot ovat joko `FlexDirection.ROW` (vasemmalta oikealle) tai `FlexDirection.ROW_REVERSE` (oikealta vasemmalle), ja pystysuorat vaihtoehdot ovat joko `FlexDirection.COLUMN` (ylös alas) tai `FlexDirection.COLUMN_REVERSE` (alas ylös). Tämä tehdään FlexLayout-oliolla, ei rakennusohjelmalla.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Sijoittelu {#positioning}

Vaakasuoraan lisättyjä komponentteja voidaan myös sijoittaa sekä vaakasuunnassa että pystysuunnassa. Käytä `justify()`, `align()` ja `contentAlign()` -menetelmiä Flex Layout Builderista määrittääksesi sijoittelu uuden Flex Layoutin luomisen yhteydessä.

Vaihtoehtoisesti voit käyttää `setJustifyContent()`-menetelmää FlexLayout-oliolla sijoittaaksesi kohteita vaakasuoraan, ja `setAlignment()`-menetelmää pystysuoran sijoittelun määrittämiseksi. Muuttaaksesi tilaa komponenttien ympärillä poikkisuuntaisella akselilla (y-akselilla vaakasuorassa layoutissa) käytä `setAlignContent()` -menetelmää.

:::tip
`setAlignment()`-menetelmä määrittää, miten kohteet näyttävät poikkisuuntaisella akselilla kokonaisuudessaan säiliössä, ja se on tehokas yksirivisten layoutien osalta.

`setAlignContent()`-menetelmät määrittävät tilan poikkisuuntaisella akselilla, ja ne vaikuttavat vain, kun layoutissa on useita rivejä.
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Kääntäminen {#wrapping}

Jotta Flex Layout -komponenttia voidaan mukauttaa edelleen, voit määrittää käyttäytymisen, jota flex layout osoittaa, kun lisätyt komponentit eivät enää mahdu näkymään. Määritä tämä rakennusohjelman avulla käyttämällä - `nowrap()` (oletus), `wrap()` ja `wrapReverse()` -menetelmiä kääntämisen määrittämiseksi.

Vaihtoehtoisesti, jos layoutisi on jo olemassa, voit käyttää `setWrap()`-menetelmää määrätäksesi, miten komponentit käyttäytyvät, kun ne eivät enää mahdu yhdelle riville.

### Väli {#spacing}

Jotta voit soveltaa vähimmäisväliä kohteiden välillä, voit asettaa gap-ominaisuuden. Se soveltaa tätä väliä vain kohteiden välillä, ei reunoilla.

Gap-ominaisuuden käyttäytyminen voidaan ajatella vähimmäisetäisyydeksi - tämä ominaisuus tulee voimaan vain, jos se on suurin laskettu tila kohteiden välillä. Jos kohteiden välinen tila olisi muuten suurempi toisen lasketun ominaisuuden, kuten `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, vuoksi, niin gap-ominaisuutta ei oteta huomioon.

### Virta {#flow}

Flex virta, joka on yhdistelmä sekä suunta- että kääntöominaisuuksia, voidaan asettaa käyttämällä `setFlow()`-menetelmää Flex Layout -oliolla. 

:::info
Määritä tämä ominaisuus luodessasi layoutia käyttämällä oikeita suuntia ja kääntömenetelmiä. Esimerkiksi, luodaksesi pystysuoran kääntövirran käytä `.vertical().wrap()` -yhdistelmää.
:::

### Säiliörakentaja {#container-builder}

Seuraava demo mahdollistaa säiliön rakentamisen halutuilla flex-ominaisuuksilla, jotka on valittu eri valikoista. Tätä työkalua voidaan käyttää paitsi luomaan visuaalinen esimerkki erilaisista menetelmistä, myös työkaluna luoda omia layoutteja haluamillasi ominaisuuksilla. Käyttääksesi mukautettua layoutia, kopioi vain tulostuskoodi ja lisää haluamasi elementit käytettäväksi ohjelmassasi.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

<!-- SUURI KOODIN LÄHDE, JOKA NÄYTTÄÄ SÄILIÖN -->
## Kohteen ominaisuudet {#item-properties}

Kohteen ominaisuudet eivät vaikuta Flex Layoutin lapsielemenkeihin, vaan itse Layoutiin. Tämä on hyödyllistä yksittäisen Flex Layout -elementin tyylittelyyn, joka on suuremman Flex Layout -elementin lapsi riippumatta kaikista lapsille sovellettavista tyyleistä.

### Käsittely {#order}

`ItemOrder`-ominaisuus määrittelee, miten komponentit näytetään Flex Layoutissa, ja kun sitä käytetään Flex Layoutissa, se määrittää kyseisen kohteen järjestysnumeron. Tämä ohittaa oletusarvoisen "lähdejärjestyksen", joka on rakennettu jokaiselle kohteelle (järjestys, jossa komponentti lisätään vanhempaansa), ja tarkoittaa, että se näytetään ennen kohteita, joilla on korkeampi järjestys, ja jäljessä kohteita, joilla on matalampi järjestys.

Tämä ominaisuus hyväksyy kokonaisluvun arvon, joka määrittää joustavan kohteen suhteellisen järjestyksen säiliössä. Mitä matalampi arvo, sitä aikaisemmin kohde näkyy järjestyksessä. Esimerkiksi, kohde, jolla on järjestysarvo 1, näkyy ennen kohdetta, jolla on järjestysarvo 2.

:::caution
On tärkeää huomata, että järjestysominaisuus vaikuttaa vain visuaaliseen järjestykseen kohteiden sisällä säiliössä, ei niiden todelliseen sijaintiin DOM:ssa. Tämä tarkoittaa, että ruudunlukijat ja muut apuvälineet lukevat silti kohteet siinä järjestyksessä, jossa ne näkyvät lähdekoodissa, eivät visuaalisessa järjestyksessä.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Itse keskittymiskyky {#self-alignment}

Flex Layoutin itse keskittymiskyky viittaa siihen, miten yksi Flex Layout -objekti on kohdistettu sen vanhemman joustavan säiliön sisällä poikkisuunnassa, joka on pystysuorassa pääakseliin nähden. Poikkisuuntaisen akselin keskittymiskykyä ohjaa `Alignment`-ominaisuus.

`align-self`-ominaisuus määrittää yksittäisen joustavan kohteen keskittymisen poikkisuisen akselin varrella, ohittaen `AlignContent`-ominaisuuden oletuskeskittymisen Flex Layout -oliolla. Tämä mahdollistaa yksittäisten Flex Layout -objektien kohdistamisen eri tavalla kuin muissa säiliössä.

:::info
Itse keskittymiskyky käyttää samoja arvoja kuin sisällön keskittyminen.
:::

Tämä ominaisuus on erityisen hyödyllinen, kun sinun on kohdistettava tietty kohde eri tavalla kuin säiliön muut kohteet. Katso alla oleva esimerkki yksittäisen kohteen kohdistamisesta:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex-pohja {#flex-basis}

`Item Basis` on ominaisuus, jota käytetään yhdessä Flex Layoutin suunnan kanssa säätääksesi joustavan kohteen alkuperäistä kokoa ennen kuin kaikki loput tila jaetaan.

`Item Basis` -ominaisuus määrittää joustavan kohteen oletuskokonaisuuden pääakselilla, joka on joko vaakasuora (rivi suunta) tai pystysuora (sarakkeen suunta). Tämä ominaisuus asettaa joustavan kohteen leveyden tai korkeuden joustavan suunnan ominaisuuden arvosta riippuen.

:::info
Oletusarvoisesti `Item Basis` -ominaisuus on asetettu automaattisesti, mikä tarkoittaa, että kohteen koko määritetään sen sisällön perusteella. Voit kuitenkin myös asettaa kohteelle tietyn koon käyttäen erilaisia yksiköitä, kuten pikseleitä (px), em-yksiköitä (em), prosentteja (%) tai muita CSS-pituusyksiköitä.
:::

Seuraava demo mahdollistaa valita yhden tai useamman laatikon ja muuttaa valittujen kohteiden `Item Basis`-arvon.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex kasvu / kutistus {#flex-grow--shrink}

`Item Grow` ja `Item Shrink` ovat kaksi ominaisuutta, jotka toimivat yhdessä keskenään ja `Item Basis` -ominaisuuden kanssa määrittääkseen, miten joustavat kohteet kasvavat tai kutistuvat täyttääkseen käytettävissä olevan tilan Flex Layout -oliolla.

`Item Grow` -ominaisuus määrittää, kuinka paljon joustava kohde voi kasvaa suhteessa muihin säiliön kohteisiin. Se ottaa kokonaislukuarvoa, joka edustaa osuutta käytettävissä olevasta tilasta, joka tulisi osoittaa kohteelle. Esimerkiksi, jos yhdellä kohteella on `Item Grow` -arvo 1 ja toisella arvo 2, niin toinen kohde kasvaa kaksi kertaa enemmän kuin ensimmäinen kohde.

`Item Shrink`-ominaisuus, puolestaan, määrittää, kuinka paljon joustava kohde voi kutistua suhteessa muihin säiliön kohteisiin. Se hyväksyy myös kokonaislukuarvoa, joka edustaa osuutta käytettävissä olevasta tilasta, joka tulisi osoittaa kohteelle. Esimerkiksi, jos yhdellä kohteella on `Item Shrink` -arvo 1 ja toisella arvo 2, niin toinen kohde kutistuu kaksi kertaa enemmän kuin ensimmäinen kohde.

Kun säiliössä on enemmän tilaa kuin sen sisältöä varten, joustavat kohteet, joilla on `Item Grow` -arvo suurempi kuin 0, laajenevat täyttämään käytettävissä olevan tilan. Se, kuinka paljon tilaa kukin kohde saa, määräytyy sen `Item Grow` -arvon ja kaikkien säiliön kohteiden yhteisen `Item Grow` -arvon suhteessa.

Vastaavasti, kun säiliössä ei ole tarpeeksi tilaa sen sisältöjen mahtumiseen, joustavat kohteet, joilla on `Item Shrink` -arvo suurempi kuin 0, kutistuvat mahtuakseen käytettävissä olevaan tilaan. Se, kuinka paljon tilaa kukin kohde luovuttaa, määräytyy sen `Item Shrink` -arvon ja kaikkien säiliön kohteiden yhteisen `Item Shrink` -arvon suhteessa.

## Esimerkkilomake {#example-form}
Alla oleva lomake demonstroi, kuinka `FlexLayout` järjestää syöttökenttiä rakenteelliseen layoutiin.

:::tip
Jos pidät pystysuorasta rakenteesta, tutustu ColumnsLayout-version tähän lomakkeeseen [ColumnsLayout](../components/columns-layout) -artikkelissa nähdäksesi, miten se vertautuu.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
