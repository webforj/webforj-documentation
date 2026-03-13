---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 8d01fe0878cf3002fe34ef2e566c2837
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka toimii monipuolisena perustana yhdistettyjen elementtien hallintaan webforJ-sovelluksissa. Sen ensisijainen tarkoitus on helpottaa vuorovaikutusta HTML-elementtien kanssa, joita edustaa Element-luokka, tarjoamalla jäsennellyn tavan käsitellä ominaisuuksia, attribuutteja ja tapahtumakuuntelijoita. Se mahdollistaa elementtien toteuttamisen ja uudelleenkäytön sovelluksessa. Käytä `ElementComposite`-luokkaa, kun toteutat Web-komponentteja käytettäväksi webforJ-sovelluksissa.

`ElementComposite`-luokkaa käytettäessä `getElement()`-menetelmä antaa pääsyn taustalla olevaan Element-komponenttiin. Vastaavasti `getNodeName()`-menetelmä antaa nimen sille solmulle DOM:ssa.

:::tip
On mahdollista tehdä kaikki myös Element-luokalla ilman `ElementComposite`-luokkaa. Kuitenkin `ElementComposite`-luokassa tarjotut menetelmät antavat käyttäjille tavan hyödyntää jo tehtyä työtä.
:::

Tämä opas näyttää, kuinka toteutetaan [Shoelace QR-koodin web-komponentti](https://shoelace.style/components/qr-code) käyttämällä `ElementComposite`-luokkaa.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Ominaisuus- ja attribuuttikuvaukset {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit web-komponenteissa edustavat komponentin tilaa. Niitä käytetään usein tietojen tai asetusten hallintaan. `ElementComposite`-luokka tarjoaa kätevän tavan työskennellä ominaisuuksien ja attribuuttien kanssa.

Ominaisuudet ja attribuutit voidaan julistaa ja alustaa `PropertyDescriptor`-jäseninä kirjoitettavassa `ElementComposite`-luokassa, ja niitä voidaan käyttää koodissa. Ominaisuuksien ja attribuuttien määrittämiseksi käytä `set()`-metodia ominaisuuden arvon asettamiseen. Esimerkiksi `set(PropertyDescriptor<V> property, V value)` asettaa ominaisuuden määritettyyn arvoon.

:::info
Ominaisuuksia käsitellään ja manipuloidaan sisäisesti komponentin koodissa, eikä niitä heijasteta DOM:ssa. Toisaalta attribuutit ovat osa komponentin ulkoista rajapintaa ja niitä voidaan käyttää tietojen välittämiseen komponenttiin ulkopuolelta, tarjoten tavan ulkoisille elementeille tai skripteille komponentin määrittämiseksi.
:::

```java
// Esimerkkioimisuus nimeltä TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Esimerkkialue nimeltä VALUE ElementComposite-luokassa
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "Otsikkoni");
set(VALUE, "Arvoni");
```

Ominaisuuden asettamisen lisäksi käytä `get()`-metodia `ElementComposite`-luokassa pääsyyn ja lukemiseen ominaisuuksista. `get()`-metodiin voidaan välittää valinnainen boolean-arvo, joka on oletuksena epätosi, jotta voidaan määrittää, tekee metodi matkan asiakkaalle arvon hakemiseksi. Tämä vaikuttaa suorituskykyyn, mutta voi olla välttämätöntä, jos ominaisuutta voidaan muuttaa pelkästään asiakkaalla.

Metodiin voidaan myös välittää Tyyppi, joka määrittää, mihin haettu tulos muunnataan.

:::tip
Tämä Tyyppi ei ole ilmeisen tarpeellinen, ja se lisää ylimääräisen tason määrittelyä tietojen haun yhteydessä.
:::

```java
// Esimerkkioimisuus nimeltä TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Alla olevassa demossa on lisätty ominaisuuksia QR-koodille dokumentaation perusteella web-komponentista. Menetelmiä on sitten toteutettu, jotka sallivat käyttäjien saada ja asettaa toteutettuja eri ominaisuuksia.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Tapahtumien rekisteröinti {#event-registration}

Tapahtumat mahdollistavat viestinnän webforJ-sovelluksen eri osien välillä. `ElementComposite`-luokka tarjoaa tapahtumankäsittelyä, joka tukee tömmöistä, rajausta, suodatusta ja mukautettua tapahtumatiedon keruuta.

Rekisteröi tapahtumakuuntelijat `addEventListener()`-menetelmällä:

```java
// Esimerkki: Lisää hiiren napsautustapahtuman kuuntelija
addEventListener(ElementClickEvent.class, event -> {
    // Käsittele napsautustapahtuma
});
```

:::info
`ElementComposite`-tapahtumat poikkeavat Element-tapahtumista siten, että tämä ei salli mitä tahansa luokkaa, vaan vain määritellyt Event-luokat.
:::

### Esitäytetyt tapahtumaluokat {#built-in-event-classes}

webforJ tarjoaa valmiiksi rakennettuja tapahtumaluokkia tyyppitietojen pääsyyn:

- **ElementClickEvent**: Hiiren napsautustapahtumat koordinaateilla (`getClientX()`, `getClientY()`), painetiedot (`getButton()`) ja modifier-näppäimet (`isCtrlKey()`, `isShiftKey()` jne.)
- **ElementDefinedEvent**: Lausutaan, kun mukautettu elementti on määritelty DOM:ssa ja valmis käytettäväksi
- **ElementEvent**: Perustapahtumaluokka, joka tarjoaa pääsyn raakatapahtumatietoihin, tapahtuman tyyppiin (`getType()`) ja tapahtuman ID:hen (`getId()`)

### Tapahtumakuormat {#event-payloads}

Tapahtumat kuljettavat tietoja asiakkaalta Java-koodiisi. Pääset näihin tietoihin käsiksi getData()-menetelmällä raakatapahtumatietojen hakemiseen tai käytä tyyppimenetelmiä, kun niitä on saatavilla esitäytetyissä tapahtumaluokissa. Lisätietoja tapahtumakuormien tehokkaasta käytöstä löytyy [Tapahtumat-oppaasta](../building-ui/events).

## Mukautetut tapahtumaluokat {#custom-event-classes}

Erityisten tapahtumakäsittelyjen toteuttamiseksi luo mukautettuja tapahtumaluokkia määritetyillä kuormilla käyttämällä @EventName- ja @EventOptions-annotaatioita.

Alla olevassa esimerkissä on luotu napsautustapahtuma ja lisätty se QR-koodikomponenttiin. Tämä tapahtuma, kun se laukaistaan, näyttää hiiren "X"-koordinaatin napsauttaessa komponenttia, joka annetaan Java-tapahtumalle tietona. Tapahtumalle on toteutettu menetelmä, joka sallii käyttäjän pääsyn näihin tietoihin, mikä on tapa, jolla se näytetään sovelluksessa.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## ElementEventOptions {#elementeventoptions}

ElementEventOptions mahdollistaa tapahtumakäyttäytymisen mukauttamisen määrittämällä, mitä tietoja kerätään, milloin tapahtumat laukaistaan ja miten niitä käsitellään. Tässä on kattava koodikatkelma, joka näyttää kaikki konfigurointivaihtoehdot:

```java
ElementEventOptions options = new ElementEventOptions()
    // Kerää mukautettua tietoa asiakkaalta
    .addData("query", "component.value")
    .addData("timestamp", "Date.now()")
    .addData("isValid", "component.checkValidity()")
    
    // Suorita JavaScript ennen tapahtuman laukeamista
    .setCode("component.classList.add('processing');")
    
    // Laadi vain, jos ehdot täyttyvät
    .setFilter("component.value.length >= 2")
    
    // Viivyttää suorittamista, kunnes käyttäjä lopettaa kirjoittamisen (300ms)
    .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Suorituskyvyn hallinta {#performance-control}

Hallitse, milloin ja kuinka usein tapahtumat lauletaan:

**Viivyttäminen** viivästyttää suoritusta, kunnes toiminta loppuu:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Odota 300ms viimeisestä tapahtumasta
```

**Rajoittaminen** rajoittaa suorituksen taajuutta:

```java
options.setThrottle(100); // Laukaise enintään kerran 100ms välein
```

Saatavilla olevat viivytysvaiheet:

- `LEADING`: Laukaise heti, odota sitten
- `TRAILING`: Odota hiljaisen ajanjakson jälkeen, sitten laukaiseminen (oletus)
- `BOTH`: Laukaise heti ja hiljaisen jakson jälkeen

## Asetusten yhdistäminen {#options-merging}

Yhdistä tapahtumakonfiguraatioita eri lähteistä käyttämällä `mergeWith()`-menetelmää. Perusasetukset tarjoavat yleistä tietoa kaikille tapahtumille, kun taas erityiset asetukset lisäävät erityisen konfiguraation. Viimeisimmät asetukset korvaavat ristiriitaiset asetukset.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Vuorovaikutus slotien kanssa {#interacting-with-slots}

Web-komponentit käyttävät usein slotteja, jotta kehittäjät voivat määrittää komponentin rakenteen ulkopuolelta. Slot on paikka web-komponentin sisällä, joka voidaan täyttää sisällöllä komponenttia käytettäessä. `ElementComposite`-luokan yhteydessä slotit tarjoavat tavan räätälöidä sisältö komponentissa. Seuraavat menetelmät tarjoavat kehittäjille mahdollisuuden vuorovaikuttaa ja manipuloida slotteja:

1. **findComponentSlot()**: Tätä menetelmää käytetään etsimään tietty komponentti kaikista sloteista komponenttisysteemissä. Se palauttaa sen slotin nimen, jossa komponentti sijaitsee. Jos komponenttia ei löydy mistään slotista, palautetaan tyhjää merkkijono.

2. **getComponentsInSlot()**: Tämä menetelmä noutaa luettelon komponenteista, jotka on määritetty tiettyyn slottiin komponenttisysteemissä. Valinnaisesti voit siirtää tietyn luokkatyypin suodattamaan menetelmän tuloksia.

3. **getFirstComponentInSlot()**: Tämä menetelmä on suunniteltu hakemaan ensimmäinen slotille määritetty komponentti. Valinnaisesti voit siirtää tietyn luokkatyypin suodattamaan tämän menetelmän tuloksia.

On myös mahdollista käyttää `add()`-menetelmää merkkijono parametrina määrittämään haluttu slot, johon lisätään siirretty komponentti.

Nämä vuorovaikutukset mahdollistavat kehittäjille web-komponenttien tehokkuuden hyödyntämisen tarjoamalla puhtaan ja suoraviivaisen API:n slotien, ominaisuuksien manipuloimiseen ja tapahtumien käsittelyyn `ElementComposite`-luokassa.
