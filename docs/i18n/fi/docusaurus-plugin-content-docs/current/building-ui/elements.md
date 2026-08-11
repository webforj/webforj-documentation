---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: 2af99ca4f1e5c8c2f7c31b3d7f647b41
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-kehittäjillä on mahdollisuus valita ei vain tarjoamastaan runsasta komponenttikirjastosta, vaan myös integroida komponentteja muualta. Tämän helpottamiseksi `Element`-komponenttia voidaan käyttää yksinkertaistamaan mitä tahansa integraatiota yksinkertaisista HTML-elementeistä monimutkaisempien mukautettujen web-komponenttien tueksi.

:::important
`Element`-komponenttia ei voi laajentaa, ja se ei ole kaikkien webforJ:n komponenttien peruskomponentti. Jotta voit lukea lisää webforJ:n komponenttirakenteesta, lue [tämä artikkeli](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
/>

## Tapahtumien lisääminen {#adding-events}

Jotta voit hyödyntää elementin mukana tulevia tapahtumia, voit käyttää `Element`-komponentin `addEventListener`-menetelmiä. Tapahtuman lisääminen vaatii vähintään tapahtuman tyypin/nimen, jota komponentti odottaa, sekä kuuntelijan, joka lisätään tapahtumaan.

Lisäksi on tarjolla muita vaihtoehtoja tapahtumien mukauttamiseksi käyttämällä Tapahtuma-vaihtoehtojen asetuksia.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/frontend/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Komponenttien vuorovaikutus {#component-interaction}

`Element`-komponentti toimii muiden komponenttien säiliönä. Se tarjoaa tavan organisoida ja kerätä tietoa lapsikomponenteista ja tarjoaa selkeän joukon toimintoja näiden lapsikomponenttien lisäämiseen tai poistamiseen tarpeen mukaan.

### Lapsikomponenttien lisääminen {#adding-child-components}

`Element`-komponentti tukee lapsikomponenttien koostumusta. Kehittäjät voivat organisoida ja hallita monimutkaisia käyttöliittymärakenteita lisäämällä komponentteja lapsina `Element`-komponentille. Kolme menetelmää on olemassa sisällön määrittämiseksi `Element`-komponenttiin:

1. **`add(Component... components)`**: Tämä menetelmä sallii yhden tai useamman komponentin lisäämisen valinnaiseen `String`, joka määrittää tietyn paikan käytettäessä Web-komponenttia. Paikan poistaminen lisää komponentin HTML-tunnusten väliin.

2. **`setText(String text)`**: Tämä menetelmä käyttäytyy samoin kuin `setHtml()`-menetelmä, mutta injektoi kirjaimellisen tekstin `Element`-komponenttiin.

  ```java
  // Näytetään kirjaimelliset merkit "<b>Status: ready</b>"
  element.setText("<b>Status: ready</b>");
  ```

  :::note Käyttämällä `<html>`-tagia
  Aiemmat versiot webforJ:stä käsittelivät `<html>`-tunnisteeseen käärittyä arvoa ja siirsivät sen `setText()`-menetelmään HTML:nä. Tämä käyttäytyminen on vanhentunut ja se poistetaan webforJ:stä versiossa 27.00.

  Ensimmäisellä kerralla, kun `<html>`-tunnisteeseen kääritty arvo saavuttaa `setText()`, varoitus tallennetaan, joka nimeää komponentin ja kutsupaikan, jotta kutsu voidaan siirtää `setHtml()`-menetelmään.

  Ota webforJ 27.00: n oletus käyttöön etukäteen asettamalla `webforj.legacyHtmlInText` arvoon `false`. Spring-sovelluksessa sama arvo asetetaan `webforj.legacy-html-in-text`.

  ```java
  // webforj.legacyHtmlInText = true (oletus)
  element.setText("<html><b>Status: ready</b></html>"); // renderöidään lihavoituna

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Status: ready</b></html>"); // näyttää merkit <b>Status: ready</b>
  ```
  :::

3. **`setHtml(String html)`**: Tämä menetelmä ottaa merkkijonon, joka annetaan metodille, ja injektoi sen HTML:nä komponenttiin. `Element`-komponentista riippuen tämä voidaan renderöidä eri tavoin.

  :::danger Ristiinsivustohyökkäykset (XSS)
  Varotoimena [ristiinsivustohyökkäyksiltä (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss) käytä vain `setHtml()`-menetelmää sisällön kanssa, jota hallitset suoraan.
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Kutsuminen `setHtml()`- tai `setText()`-menetelmällä korvataan sisällön, joka tällä hetkellä on elementin avaus- ja sulkutunnusten välillä.
:::

### Komponenttien poistaminen {#removing-components}

Lisäksi komponenttien lisäämisen ohella on toteutettu seuraavat menetelmät erilaisten lapsikomponenttien poistamiseksi:

1. **`remove(Component... components)`**: Tämä menetelmä ottaa yhden tai useamman komponentin ja poistaa ne lapsikomponenteista.

2. **`removeAll()`**: Tämä menetelmä poistaa kaikki lapsikomponentit `Element`-komponentista.

### Komponenttien käyttö {#accessing-components}

Käyttääksesi erilaisia lapsikomponentteja, jotka ovat läsnä `Element`-komponentissa, tai tietoa näistä komponenteista, käytettävissä on seuraavat menetelmät:

1. **`getComponents()`**: Tämä menetelmä palauttaa Java `List`-listan kaikista `Element`-komponentin lapsista.

2. **`getComponents(String id)`**: Tämä menetelmä on samanlainen kuin edellinen, mutta ottaa palvelinpuolen ID:n tietystä komponentista ja palauttaa sen löydettynä.

3. **`getComponentCount()`**: Palauttaa pysyvän lapsikomponenttien määrän, jotka ovat läsnä `Element`-komponentissa.

## JavaScript-funktioiden kutsuminen {#calling-javascript-functions}

`Element`-komponentti tarjoaa kaksi API-menetelmää, jotka mahdollistavat JavaScript-funktioiden kutsumisen HTML-elementeissä.

1. **`callJsFunction(String functionName, Object... arguments)`**: Tämä menetelmä ottaa funktioiden nimen merkkijonona ja valinnaisesti yhden tai useamman objektin parametrina funktiolle. Tämä menetelmä suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty**, kunnes JS-menetelmä palauttaa, ja se tuloksena syntyy round trip. Funktion tulokset palautetaan Object-tyyppinä, jota voidaan muuttaa ja käyttää Javassa.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Samoin kuin edellisessä menetelmässä, funktiotunnus ja valinnaiset argumentit voidaan välittää funktiolle. Tämä menetelmä suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, mikä mahdollistaa lisää vuorovaikutusta funktion ja sen kuorman kanssa.

### Parametrien välittäminen {#passing-parameters}

Argumentit, jotka siirretään näihin menetelmiin, joita käytetään JS-funktioiden suorittamisessa, sarjoitetaan JSON-taulukkoina. Kaksi huomattavaa argumenttityyppiä käsitellään seuraavasti:
- `this`: Käyttäessäsi `this`-avainsanaa, se antaa metodille viittauksen kutsuvan komponentin asiakaspuolen versioon.
- `Component`: Kaikki Java-komponentti-instanssit, jotka siirretään johonkin JsFunction-menetelmään, korvataan komponentin asiakaspuolen versiolla.

:::info
Sekä synkroniset että asynkroniset funktiokutsut odottavat, kunnes `Element` on lisätty DOM:iin ennen kuin funktio suoritetaan, mutta `callJsFunction()` ei odota komponenttiargumenttien liittämistä, mikä voi johtaa epäonnistumiseen. Toisaalta `callJsFunctionAsync()`-kutsun suorittaminen saattaa jäädä loppuun saattamatta, jos komponenttiargumenttia ei koskaan liitetä.
:::

Alla olevassa demonissa tapahtuma lisätään HTML `Button`:iin. Tämä tapahtuma laukaistaan ohjelmallisesti kutsumalla `callJsFunctionAsync()`-menetelmää. Tämän seurauksena <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> käytetään luomaan toinen viestiruutu, kun asynkroninen toiminto on suoritettu.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='240px'
/>

## JavaScriptin suorittaminen {#executing-javascript}

Lisäksi on mahdollista suorittaa JavaScriptiä sovellustasolla, on mahdollista suorittaa JavaScriptiä myös `Element`-tasolla. Tämän suorittaminen `Element`-tasolla mahdollistaa HTML-elementin yhteyden sisällyttämisen suoritukseen. Tämä on voimakas työkalu, joka toimii kehittäjän väylänä interaktiivisiin mahdollisuuksiin asiakaspuolen ympäristöissä.

Samoin kuin funktioiden suorittamisessa JavaScriptin suorittaminen voidaan tehdä synkronisesti tai asynkronisesti seuraavilla menetelmillä:

1. **`executeJs(String script)`**: Tämä menetelmä ottaa merkkijonon, joka suoritetaan JavaScript-koodina asiakkaalla. Tämä skripti suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty** siihen asti, kunnes JS-suoritus palautuu, ja tuloksena syntyy round trip. Funktioiden tulokset palautetaan Object-tyyppinä, jota voidaan muuttaa ja käyttää Javassa.

2. **`executeJsAsync(String script)`**: Samoin kuin edellisessä menetelmässä, annettu merkkijono-parametri suoritetaan JavaScript-koodina asiakkaalla. Tämä menetelmä suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, mikä mahdollistaa lisää vuorovaikutusta funktion ja sen kuorman kanssa.

:::tip
Nämä menetelmät pääsevät käsiksi `component`-avainsanaan, mikä antaa JavaScript-koodille pääsyn asiakaspuolen instanssiin suorittaessaan JavaScriptiä.
:::
