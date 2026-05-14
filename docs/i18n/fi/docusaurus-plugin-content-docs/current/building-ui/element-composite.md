---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: c64ec386d273ab7facb974f5577afecf
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka toimii monipuolisena perustana yhdistettyjen elementtien hallintaan webforJ-sovelluksissa. Sen ensisijainen tarkoitus on helpottaa vuorovaikutusta HTML-elementtien kanssa, joita edustaa `Element`-luokka, tarjoamalla rakenteellisen lähestymistavan ominaisuuksien, attribuuttien ja tapahtumakuuntelijoiden käsittelyyn. Se mahdollistaa elementtien toteuttamisen ja uudelleenkäytön sovelluksessa. Käytä `ElementComposite`-luokkaa Web-komponenttien toteuttamiseen, joita käytetään webforJ-sovelluksissa.

`ElementComposite`-luokkaa käytettäessä `getElement()`-metodi antaa sinulle pääsyn taustalla olevaan `Element`-komponenttiin. Vastaavasti `getNodeName()`-metodi antaa sinulle kyseisen solmun nimen DOM:issa.

:::tip
On mahdollista tehdä kaikki `Element`-luokan itsensä kanssa, ilman `ElementComposite`-luokan käyttöä. Kuitenkin `ElementComposite`-luokan metodit tarjoavat sinulle tavan käyttää uudelleen työtäsi.
:::

Tämän sivun esimerkit osoittavat, kuinka toteuttaa [Shoelace QR-koodi web-komponentti](https://shoelace.style/components/qr-code) käyttäen `ElementComposite`-luokkaa.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Ominaisuudet ja attribuuttikuvastot {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit web-komponenteissa kuvaavat komponentin tilaa. Niitä käytetään usein datan tai konfiguraation hallintaan. `ElementComposite`-luokka tarjoaa kätevän tavan työskennellä ominaisuuksien ja attribuuttien kanssa.

Ominaisuudet ja attribuutit voidaan määrittää ja alustaa `PropertyDescriptor`-jäseninä kirjoitettavassa `ElementComposite`-luokassa ja niitä voidaan käyttää koodissa. Ominaisuuksien ja attribuuttien määrittämiseksi käytä `set()`-metodia ominaisuuden arvon asettamiseen. Esimerkiksi, `set(PropertyDescriptor<V> property, V value)` asettaa ominaisuuden määritettyyn arvoon.

:::info
Ominaisuuksia päästään käsittelemään ja manipuloimaan komponentin sisäisessä koodissa, eivätkä ne heijastu DOM:iin. Toisaalta attribuutit ovat osa komponentin ulkoista rajapintaa ja niitä voidaan käyttää tiedon siirtämiseen komponenttiin ulkopuolelta, mikä tarjoaa tavan ulkoisille elementeille tai skripteille konfiguroida komponenttia.
:::

```java
// Esimerkki ominaisuudesta nimeltä TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Esimerkki attribuutista nimeltä VALUE ElementComposite-luokassa
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "Otsikkoni");
set(VALUE, "Arvoni");
```

Käytä `get()`-metodia `ElementComposite`-luokassa pääsyyn ominaisuuksiin ja niiden lukemiseen. `get()`-metodi voi hyväksyä valinnaisen `boolean`-arvon, joka on oletuksena false, joka määrää, tekeekö metodi matkan asiakkaalle arvon hakemiseksi. Tämä vaikuttaa suorituskykyyn, mutta voi olla tarpeen, jos ominaisuutta voidaan muuttaa vain asiakkaalla.

`Type`-tyyppi voidaan myös välittää metodille, joka määrää, mihin tulosta tulee käsittää.

:::tip
Tämä `Type` ei ole ilmeisesti välttämätön, ja lisää ylimääräisen määrittelyn kerroksen, kun dataa haetaan.
:::

```java
// Esimerkki ominaisuudesta nimeltä TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Alla olevassa demossa ominaisuuksia on lisätty QR-koodille dokumentaation perusteella web-komponentille. Menetelmiä on sitten toteutettu, mikä mahdollistaa käyttäjien saada ja asettaa erilaisia ominaisuuksia, joita on toteutettu.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Tapahtumien rekisteröinti {#event-registration}

Tapahtumat mahdollistavat viestinnän eri osien välillä webforJ-sovelluksessa. `ElementComposite`-luokka tarjoaa tapahtumien käsittelyn, joka tukee debounce-toimintoa, throttlingia, suodatusta, ja mukautetun tapahtumatiedon keräämistä.

Rekisteröi tapahtumakuuntelijat käyttämällä `addEventListener()`-metodia:

```java
// Esimerkki: Klikkaustapahtuman kuuntelijan lisääminen
addEventListener(ElementClickEvent.class, event -> {
  // Käsittele klikkauksen tapahtuma
});
```

:::info
`ElementComposite`-tapahtumat poikkeavat `Element`-tapahtumista siten, että ne eivät salli mitä tahansa luokkaa, vaan vain määritettyjä `Event`-luokkia.
:::

### Valmiit tapahtumaluokat {#built-in-event-classes}

webforJ tarjoaa valmiiksi rakennettuja tapahtumaluokkia, joilla on tyypitettyä datan käyttöä:

- **ElementClickEvent**: Hiiren klikkaustapahtumat koordinaateilla (`getClientX()`, `getClientY()`), painiketuototiedot (`getButton()`), ja modifier-näppäimet (`isCtrlKey()`, `isShiftKey()`, jne.)
- **ElementDefinedEvent**: Laukaisee, kun mukautettu elementti määritellään DOM:issa ja on valmis käytettäväksi
- **ElementEvent**: Perustapahtumaluokka, joka tarjoaa pääsyn raakaan tapahtumatietoon, tapahtuman tyyppiin (`getType()`) ja tapahtuman ID:hen (`getId()`)

### Tapahtuman payloadit {#event-payloads}

Tapahtumat kuljettavat dataa asiakkaalta Java-koodillesi. Pääset tähän dataan käsiksi `getData()`-metodin avulla raakojen tapahtumatietojen osalta tai käytä tyypitettyjä menetelmiä silloin, kun ne ovat saatavilla valmiiksi rakennetuissa tapahtumaluokissa. Lisätietoja tapahtuman payloadien tehokkaasta käytöstä löydät [Tapahtumat-oppaasta](../building-ui/events).

## Mukautetut tapahtumaluokat {#custom-event-classes}

Erityisiä tapahtumien käsittelyä varten luo mukautettuja tapahtumaluokkia, joissa on määritetyt payloadit käyttämällä `@EventName` ja `@EventOptions` -annotaatioita.

Alla olevassa esimerkissä on luotu klikkaustapahtuma, joka on sitten lisätty QR-koodikomponenttiin. Tämä tapahtuma, kun se laukaistaan, näyttää hiiren "X" koordinaatin klikkauksen aikana komponentin, joka annetaan Java-tapahtumalle datana. Toteutetaan sitten menetelmä, joka mahdollistaa käyttäjän päästä tähän dataan käsiksi, mikä on se, kuinka se näytetään sovelluksessa.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` antaa sinun mukauttaa tapahtumakäyttäytymistä määrittämällä, mitä dataa kerätään, milloin tapahtumat laukaistaan ja miten niitä käsitellään. Tässä on kattava koodinäyte, joka näyttää kaikki konfigurointivaihtoehdot:

```java
ElementEventOptions options = new ElementEventOptions()
  // Kerää mukautettua dataa asiakkaalta
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Suorita JavaScript ennen tapahtuman tulemista
  .setCode("component.classList.add('processing');")
  
  // Laukaise vain, jos ehdot täyttyvät
  .setFilter("component.value.length >= 2")
  
  // Viivästytä suorittamista, kunnes käyttäjä lopettaa kirjoittamisen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Suorituskyvyn hallinta {#performance-control}

Hallitse, milloin ja kuinka usein tapahtumat laukaistaan:

**Debouncing** viivyttää suorittamista, kunnes toiminta loppuu:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Odota 300ms viimeisen tapahtuman jälkeen
```

**Throttling** rajoittaa suoritusrajoituksia:

```java
options.setThrottle(100); // Laukaise enintään kerran 100ms aikana
```

Saatavilla olevat debounce-vaiheet:

- `LEADING`: Laukaise heti, odota sitten
- `TRAILING`: Odota hiljaista jaksoa, laukaise sitten (oletusarvo)
- `BOTH`: Laukaise heti ja hiljaista jaksoa myöhemmin

## Asetusten yhdistäminen {#options-merging}

Yhdistä tapahtumakonfiguraatiot eri lähteistä käyttäen `mergeWith()`. Perusasetukset tarjoavat yhteiset tiedot kaikille tapahtumille, kun taas erityiset asetukset lisäävät erikoistuneen konfiguraation. Myöhemmät asetukset korvaavat ristiriitaiset asetukset.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Vuorovaikutus slotit {#interacting-with-slots}

Web-komponentit käyttävät usein slotteja, jotta kehittäjät voivat määritellä komponentin rakenteen ulkopuolelta. Slot on paikka, joka on määritetty web-komponentin sisälle, ja joka voidaan täyttää sisällöllä komponentin käytön yhteydessä. `ElementComposite`-luokan yhteydessä slotit tarjoavat tavan mukauttaa sisältöä komponentissa. Seuraavat metodit mahdollistavat kehittäjille vuorovaikutuksen ja slotien manipuloinnin:

1. **`findComponentSlot()`**: Tätä metodia käytetään etsimään tiettyä komponenttia kaikkien slotien väliltä komponenttijärjestelmässä. Se palauttaa slotin nimen, jossa komponentti sijaitsee. Jos komponenttia ei löydy mistään slotista, palautetaan tyhjää merkkijonoa.

2. **`getComponentsInSlot()`**: Tämä metodi noutaa luettelon komponenteista, jotka on määritetty tiettyyn slottiin komponenttijärjestelmässä. Valinnaisesti voit välittää tietyn luokkatyypin suodattaaksesi metodin tulokset.

3. **`getFirstComponentInSlot()`**: Tämä metodi on suunniteltu hakemaan ensimmäinen komponentti, joka on määritetty slotille. Valinnaisesti voit välittää tietyn luokkatyypin suodattaaksesi tämän metodin tulokset.

On myös mahdollista käyttää `add()`-metodia, jossa on `String`-parametri, määrittämään haluttu slot, johon lisätään välitetty komponentti.

Nämä vuorovaikutukset mahdollistavat kehittäjille web-komponenttien voiman hyödyntämisen tarjoamalla puhtaan ja yksinkertaisen API:n slotien, ominaisuuksien ja tapahtumien käsittelyyn `ElementComposite`-luokassa.
