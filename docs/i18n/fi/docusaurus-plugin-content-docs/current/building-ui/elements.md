---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 820bed6c059dad74a523673f245f3b2a
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-kehittäjillä on mahdollisuus valita ei vain rikkaasta komponenttikirjastosta, vaan myös integroida komponentteja muualta. Tämän helpottamiseksi `Element`-komponenttia voidaan käyttää yksinkertaistamaan minkä tahansa integraatiota yksinkertaisista HTML-elementeistä monimutkaisempihin räätälöityihin verkkokomponentteihin.

:::important
`Element`-komponentti ei ole laajennettavissa, eikä se ole kaikkien webforJ:n komponenttien peruskomponentti. Lue lisää webforJ:n komponenttihierarkiasta lukemalla [tämä artikkeli](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## Tapahtumien lisääminen {#adding-events}

Jotta voit hyödyntää elementin mukana tulevia tapahtumia, voit käyttää `Element`-komponentin `addEventListener`-metodeja. Tapahtuman lisääminen vaatii vähintään tapahtuman tyypin/nimen, jota komponentti odottaa, sekä kuuntelijan, joka lisätään tapahtumaan.

On myös lisä vaihtoehtoja, joilla voit mukauttaa tapahtumia edelleen käyttämällä Tapahtumaasetuksia.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Komponenttien interaktiot {#component-interaction}

`Element`-komponentti toimii säilönä muille komponenteille. Se tarjoaa tavan organisoida ja hankkia tietoa lapsikomponenteista ja tarjoaa selvän joukon toimintoja näiden lapsikomponenttien lisäämiseksi tai poistamiseksi tarvittaessa.

### Lapsikomponenttien lisääminen {#adding-child-components}

`Element`-komponentti tukee lapsikomponenttien koostumista. Kehittäjät voivat organisoida ja hallita monimutkaisia käyttöliittymän rakenteita lisäämällä komponentteja lapsina `Elementille`. Kolme metodia on olemassa sisällön asettamiseksi `Elementiin`:

1. **`add(Component... components)`**: Tämä metodi sallii yhden tai useamman komponentin lisäämisen valinnaiseen `String`-arvoon, joka määrittelee erityisen slotin käytettäväksi Verkkokomponentin kanssa. Slotin ohittaminen lisää komponentin HTML-tägien väliin.

2. **`setHtml(String html)`**: Tämä metodi ottaa metodille syötetyn `String`-arvon ja injektoi sen HTML:nä komponenttiin. Riippuen `Elementistä`, tämä voidaan renderöidä eri tavoilla.

3. **`setText(String text)`**: Tämä metodi käyttäytyy samalla tavalla kuin `setHtml()`-metodi, mutta injektoi kirjaimellista tekstiä `Elementtiin`.

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
`setHtml()`- tai `setText()`-kutsuminen korvataan sisällön, joka tällä hetkellä sijaitsee elementin avaavien ja sulkevien tagien välissä.
:::

### Komponenttien poistaminen {#removing-components}

Lapsikomponenttien lisäämisen lisäksi `Elementissä` on toteutettu seuraavat metodit erilaisten lapsikomponenttien poistamiseksi:

1. **`remove(Component... components)`**: Tämä metodi ottaa yhden tai useamman komponentin ja poistaa ne lapsikomponenteina.

2. **`removeAll()`**: Tämä metodi poistaa kaikki lapsikomponentit `Elementistä`.

### Komponenttien hakeminen {#accessing-components}

Jotta voit hankkia erilaisia lapsikomponentteja, jotka ovat läsnä `Elementissä`, tai tietoa näistä komponenteista, seuraavat metodit ovat käytettävissä:

1. **`getComponents()`**: Tämä metodi palauttaa Java `List`:in kaikista `Elementin` lapsista.

2. **`getComponents(String id)`**: Tämä metodi on samanlainen kuin yllä oleva metodi, mutta se ottaa palvelinpuolen ID:n tietystä komponentista ja palauttaa sen, kun se löytyy.

3. **`getComponentCount()`**: Palauttaa lapsikomponenttien määrän, joka on läsnä `Elementissä`.

## JavaScript-funktioiden kutsuminen {#calling-javascript-functions}

`Element`-komponentti tarjoaa kaksi API-metodia, jotka mahdollistavat JavaScript-funktioiden kutsumisen HTML-elementeissä.

1. **`callJsFunction(String functionName, Object... arguments)`**: Tämä metodi ottaa funktion nimen merkkijonona ja valinnaisesti yhden tai useamman objektin parametrina. Tämä metodi suoritetaan synnkkisesti, mikä tarkoittaa, että **suorittava säie on estetty**, kunnes JS-metodi palauttaa, ja se aiheuttaa kierroksen. Funktion tulokset palautetaan `Object`-tyyppinä, joka voidaan muuntaa ja käyttää Javassa.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Kuten edellisessä metodissa, funktion nimen ja valinnaiset argumentit voidaan siirtää. Tämä metodi suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka mahdollistaa lisävuorovaikutuksen funktion ja sen kuorman kanssa.

### Parametrien välittäminen {#passing-parameters}

Nämä metodit, joita käytetään JS-funktioiden suorittamisessa, vastaanottavat argumentit, jotka sarjoitetaan JSON-taulukoksi. Kaksi huomionarvoista argumenttityyppiä käsitellään seuraavasti:
- `this`: Käyttämällä `this`-avainsanaa annetaan metodille viittaus kutsuvan komponentin asiakaspuolen versioon.
- `Component`: Kaikki Java-komponentti-instanssit, jotka siirretään yhteen JsFunction-metodeista, korvataan komponentin asiakaspuolen versiolla.

:::info
Sekä synkroninen että asynkroninen funktion kutsuminen odottavat, että `Element` on lisätty DOM:iin ennen funktion suorittamista, mutta `callJsFunction()` ei odota mitään `component`-argumentteja liitettäväksi, mikä voi johtaa epäonnistumiseen. Toisin sanoen, `callJsFunctionAsync()`-kutsuminen ei ehkä koskaan valmistu, jos komponenttiargumenttia ei koskaan liitetä.
:::

Alla olevassa demossa tapahtuma lisätään HTML `Nappiin`. Tämä tapahtuma suoritetaan sitten ohjelmallisesti kutsumalla `callJsFunctionAsync()`-metodia. Saadun <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> -arvoa käytetään sitten luomaan toinen viestiruudukas, kun asynkroninen toiminto on valmis.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## JavaScriptin suorittaminen {#executing-javascript}

Lisäksi JavaScriptin suorittaminen sovelluskerroksessa, sen on mahdollista suorittaa JavaScriptiä `Element`-tasolla. Tällä tasolla tapahtuva suorittaminen mahdollistaa HTML-elementin kontekstin sisällyttämisen suoritukseen. Tämä on tehokas työkalu, joka toimii kehittäjän väylänä vuorovaikutteisiin kykyihin asiakaspuolen ympäristöissä.

Samanlainen kuin funktioiden suorittaminen, JavaScriptin suorittaminen voidaan tehdä synkronisesti tai asynkronisesti seuraavilla metodeilla:

1. **`executeJs(String script)`**: Tämä metodi ottaa `String`:in, joka suoritetaan JavaScript-koodina asiakkaalla. Tämä skripti suoritetaan synnkkisesti, mikä tarkoittaa, että **suorittava säie on estetty**, kunnes JS-suoritus palautuu, ja se aiheuttaa kierroksen. Funktion tulokset palautetaan `Object`-tyyppinä, joka voidaan muuntaa ja käyttää Javassa.

2. **`executeJsAsync(String script)`**: Kuten edellisessä metodissa, siirretty `String`-parametri suoritetaan JavaScript-koodina asiakkaalla. Tämä metodi suoritetaan asynkronisesti eikä **estää suorittavaa säiettä**. Se palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka mahdollistaa lisävuorovaikutuksen funktion ja sen kuorman kanssa.

:::tip
Nämä metodit pääsevät `component`-avainsanaan, mikä antaa JavaScript-koodille pääsyn komponentin asiakaspuolen instanssiin, joka suorittaa JavaScriptin.
:::
