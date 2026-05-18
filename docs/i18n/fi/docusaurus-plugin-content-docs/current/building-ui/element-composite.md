---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: de075e855ba84ee82ec08c2bef771ea8
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka toimii monipuolisena perustana yhdistelmien hallinnassa webforJ-sovelluksissa. Sen ensisijainen tarkoitus on helpottaa vuorovaikutusta HTML-elementtien kanssa, joita esittää `Element`-luokka, tarjoamalla jäsennellyn lähestymistavan ominaisuuksien, attribuuttien ja tapahtumakuuntelijoiden hallintaan. Se mahdollistaa elementtien toteuttamisen ja uudelleenkäytön sovelluksessa. Käytä `ElementComposite`-luokkaa toteuttaessasi Web-komponentteja käytettäväksi webforJ-sovelluksissa.

Käyttämällä `ElementComposite`-luokkaa, `getElement()`-menetelmä antaa pääsyn taustalla olevaan `Element`-komponenttiin. Vastaavasti `getNodeName()`-menetelmä antaa nimen kyseiselle solmulle DOM:ssa.

:::tip
Kaiken voi tehdä myös `Element`-luokalla itsessään, ilman `ElementComposite`-luokan käyttöä. Kuitenkin `ElementComposite`-luokan menetelmät tarjoavat tavan hyödyntää työtäsi uudelleen.
:::

Tämän sivun esimerkit havainnollistavat, kuinka toteutetaan [Shoelace QR-koodin web-komponentti](https://shoelace.style/components/qr-code) käyttämällä `ElementComposite`-luokkaa.

<ComponentDemo
path='/webforj/qrdemo'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java']}
height='175px'
/>

## Ominuus- ja attribuuttikuvastot {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit web-komponenteissa edustavat komponentin tilaa. Niitä käytetään usein datan tai konfiguraation hallintaan. `ElementComposite`-luokka tarjoaa kätevän tavan työskennellä ominaisuuksien ja attribuuttien kanssa.

Ominaisuudet ja attribuutit voidaan julistaa ja alustaa `PropertyDescriptor`-jäseninä kirjoitettavassa `ElementComposite`-luokassa, ja niitä voidaan sitten käyttää koodissa. Ominaisuuksien ja attribuuttien määrittämiseen käytetään `set()`-menetelmää ominaisuuden arvon asettamiseen. Esimerkiksi, `set(PropertyDescriptor<V> property, V value)` asettaa ominaisuuden määritettyyn arvoon.

:::info
Ominaisuuksia käsitellään ja manipuloidaan sisäisesti komponentin koodissa eivätkä ne heijastu DOM:iin. Toisaalta attribuutit ovat osa komponentin ulkoista rajapintaa ja niitä voidaan käyttää jakamaan tietoa komponenttiin ulkopuolelta, tarjoten tavan ulkoisille elementeille tai skripteille konfiguroida komponenttia.
:::

```java
// Esimerkkitäsmä, nimeltään TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Esimerkkitunnus, nimeltään VALUE ElementComposite-luokassa
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "Otsikko");
set(VALUE, "Arvo");
```

Käytä `get()`-menetelmää `ElementComposite`-luokassa päästäksesi käsiksi ja lukeaksesi ominaisuuksia. `get()`-menetelmä voi hyväksyä valinnaisen `boolean`-arvon, joka on oletuksena false, määrätäksesi, tekeekö menetelmä matkan asiakkaalle arvoa noutaessaan. Tämä vaikuttaa suorituskykyyn, mutta voi olla tarpeen, jos ominaisuutta voidaan muokata täysin asiakkaalla.

Myös `Type` voidaan välittää menetelmälle, mikä määrää, mihin tuloksen halutaan kastaa.

:::tip
Tämä `Type` ei ole välttämätön, ja se lisää ylimääräisen määrittelyn tason datan noudon yhteydessä.
:::

```java
// Esimerkkittäsmä, nimeltään TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Alla olevassa demossa ominaisuuksia on lisätty QR-koodille web-komponentin dokumentaation perusteella. Menetelmiä on sitten toteutettu, jotka mahdollistavat käyttäjien saamisen ja asettamisen erilaisten toteutettujen ominaisuuksien välillä.

<ComponentDemo
path='/webforj/qrproperties'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java']}
height='250px'
/>

## Tapahtumien rekisteröinti {#event-registration}

Tapahtumat mahdollistavat viestinnän eri osien välillä webforJ-sovelluksessasi. `ElementComposite`-luokka tarjoaa tapahtumien käsittelyn tukea vaimennukselle, throttlingille, suodattamiselle ja mukautetun tapahtumatiedon keräämiselle.

Rekisteröi tapahtumakuuntelijat käyttämällä `addEventListener()`-menetelmää:

```java
// Esimerkki: Klikkaustapahtuman kuuntelijan lisääminen
addEventListener(ElementClickEvent.class, event -> {
  // Käsittele klikkaustapahtuma
});
```

:::info
`ElementComposite`-tapahtumat poikkeavat `Element`-tapahtumista siinä, että ne eivät salli mitään luokkaa, vaan vain määrättyjä `Event`-luokkia.
:::

### Sisäänrakennetut tapahtumaluokat {#built-in-event-classes}

webforJ tarjoaa valmiiksi rakennettuja tapahtumaluokkia, joilla on typistetty tiedonsyöttö:

- **ElementClickEvent**: Hiiren klikkaustapahtumat koordinaateilla (`getClientX()`, `getClientY()`), painetietoa (`getButton()`) ja modifier-näppäimiä (`isCtrlKey()`, `isShiftKey()`, jne.)
- **ElementDefinedEvent**: Käynnistetään, kun mukautettu elementti on määritelty DOM:ssa ja valmis käyttöön
- **ElementEvent**: Perustapahtumaluokka, joka tarjoaa pääsyn raakadatasta, tapahtumatyypistä (`getType()`) ja tapahtuma-ID:stä (`getId()`)

### Tapahtumakuormat {#event-payloads}

Tapahtumat kuljettavat tietoa asiakkaalta Java-koodillesi. Pääset tähän tietoon `getData()`-menetelmän kautta saadaksesi raakadataa tai käytä tyypitettyjä metodeja, kun ne ovat käytettävissä sisäänrakennetuissa tapahtumaluokissa. Lisätietoja tapahtumakuormien tehokkaasta käytöstä saat [Tapahtumat-oppaasta](../building-ui/events).

## Mukautetut tapahtumaluokat {#custom-event-classes}

Erityisen tapahtumakäsittelyn tueksi voit luoda mukautettuja tapahtumaluokkia, joissa on konfiguroituja kuormia käyttämällä `@EventName` ja `@EventOptions` -anotaatiota.

Alla olevassa esimerkissä on luotu klikkaustapahtuma, joka on lisätty QR-koodikomponenttiin. Tämä tapahtuma, kun se laukaisee, näyttää hiiren "X"-koordinaatin komponenttiin napsauttaessa, joka annetaan Java-tapahtumalle datana. Tapahtumaa varten on toteutettu menetelmä, joka mahdollistaa käyttäjän pääsyn tähän tietoon, joka on tapa, jolla se näkyy sovelluksessa.

<ComponentDemo
path='/webforj/qrevent'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java']}
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` antaa sinun mukauttaa tapahtumakäyttäytymistä määrittämällä, mitä tietoja kerätään, milloin tapahtumat laukaistaan ja miten niitä käsitellään. Tässä on kattava koodinäyte, joka näyttää kaikki konfigurointivaihtoehdot:

```java
ElementEventOptions options = new ElementEventOptions()
  // Kerää mukautettuja tietoja asiakkaalta
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Suorita JavaScript ennen tapahtuman laukaisemista
  .setCode("component.classList.add('processing');")
  
  // Laukaise vain, jos ehdot täyttyvät
  .setFilter("component.value.length >= 2")
  
  // Viivytä suorittamista, kunnes käyttäjä lopettaa näppäilyn (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Suorituskyvyn hallinta {#performance-control}

Hallitse, milloin ja kuinka usein tapahtumat lauotaan:

**Debouncing** viivyttää suoritusta, kunnes toiminta lopettaa:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Odota 300ms viimeisen tapahtuman jälkeen
```

**Throttling** rajoittaa suoritusten taajuutta:

```java
options.setThrottle(100); // Laukaise korkeintaan kerran 100ms
```

Saatavilla olevat vaimennusvaiheet:

- `LEADING`: Laukaise heti, odota sitten
- `TRAILING`: Odota hiljaista jaksoa, laukaise sitten (oletus)
- `BOTH`: Laukaise heti ja hiljaisten jaksojen jälkeen

## Vaihtoehtojen yhdistäminen {#options-merging}

Yhdistä tapahtumakonfiguraatiot eri lähteistä käyttämällä `mergeWith()`. Perusvaihtoehdot tarjoavat yleistä tietoa kaikille tapahtumille, kun taas erityiset vaihtoehdot lisäävät erikoiskonfiguraatiota. Myöhemmät vaihtoehdot ohittavat ristiriitaiset asetukset.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Vuorovaikutus slotien kanssa {#interacting-with-slots}

Web-komponentit käyttävät usein slotteja, joiden avulla kehittäjät voivat määritellä komponentin rakenteen ulkopuolelta. Slot on paikkamerkki web-komponentissa, joka voidaan täyttää sisällöllä, kun komponenttia käytetään. `ElementComposite`-luokan yhteydessä slotit tarjoavat tavan mukauttaa sisältöä komponentissa. Seuraavat menetelmät on tarjottu kehittäjien vuorovaikutuksen ja slotien manipuloinnin mahdollistamiseksi:

1. **`findComponentSlot()`**: Tätä menetelmää käytetään etsimään tiettyä komponenttia kaikkien komponenttien slotista. Se palauttaa nimen slotille, johon komponentti kuuluu. Jos komponenttia ei löydy mistään slotista, palautetaan tyhjää merkkijonoa.

2. **`getComponentsInSlot()`**: Tämä menetelmä noutaa luettelon komponenteista, jotka on määrättynä tiettyyn slotin komponenttijärjestelmässä. Valinnaisesti voit välittää tietyn luokan tyypin suodattaaksesi menetelmän tuloksia.

3. **`getFirstComponentInSlot()`**: Tämä menetelmä on tarkoitettu noutamaan ensimmäinen komponentti, joka on määrättynä slotissa. Valinnaisesti voit välittää tietyn luokan tyypin suodattaaksesi tämän menetelmän tuloksia.

Myös `add()`-menetelmää voidaan käyttää `String`-parametrin kanssa halutun slotin määrittämiseksi, johon lisätään annettu komponentti.

Nämä vuorovaikutukset mahdollistavat kehittäjille web-komponenttien hyödyntämisen tarjoamalla puhtaan ja suoraviivaisen API:n slotien, ominaisuuksien ja tapahtumien käsittelemiseksi `ElementComposite`-luokassa.
