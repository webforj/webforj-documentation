---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 1fc82a7db864ec48118fb611a94a57fc
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka toimii monipuolisena perustana yhdistelementtien hallintaan webforJ-sovelluksissa. Sen ensisijainen tarkoitus on helpottaa HTML-elementtien, joita edustaa `Element`-luokka, välistä vuorovaikutusta tarjoamalla jäsennellyn lähestymistavan ominaisuuksien, attribuuttien ja tapahtumakuuntelijoiden käsittelyyn. Se mahdollistaa elementtien toteuttamisen ja uudelleenkäytön sovelluksessa. Käytä `ElementComposite`-luokkaa, kun toteutat Web-komponentteja webforJ-sovelluksissa.

Käytettäessä `ElementComposite`-luokkaa `getElement()`-metodi antaa pääsyn taustalla olevaan `Element`-komponenttiin. Samoin `getNodeName()`-metodi antaa nimen tälle solmulle DOM:ssa.

:::tip
On mahdollista tehdä kaikki `Element`-luokan itsensä avulla ilman `ElementComposite`-luokan käyttöä. Kuitenkin `ElementComposite`-luokassa tarjotut menetelmät antavat käyttäjille mahdollisuuden uudelleenkäyttää jo tehtyä työtä.
:::

Tämä opas näyttää, miten toteuttaa [Shoelace QR-koodi web-komponentti](https://shoelace.style/components/qr-code) käyttäen `ElementComposite`-luokkaa.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Ominaisuudet ja attribuuttikuvastot {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit web-komponenteissa edustavat komponentin tilaa. Niitä käytetään usein datan tai konfiguraation hallintaan. `ElementComposite`-luokka tarjoaa kätevän tavan työskennellä ominaisuuksien ja attribuuttien kanssa.

Ominaisuudet ja attribuutit voidaan määritellä ja alustaa `PropertyDescriptor`-jäseninä kirjoitettavassa `ElementComposite`-luokassa ja käyttää sitten koodissa. Määritä ominaisuudet ja attribuutit käyttämällä `set()`-metodia arvon asettamiseksi ominaisuudelle. Esimerkiksi `set(PropertyDescriptor<V> property, V value)` asettaa ominaisuuden tiettyyn arvoon.

:::info
Ominaisuuksiin pääsee käsiksi ja niitä muokataan komponentin sisäisessä koodissa, eivätkä ne heijastu DOM:ssa. Toisaalta, attribuutit ovat osa komponentin ulkoista rajapintaa ja niitä voidaan käyttää tietojen siirtämiseen komponenttiin ulkopuolelta, tarjoten tavan ulkoisille elementeille tai skripteille konfiguroida komponenttia.
:::

```java
// Esimerkillinen ominaisuus nimeltä TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Esimerkillinen attribuutti nimeltä VALUE ElementComposite-luokassa
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "My Title");
set(VALUE, "My Value");
```

Ominaisuuden asettamisen lisäksi käytä `get()`-metodia `ElementComposite`-luokassa pääsyyn ja ominaisuuksien lukemiseen. `get()`-metodille voidaan antaa valinnainen `boolean`-arvo, joka on oletusarvoisesti epätosi, määräämään, saako metodi tehdä matkan asiakkaalle arvon hakemiseksi. Tämä vaikuttaa suorituskykyyn, mutta voi olla tarpeellista, jos ominaisuutta voidaan muokata pelkästään asiakkaalla.

Metodille voidaan myös antaa `Type`, joka määrää, mihin haettu tulos muunnetaan.

:::tip
Tämä `Type` ei ole välttämätön, ja se lisää ylimääräisen tason spesifikaatiota, kun dataa haetaan.
:::

```java
// Esimerkillinen ominaisuus nimeltä TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Alla olevassa demon esimerkissä ominaisuudet on lisätty QR-koodille web-komponentin dokumentaation perusteella. Menetelmiä on sitten toteutettu, jotka mahdollistavat käyttäjien saada ja asettaa toteutettuja erilaisia ominaisuuksia.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Tapahtumien rekisteröinti {#event-registration}

Tapahtumat mahdollistavat viestinnän eri osien välillä webforJ-sovelluksessa. `ElementComposite`-luokka tarjoaa tapahtumien käsittelyä, jossa on tukea debounce-, throttle-, suodatus- ja mukautetulle tapahtumatietojen keruulle.

Rekisteröi tapahtumakuuntelijat käyttämällä `addEventListener()`-metodia:

```java
// Esimerkki: Lisää hiiren napsautustapahtumakuuntelija
addEventListener(ElementClickEvent.class, event -> {
  // Käsittele napsautustapahtuma
});
```

:::info
`ElementComposite`-tapahtumat ovat erilaisia kuin `Element`-tapahtumat siten, että tämä ei salli mitä tahansa luokkaa, vaan vain määriteltyjä `Event`-luokkia.
:::

### Valmiit tapahtumaluokat {#built-in-event-classes}

webforJ tarjoaa valmiit tapahtumaluokat, joihin on tyypitetty pääsy datalle:

- **ElementClickEvent**: Hiiren napsautustapahtumat koordinaateilla (`getClientX()`, `getClientY()`), painetietoja (`getButton()`) ja modifier-näppäimiä (`isCtrlKey()`, `isShiftKey()`, jne.)
- **ElementDefinedEvent**: Laukaisee, kun mukautettu elementti määritellään DOM:ssa ja on valmis käytettäväksi
- **ElementEvent**: Perustapahtumaluokka, joka tarjoaa pääsyn raakadatasiin, tapahtumatyyppiin (`getType()`) ja tapahtuma-ID:hen (`getId()`)

### Tapahtumatietopaketit {#event-payloads}

Tapahtumat kuljettavat tietoa asiakkaalta Java-koodillesi. Pääset käsiksi tähän dataan käyttämällä `getData()` raakadatalle tai käytä tyypitettyjä metodeja, kun niitä on käytettävissä valmiissa tapahtumaluokissa. Lisätietoja tapahtumatietopaketin tehokkaasta käytöstä löytyy [Tapahtumat-oppaasta](../building-ui/events).

## Mukautetut tapahtumaluokat {#custom-event-classes}

Erityistä tapahtumakäsittelyä varten luo mukautettuja tapahtumaluokkia, joissa on konfiguroidut tietopaketit käyttämällä `@EventName` ja `@EventOptions` -annotaatioita.

Alla olevassa esimerkissä on luotu napsautustapahtuma, joka on sitten lisätty QR-koodikomponenttiin. Tämä tapahtuma, kun se laukaistaan, näyttää hiiren "X"-koordinaatin napsautettaessa komponenttia, joka annetaan Java-tapahtumalle datana. Menetelmä on sitten toteutettu, jotta käyttäjä voi käyttää tätä dataa, mikä on tapa, jolla se näytetään sovelluksessa.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` antaa sinun mukauttaa tapahtumien käyttäytymistä konfiguroimalla, mitä dataa kerätään, milloin tapahtumat laukaistaan ja miten niitä käsitellään. Tässä on kattava koodipala, joka näyttää kaikki konfigurointivaihtoehdot:

```java
ElementEventOptions options = new ElementEventOptions()
  // Kerää mukautettuja tietoja asiakkaalta
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Suorita JavaScript ennen kuin tapahtuma laukaistaan
  .setCode("component.classList.add('processing');")
  
  // Laukaise vain, jos olosuhteet täyttyvät
  .setFilter("component.value.length >= 2")
  
  // Viivytä suoritus, kunnes käyttäjä lakkaa kirjoittamasta (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Suorituskyvyn hallinta {#performance-control}

Hallitse, milloin ja kuinka usein tapahtumat laukaistaan:

**Viivästys (Debouncing)** viivästyttää suorittamista, kunnes toiminta lakkaa:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Odota 300ms viimeisen tapahtuman jälkeen
```

**Rajoitus (Throttling)** rajoittaa suorittamisen taajuutta:

```java
options.setThrottle(100); // Laukaise korkeintaan kerran 100ms välein
```

Saatavilla olevat viivästysvaiheet:

- `LEADING`: Laukaise heti, odota sitten
- `TRAILING`: Odota rauhallista jaksoa, sitten laukaise (oletus)
- `BOTH`: Laukaise heti ja rauhallisen jakson jälkeen

## Asetusten yhdistäminen {#options-merging}

Yhdistä tapahtumakonfiguraatioita eri lähteistä käyttämällä `mergeWith()`. Perusasetukset tarjoavat yhteisiä tietoja kaikille tapahtumille, kun taas erityiset asetukset lisäävät erikoiskonfiguraatioita. Myöhemmät asetukset ohittavat ristiriitaiset asetukset.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Vuorovaikutus slotien kanssa {#interacting-with-slots}

Web-komponentit käyttävät usein slotteja, jotta kehittäjät voivat määrittää komponentin rakenteen ulkopuolelta. Slotti on paikka web-komponentin sisällä, joka voidaan täyttää sisällöllä komponenttia käytettäessä. `ElementComposite`-luokan yhteydessä slotit tarjoavat tavan mukauttaa sisältöä komponentin sisällä. Seuraavat metodit on tarjottu, jotta kehittäjät voivat vuorovaikuttaa ja manipuloida slotteja:

1. **`findComponentSlot()`**: Tätä metodia käytetään etsimään tiettyä komponenttia kaikista slotteista komponenttijärjestelmässä. Se palauttaa slotin nimen, johon komponentti on sijoitettu. Jos komponenttia ei löydy mistään slotista, palautuu tyhjää merkkijonoa.

2. **`getComponentsInSlot()`**: Tämä metodi hakee luettelon annetuista komponenteista, jotka on määritetty tiettyyn slottiin komponenttijärjestelmässä. Valinnaisesti voit antaa tietyn luokkatyypin suodattaaksesi metodin tuloksia.

3. **`getFirstComponentInSlot()`**: Tämä metodi on suunniteltu hakemaan ensimmäinen komponentti, joka on määritetty slottiin. Valinnaisesti voit antaa tietyn luokkatyypin suodattaaksesi tämän metodin tuloksia.

On myös mahdollista käyttää `add()`-metodia, jossa on `String`-parametri, määrittämään haluttu slotti, johon lisätä annettu komponentti.

Nämä vuorovaikutukset antavat kehittäjille mahdollisuuden hyödyntää web-komponenttien voimaa tarjoamalla puhtaan ja suoraviivaisen API:n slotien, ominaisuuksien ja tapahtumien käsittelyyn `ElementComposite`-luokassa.
