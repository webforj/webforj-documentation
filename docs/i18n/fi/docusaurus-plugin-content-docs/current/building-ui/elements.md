---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 067ff9e31676f6991dab011252151043
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ:n kehittäjillä on mahdollisuus valita paitsi runsasta komponenttikirjastoa, myös integroida komponentteja muualta. Tämän helpottamiseksi `Element`-komponenttia voidaan käyttää yksinkertaistamaan minkä tahansa integrointia yksinkertaisista HTML-elementeistä monimutkaisempien mukautettujen verkkokomponenttien saakka.

:::important
`Element`-komponenttia ei voi laajentaa, eikä se ole kaikkien webforJ:n komponenttien peruskomponentti. Lue lisää webforJ:n komponenttihierarkiasta [tästä artikkelista](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Tapahtumien lisääminen {#adding-events}

Jotta voit hyödyntää elementtiisi mahdollisesti liittyviä tapahtumia, voit käyttää `Element`-komponentin `addEventListener`-menetelmiä. Tapahtuman lisääminen vaatii vähintään tapahtuman tyypin/nimen, jota komponentti odottaa, ja kuuntelijan, joka lisätään tapahtumaan.

Lisäksi on muita vaihtoehtoja, jotka mahdollistavat tapahtumien tarkemman mukauttamisen käyttämällä tapahtumaoptioita.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Komponenttien vuorovaikutus {#component-interaction}

`Element`-komponentti toimii säilönä muille komponenteille. Se tarjoaa tavan järjestää ja hakea tietoa lapsikomponenteilta ja tarjoaa selkeän joukon toimintoja, joilla voidaan lisätä tai poistaa näitä lapsikomponentteja tarpeen mukaan.

### Lapsikomponenttien lisääminen {#adding-child-components}

`Element`-komponentti tukee lapsikomponenttien koostamista. Kehittäjät voivat järjestää ja hallita monimutkaisia käyttöliittymärakenteita lisäämällä komponentteja lapsiksi `Element:lle`. Kolme menetelmää on käytössä sisällön asettamiseksi `Element`-komponenttiin:

1. **`add(Component... components)`**: Tämä menetelmä sallii yhden tai useamman komponentin lisäämisen valinnaiseen `String`:iin, joka määrittelee tietyn slotin, kun sitä käytetään verkkokomponentin kanssa. Slotin jättämisessä huomiotta komponentti lisätään HTML-tunnisteiden väliin.

2. **`setHtml(String html)`**: Tämä menetelmä ottaa `String`:n, joka annetaan menetelmälle, ja injektoi sen HTML:na komponenttiin. Riippuen `Element`:stä, tämä saatetaan renderöidä eri tavoin.

3. **`setText(String text)`**: Tämä menetelmä toimii samalla tavalla kuin `setHtml()`-menetelmä, mutta injektoi kirjaimellista tekstiä `Element`:iin.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
`setHtml()`- tai `setText()`-kutsuminen korvää sisällön, joka tällä hetkellä sijaitsee elementin avaus- ja sulkutunnisteiden välillä.
:::

### Komponenttien poistaminen {#removing-components}

Lisäksi komponenttien lisäämisen mahdollisuuden lisäksi `Element`:lle on toteutettu seuraavat menetelmät erilaisten lapsikomponenttien poistamiseksi:

1. **`remove(Component... components)`**: Tämä menetelmä ottaa yhden tai useamman komponentin ja poistaa ne lapsikomponenteista.

2. **`removeAll()`**: Tämä menetelmä poistaa kaikki lapsikomponentit `Element`:stä.

### Komponenttien hakeminen {#accessing-components}

Jotta voit käyttää erilaisia lapsikomponentteja, jotka ovat läsnä `Element`:ssä, tai tietoa näistä komponenteista, seuraavat menetelmät ovat käytettävissä:

1. **`getComponents()`**: Tämä menetelmä palauttaa Java `List`:n kaikista `Element`:n lapsista.

2. **`getComponents(String id)`**: Tämä menetelmä on samanlainen kuin edellä oleva, mutta ottaa palvelinpuolen ID:n tietyltä komponentilta ja palauttaa sen, kun se löytyy.

3. **`getComponentCount()`**: Palauttaa lukumäärän lapsikomponenteista, jotka ovat läsnä `Element`:ssä.

## JavaScript-funktioiden kutsuminen {#calling-javascript-functions}

`Element`-komponentti tarjoaa kaksi API-menetelmää, jotka sallivat JavaScript-funktioiden kutsumisen HTML-elementeille.

1. **`callJsFunction(String functionName, Object... arguments)`**: Tämä menetelmä ottaa funktio-nimen merkkijonona ja valinnaisesti ottaa yhden tai useamman olion parametreiksi funktiolle. Tämä menetelmä suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty**, kunnes JS-menetelmä palauttaa, ja tuloksena on pyynnön ja vasteen kierto. Funktion tulokset palautetaan `Object`:ina, jota voidaan luokitella ja käyttää Javassa.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Kuten edellisessä menetelmässä, voidaan antaa funktio-nimi ja valinnaiset argumentit funktiolle. Tämä menetelmä suoritetaan asynkronisesti eikä **estä suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:in, joka mahdollistaa lisävuorovaikutuksen funktion ja sen kuormituksen kanssa.

### Parametrien siirtäminen {#passing-parameters}

Nämä menetelmät, joille annetaan argumentteja, jotka käytetään JS-funktioiden suorituksessa, sarjoitetaan JSON-taulukkona. Kaksi huomattavaa argumenttityyppiä käsitellään seuraavasti:
- `this`: `this`-avainsanan käyttäminen antaa menetelmälle viitteen kutsuvan komponentin asiakaspuolen versioon.
- `Component`: Kaikki Java-komponentti-instanssit, jotka annetaan yhteen JsFunction-menetelmistä, korvataan komponentin asiakaspuolen versiolla.

:::info
Sekä synkroniset että asynkroniset funktiokutsut odottavat, kunnes `Element` on lisätty DOM:iin ennen kuin funktiota suoritetaan, mutta `callJsFunction()` ei odota komponenttiargumenttien liittämistä, mikä voi johtaa epäonnistumiseen. Toisaalta `callJsFunctionAsync()`-menetelmän kutsuminen ei välttämättä koskaan valmistu, jos komponenttiargumenttia ei koskaan liitetä.
:::

Alla olevassa demossa tapahtuma lisätään HTML `Button`:iin. Tämä tapahtuma laukaistaan sitten ohjelmallisesti kutsumalla `callJsFunctionAsync()`-menetelmää. Tämän seurauksena syntyy <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, jota käytetään luomaan toinen viestiruudun, kun asynkroninen toiminto on saatu päätökseen.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## JavaScriptin suorittaminen {#executing-javascript}

Lisäksi JavaScriptin suorittaminen sovellusnäkökulmasta, on mahdollista suorittaa JavaScriptiä myös `Element`-tason käytössä. Tällainen suorittaminen `Element`-tasolla mahdollistaa HTML-elementin kontekstiin sisällyttämisen suorittamisen aikana. Tämä on tehokas työkalu, joka toimii kehittäjän väylänä interaktiivisiin kykyihin asiakaspuolen ympäristöissä.

Funktion suorittamisen tavoin JavaScriptin suorittaminen voidaan tehdä synkronisesti tai asynkronisesti seuraavilla menetelmillä:

1. **`executeJs(String script)`**: Tämä menetelmä ottaa merkkijonon, joka suoritetaan JavaScript-koodina asiakkaalla. Tämä skripti suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty**, kunnes JS-suoritus palauttaa, ja tuloksena on pyynnön ja vasteen kierto. Tulokset palautetaan `Object`:ina, jota voidaan luokitella ja käyttää Javassa.

2. **`executeJsAsync(String script)`**: Kuten edellisessä menetelmässä, annettu merkkijonoparametri suoritetaan JavaScript-koodina asiakkaalla. Tämä menetelmä suoritetaan asynkronisesti eikä **estä suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:in, joka mahdollistaa lisävuorovaikutuksen funktion ja sen kuormituksen kanssa.

:::tip
Nämä menetelmät pääsevät `component`-avainsanaan, joka antaa JavaScript-koodille pääsyn komponentin asiakaspuolen instanssiin, joka suorittaa JavaScriptiä.
:::
