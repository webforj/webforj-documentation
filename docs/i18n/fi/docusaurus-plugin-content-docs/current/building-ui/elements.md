---
sidebar_position: 3
title: Elements
slug: element
_i18n_hash: 749e84016c244ec7349221d00dc0de9a
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-kehittäjät voivat valita paitsi runsaasta komponenttien kirjastosta, myös integroida komponentteja muualta. Tämän helpottamiseksi `Element`-komponenttia voidaan käyttää yksinkertaistamaan mitä tahansa integraatiota yksinkertaisista HTML-elementeistä monimutkaisiin mukautettuihin verkkokomponentteihin.

:::important
`Element`-komponenttia ei voi laajentaa, eikä se ole peruskomponentti kaikille webforJ:n komponenteille. Lue lisää webforJ:n komponenttipuusta lukemalla [tämä artikkeli](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Tapahtumien lisääminen {#adding-events}

Jotta voit hyödyntää elementtiisi liittyviä tapahtumia, voit käyttää `Element`-komponentin `addEventListener`-menetelmiä. Tapahtuman lisääminen vaatii vähintään tapahtuman tyypin/nimen, jota komponentti odottaa, ja kuuntelijan, joka lisätään tapahtumalle.

On myös lisävaihtoehtoja tapahtumien ulkoasun mukauttamiseksi käyttämällä Event Options -asetuksia.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Komponenttien vuorovaikutus {#component-interaction}

`Element`-komponentti toimii säilönä muille komponenteille. Se tarjoaa tavan organisoida ja hakea tietoja lapsikomponenteista ja tarjoaa selkeän toimintavalikoiman näiden lapsikomponenttien lisäämiseksi tai poistamiseksi tarpeen mukaan.

### Lapsikomponenttien lisääminen {#adding-child-components}

`Element`-komponentti tukee lapsikomponenttien yhdistämistä. Kehittäjät voivat organisoida ja hallita monimutkaisia käyttöliittymärakenteita lisäämällä komponentteja lapsiksi `Elementtiin`. Kolme menetelmää on olemassa sisällön asettamiseksi `Element`-komponenttiin:

1. **`add(Component... components)`**: Tämä menetelmä sallii yhden tai useamman komponentin lisäämisen valinnaiseen `String`:iin, joka osoittaa tietyn slotin käytettäväksi Web-komponentin kanssa. Slotin jättämättä jättäminen lisää komponentin HTML-tunnisteiden väliin.

2. **`setHtml(String html)`**: Tämä menetelmä ottaa parametrina annetun `Stringin` ja injektoi sen HTML:nä komponenttiin. Riippuen `Element`-komponentista, tämä voi renderöidä eri tavoin.

3. **`setText(String text)`**: Tämä menetelmä toimii samalla tavalla kuin `setHtml()`-menetelmä, mutta injektoi kirjaimellisen tekstin `Element`-komponenttiin.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
`setHtml()`- tai `setText()`-kutsuminen korvataa sisällön, joka tällä hetkellä sijaitsee elementin avaus- ja sulkutunnisteiden välillä.
:::

### Komponenttien poistaminen {#removing-components}

Lisäksi komponenttien lisäämiseen `Elementiin` on käytössä seuraavat menetelmät erilaisia lapsikomponentteja varten:

1. **`remove(Component... components)`**: Tämä menetelmä ottaa yhden tai useamman komponentin ja poistaa ne lapsikomponenteina.

2. **`removeAll()`**: Tämä menetelmä poistaa kaikki lapsikomponentit `Elementistä`.

### Komponenttien hakeminen {#accessing-components}

Voit käyttää erilaisia lapsikomponentteja, jotka ovat läsnä `Elementissä`, tai tietoja näistä komponenteista, seuraavia menetelmiä:

1. **`getComponents()`**: Tämä menetelmä palauttaa Java `List`:in kaikista `Elementin` lapsista.

2. **`getComponents(String id)`**: Tämä menetelmä on samanlainen kuin yllä oleva menetelmä, mutta ottaa palvelinpuolen ID:n tietyltä komponentilta ja palauttaa sen, kun se löydetään.

3. **`getComponentCount()`**: Palauttaa lapsikomponenttien lukumäärän, jotka ovat läsnä `Elementissä`. 

## JavaScript-funktioiden kutsuminen {#calling-javascript-functions}

`Element`-komponentti tarjoaa kaksi API-menetelmää, jotka mahdollistavat JavaScript-funktioiden kutsumisen HTML-elementeille.

1. **`callJsFunction(String functionName, Object... arguments)`**: Tämä menetelmä ottaa funktionsimen merkkijonona ja valinnaisesti yhden tai useamman objektin parametrina funktiolle. Tämä menetelmä suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty** kunnes JS-menetelmä palautuu, ja tuloksena on pyörämatka. Funktioiden tulokset palautetaan `Objectina`, joka voidaan muuntaa ja käyttää Javassa.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Kuten edellisessä menetelmässä, funktionsimen ja valinnaisten argumenttien ansiosta voidaan myös siirtää. Tämä menetelmä suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:n, joka mahdollistaa lisävuorovaikutuksen funktion ja sen tietojoukon kanssa.

### Parametrien siirtäminen {#passing-parameters}

Näihin menetelmiin siirretyt argumentit, joita käytetään JS-funktioiden suorittamisessa, serialisoidaan JSON-taulukoksi. Kaksi huomionarvoista argumenttityyppiä käsitellään seuraavasti:
- `this`: `this`-avainsanan käyttäminen antaa menetelmälle viittauksen asiakaspuolen instanssiin, joka kutsuu komponenttia.
- `Component`: Kaikki Java-komponentti-instanssit, jotka siirretään johonkin JsFunction-menetelmistä, korvataan asiakaspuolen komponentilla.

:::info
Sekä synkroniset että asynkroniset funktiokutsut odottavat, että `Element` on lisätty DOM:iin ennen funktion suorittamista, mutta `callJsFunction()` ei odota enempää komponenttiargumentteja liitettäväksi, mikä voi johtaa epäonnistumiseen. Toisaalta `callJsFunctionAsync()`-kutsun suorittaminen ei ehkä koskaan pääty, jos komponenttiargumenttia ei liitetä.
:::

Alla olevassa demossa HTML `Button`-elementille lisätään tapahtuma. Tätä tapahtumaa ammutaan sitten ohjelmallisesti kutsumalla `callJsFunctionAsync()`-menetelmää. Tuloksena oleva <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> käytetään sitten luomaan toinen viestiruudun, kun asynkroninen toiminto on valmis.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## JavaScriptin suorittaminen {#executing-javascript}

JavaScriptin suorittamisen lisäksi sovellustasolla on myös mahdollista suorittaa JavaScriptiä `Element`-tasolla. Tämä suoritus `Element`-tasolla mahdollistaa HTML-elementin kontekstin sisällyttämisen suoritukseen. Tämä on voimakas työkalu, joka toimii kehittäjän väylänä vuorovaikutteisiin mahdollisuuksiin asiakaspuolen ympäristöissä.

Samoin kuin funktioiden suorittaminen, JavaScriptin suorittaminen voidaan tehdä synkronisesti tai asynkronisesti seuraavilla menetelmillä:

1. **`executeJs(String script)`**: Tämä menetelmä ottaa `Stringin`, joka suoritetaan JavaScript-koodina asiakkaassa. Tämä skripti suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty** kunnes JS-suoritus palautuu ja tuloksena on pyörämatka. Funktion tulokset palautetaan `Objectina`, joka voidaan muuntaa ja käyttää Javassa.

2. **`executeJsAsync(String script)`**: Kuten edellisessä menetelmässä, annettu `String`-parametri suoritetaan JavaScript-koodina asiakaspuolella. Tämä menetelmä suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:n, joka mahdollistaa lisävuorovaikutuksen funktion ja sen tietojoukon kanssa.

:::tip
Nämä menetelmät pääsevät `component`-avainsanaan, mikä antaa JavaScript-koodille pääsyn asiakaspuolen komponenttiin, joka suorittaa JavaScriptiä.
:::
