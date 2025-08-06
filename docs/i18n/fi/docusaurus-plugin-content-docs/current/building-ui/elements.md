---
sidebar_position: 3
title: Elements
slug: element
_i18n_hash: d77ff55b483b72de9ee1d36473d7751d
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-kehittäjillä on mahdollisuus valita paitsi tarjoamastaan kattavasta komponenttikirjastosta, myös integroida komponentteja muualta. Tämän helpottamiseksi `Element`-komponenttia voi käyttää yksinkertaisten HTML-elementtien ja monimutkaisempien mukautettujen web-komponenttien integroinnin yksinkertaistamiseksi.

:::important
`Element`-komponenttia ei voi laajentaa, eikä se ole peruskomponentti kaikille webforJ:n komponenteille. Lisätietoja webforJ:n komponenttierarkkiasta, lue [tämä artikkeli](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Tapahtumien lisääminen {#adding-events}

Voidaksesi hyödyntää elementtiisi liittyviä tapahtumia, voit käyttää `Element`-komponentin `addEventListener`-menetelmiä. Tapahtuman lisääminen vaatii vähintään tapahtuman tyypin/nimen, jota komponentti odottaa, sekä kuuntelijan, joka lisätään tapahtumaan.

On myös lisäoptioita, joilla voit mukauttaa tapahtumia edelleen käyttämällä Tapahtumaasetuksia.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Komponenttien vuorovaikutus {#component-interaction}

`Element`-komponentti toimii säilönä muille komponenteille. Se tarjoaa tavan organisoida ja noutaa tietoja lapsikomponenteilta ja tarjoaa selkeän joukon toimintoja näiden lapsikomponenttien lisäämiseksi tai poistamiseksi tarpeen mukaan.

### Lapsikomponenttien lisääminen {#adding-child-components}

`Element`-komponentti tukee lapsikomponenttien koostamista. Kehittäjät voivat organisoida ja hallita monimutkaisia käyttöliittymästruktuureja lisäämällä komponentteja lapsina `Element`:lle. Kolme menetelmää on käytettävissä sisällön asettamiseksi `Element`-komponenttiin:

1. **`add(Component... components)`**: Tämä menetelmä sallii yhden tai useamman komponentin lisäämisen valinnaiseen `String`-parametriin, joka määrittelee tietyn paikan käytettäessä Web-komponenttia. Paikan jättämistä ei tarvita, jolloin komponentti lisätään HTML-tägien väliin.

2. **`setHtml(String html)`**: Tämä menetelmä ottaa vastaan `String`-arvon, joka injektoidaan HTML:na komponenttiin. `Element`-komponentin mukaan tämä voi renderöityä eri tavalla.

3. **`setText(String text)`**: Tämä menetelmä käyttäytyy samalla tavalla kuin `setHtml()`-menetelmä, mutta injektoi kirjaimellista tekstiä `Element`:iin.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
`setHtml()` tai `setText()` -menetelmän kutsuminen korvataa tällä hetkellä sisältö, joka on komponentin avaavien ja sulkevien tagien välissä.
:::

### Komponenttien poistaminen {#removing-components}

Lisäksi komponenttien lisäämisen ohella `Element`:ssä on toteutettu seuraavat menetelmät erilaisten lapsikomponenttien poistamiseksi:

1. **`remove(Component... components)`**: Tämä menetelmä ottaa vastaan yhden tai useamman komponentin ja poistaa ne lapsikomponentteina.

2. **`removeAll()`**: Tämä menetelmä poistaa kaikki lapsikomponentit `Element`:stä.

### Komponenttien saavuttaminen {#accessing-components}

Saadaksesi käyttöön eri lapsikomponentit, jotka ovat läsnä `Element`:ssä, tai tietoa näistä komponenteista, käytettävissä on seuraavat menetelmät:

1. **`getComponents()`**: Tämä menetelmä palauttaa Java `List`:an kaikista `Element`:n lapsista.

2. **`getComponents(String id)`**: Tämä menetelmä on samanlainen kuin yllä oleva menetelmä, mutta ottaa palvelinpuolen ID:n tietyltä komponentilta ja palauttaa sen löydettäessä.

3. **`getComponentCount()`**: Palauttaa lapsikomponenttien lukumäärän, jotka ovat läsnä `Element`:ssä.

## JavaScript-toimintojen kutsuminen {#calling-javascript-functions}

`Element`-komponentti tarjoaa kaksi API-menetelmää, jotka sallivat JavaScript-toimintojen kutsumisen HTML-elementeille.

1. **`callJsFunction(String functionName, Object... arguments)`**: Tämä menetelmä ottaa vastaan toiminnon nimen merkkijonona ja valinnaisesti yhden tai useamman objektin, jotka toiminto ottaa parametreiksi. Tämä menetelmä suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie estetään** kunnes JS-metodi palautuu, ja se johtaa pyynnön ja vastauksen kiertoon. Toiminnon tulokset palautetaan `Object`-arvona, joka voidaan tyyppimuuttaa ja käyttää Javassa.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Kuten edellisessä menetelmässä, myös toiminnon nimi ja valinnaiset argumentit voidaan siirtää. Tämä menetelmä suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka mahdollistaa lisävuorovaikutuksen toiminnon ja sen kuorman kanssa.

### Parametrien välittäminen {#passing-parameters}

Nämä menetelmät, joilla käytetään JS-toimintojen suorittamiseksi, käsittelevät argumentteja JSON-taulukon muodossa. On kaksi huomattavaa argumenttityyppiä, jotka käsitellään seuraavasti:
- `this`: `this`-avainsanan käyttö antaa metodille viitteen kutsuvan komponentin asiakaspäädyn versioon.
- `Component`: Tarkat Java-komponenttikeskukset, jotka siirretään johonkin JsFunction-menetelmistä, korvataan asiakkaan puolen version kanssa komponentista.

:::info
Sekä synkroniset että asynkroniset toimintojen kutsuminen odottavat kunnes `Element` on lisätty DOM:iin ennen toiminnon suorittamista, mutta `callJsFunction()` ei odota никакие `component`-argumentit liitetään, mikä voi johtaa epäonnistumiseen. Toisaalta `callJsFunctionAsync()` -kutsuminen ei ehkä koskaan valmistu, jos komponenttiargumenttia ei koskaan liitetä.
:::

Alla olevassa demonissa HTML `Button`-elementtiin lisätään tapahtuma. Tämä tapahtuma käynnistetään ohjelmallisesti kutsumalla `callJsFunctionAsync()`-menetelmää. Tämän seurauksena saatu <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> käytetään toisen viestiruudun luomiseen, kun asynkroninen toiminto on valmis.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## JavaScriptin suorittaminen {#executing-javascript}

Lisäksi JavaScriptin suorittaminen sovellustasolta on mahdollista myös suorittaa JavaScriptiä `Element`-tasolla. Tämä suorittaminen `Element`-tasolla mahdollistaa HTML-elementin kontekstin sisällyttämisen suoritukseen. Tämä on tehokas työkalu, joka toimii kehittäjän väylänä asiakaspuolen ympäristöihin vuorovaikutuksessa.

Samaan tapaan, kuten toiminnon suorittaminen, JavaScriptin suorittaminen voi tapahtua joko synkronisesti tai asynkronisesti seuraavilla menetelmillä:

1. **`executeJs(String script)`**: Tämä menetelmä ottaa vastaan `String`, joka suoritetaan JavaScript-koodina asiakaspuolella. Tämä skripti suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie estetään** kunnes JS-suoritus palautuu ja se johtaa pyynnön ja vastauksen kiertoon. Tarkastuksen tuloksena saadaan `Object`, joka voidaan tyyppimuuttaa ja käyttää Javassa.

2. **`executeJsAsync(String script)`**: Kuten edellisessä menetelmässä, siirretty `String`-parametri suoritetaan JavaScript-koodina asiakkaalla. Tämä menetelmä suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka mahdollistaa lisävuorovaikutuksen toiminnon ja sen kuorman kanssa.

:::tip
Nämä menetelmät ovat pääsy `component`-avainsanaan, mikä antaa JavaScript-koodille pääsyn asiakkaan puolen komponentin instanssiin, joka suorittaa JavaScriptiä.
:::
