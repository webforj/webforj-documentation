---
sidebar_position: 4
title: Element Composite
slug: element_composite
_i18n_hash: 88eca7b854822f9d78ac20731ac5a857
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka toimii monipuolisena perustana yhdistelmien hallinnassa webforJ-sovelluksissa. Sen ensisijainen tarkoitus on helpottaa HTML-elementtien, joita edustaa `Element`-luokka, vuorovaikutusta tarjoamalla rakenteellisen tavan käsitellä ominaisuuksia, attribuutteja ja tapahtumakuuntelijoita. Se mahdollistaa elementtien toteuttamisen ja uudelleenkäytön sovelluksessa. Käytä `ElementComposite`-luokkaa toteuttaessasi Web Komponentteja webforJ-sovelluksissa.

Kun käytät `ElementComposite`-luokkaa, `getElement()`-metodi antaa sinulle pääsyn taustalla olevaan `Element`-komponenttiin. Samoin `getNodeName()`-metodi kertoo kyseisen solmun nimen DOM:issa.

:::tip
On mahdollista tehdä kaikki pelkästään `Element`-luokalla ilman `ElementComposite`-luokan käyttöä. Kuitenkin `ElementComposite`:ssa tarjotut metodit antavat käyttäjille tavan hyödyntää jo tehtyä työtä.
:::

Käymme tässä oppaassa läpi [Shoelace QR-koodi web-komponenttia](https://shoelace.style/components/qr-code) käyttäen `ElementComposite`-luokkaa.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Ominaisuus- ja attribuutitiedot {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit web-komponenteissa edustavat komponentin tilaa. Niitä käytetään usein tietojen tai kokoonpanon hallintaan. `ElementComposite`-luokka tarjoaa kätevän tavan työskennellä ominaisuuksien ja attribuuttejen kanssa.

Ominaisuudet ja attribuutit voidaan ilmoittaa ja alustaa `PropertyDescriptor`-jäseninä kirjoitettavassa `ElementComposite`-luokassa ja niitä voidaan käyttää koodissa. Ominaisuuksien ja attribuuttien määrittelemiseksi käytä `set()`-metodia asettaaksesi ominaisuuden arvon. Esimerkiksi `set(PropertyDescriptor<V> property, V value)` asettaa ominaisuuden määriteltyyn arvoon.

:::info
Ominaisuuksiin päästään ja niitä manipuloidaan sisäisesti komponentin koodissa, eikä niiden arvo näy DOM:ssa. Atributit sen sijaan ovat osa komponentin ulkoista rajapintaa ja niitä voidaan käyttää tietojen välittämiseen komponentille ulkopuolelta, mikä tarjoaa tavan ulkoisten elementtien tai skriptien konfiguroida komponenttia.
:::

```java
// Esimerkki ominaisuudesta nimeltä TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Esimerkki attribuutista nimeltä VALUE ElementComposite-luokassa
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "My Title");
set(VALUE, "My Value");
```

Ominaisuuden asettamisen lisäksi käytä `get()`-metodia `ElementComposite`-luokassa päästäksesi käsiksi ja lukiaksesi ominaisuuksia. `get()`-metodille voidaan antaa valinnainen `boolean`-arvo, joka on oletuksena epätosi, määrittämään, tuleeko metodin tehdä matka asiakkaalle saadakseen arvo. Tämä vaikuttaa suorituskykyyn, mutta voi olla tarpeen, jos ominaisuutta voidaan muuttaa pelkästään asiakkaalla.

Metodille voidaan myös antaa `Type`, joka määrää, mihin haettu tulos muunnetaan.

:::tip
Tätä `Type`:a ei tarvitse käyttää, ja se lisää ylimääräisen tason määrittelyä arvoa haettaessa.
:::

```java
// Esimerkki ominaisuudesta nimeltä TITLE ElementComposite-luokassa
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Alla olevassa demo-kohteessa ominaisuuksia on lisätty QR-koodille web-komponentin dokumentaation perusteella. Tämän jälkeen on toteutettu metodeja, jotka mahdollistavat käyttäjien eri ominaisuuksien saamisen ja asettamisen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Tapahtumarekisteröinti {#event-registration}

Tapahtumat ovat tärkeä osa web-komponentteja, mahdollistaen viestinnän sovelluksen eri osien välillä. `ElementComposite`-luokka yksinkertaistaa tapahtumien rekisteröintiä ja käsittelyä. Rekisteröidäksesi tapahtumakuuntelijan, käytä `addEventListener()`-metodia rekisteröidäksesi tapahtumakuuntelijoita tiettyihin tapahtumatyyppiin. Määritä tapahtumaluokka, kuuntelija ja valinnaiset tapahtumaoptioit.

```java
// Esimerkki: Klikkaustapahtuman kuuntelijan lisääminen
addEventListener(ClickEvent.class, event -> {
    // Käsittele klikkaustapahtuma
});
```

:::info
`ElementComposite`-tapahtumat poikkeavat `Element`-tapahtumista siten, että tämä ei salli minkään luokan, vaan vain määriteltyjä `Event`-luokkia.
:::

Alla olevassa esittelyssä on luotu klikkaustapahtuma ja lisätty se QR-koodikomponenttiin. Tämä tapahtuma, kun se laukaistaan, näyttää "X"-koordinaatin hiirestä komponenttia napsautettaessa, joka annetaan Java-tapahtumana tietona. Tämän jälkeen on toteutettu metodi, joka mahdollistaa käyttäjän pääsyn tähän tietoon, ja näin se näytetään sovelluksessa.
<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## Vuorovaikutus Slottien kanssa {#interacting-with-slots}

Web-komponentit käyttävät usein slottia, jotta kehittäjät voivat määrittää komponentin rakenteen ulkopuolelta. Slot on paikkaholdari web-komponentissa, joka voidaan täyttää sisällöllä, kun käytetään komponenttia. `ElementComposite`-luokan kontekstissa slotit tarjoavat tavan mukauttaa komponentin sisältöä. Seuraavat metodit on tarjottu, jotta kehittäjät voivat vuorovaikuttaa slottien kanssa ja manipuloida niitä:

1. **`findComponentSlot()`**: Tätä metodia käytetään etsimään tiettyä komponenttia kaikista sloteista komponenttijärjestelmässä. Se palauttaa sen slotin nimen, johon komponentti on sijoitettu. Jos komponenttia ei löydy mistään slotista, palautetaan tyhjää merkkijonoa.

2. **`getComponentsInSlot()`**: Tämä metodi palauttaa luettelon komponentteista, jotka on määritelty tiettyyn slottiin komponenttijärjestelmässä. Valinnaisesti voit antaa tietyn luokan suodattaaksesi menetelmän tuloksia.

3. **`getFirstComponentInSlot()`**: Tämä metodi on suunniteltu hakemaan ensimmäinen komponentti, joka on määritelty slotilla. Valinnaisesti voit antaa tietyn luokan suodattaaksesi tämän metodin tuloksia.

On myös mahdollista käyttää `add()`-metodia `String`-parametrilla määrittääksesi halutun slotin, johon lisätä välitetty komponentti.

Nämä vuorovaikutukset mahdollistavat kehittäjille web-komponenttien hyödyntämisen tarjoamalla puhtaan ja yksinkertaisen rajapinnan slottien, ominaisuuksien ja tapahtumien käsittelemiseksi `ElementComposite`-luokassa.
