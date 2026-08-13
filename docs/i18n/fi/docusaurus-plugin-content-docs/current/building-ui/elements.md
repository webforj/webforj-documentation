---
sidebar_position: 5
title: Elements
sidebar_class_name: updated-content
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: 988b2a49584036eee3b0475215a707ae
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-kehittäjillä on mahdollisuus valita paitsi tarjoamastaan laajasta komponenttikirjastosta, myös integroimalla komponentteja muualta. Tämän helpottamiseksi `Element`-komponenttia voidaan käyttää yksinkertaistamaan kaiken integrointia yksinkertaisista HTML-elementeistä monimutkaisempiin mukautettuihin verkkokomponentteihin.

:::important
`Element`-komponenttia ei voi laajentaa, eikä se ole kaikkien webforJ-komponenttien peruskomponentti. Lisätietoja webforJ:n komponenttierarkkitehtuurista saat lukemalla [tämän artikkelin](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementMeterView.java',
  'src/main/resources/static/css/element/elementMeter.css',
]}
height='240px'
/>

## Tapahtumien lisääminen {#adding-events}

Jotta voit hyödyntää elementin mukana tulevia tapahtumia, voit käyttää `Element`-komponentin `addEventListener`-menetelmiä. Tapahtuman lisääminen vaatii vähintään tapahtuman tyypin/nimen, jota komponentti odottaa, ja kuuntelijan, joka lisätään tapahtumaan.

Lisäksi on lisävalintoja, joilla voidaan mukauttaa tapahtumia edelleen käyttämällä tapahtumavaihtoehtojen asetuksia.

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementTagInputView.java',
  'src/main/resources/static/css/element/elementTagInput.css',
]}
height='240px'
/>

## Komponenttien vuorovaikutus {#component-interaction}

`Element`-komponentti toimii muiden komponenttien säiliönä. Se tarjoaa tavan organisoida ja hakea tietoja lapsikomponenteista ja tarjoaa selkeän joukon toimintoja lisätä tai poistaa näitä lapsikomponentteja tarpeen mukaan.

### Lapsikomponenttien lisääminen {#adding-child-components}

`Element`-komponentti tukee lapsikomponenttien yhdistämistä. Kehittäjät voivat järjestää ja hallita monimutkaisia UI-rakenteita lisäämällä komponentteja lapsiksi `Element`-komponenttiin. Kolme menetelmää on olemassa sisällön asettamiseksi `Element`-komponenttiin:

1. **`add(Component... components)`**: Tämä menetelmä mahdollistaa yhden tai useamman komponentin lisäämisen valinnaiseen `String`-arvoon, joka määrittää tarkat sijainnit käytettäessä verkkokomponenttia. Jos sijaintia ei anneta, komponentti lisätään HTML-tunnisteiden väliin.

2. **`setHtml(String html)`**: Tämä menetelmä ottaa sisään tulevan `String`-arvon ja injektoi sen HTML:nä komponenttiin. `Element`:n mukaan tämä voidaan renderöidä eri tavoin.

3. **`setText(String text)`**: Tämä menetelmä toimii samalla tavalla kuin `setHtml()`-menetelmä, mutta injektoi kirjaimellisen tekstin `Element`:iin.

<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementFigureView.java',
  'src/main/resources/static/css/element/elementFigure.css',
]}
height='240px'
/>

:::warning Sisällön korvaaminen
`setHtml()`- tai `setText()`-kutsumisen yhteydessä korvataan elementin avaus- ja lopputunnisteiden välillä oleva sisältö.
:::

### Komponenttien poistaminen {#removing-components}

Lisäksi komponenttien lisäämiseen `Element`:iin on toteutettu seuraavat menetelmät erilaisten lapsikomponenttien poistamiseksi:

1. **`remove(Component... components)`**: Tämä menetelmä ottaa yhden tai useamman komponentin ja poistaa ne lapsikomponenteina.

2. **`removeAll()`**: Tämä menetelmä poistaa kaikki lapsikomponentit `Element`:istä.

### Komponenttien hakeminen {#accessing-components}

Suuntautuaksesi `Element`:issä oleviin lapsikomponentteihin tai näitä komponentteja koskeviin tietoihin, seuraavat menetelmät ovat käytettävissä:

1. **`getComponents()`**: Tämä menetelmä palauttaa Java `List`:n kaikista `Element`:in lapsista.

2. **`getComponents(String id)`**: Tämä menetelmä on samanlainen kuin yllä oleva, mutta se ottaa palvelinpuolen tietyn komponentin ID:n ja palauttaa sen, jos se löytyy.

3. **`getComponentCount()`**: Palauttaa `Element`:issä olevien lapsikomponenttien määrän.

## JavaScript-funktioiden kutsuminen {#calling-javascript-functions}

`Element`-komponentti tarjoaa kaksi API-menetelmää, joiden avulla JavaScript-funktioita voidaan kutsua HTML-elementeissä.

1. **`callJsFunction(String functionName, Object... arguments)`**: Tämä menetelmä ottaa funktion nimen merkkijonona ja valinnaisesti yhden tai useamman objektin funktion parametreina. Tämä menetelmä suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty** kunnes JS-menetelmä palauttaa, ja tämä johtaa vuorovaikutukseen. Funktion tulokset palautetaan `Object`-tyyppisinä, jotka voidaan muuntaa ja käyttää Javassa.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Kuten edellisessä menetelmässä, voidaan antaa funktion nimi ja valinnaiset argumentit. Tämä menetelmä suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:n, joka mahdollistaa lisävuorovaikutuksen funktion ja sen datan kanssa.

### Parametrien välittäminen {#passing-parameters}

Argumentit, jotka jaetaan näille menetelmille, joita käytetään JS-funktioiden suorittamisessa, sarjoitetaan JSON-taulukkona. Kaksi huomattavaa argumenttityyppiä käsitellään seuraavasti:
- `this`: Käyttämällä `this`-avainsanaa annetulle metodille annetaan viittaus asiakaspuolen version kutsuvasta komponentista.
- `Component`: Kaikki Java-komponentti-instanssit, jotka annetaan johonkin JsFunction-menetelmistä, korvataan asiakaspuolen version komponentista.

:::warning Odottaminen komponenttien argumenttien osalta
Sekä synkroniset että asynkroniset funktiokutsut odottavat, kunnes `Element` on lisätty DOM:iin ennen funktion suorittamista, mutta `callJsFunction()` ei odota, että mitään `component`-argumentteja liitetään, mikä voi johtaa epäonnistumiseen. Toisaalta, `callJsFunctionAsync()`-kutsut voivat jäädä ikuisesti kesken, jos komponenttiargumentteja ei liitetä koskaan.
:::

Alla olevassa demossa **Focus search** -valinta kutsuu alkuperäistä `focus()`-menetelmää hakukentässä `callJsFunctionAsync()`-kutsulla. Tämän seurauksena käytetään <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:ia varmistaakseen kutsun toastilla, kun asynkroninen toiminto on valmis.

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementSearchView.java',
  'src/main/resources/static/css/element/elementSearch.css',
]}
height='240px'
/>

## JavaScriptin suorittaminen {#executing-javascript}

Nimettömien funktioiden kutsumisen lisäksi `Element` voi suorittaa raakkaskriptejä, jotka on rajattu kyseiseen elementtiin `executeJs`, `executeJsAsync` ja `executeJsVoidAsync` -menetelmillä. Katso [Suorita JavaScript](./execute-javascript.md) näitä menetelmiä varten, niiden synkronista ja asynkronista käyttäytymistä varten sekä kuinka palautetut arvot muuntuvat Java-tyypeiksi.
