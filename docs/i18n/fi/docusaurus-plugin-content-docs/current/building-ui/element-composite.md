---
sidebar_position: 4
title: Element Composite
slug: element_composite
_i18n_hash: 78629dd08e77cbd5f111aabb094f8db8
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka toimii monipuolisena perustana yhdistettyjen elementtien hallinnassa webforJ-sovelluksissa. Sen ensisijainen tarkoitus on helpottaa HTML-elementtien, joita edustaa `Element`-luokka, vuorovaikutusta tarjoamalla rakenteellisen tavan käsitellä ominaisuuksia, attribuutteja ja tapahtumakuuntelijoita. Se mahdollistaa elementtien toteuttamisen ja uudelleenkäytön sovelluksessa. Käytä `ElementComposite`-luokkaa toteuttaessasi Web-komponentteja webforJ-sovelluksissa.

Kun käytät `ElementComposite`-luokkaa, `getElement()`-menetelmä antaa sinulle pääsyn taustalla olevaan `Element`-komponenttiin. Samoin `getNodeName()`-menetelmä antaa sinulle sen solmun nimen DOM:ssa.

:::tip
On mahdollista tehdä kaikki pelkästään `Element`-luokan avulla ilman `ElementComposite`-luokkaa. Kuitenkin `ElementComposite`:ssa tarjotut menetelmät antavat käyttäjille tavan uudelleenkäyttää tehtyä työtä.
:::

Käymme tässä oppaassa läpi [Shoelace QR-koodin web-komponentin](https://shoelace.style/components/qr-code) toteuttamista `ElementComposite`-luokkaa käyttäen.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Ominaisuus- ja attribuutitiedot {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit web-komponenteissa kuvaavat komponentin tilaa. Niitä käytetään usein datan tai konfiguraation hallintaan. `ElementComposite`-luokka tarjoaa kätevän tavan työskennellä ominaisuuksien ja attribuuttien kanssa.

Ominaisuudet ja attribuutit voidaan määrätä ja alustaa `PropertyDescriptor`-jäseninä kirjoitettavassa `ElementComposite`-luokassa, ja niitä voidaan sitten käyttää koodissa. Määrittääksesi ominaisuuksia ja attribuutteja, käytä `set()`-menetelmää asettaaksesi ominaisuuden arvo. Esimerkiksi `set(PropertyDescriptor<V> property, V value)` asettaa ominaisuuden määritettyyn arvoon.

:::info
Ominaisuuksia päästään käsittelemään ja muokkaamaan sisäisesti komponentin koodissa, eikä niillä ole vaikutusta DOM:ssa. Toisaalta attribuutit ovat osa komponentin ulkoista rajapintaa ja niitä voidaan käyttää tietojen siirtämiseen komponenttiin ulkopuolelta, jolloin ulkoisilla elementeillä tai skripteillä on mahdollisuus konfiguroida komponenttia.
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

Ominaisuuden asettamisen ohella voit hyödyntää `get()`-menetelmää `ElementComposite`-luokassa pääset ja luet ominaisuuksia. `get()`-menetelmä voidaan syöttää vaihtoehtoinen `boolean`-arvo, joka on oletusarvoisesti epätosi, määrittämään, tulisiko menetelmän tehdä pyyntö asiakkaalle arvon hakemiseksi. Tämä vaikuttaa suorituskykyyn, mutta voi olla tarpeellista, jos ominaisuus voidaan muuttaa pelkästään asiakkaalla.

Menetelmälle voidaan myös syöttää `Type`, joka määrittelee, mihin tuloksia muotoillaan.

:::tip
Tämä `Type` ei ole välttämätön, ja se lisää ylimääräisen tason tarkennusta, kun dataa noudetaan.
:::

```java
// Esimerkki ominaisuudesta nimeltä TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Alla olevassa demossa ominaisuuksia on lisätty QR-koodille web-komponentin dokumentaation perusteella. Menetelmät on sitten toteutettu, jolloin käyttäjät voivat saada ja asettaa erilaisia toteutettuja ominaisuuksia.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Tapahtumien rekisteröinti {#event-registration}

Tapahtumat ovat olennainen osa web-komponentteja, jotka mahdollistavat viestinnän sovelluksen eri osien välillä. `ElementComposite`-luokka helpottaa tapahtumien rekisteröintiä ja käsittelyä. Rekisteröidäksesi tapahtumakuuntelijan, käytä `addEventListener()`-menetelmää rekisteröidäksesi tapahtumakuuntelijoita tietyille tapahtumatyypeille. Määritä tapahtumaluokka, kuuntelija ja valinnaiset tapahtumavalinnat.

```java
// Esimerkki: Klikkaa tapahtumakuuntelijan lisääminen
addEventListener(ClickEvent.class, event -> {
    // Käsittele klikkaustapahtuma
});
```

:::info
`ElementComposite`-tapahtumat eroavat `Element`-tapahtumista siinä, että tämä ei salli mitä tahansa luokkaa, vaan vain määritettyjä `Event`-luokkia.
:::

Alla olevassa esityksessä on luotu klikkaustapahtuma, joka on sitten lisätty QR-koodikomponenttiin. Tämä tapahtuma, kun se laukaistaan, näyttää komponenttia napsautettaessa hiiren "X"-koordinaatin, joka annetaan Java-tapahtumalle datana. Metodi on sitten toteutettu, jotta käyttäjä voi päästä käsiksi tähän dataan, joka on tapa, jolla se esitetään sovelluksessa.
<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## Vuorovaikutus slotien kanssa {#interacting-with-slots}

Web-komponentit käyttävät usein slotteja, jotka mahdollistavat kehittäjien määrittää komponentin rakenteen ulkopuolelta. Slot on paikalla oleva tila web-komponentissa, joka voidaan täyttää sisällöllä komponentin käytön yhteydessä. `ElementComposite`-luokan kontekstissa slotit tarjoavat tavan mukauttaa sisältöä komponentissa. Seuraavat menetelmät on tarjottu kehittäjien vuorovaikutukseen ja slotien manipulointiin:

1. **`findComponentSlot()`**: Tätä menetelmää käytetään etsimään tiettyä komponenttia kaikkien slotien joukosta komponenttijärjestelmässä. Se palauttaa slotin nimen, jossa komponentti sijaitsee. Jos komponenttia ei löydy mistään slotista, palautuu tyhjää merkkijonoa.

2. **`getComponentsInSlot()`**: Tämä menetelmä noutaa luettelon komponentteista, jotka on määritetty tiettyyn slottiin komponenttijärjestelmässä. Valinnaisesti voit syöttää tietyn luokkatyypin suodattaaksesi menetelmän tuloksia.

3. **`getFirstComponentInSlot()`**: Tätä menetelmää käytetään ensimmäisen komponentin noutamiseen, joka on määritetty slottiin. Valinnaisesti voit syöttää tietyn luokkatyypin suodattaaksesi tämän menetelmän tuloksia.

On myös mahdollista käyttää `add()`-menetelmää, jossa on `String`-parametri määrittääksesi halutun slotin, johon lisätään siirretty komponentti.

Nämä vuorovaikutukset mahdollistavat kehittäjille web-komponenttien voiman hyödyntämisen tarjoamalla selkeän ja suoraviivaisen API:n slotten, ominaisuuksien manipulointiin ja tapahtumien käsittelyyn `ElementComposite`-luokassa.
