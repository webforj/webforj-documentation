---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: dff3b1c4df821aad3c4c7a4c66cfff65
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-kehittäjillä on mahdollisuus valita ei vain tarjotusta laajasta komponenttikirjastosta, vaan myös integroida komponentteja muualta. Tämä helpottamiseksi `Element`-komponenttia voidaan käyttää yksinkertaistamaan kaiken integroimista yksinkertaisista HTML-elementeistä monimutkaisempiin mukautettuihin web-komponentteihin.

:::important
`Element`-komponenttia ei voi laajentaa, eikä se ole kaikkien webforJ:n komponenttien peruskomponentti. Lue lisää webforJ:n komponenttihierarkiasta [tästä artikkelista](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementMeterView.java'
]}
height='250px'
/>

## Tapahtumien lisääminen {#adding-events}

Jotta voit hyödyntää elementtiisi liittyviä tapahtumia, voit käyttää `Element`-komponentin `addEventListener`-menetelmiä. Tapahtuman lisääminen vaatii vähintään tapahtuman tyypin/nimen, jota komponentti odottaa, ja kuuntelijan, joka lisätään tapahtumaan.

Lisäksi on muita vaihtoehtoja, joilla voit räätälöidä tapahtumia lisäämällä Event Options -konfiguraatiot.

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementTagInputView.java',
  'src/main/frontend/css/element/elementtaginput.css',
]}
height='250px'
/>

## Komponenttien vuorovaikutus {#component-interaction}

`Element`-komponentti toimii säilönä muille komponenteille. Se tarjoaa tavan organisoida ja kerätä tietoa lapsikomponenteista sekä tarjoaa selkeän joukon toimintoja näiden lapsikomponenttien lisäämiseksi tai poistamiseksi tarpeen mukaan.

### Lapsikomponenttien lisääminen {#adding-child-components}

`Element`-komponentti tukee lapsikomponenttien koostumista. Kehittäjät voivat organisoida ja hallita monimutkaisia käyttöliittymärakenteita lisäämällä komponentteja lapsiksi `Element`-komponenttiin. Kolme menetelmää on olemassa sisällön asettamiseksi `Element`-komponenttiin:

1. **`add(Component... components)`**: Tämä menetelmä sallii yhden tai useamman komponentin lisäämisen valinnaiseen `String`-muuttujaan, joka määrittelee tietyn slotin käytettäessä Web-komponenttia. Slottia jättämällä komponentti lisätään HTML-tunnisteiden väliin.

2. **`setHtml(String html)`**: Tämä menetelmä ottaa `String`-arvon, joka annetaan menetelmälle, ja injektoi sen HTML:nä komponenttiin. Riippuen `Element`:istä, tämä voidaan renderöidä eri tavoin.

3. **`setText(String text)`**: Tämä menetelmä käyttäytyy samalla tavalla kuin `setHtml()`-menetelmä, mutta injektoi litteän tekstin `Element`-komponenttiin.

<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementFigureView.java',
  'src/main/frontend/css/element/elementfigure.css',
]}
height='250px'
/>

:::warning Sisällön korvaaminen
`setHtml()`- tai `setText()`-kutsujen tekeminen korvataan sisällön, joka tällä hetkellä on elementin avaus- ja sulkutunnisteiden välissä.
:::

### Komponenttien poistaminen {#removing-components}

Lisäksi komponenttien lisäämisen yhteydessä `Element`-komponentille on toteutettu seuraavat menetelmät erilaisten lapsikomponenttien poistamiseksi:

1. **`remove(Component... components)`**: Tämä menetelmä ottaa yhden tai useamman komponentin ja poistaa ne lapsikomponenteina.

2. **`removeAll()`**: Tämä menetelmä poistaa kaikki lapsikomponentit `Element`-komponentista.

### Komponentteihin pääsy {#accessing-components}

Päästäksesi eri lapsikomponentteihin, jotka ovat läsnä `Element`-komponentissa, tai saadaksesi tietoa näistä komponenteista, seuraavat menetelmät ovat saatavilla:

1. **`getComponents()`**: Tämä menetelmä palauttaa Java `List`-luettelon kaikista `Element`-komponentin lapsista.

2. **`getComponents(String id)`**: Tämä menetelmä on samanlainen kuin edellinen, mutta se ottaa palvelinpuolen ID:n tietystä komponentista ja palauttaa sen, jos se löytyy.

3. **`getComponentCount()`**: Palauttaa lapsikomponenttien määrän, jotka ovat läsnä `Element`-komponentissa.


## JavaScript-funktioiden kutsuminen {#calling-javascript-functions}

`Element`-komponentti tarjoaa kaksi API-menetelmää, jotka mahdollistavat JavaScript-funktioiden kutsumisen HTML-elementeissä.

1. **`callJsFunction(String functionName, Object... arguments)`**: Tämä menetelmä ottaa funktion nimen merkkijonona ja mahdollisesti yhden tai useamman objektin funktion parametreina. Tämä menetelmä suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty** kunnes JS-metodi palauttaa tulokset, ja se aiheuttaa pyörämatkan. Funktion tulokset palautetaan `Object`-muodossa, joka voidaan palauttaa ja käyttää Javassa.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Kuten edellisessä menetelmässä, funktion nimen ja valinnaisia argumentteja voidaan välittää. Tämä menetelmä suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka mahdollistaa lisävuorovaikutuksen funktion ja sen kuormituksen kanssa.

### Parametrien välittäminen {#passing-parameters}

Nämä menetelmiin välitetyt argumentit, joita käytetään JS-funktioiden suorittamisessa, sarjoitetaan JSON-taulukoksi. Kaksi huomattavaa argumenttityyppiä käsitellään seuraavasti:
- `this`: Käyttämällä `this`-avainsanaa saat toiminnolle viittauksen asiakaspään versioon kutsuvasta komponentista.
- `Component`: Kaikki Java-komponentti-instanssit, jotka on annettu johonkin JsFunction-menetelmistä, korvataan asiakaspään version mukaisella komponentilla.

:::warning Odottaminen komponenttiargumenttien puolesta
Sekä synkroniset että asynkroniset toimintokutsut odottavat, kunnes `Element` on lisätty DOM:iin ennen funktion suorittamista, mutta `callJsFunction()` ei odota, että jokin `component`-argumentti liittyy, mikä voi johtaa epäonnistumiseen. Toisaalta, `callJsFunctionAsync()`-kutsuminen voi joskus jäädä keskeytymään, jos komponenttiargumenttia ei koskaan liitetä.
:::

Alla olevassa demonissa, valitseminen **Kohdistushaku** kutsuu natiivi `focus()`-menetelmän hakusyötteelle `callJsFunctionAsync()`-menetelmällä. Tuloksena oleva <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> käytetään vahvistamaan kutsu toast-viestin avulla, kun asynkroninen toiminto on valmis.

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementSearchView.java',
  'src/main/frontend/css/element/elementsearch.css',
]}
height='250px'
/>

## JavaScriptin suorittaminen {#executing-javascript}

Nimettyjen funktioiden kutsumisen lisäksi, `Element` voi suorittaa raakaskriptejä, jotka on rajattu tälle elementille `executeJs`, `executeJsAsync` ja `executeJsVoidAsync` -menetelmillä. Katso [Suorita JavaScript](./execute-javascript.md) näille menetelmille, niiden synkroniselle ja asynkroniselle käyttäytymiselle sekä miten palautetut arvot muuttuvat Java-tyypeiksi.
