---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: e90e93f7db172a33b2ce205bfd6a6b3c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

`DateTimeField`-komponentti mahdollistaa käyttäjien syöttää sekä päivämäärän että kellonajan yhdellä kentällä, kattaen vuoden, kuukauden, päivän, tunnit ja minuutit. Se validoi syötteen tarkkuuden ja voi esittää päivämäärä-aikavalitsimen valinnan helpottamiseksi.

<!-- INTRO_END -->

## Using `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yhteiset ominaisuudet kaikille kenttäkomponenteille. Seuraava esimerkki luo merkitty `DateTimeField`-kentän lähtöpäivämäärän ja -ajan valitsemiseksi.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Usages {#usages}

`DateTimeField`-komponenttia on parasta käyttää tilanteissa, joissa sekä päivämäärän **että** kellonajan tallentaminen tai esittäminen on tärkeää sovelluksellesi. Tässä on joitakin esimerkkejä siitä, milloin `DateTimeField`-komponenttia tulisi käyttää:

1. **Tapahtumien aikataulutus ja kalenterit**: Anna käyttäjien tehokkaasti aikatauluttaa tapahtumia, varata aikoja ja hallita kalentereitaan tarjoamalla heille yksittäinen komponentti, jonka avulla he voivat valita päivämäärän ja ajan.
<!-- vale off -->
2. **Sisään- ja uloskirjautuminen**: Helpota käyttäjän sisään- ja uloskirjautumisaikojen valintaa, kun jakso voi kestää useita päiviä.
<!-- vale on -->
3. **Tietojen lokitus ja aikaleimat**: Hyödynnä `DateTimeField`-komponentteja sovelluksissa, joissa tallennetaan päivämäärä ja aika, jolloin tapahtumat tapahtuvat tai kun käyttäjä lähettää tietoja.

4. **Tehtävien hallinta ja määräajat**: `DateTimeField`-komponentit ovat arvokkaita sovelluksissa, jotka liittyvät tehtävien hallintaan tai määräaikojen asettamiseen, joissa sekä päivämäärä että aika ovat tärkeitä tarkassa aikataulutuksessa.

## Field value (`LocalDateTime`) {#field-value-localdatetime}

Sisäisesti `DateTimeField`-komponentti edustaa arvoaan käyttäen `LocalDateTime`-objektia `java.time`-paketista. Tämä mahdollistaa tarkan hallinnan sekä päivämäärän että ajan syötekomponenteista.

Vaikka **asiakaspään** arvo näkyy käyttäjän selaimen alueen mukaan (esim. päivämäärä- ja aikamuodot, jotka vastaavat paikallisia käytäntöjä), **analysoitu** arvo seuraa tiukkaa ja ennakoitavaa rakennetta: **`yyyy-MM-ddTHH:mm:ss`**.

### Getting and setting the value {#getting-and-setting-the-value}

Nykyisen arvon hakemiseen käytä `getValue()`-metodia:

```java
LocalDateTime value = dateTimeField.getValue();
```

Arvon ohjelmalliseen asettamiseen käytä `setValue()`-metodia:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Using `setText()` {#using-settext}

Jos haluat asettaa arvon raakana merkkijonona, sen on noudatettava tarkkaa muotoa `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // voimassa

dateTimeField.setText("24-04-27T14:30:00"); // ei voimassa
```

:::warning
 Kun käytät `setText()`-metodia, `IllegalArgumentException` heitetään, jos komponentti ei voi analysoida syötettä muodossa `yyyy-MM-ddTHH:mm:ss`.
:::

## Static utilities {#static-utilities}

`DateTimeField`-luokka tarjoilee myös seuraavat staattiset apumetodit:

- `fromDateTime(String dateTimeAsString)`: Muuntaa päivämäärä- ja aikamerkkijonon muodossa `yyyy-MM-ddTHH:mm:ss` `LocalDateTime`-objektiksi, jota voidaan käyttää tämän luokan kanssa tai muualla.

- `toDateTime(LocalDateTime dateTime)`: Muuntaa `LocalDateTime`-objektin päivämäärä- ja aikamerkkijonoksi muodossa `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)`: Tarkistaa, onko annettu merkkijono voimassa oleva `yyyy-MM-ddTHH:mm:ss` päivämäärä ja aika. Tämä palauttaa boolean-arvon true, jos näin on, ja false muuten.

## Min and max value {#min-and-max-value}

### The min value {#the-min-value}

Jos komponenttiin syötetty arvo on aikaisempi kuin määritetty vähimmäisaikatunnus, komponentti epäonnistuu rajoitteen validoinnissa. Kun sekä min- että max-arvot on asetettu, min-arvon on oltava aikaleima, joka on sama tai aikaisempi kuin max-arvo.

```java
// Aseta vähimmäis sallittu aikaleima: 1. tammikuuta 2023 klo 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### The max value {#the-max-value}

Jos komponenttiin syötetty arvo on myöhempi kuin määritetty enimmäisaikatunnus, komponentti epäonnistuu rajoitteen validoinnissa. Kun sekä min- että max-arvot on asetettu, max-arvon on oltava aikaleima, joka on sama tai myöhempi kuin min-arvo.

```java
// Aseta enimmäis sallittu aikaleima: 31. joulukuuta 2023 klo 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Best practices {#best-practices}

Parhaan käyttäjäkokemuksen varmistamiseksi `DateTimeField`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

- **Paikallinen päivämääräesitys**: Paikallista päivämäärämuoto ja ota huomioon alueelliset mieltymykset jotta päivämäärät esitetään käyttäjälle tutussa muodossa.

- **Sisällytä aikavyöhykkeet**: Jos sovelluksesi käsittelee aikaherkkiä tietoja eri aikavyöhykkeillä, harkitse aikavyöhykkeen valintaa päivämääräkentän rinnalla varmistaaksesi tarkka päivämäärä-aikakuvasto.

- **Esteettömyys**: Käytä `DateTimeField`-komponenttia esteettömyys mielessä. Varmista, että se täyttää esteettömyysvaatimukset, kuten oikeiden tunnisteiden tarjoamisen ja yhteensopivuuden apuvälineiden kanssa.

- **Automaattinen nykyisen päivämäärän täydentäminen**: Harkitse mahdollisuutta automaattisesti täydentää nykyinen päivämäärä ja aika oletusarvoksi päivämäärä-aikakenttään, jos se on sovelluksesi käyttötapaukselle sopivaa.
