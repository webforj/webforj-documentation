---
title: FlexLayout
sidebar_position: 45
_i18n_hash: 5c12042a5890f07259e77e0d2111a5c6
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

`FlexLayout`-komponentti järjestää lapset komponentit riville tai sarakkeelle käyttäen CSS Flexbox -mallia. Se antaa sinulle hallinnan kohdistamisesta, välistyksestä, kääntämisestä ja siitä, kuinka kohteet kasvavat tai pienenevät täyttääkseen käytettävissä olevan tilan.

<!-- INTRO_END -->

## Flex-layout-ominaisuudet {#flex-layout-properties}

Flex-layoutin ominaisuudet voidaan jakaa kahteen kategoriaan: ominaisuudet, jotka koskevat laitteita layoutissa, ja ominaisuudet, jotka koskevat itse layouttia. Flex layout, tai vanhempielementti, on laatikko/säiliö, joka voi sisältää yhden tai useamman komponentin. Kaikesta Flex Layoutin sisällä kutsutaan laitteeksi tai lapsielementiksi. Flex Layout tarjoaa joitakin vankkoja kohdistusmahdollisuuksia, jotka voidaan saavuttaa joko säiliö- tai laiteominaisuuksien avulla.

:::tip
webforJ:n layout-komponentti seuraa [CSS:n flexbox-layoutin](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) mallia. Nämä työkalut on kuitenkin tarkoitettu käytettäväksi kokonaisuudessaan Javassa, eikä niiden tarvitse edellyttää CSS:n käyttöä Java API -menetelmien ulkopuolella.
:::

## Säiliöominaisuudet {#container-properties}

Säiliöominaisuudet koskevat kaikkia komponentteja komponentin sisällä, eivätkä itse layouttia. Ne eivät vaikuta vanhemman suuntaan tai sijoitteluun - vain lapsikomponentteihin.

### Suunta {#direction}

Flex Layout lisää komponentteja vierekkäin kehittäjän valitseman suunnan mukaan - joko vaakasuoraan tai pystysuoraan. Kun käytät rakentajaa, käytä `horizontal()`, `horizontalReverse()`, `vertical()` tai `verticalReverse()` -menetelmiä kutsuessasi `create()`-menetelmää `FlexLayout`-objektilla, jotta voit määrittää tämän layoutin, kun objekti luodaan.

Vaihtoehtoisesti voit käyttää `setDirection()`-menetelmää. Vaakasuorat vaihtoehdot ovat joko `FlexDirection.ROW` (vasemmalta oikealle) tai `FlexDirection.ROW_REVERSE` (oikealta vasemmalle), ja pystysuorat vaihtoehdot ovat joko `FlexDirection.COLUMN` (ylhäältä alas) tai `FlexDirection.COLUMN_REVERSE` (alhaalta ylös). Tämä tehdään FlexLayout-objektilla, ei rakentajalla.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Sijoittaminen {#positioning}

Vaakasuoraan lisättyjä komponentteja voidaan myös sijoittaa sekä vaaka- että pystysuoraan. Käytä `justify()`, `align()` ja `contentAlign()` -menetelmiä Flex Layout Builderista määrittääksesi sijoitusta uuden Flex Layoutin luomisen yhteydessä.

Vaihtoehtoisesti voit käyttää itse FlexLayout-objektilla `setJustifyContent()`-menetelmää sijoittaaksesi kohteita vaakasuoraan ja `setAlignment()`-menetelmää pystysuoran sijoituksen määrittämiseen. Muuttaaksesi tilaa komponenttien ympärillä poikkisaksalla (y-akselilla vaakasuorissa layoutissa) käytä `setAlignContent()` -menetelmää.

:::tip
`setAlignment()`-menetelmä määrittää, kuinka kohteet näkyvät poikkisaksalla yhtenä kokonaisuutena säiliössä, ja se on tehokas yksirivisissä viivoissa.

`setAlignContent()` -menetelmä määrittää tilan poikkisaksalla, ja se vaikuttaa vain, kun layoutissa on useita rivejä.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Kääntö {#wrapping}

Jatkaksesi Flex Layout -komponentin mukauttamista, voit määrittää flex layoutin käyttäytymisen, kun lisäämäsi komponentit eivät enää mahdu näyttöön. Konfiguroidaksesi tätä rakentajan avulla, käytä `nowrap()` (oletus), `wrap()` ja `wrapReverse()` -menetelmiä kääntämisen määrittämiseen.

Vaihtoehtoisesti, jos layoutisi on jo olemassa, käytä `setWrap()` -menetelmää määrittääksesi, miten komponentit käyttäytyvät, kun niitä ei enää voida mahtua yhdelle riville.

### Välistys {#spacing}

Voit asettaa vähimmäistilan kohteiden väliin asettamalla gap-ominaisuuden. Se sovelletaan tätä väliä vain kohteiden välillä, ei ulkoreunoilla. 

Gap-ominaisuuden käyttäytyminen voidaan ajatella vähimmäisetäisyydeltä - tämä ominaisuus vaikuttaa vain, jos se on suurin lasketttu tila kohteiden välillä. Jos tila kohteiden välillä olisi muuten suurempi toisen lasketun ominaisuuden vuoksi, kuten `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)` vuoksi, niin gap-ominaisuutta ei oteta huomioon.

### Virta {#flow}

Flex flow, joka on yhdistelmä sekä suunta- että kääntöominaisuuksia, voidaan asettaa käyttämällä `setFlow()`-menetelmää Flex Layout -objektilla.

:::info
Määritä tämä ominaisuus luomalla layout, käyttämällä oikeita suunta- ja kääntömenetelmiä. Esimerkiksi, luodaksesi sarakekääntövirran, käytä `.vertical().wrap()` -yhdistelmää.
:::

### Säiliön rakennus {#container-builder}

Seuraava demo mahdollistaa sinulle säiliön rakentamisen halutuilla flex-ominaisuuksilla, jotka on valittu eri valikoista. Tätä työkalua voidaan käyttää paitsi visuaalisen esimerkin luomiseen eri menetelmistä, myös työkaluna omien layouttiesi luomiseen haluamillasi ominaisuuksilla. Käyttääksesi mukautettua layoutia, kopioi vain tuotoksen koodi ja lisää haluamasi elementit ohjelmassasi.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

<!-- SUURI KOODIN LÄHDE, JOKA NÄYTTÄÄ SÄILIÖN -->
## Kohteen ominaisuudet {#item-properties}

Kohteen ominaisuudet eivät vaikuta Flex Layoutin lapsielementteihin, vaan itse Layoutiin. Tämä on hyödyllistä, kun stylistätte yksittäistä Flex Layout -elementtiä, joka on suuremman Flex Layout -elementin lapsi, riippumatta tyyleistä, jotka koskevat kaikkia lapsia.

### Järjestys {#order}

`ItemOrder`-ominaisuus määrittää, kuinka komponentit näytetään Flex Layoutissa, ja kun käytetään Flex Layoutissa, se määrittää, mikä on tämän layoutin erityinen järjestysnumero. Tämä ohittaa jokaisen kohteen sisäänrakennetun "lähdejärjestyksen", mikä tarkoittaa, että se näytetään ennen korkeammalla järjestyksellä olevia kohteita ja sen jälkeen alhaisemmalla järjestyksellä olevia kohteita.

Tämä ominaisuus hyväksyy kokonaisluvun arvoa, joka määrittää flex-kohteen suhteellisen järjestyksen säiliössä. Mitä alhaisempi arvo, sitä aikaisemmin kohde tulee järjestykseen. Esimerkiksi, jos kohteella on järjestysarvo 1, se tulee ennen kohdetta, jonka järjestysarvo on 2.

:::caution
On tärkeää huomata, että järjestysominaisuus vaikuttaa vain kohteiden visuaaliseen järjestykseen säiliössä, ei niiden todelliseen sijaintiin DOM:ssa. Tämä tarkoittaa, että ruudunlukijat ja muut apuvälineet lukevat silti kohteet siinä järjestyksessä, jossa ne näkyvät lähdekoodissa, ei visuaalisessa järjestyksessä.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Itsensä kohdistaminen {#self-alignment}

Flex Layoutin itsensä kohdistaminen viittaa siihen, kuinka yksittäinen Flex Layout -objekti on kohdistettu sen vanhempaan flex-säiliöön poikkisaksalla, joka on kohtisuora pääakseliin. Poikkisaksa kohdistus ohjataan `Alignment`-ominaisuudella.

Align-self-ominaisuus määrittää yksittäisen flex-kohteen kohdistamisen poikkisaksalla, ohittaen oletuskohdistuksen, joka on asetettu `AlignContent`-ominaisuudelle Flex Layout -objektissa. Tämä antaa sinulle mahdollisuuden kohdistaa yksittäiset Flex Layout -objektit eri tavalla kuin muut säiliössä.

:::info
Itsensä kohdistaminen käyttää samoja arvoja kuin sisältökohdistus
:::

Tämä ominaisuus on erityisen hyödyllinen, kun sinun on kohdistettava tietty kohde eri tavalla kuin muut kohteet säiliössä. Katso alla oleva esimerkki, jossa kohdistetaan yksittäistä kohdetta:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex-pohja {#flex-basis}

`Item Basis` on ominaisuus, joka käytetään yhdessä Flex Layoutin suunnan kanssa asettamaan flex-kohteen alkuperäinen koko ennen kuin muuta tilaa jaetaan.

`Item Basis`-ominaisuus määrittää flex-kohteen oletuskoko pääakselilla, joka on joko vaakasuora (Rivisuunta) tai pystysuora (Sarakkeen suunta). Tämä ominaisuus asettaa flex-kohteen leveyden tai korkeuden riippuen flex-suuntan arvosta.

:::info
Oletusarvoisesti `Item Basis` -ominaisuus on asetettu automaattiseen, mikä tarkoittaa, että kohteen koko määräytyy sen sisällön mukaan. Voit kuitenkin myös asettaa tietyn koon kohteelle käyttäen erilaisia yksiköitä, kuten pikseleitä (px), em-yksiköitä (em), prosentteja (%) tai mitä tahansa muuta CSS-pituusyksikköä.
:::

Seuraava demo mahdollistaa sinulle yhden tai useamman laatikon valitsemisen ja `Item Basis`-arvon muuttamisen valituissa kohteissa.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex-kasvu / supistus {#flex-grow--shrink}

`Item Grow` ja `Item Shrink` ovat kaksi ominaisuutta, jotka toimivat yhdessä keskenään ja `Item Basis`-ominaisuuden kanssa määrittämään, kuinka flex-kohteet kasvavat tai kutistuvat täyttääkseen käytettävissä olevan tilan Flex Layout -objektissa.

`Item Grow`-ominaisuus määrittää, kuinka paljon flex-kohde voi kasvaa suhteessa muihin säiliön kohteisiin. Se ottaa yksikköä ilmaiseman arvon, joka edustaa osuutta käytettävissä olevasta tilasta, joka tulisi jakaa kohteelle. Esimerkiksi, jos yhdellä kohteella on `Item Grow` -arvo 1 ja toisella arvo 2, toinen kohde kasvaa kaksinkertaisesti verrattuna ensimmäiseen kohteeseen.

`Item Shrink` -ominaisuus puolestaan määrittää, kuinka paljon flex-kohde voi kutistua suhteessa muihin säiliön kohteisiin. Se ottaa myös yksikköä ilmaiseman arvon, joka edustaa osuutta käytettävissä olevasta tilasta, joka tulisi jakaa kohteelle. Esimerkiksi, jos yhdellä kohteella on `Item Shrink` -arvo 1 ja toisella arvo 2, toinen kohde kutistuu kaksinkertaisesti verrattuna ensimmäiseen kohteeseen.

Kun säiliössä on enemmän tilaa kuin mitä sen sisällön mahtuu, flex-kohteet, joilla on `Item Grow` -arvo suurempi kuin 0, laajenevat täyttämään käytettävissä olevan tilan. Se, kuinka paljon tilaa kukin kohde saa, määräytyy sen `Item Grow` -arvon suhteena kaikkien säiliön kohteiden kokonaisenkasvuarvoon.

Samoin, kun säiliössä ei ole tarpeeksi tilaa mahtaakseen sen sisältöä, flex-kohteet, joilla on `Item Shrink` -arvo suurempi kuin 0, kutistuvat sopimaan käytettävissä olevaan tilaan. Se, kuinka paljon tilaa kukin kohde luovuttaa, määräytyy sen `Item Shrink` -arvon suhteena kaikkien säiliön kohteiden kokonaisenkasvuarvoon.

## Esimerkkilomake {#example-form}
Alla oleva lomake osoittaa, kuinka `FlexLayout` järjestää syöttökentät rakenteelliseen layoutiin.

:::tip
Jos suosittelet sarakepohjaista rakennetta, tutustu lomakkeen ColumnsLayout-versioon artikkelissa [`ColumnsLayout`](../components/columns-layout) ja vertaa sitä.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
