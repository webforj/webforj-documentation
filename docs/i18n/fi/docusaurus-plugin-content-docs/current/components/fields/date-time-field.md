---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 1214ec1391242fb6b3ff7f60664a6f79
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

`DateTimeField`-komponentti antaa käyttäjien syöttää sekä päivämäärän että ajan yhdellä kentällä, kattaen vuoden, kuukauden, päivän, tunnit ja minuutit. Se validoi syötteen tarkkuuden ja voi esittää päivämäärä-aikavalitsin valinnan helpottamiseksi.

<!-- INTRO_END -->

## Käyttäminen `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yhteisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki luo nimetty `DateTimeField`-kentän lähtöpäivämäärän ja -ajan valitsemiseksi.

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## Käytännöt {#usages}

`DateTimeField` on parhaiten käytössä tilanteissa, joissa sekä päivämäärän **että** ajan tavoittaminen tai näyttäminen on olennaista sovelluksellesi. Tässä on joitakin esimerkkejä siitä, milloin käyttää `DateTimeField`:iä:

1. **Tapahtumien aikatauluttaminen ja kalenterit**: Anna käyttäjien aikatauluttaa tapahtumia, varata aikaisia ja hallita kalentereitaan tehokkaasti antamalla heille yksi komponentti, joka sallii päivämäärän ja ajan valinnan.
<!-- vale off -->
2. **Sisään- ja uloskirjautuminen**: Helpota käyttäjän sisään- ja uloskirjautumisaikojen valintaa, kun ajanjakso voi kestää useita päiviä.
<!-- vale on -->
3. **Datan lokitus ja aikaleimat**: Hyödynnä `DateTimeField`-kenttiä sovelluksissa, jotka liittyvät tapahtumien tai käyttäjän syöttämien tietojen päivämäärän ja ajan tallentamiseen.

4. **Tehtävien hallinta ja määräajat**: `DateTimeField`-kentät ovat arvokkaita sovelluksissa, jotka liittyvät tehtävien hallintaan tai määräaikojen asettamiseen, joissa sekä päivämäärä että aika ovat tärkeitä tarkan aikataulutuksen kannalta.

## Kenttäarvo (`LocalDateTime`) {#field-value-localdatetime}

Sisäisesti `DateTimeField`-komponentti edustaa arvoaan käyttäen `LocalDateTime`-objektia `java.time`-paketista. Tämä tarjoaa tarkan hallinnan sekä syötteen päivämäärä- että aikojaosien osalta.

Kun **asiakkaan puolen** arvo esitetään käyttäjän selainlokkaus (esim. päivämäärä- ja aikatavat, jotka vastaavat paikallisia käytäntöjä), **jäljitetty** arvo noudattaa tiukkaa ja ennustettavaa rakennetta: **`yyyy-MM-ddTHH:mm:ss`**.

### Arvon saaminen ja asettaminen {#getting-and-setting-the-value}

Hanki nykyinen arvo käyttämällä `getValue()`-menetelmää:

```java
LocalDateTime value = dateTimeField.getValue();
```

Aseta ohjelmallisesti arvo käyttämällä `setValue()`-menetelmää:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Käyttäminen `setText()` {#using-settext}

Jos haluat asettaa arvon raakatekstin avulla, sen on noudatettava tarkkaa muotoa `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // kelvollinen

dateTimeField.setText("24-04-27T14:30:00"); // kelvoton
```

:::warning
 Kun käytät `setText()`-menetelmää, jos komponentti ei voi jäsentää syötteitä muodossa `yyyy-MM-ddTHH:mm:ss`, heitetään `IllegalArgumentException`.
:::

## Staattiset työkalut {#static-utilities}

DateTimeField-luokka tarjoaa myös seuraavat staattiset utiliteetti-metodit:

- `fromDateTime(String dateTimeAsString)`: Muuntaa päivämäärä- ja aikatiedoston merkkijonon muodossa `yyyy-MM-ddTHH:mm:ss` `LocalDateTime`-objektiksi, jota voidaan sitten käyttää tämän luokan kanssa tai muualla.

- `toDateTime(LocalDateTime dateTime)`: Muuntaa `LocalDateTime`-objektin päivämäärä- ja aikamerkkijonoksi muodossa `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)`: Tarkistaa, onko annettu merkkijono kelvollinen `yyyy-MM-ddTHH:mm:ss`-päivämäärä ja aika. Tämä palauttaa totuusarvon true, jos näin on, muuten false.

## Minimi ja maksimiarvo {#min-and-max-value}

### Minimiväriarvo {#the-min-value}

Jos komponenttiin syötetty arvo on aikaisempi kuin määritetty minimiaikaleima, komponentti epäonnistuu rajoitteen validoinnissa. Kun sekä minimi- että maksimiarvot on asetettu, minimiväriarvon on oltava aikaleima, joka on sama tai aikaisempi kuin maksimiväriarvo.

```java
// Aseta sallittu minimiaikaleima: 1. tammikuuta 2023 klo 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### Maksimiväriarvo {#the-max-value}

Jos komponenttiin syötetty arvo on myöhempi kuin määritetty maksimiaikaleima, komponentti epäonnistuu rajoitteen validoinnissa. Kun sekä minimi- että maksimiarvot on asetettu, maksimiväriarvon on oltava aikaleima, joka on sama tai myöhempi kuin minimiväriarvo.

```java
// Aseta sallittu maksimiaikaleima: 31. joulukuuta 2023 klo 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Parhaat käytännöt {#best-practices}

Jotta voit varmistaa optimaalisen käyttäjäkokemuksen käyttäessäsi `DateTimeField`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

- **Paikallinen päivämääränäyttö**: Paikallisten päivämäärämuotojen ja alueellisten mieltymysten huomioiminen varmistaa, että päivämäärät esitetään käyttäjälle tutussa muodossa.

- **Aikavyöhykkeet**: Jos sovelluksesi käsittelee aikaherkkää tietoa eri aikavyöhykkeillä, harkitse aikavyöhykkeen valinnan lisäämistä päivämääräkentän yhteyteen varmistaaksesi tarkan päivämäärä-ja aik esityksen.

- **Saavutettavuus**: Hyödynnä `DateTimeField`-komponenttia saavutettavuus huomioon ottaen. Varmista, että se täyttää saavutettavuusstandardit, kuten että tarjoat asianmukaiset etikettit ja se on yhteensopiva avustavien teknologioiden kanssa.

- **Nykyisen päivämäärän automaattinen täyttö**: Harkitse mahdollisuuden tarjoamista nykyisen päivämäärän ja ajan automaattiseen täyttämiseen oletusarvoksi päivämäärä- ja aikakentässä, jos se on sovelluksesi käyttötapaukselle sopivaa.
