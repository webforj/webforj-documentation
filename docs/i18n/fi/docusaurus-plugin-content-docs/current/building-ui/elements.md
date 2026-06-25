---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: d941e314cdd63d19471e80936ef5d6bc
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-kehittäjillä on mahdollisuus valita paitsi laajasta komponenttikirjastosta, myös integroida komponentteja muualta. Tämän helpottamiseksi `Element`-komponenttia voidaan käyttää yksinkertaistamaan kaikkea yksinkertaisista HTML-elementeistä monimutkaisempiin mukautettuihin verkkokomponentteihin.

:::important
`Element`-komponenttia ei voi laajentaa, eikä se ole kaikkien webforJ:n komponenttien peruskomponentti. Lue lisää webforJ:n komponentti-hierarkiasta lukemalla [tämä artikkeli](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## Tapahtumien lisääminen {#adding-events}

Jotta voit hyödyntää elementtiin liittyviä tapahtumia, voit käyttää `Element`-komponentin `addEventListener`-menetelmiä. Tapahtuman lisääminen vaatii vähintään tapahtuman tyypin/nimen, jota komponentti odottaa, sekä kuuntelijan, joka lisätään tapahtumaan.

On myös lisäasetuksia, joilla voit muokata tapahtumia käyttämällä tapahtuma-asetuskonfiguraatioita.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Komponenttien vuorovaikutus {#component-interaction}

`Element`-komponentti toimii säiliönä muille komponenteille. Se tarjoaa tavan järjestää ja noutaa tietoa lapsikomponenteista ja tarjoaa selkeän joukon toimintoja lisätä tai poistaa näitä lapsikomponentteja tarpeen mukaan.

### Lapsikomponenttien lisääminen {#adding-child-components}

`Element`-komponentti tukee lapsikomponenttien yhdistelmää. Kehittäjät voivat järjestää ja hallita monimutkaisia käyttöliittymärakenteita lisäämällä komponentteja lapsina `Elementille`. Kolme menetelmää on käytettävissä sisällön asettamiseksi `Elementiin`:

1. **`add(Component... components)`**: Tämä menetelmä sallii yhden tai useamman komponentin lisäämisen valinnaiseen `String`, joka määrittelee tietyn paikan käytettäväksi verkkokomponentin kanssa. Paikan jättäminen pois lisää komponentin HTML-tunnisteiden väliin.

2. **`setText(String text)`**: Tämä menetelmä toimii samalla tavalla kuin `setHtml()`-menetelmä, mutta injektoi kirjaimellisen tekstin `Elementtiin`.

```java
// Näytetään kirjaimellisina merkeinä "<b>Status: ready</b>"
element.setText("<b>Status: ready</b>");
```

:::note Käyttäen `<html>`-tunnistetta
WebforJ:n aikaisemmissa versioissa `<html>`-merkeillä kääritty arvo, joka siirrettiin `setText()`-menetelmälle, käsiteltiin HTML:nä. Tämä käyttäytyminen on vanhentunut ja se poistetaan webforJ 27.00:ssa.

Ensimmäisen kerran, kun `<html>`-merkeillä kääritty arvo saavuttaa `setText()`:n, tallennetaan varoitus, joka nimeää komponentin ja kutsupaikan, jotta kutsu voidaan siirtää `setHtml()`-menetelmään.

Adoptoidaksesi webforJ 27.00 oletusasetuksen etukäteen, aseta `webforj.legacyHtmlInText` arvoksi `false`. Spring-sovelluksessa sama arvo asetetaan `webforj.legacy-html-in-text` kautta.

```java
// webforj.legacyHtmlInText = true (oletus)
element.setText("<html><b>Status: ready</b></html>"); // renderöi lihavoituna

// webforj.legacyHtmlInText = false
element.setText("<html><b>Status: ready</b></html>"); // näyttää merkit <b>Status: ready</b>
```
:::

3. **`setHtml(String html)`**: Tämä menetelmä ottaa metodille siirretyn `String`-arvon ja injektoi sen HTML:nä komponenttiin. Riippuen `Elementistä`, tätä saatetaan renderöidä eri tavoin.

:::danger Ristiinsivustohyökkäykset (XSS)
Varotoimenpiteenä [ristiinsivustohyökkäyksiä (XSS) vastaan](/docs/security/application-security/common-threats#cross-site-scripting-xss), käytä `setHtml()`-menetelmää vain sisällön kanssa, jota hallitset suoraan.
:::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Kutsumalla `setHtml()` tai `setText()` korvataan sisältö, joka on tällä hetkellä elementin avaus- ja sulkutunnisteiden välillä.
:::

### Komponenttien poistaminen {#removing-components}

Lisäksi komponenttien lisäämiseen `Elementiin` on toteutettu seuraavat menetelmät lapsikomponenttien poistamiseen:

1. **`remove(Component... components)`**: Tämä menetelmä ottaa yhden tai useamman komponentin ja poistaa ne lapsikomponenteista.

2. **`removeAll()`**: Tämä menetelmä poistaa kaikki lapsikomponentit `Elementistä`.

### Komponenttien käsittely {#accessing-components}

Päästäksesi käsiksi erilaisiin lapsikomponentteihin, jotka ovat läsnä `Elementissä`, tai tietoihin näistä komponenteista, seuraavat menetelmät ovat saatavilla:

1. **`getComponents()`**: Tämä menetelmä palauttaa Java `List`:n kaikista lapsista `Elementissä`.

2. **`getComponents(String id)`**: Tämä menetelmä on samanlainen kuin yllä oleva, mutta se ottaa käyttöön palvelinpuolen ID:n tietyltä komponentilta ja palauttaa sen löydettäessä.

3. **`getComponentCount()`**: Palauttaa lapsikomponenttien määrän, joka on läsnä `Elementissä`.

## JavaScript-toimintojen kutsuminen {#calling-javascript-functions}

`Element`-komponentti tarjoaa kaksi API-menetelmää, joiden avulla voidaan kutsua JavaScript-toimintoja HTML-elementeissä.

1. **`callJsFunction(String functionName, Object... arguments)`**: Tämä menetelmä ottaa toiminnon nimen merkkijonona, ja tarvittaessa ottaa yhden tai useamman objektin toiminnon parametreiksi. Tämä menetelmä suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty** kunnes JS-menetelmä palauttaa, mikä johtaa pyynnön kiertoon. Toiminnon tulokset palautetaan `Object`-tyyppinä, jota voidaan kaataa ja käyttää Javassa.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Kuten edellisessä menetelmässä, voidaan siirtää toiminnon nimi ja valinnaisia argumentteja. Tämä menetelmä suoritetaan epäsynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka mahdollistaa lisävuorovaikutuksen toiminnon ja sen kuormituksen kanssa.

### Parametrien siirtäminen {#passing-parameters}

Argumentit, jotka siirretään näihin menetelmiin, joita käytetään JS-toimintojen suorittamisessa, sarjoitetaan JSON-taulukkona. Kaksi huomionarvoista argumenttityyppiä käsitellään seuraavasti:
- `this`: Käyttämällä `this`-avainsanaa annetaan menetelmälle viittaus kutsuvan komponentin asiakaspuolen versioon.
- `Component`: Kaikki Java-komponentti-instanssit, jotka siirretään yhteen JsFunction-menetelmistä, korvataan asiakaspuolen komponentin versiolla.

:::info
Sekä synkroniset että epäsynkroniset toiminton kutsuminen odottaa, kunnes `Element` on lisätty DOM:iin ennen toiminnon suorittamista, mutta `callJsFunction()` ei odota mitään `component`-argumentteja liitettäväksi, mikä voi johtaa epäonnistumiseen. Toisaalta, `callJsFunctionAsync()`-kutsun suorittaminen ei ehkä koskaan valmistu, jos komponenttiargumenttia ei koskaan liitetä.
:::

Alla olevaan demoon lisätään tapahtuma HTML `Button`:lle. Tämä tapahtuma laukaisetaan ohjelmallisesti kutsumalla `callJsFunctionAsync()`-menetelmää. Tuloksena olevaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> -objektia käytetään sitten luomaan toinen viestiruudukka, kun epäsynkroninen toiminto on suoritettu.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## JavaScriptin suorittaminen {#executing-javascript}

Lisäksi JavaScriptin suorittaminen sovellustasolla on mahdollista suorittaa JavaScript `Element`-tasolla myös. Tämän suorituksen tekeminen `Element`-tasolla mahdollistaa HTML-elementin kontekstin sisällyttämisen suoritukseen. Tämä on voimakas työkalu, joka toimii kehittäjän kanavana vuorovaikutteisiin kykyihin asiakaspuolellisissa ympäristöissä.

Samanlaista kuin toiminnan suoritus, JavaScriptin suorittaminen voidaan tehdä synkronisesti tai epäsynkronisesti seuraavilla menetelmillä:

1. **`executeJs(String script)`**: Tämä menetelmä ottaa `String`-arvon, joka suoritetaan JavaScript-koodina asiakkaalla. Tämä skripti suoritetaan synkronisesti, mikä tarkoittaa, että **suorittava säie on estetty** kunnes JS-suoritus palauttaa, mikä johtaa pyynnön kiertoon. Toiminnon tulokset palautetaan `Object`-tyyppinä, jota voidaan kaataa ja käyttää Javassa.

2. **`executeJsAsync(String script)`**: Kuten edellisessä menetelmässä, siirretty `String`-parametri suoritetaan JavaScript-koodina asiakaspuolella. Tämä menetelmä suoritetaan epäsynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka mahdollistaa lisävuorovaikutuksen toiminnon ja sen kuormituksen kanssa.

:::tip
Nämä menetelmät pääsevät käsiksi `component`-avainsanaan, joka antaa JavaScript-koodille pääsyn komponentin asiakaspuolen instanssiin, joka suorittaa JavaScriptin.
:::
