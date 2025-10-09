---
sidebar_position: 1
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 6e201040e3dfd4be12037094eb9e978e
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka toimii monipuolisena perustana komponenttien hallintaan webforJ-sovelluksissa. Sen pääasiallinen tarkoitus on helpottaa HTML-elementtien, joita edustaa `Element`-luokka, vuorovaikutusta tarjoamalla rakenteellisen lähestymistavan ominaisuuksien, attribuuttien ja tapahtumakuuntelijoiden käsittelyyn. Se mahdollistaa elementtien toteuttamisen ja uudelleenkäytön sovelluksessa. Käytä `ElementComposite`-luokkaa, kun toteutat Web-komponentteja webforJ-sovelluksissa.

Kun käytät `ElementComposite`-luokkaa, `getElement()`-metodi antaa sinulle pääsyn taustalla olevaan `Element`-komponenttiin. Samoin `getNodeName()`-metodi antaa sinulle sen solmun nimen DOM:ssa.

:::tip
On mahdollista tehdä kaikki pelkästään `Element`-luokalla, ilman `ElementComposite`-luokan käyttöä. Kuitenkin `ElementComposite`:ssa tarjotut metodit tarjoavat käyttäjille tavan hyödyntää jo tehtyä työtä.
:::

Tämä opas havainnollistaa, kuinka toteuttaa [Shoelace QR-koodin web-komponentti](https://shoelace.style/components/qr-code) käyttäen `ElementComposite`-luokkaa.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Ominaisuus- ja attribuuttikuvastot {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit web-komponenteissa edustavat komponentin tilaa. Niitä käytetään usein tietojen tai konfiguraation hallintaan. `ElementComposite`-luokka tarjoaa kätevän tavan työskennellä ominaisuuksien ja attribuuttien kanssa.

Ominaisuudet ja attribuutit voidaan julistaa ja alustaa `PropertyDescriptor`-jäseninä kirjoitetussa `ElementComposite`-luokassa, ja niitä voidaan käyttää koodissa. Ominaisuuksien ja attribuuttien määrittämiseksi käytä `set()`-metodia ominaisuuden arvon asettamiseksi. Esimerkiksi `set(PropertyDescriptor<V> property, V value)` asettaa ominaisuuden määritettyyn arvoon.

:::info
Ominaisuudet saavutetaan ja manipuloidaan komponentin koodin sisällä, eivätkä ne näy DOM:ssa. Sen sijaan attribuutit ovat osa komponentin ulkoista rajapintaa ja niitä voidaan käyttää tietojen välittämiseen komponenttiin ulkopuolelta, mikä tarjoaa tavan ulkoisille elementeille tai skripteille määrittää komponenttia.
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

Ominaisuuden asettamisen lisäksi käytä `get()`-metodia `ElementComposite`-luokassa pääsyyn ja lukemiseen ominaisuuksista. `get()`-metodille voidaan antaa valinnainen `boolean`-arvo, joka on oletuksena false, ohjaamaan, tulisiko metodin tehdä matka asiakkaalle arvon hakemiseksi. Tämä vaikuttaa suorituskykyyn, mutta voi olla tarpeen, jos ominaisuutta voidaan muokata pelkästään asiakkaalla.

Metodille voidaan myös antaa `Type`, joka määrää, mihin haettu tulos tulee kastaa.

:::tip
Tätä `Type`:a ei ole erityisesti tarpeellinen, ja se lisää ylimääräisen tarkkuustason, kun data haetaan.
:::

```java
// Esimerkki ominaisuudesta nimeltä TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Alla olevassa demonäytössä on lisätty ominaisuuksia QR-koodille web-komponentin dokumentaation perusteella. Menetelmät on sitten toteutettu, jolloin käyttäjät voivat saada ja asettaa erilaisia ominaisuuksia.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Tapahtumarekisteröinti {#event-registration}

Tapahtumat mahdollistavat viestinnän eri osien välillä webforJ-sovelluksessa. `ElementComposite`-luokka tarjoaa tapahtumankäsittelyn tuella deboucingille, throttlingille, suodatukselle ja mukautetulle tapahtumatietojen keräykselle.

Rekisteröi tapahtumakuuntelijat käyttämällä `addEventListener()`-metodia:

```java
// Esimerkki: Klikkaustapahtuman kuuntelijan lisääminen
addEventListener(ElementClickEvent.class, event -> {
    // Käsittele klikkaustapahtuma
});
```

:::info
`ElementComposite`-tapahtumat ovat erilaisia kuin `Element`-tapahtumat, sillä ne eivät salli minkään luokan käyttöä, vaan vain määriteltyjä `Event`-luokkia.
:::

### Valmiit tapahtumaluokat {#built-in-event-classes}

webforJ tarjoaa valmiita tapahtumaluokkia, joilla on tyypitetty tietojen saatavuus:

- **ElementClickEvent**: Hiiren klikkaustapahtumat koordinaateilla (`getClientX()`, `getClientY()`), painetietoja (`getButton()`), ja modifioivia näppäimiä (`isCtrlKey()`, `isShiftKey()`, jne.)
- **ElementDefinedEvent**: Laukeaa, kun mukautettu elementti on määritelty DOM:ssa ja valmis käytettäväksi
- **ElementEvent**: Perustapahtumaluokka, joka tarjoaa pääsyn raakatapahtumatietoihin, tapahtumatyyppeihin (`getType()`) ja tapahtuman ID:hen (`getId()`)

### Tapahtumapaketit {#event-payloads}

Tapahtumat kuljettavat tietoa asiakkaalta Java-koodiin. Pääset tähän tietoon `getData()`-metodin kautta raakatapahtumatietojen hakemiseen tai käytä tyypitettyjä menetelmiä, kun ne ovat saatavilla valmiissa tapahtumaluokissa. Lisätietoja tapahtumapaketin tehokkaasta käytöstä saat [Tapahtumat-oppaasta](../building-ui/events).

## Mukautetut tapahtumaluokat {#custom-event-classes}

Erityisten tapahtumien käsittelyä varten luo mukautettuja tapahtumaluokkia, joissa on määritetyt paketit käyttäen `@EventName`- ja `@EventOptions`-annotaatioita.

Alla olevassa esimerkissä on luotu klikkaustapahtuma ja lisätty se QR-koodikomponenttiin. Tämä tapahtuma, kun se laukaistaan, näyttää hiiren "X" koordinaatin klikkaushetkellä, joka toimitetaan Java-tapahtumana tietona. Tapahtumalle on sitten toteutettu menetelmä, jonka avulla käyttäjä voi käyttää tätä tietoa, mikä on se, miten se näytetään sovelluksessa.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` antaa sinulle mahdollisuuden mukauttaa tapahtuman käyttäytymistä määrittämällä, mitä tietoa kerätään, milloin tapahtumat laukaistaan ja miten niitä käsitellään. Tässä on kattava koodipätkä, joka näyttää kaikki konfigurointivaihtoehdot:

```java
ElementEventOptions options = new ElementEventOptions()
    // Kerää mukautettuja tietoja asiakkaalta
    .addData("query", "component.value")
    .addData("timestamp", "Date.now()")
    .addData("isValid", "component.checkValidity()")
    
    // Suorita JavaScript ennen tapahtuman laukaustumista
    .setCode("component.classList.add('processing');")
    
    // Laula vain, jos ehdot täyttyvät
    .setFilter("component.value.length >= 2")
    
    // Viivästytä suorittamista, kunnes käyttäjä lopettaa kirjoittamisen (300ms)
    .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Suorituskyvyn hallinta {#performance-control}

Hallitse, milloin ja kuinka usein tapahtumat laukeavat:

**Deboucing** viivästyttää suorittamista, kunnes toiminta loppuu:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Odota 300ms viimeisestä tapahtumasta
```

**Throttling** rajoittaa suorituskyvyn taajuutta:

```java
options.setThrottle(100); // Laukaise enintään kerran 100ms
```

Käytettävissä olevat deboucing-vaiheet:

- `LEADING`: Laukaise heti, odota sitten
- `TRAILING`: Odota hiljaista jaksoa ja laukaise sitten (oletusarvo)
- `BOTH`: Laukaise heti ja hiljaisen jakson jälkeen

## Options merging {#options-merging}

Yhdistä tapahtumakonfiguraatiot eri lähteistä käyttämällä `mergeWith()`. Perusvaihtoehdot tarjoavat yleisiä tietoja kaikille tapahtumille, kun taas erityiset vaihtoehdot lisäävät mukautetun konfiguroinnin. Myöhemmät vaihtoehdot korvaavat ristiriitaiset asetukset.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Vuorovaikutus slotien kanssa {#interacting-with-slots}

Web-komponentit käyttävät usein sloteja, jotta kehittäjät voivat määrittää komponentin rakenteen ulkopuolelta. Slot on paikkamerkki web-komponentin sisällä, joka voidaan täyttää sisällöllä komponenttia käytettäessä. `ElementComposite`-luokan yhteydessä slotit tarjoavat tavan mukauttaa sisältöä komponentin sisällä. Seuraavat menetelmät on tarjottu kehittäjille vuorovaikutukseen ja slotien manipulointiin:

1. **`findComponentSlot()`**: Tätä metodia käytetään etsimään tietty komponentti kaikista sloteista komponenttijärjestelmässä. Se palauttaa sen slotin nimen, jossa komponentti sijaitsee. Jos komponenttia ei löydy mistään slotista, palautetaan tyhjät merkkijonot.

2. **`getComponentsInSlot()`**: Tämä metodi hakee listan komponenteista, jotka on osoitettu tiettyyn slotiin komponenttijärjestelmässä. Valinnaisesti voit antaa tietyn luokan suodattaaksesi menetelmän tuloksia.

3. **`getFirstComponentInSlot()`**: Tämä metodi on suunniteltu hakemaan ensimmäinen slotille osoitettu komponentti. Valinnaisesti voit antaa tietyn luokan suodattaaksesi tämän metodin tuloksia.

On myös mahdollista käyttää `add()`-metodia, jossa on `String`-parametri, määritelläksesi halutun slotin, johon haluat lisätä välitetyn komponentin.

Nämä vuorovaikutukset mahdollistavat kehittäjille web-komponenttien voiman hyödyntämisen tarjoamalla puhtaan ja suoraviivaisen API:n slotien, ominaisuuksien ja tapahtumien käsittelyyn `ElementComposite`-luokassa.
