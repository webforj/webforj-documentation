---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 2ea3ba8ae8756dcea1ee5d0eb9fb0cf9
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-kehittäjillä on mahdollisuus valita ei vain tarjoamastaan rikkaasta komponenttikirjastosta, vaan myös integroida komponentteja muualta. Tämän helpottamiseksi `Element`-komponenttia voidaan käyttää yksinkertaistamaan integroitamista yksinkertaisista HTML-elementeistä monimutkaisempiin mukautettuihin web-komponentteihin.

:::important
`Element`-komponenttia ei voi laajentaa, eikä se ole kaikkien webforJ:n komponenttien peruskomponentti. Lue lisää webforJ:n komponenttirakenteesta lukemalla [tämä artikkeli](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Tapahtumien lisääminen {#adding-events}

Voidaksesi hyödyntää elementtisi mukana tulevia tapahtumia, voit käyttää `Element`-komponentin `addEventListener`-menetelmiä. Tapahtuman lisääminen vaatii vähintään tapahtuman tyypin/nimen, jota komponentti odottaa, ja kuuntelijan, joka lisätään tapahtumaan.

Lisäksi on saatavilla lisävaihtoehtoja tapahtumien mukauttamiseksi Event Options -asetusten avulla.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Komponenttien vuorovaikutus {#component-interaction}

`Element`-komponentti toimii säilyttävänä komponenttina muille komponenteille. Se tarjoaa tavan järjestää ja noutaa tietoa lapsikomponenteista ja tarjoaa selkeän joukon toimintoja näiden lapsikomponenttien lisäämiseksi tai poistamiseksi tarpeen mukaan.


### Lapsikomponenttien lisääminen {#adding-child-components}

`Element`-komponentti tukee lapsikomponenttien koostumusta. Kehittäjät voivat järjestää ja hallita monimutkaisia käyttöliittymärakenteita lisäämällä komponentteja lapsiksi `Elementille`. Kolme menetelmää on olemassa sisällön asettamiseksi `Elementiin`:

1. **`add(Component... components)`**: Tämä menetelmä sallii yhden tai useamman komponentin lisäämisen valinnaiseen `String`:iin, joka määrää tietyn slotin käytettäväksi Web-komponentin kanssa. Slotin jättämistä pois lisää komponentin HTML-tunnisteiden väliin.

2. **`setHtml(String html)`**: Tämä menetelmä ottaa `String`:in, joka annetaan menetelmälle, ja injektoi sen HTML:na komponenttiin. Riippuen `Elementistä`, tämä voi renderöityä eri tavoin.

3. **`setText(String text)`**: Tämä menetelmä käyttäytyy samoin kuin `setHtml()`-menetelmä, mutta injektoi kirjaimellista tekstiä `Elementtiin`.


<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
`setHtml()`- tai `setText()`-kutsuminen korvää nykyisen sisällön, joka on komponentin avaus- ja sulkutunnisteiden välillä.
:::

### Komponenttien poistaminen {#removing-components}

Lisäksi komponenttien lisäämisen ohella `Elementiin` on toteutettu seuraavat menetelmät erilaisten lapsikomponenttien poistamiseksi:

1. **`remove(Component... components)`**: Tämä menetelmä ottaa yhden tai useamman komponentin ja poistaa ne lapsikomponenteina.

2. **`removeAll()`**: Tämä menetelmä poistaa kaikki lapsikomponentit `Elementistä`.

### Komponenttien käyttäminen {#accessing-components}

Jotta voit käyttää erilaisia lapsikomponentteja, jotka ovat läsnä `Elementissä`, tai tietoa näistä komponenteista, seuraavat menetelmät ovat käytettävissä:

1. **`getComponents()`**: Tämä menetelmä palauttaa Java `List`:in kaikista `Elementin` lapsista.

2. **`getComponents(String id)`**: Tämä menetelmä on samankaltainen kuin edellä, mutta ottaa palvelinpuolen ID:n tietylle komponentille ja palauttaa sen löydettäessä.

3. **`getComponentCount()`**: Palauttaa lapsikomponenttien määrän, joka on läsnä `Elementissä`. 


## JavaScript-toimintojen kutsuminen {#calling-javascript-functions}

`Element`-komponentti tarjoaa kaksi API-menetelmää, jotka mahdollistavat JavaScript-toimintojen kutsumisen HTML-elementeille.

1. **`callJsFunction(String functionName, Object... arguments)`**: Tämä menetelmä ottaa toiminnon nimen merkkijonona ja valinnaisesti ottaa yhden tai useamman Object:n parametreiksi toiminnolle. Tämä menetelmä suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty**, kunnes JS-menetelmä palautuu, ja tuloksena on round trip. Toiminnon tulokset palautetaan `Object`:ina, jota voidaan muuntaa ja käyttää Javassa.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Kuten edellisessä menetelmässä, toiminnon nimen ja valinnaiset argumentit voidaan välittää. Tämä menetelmä suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:in, joka mahdollistaa lisävuorovaikutuksen toiminnon ja sen kuorman kanssa.

### Parametrien välittäminen {#passing-parameters}

Argumentit, jotka annetaan näille menetelmille, joita käytetään JS-toimintojen suorittamisessa, sarjoitetaan JSON-taulukkona. Kaksi mainittavaa argumenttityyppiä käsitellään seuraavasti:
- `this`: `this`-avainsanan käyttäminen antaa menetelmälle viittauksen asiakaspuolen versioon kutsuvasta komponentista.
- `Component`: Kaikki Java-komponentti-instanssit, jotka välitetään johonkin JsFunction-menetelmistä, korvataan asiakaspuolen versiolla komponentista.

:::info
Sekä synkroniset että asynkroniset funktion kutsumiset odottavat, että `Element` on lisätty DOM:iin ennen toiminnon suorittamista, mutta `callJsFunction()` ei odota mitään `component`-argumenttien liittämistä, mikä voi johtaa epäonnistumiseen. Toisaalta, `callJsFunctionAsync()`-kutsuminen ei ehkä koskaan valmistu, jos komponenttiargumentti ei liity.
:::

Alla olevassa demossa tapahtuma lisätään HTML `Button`-komponenttiin. Tätä tapahtumaa kutsutaan ohjelmallisesti soittamalla `callJsFunctionAsync()`-menetelmää. Tuloksena olevaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:ia käytetään sitten toisen viestilaatikon luomiseen, kun asynkroninen toiminto on saatu päätökseen.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## JavaScriptin suorittaminen {#executing-javascript}

Lisäksi JavaScriptin suorittaminen sovellustasolla on mahdollista myös `Element`-tasolla. Tämän suorittamisen tekeminen `Element`-tasolla mahdollistaa HTML-elementin kontekstin sisällyttämisen suoritukseen. Tämä on tehokas työkalu, joka toimii kehittäjän välineenä vuorovaikutteisille kyvyille asiakaspuolen ympäristöissä.

Samanlaista toimintojen suorittamista voidaan tehdä synkronisesti tai asynkronisesti seuraavilla menetelmillä:

1. **`executeJs(String script)`**: Tämä menetelmä ottaa `String`:in, joka suoritetaan JavaScript-koodina asiakkaalla. Tämä skripti suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty**, kunnes JS-suoritus palautuu ja tuloksena on round trip. Toiminnon tulokset palautetaan `Object`:ina, jota voidaan muuntaa ja käyttää Javassa.

2. **`executeJsAsync(String script)`**: Kuten edellisessä menetelmässä, välitetyllä `String`-parametrilla suoritetaan JavaScript-koodi asiakkaalla. Tämä menetelmä suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:in, joka mahdollistaa lisävuorovaikutuksen toiminnon ja sen kuorman kanssa.

:::tip
Nämä menetelmät pääsevät käsiksi `component`-avainsanaan, joka antaa JavaScript-koodille pääsyn komponentin asiakaspuolen instanssiin, joka suorittaa JavaScriptin.
:::
