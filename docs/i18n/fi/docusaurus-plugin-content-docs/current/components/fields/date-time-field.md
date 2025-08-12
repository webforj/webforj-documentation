---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
sidebar_class_name: updated-content
_i18n_hash: dd6fe3e8a737f5b016f92629d9767dbb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

`DateTimeField`-komponentti on suunniteltu mahdollistamaan käyttäjien syöttää sekä päivämäärä että aika. Tämä sisältää vuoden, kuukauden ja päivän määrittämisen sekä ajan tunteina ja minuutteina. Se tarjoaa käyttäjille mahdollisuuden validoida syötteensä tarkkuuden varmistamiseksi tai hyödyntää erillistä päivämäärä-aikavalintainterfacea valintaprosessin sujuvoittamiseksi.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Käyttötapaukset {#usages}

`DateTimeField` on parhaiten käytössä tilanteissa, joissa sekä päivämäärän **että** ajan kaappaaminen tai näyttäminen on olennaista sovelluksellesi. Tässä on joitakin esimerkkejä siitä, milloin `DateTimeField`-komponenttia tulisi käyttää:

1. **Tapahtumien aikatauluttaminen ja kalenterit**: Anna käyttäjien tehokkaasti aikatauluttaa tapahtumia, varata tapaamisia ja hallita kalentereitaan tarjoamalla heille yksi komponentti, joka mahdollistaa päivämäärän ja ajan valinnan.
<!-- vale off -->
2. **Sisään- ja uloskirjautuminen**: Helpota käyttäjien valintaa sisään- ja uloskirjautumisaikojen osalta, kun aikaväli voi kattaa useita päiviä.
<!-- vale on -->
3. **Tietojen lokitus ja aikaleimat**: Hyödynnä `DateTimeField`-komponentteja sovelluksissa, jotka sisältävät tapahtumien päivämäärän ja ajan tallentamisen tai kun käyttäjä lähettää tietoja. 

4. **Tehtävien hallinta ja määräajat**: `DateTimeField`-komponentit ovat arvokkaita sovelluksissa, jotka sisältävät tehtävien hallintaa tai määräaikojen asettamista, joissa sekä päivämäärä että aika ovat tärkeitä tarkassa aikataulutuksessa.

## Kentän arvo (`LocalDateTime`) {#field-value-localdatetime}

Sisäisesti `DateTimeField`-komponentti edustaa arvoaan käyttäen `LocalDateTime`-objektia `java.time`-paketista. Tämä tarjoaa tarkkaa hallintaa sekä syötteen päivämäärä- että aikasäännöistä.

Vaikka **asiakaspään** arvo renderöidään käyttäjän selaimen alueen mukaan (esim. päivämäärä- ja aikamuodot, jotka vastaavat paikallisia käytäntöjä), **parsing**-arvo seuraa tiukkaa ja ennustettavaa rakennetta: **`yyyy-MM-ddTHH:mm:ss`**.

### Arvon hakeminen ja asettaminen {#getting-and-setting-the-value}

Jotta saat nykyisen arvon, käytä `getValue()`-metodia:

```java
LocalDateTime value = dateTimeField.getValue();
```

Aseta arvo ohjelmallisesti käyttämällä `setValue()`-metodia:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### `setText()`-käyttäminen {#using-settext}

Jos haluat asettaa arvon raakana merkkijonona, sen on seurattava tarkkaa muotoa `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // voimassa

dateTimeField.setText("24-04-27T14:30:00"); // ei voimassa
```

:::warning
 Käyttäessäsi `setText()`-metodia `IllegalArgumentException` heitetään, jos komponentti ei voi analysoida syötettä muodossa `yyyy-MM-ddTHH:mm:ss`.
:::

## Staattiset apuohjelmat {#static-utilities}

DateTimeField-luokka tarjoaa myös seuraavat staattiset apuohjelmat:

- `fromDateTime(String dateTimeAsString)`: Muunna päivämäärä- ja aikatieto merkkijonossa muodossa `yyyy-MM-ddTHH:mm:ss` LocalDateTime-objektiksi, jota voidaan sitten käyttää tässä luokassa tai muualla.

- `toDateTime(LocalDateTime dateTime)`: Muunna LocalDateTime-objekti päivämäärä- ja aikatietomuotoon `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)`: Tarkistaa, onko annettu merkkijono voimassa oleva `yyyy-MM-ddTHH:mm:ss` päivämäärä ja aika. Tämä palauttaa totuusarvon true, jos näin on, muuten false.

## Minimi- ja maksimimäärä {#min-and-max-value}

### Minimiarvo {#the-min-value}

Jos komponenttiin syötetty arvo on aikaisempi kuin määritetty vähimmäis aikaleima, komponentti epäonnistuu rajoitusten validoinnissa. Kun sekä minimi- että maksimimäärät on asetettu, vähimmäisarvon on oltava aikaleima, joka on sama tai aikaisempi kuin maksimimäärä.

```java
// Aseta sallittu vähimmäisaikaleima: 1. tammikuuta 2023 klo 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### Maksimimäärä {#the-max-value}

Jos komponenttiin syötetty arvo on myöhempi kuin määritetty enimmäis aikaleima, komponentti epäonnistuu rajoitusten validoinnissa. Kun sekä minimi- että maksimimäärät on asetettu, maksimimäärän on oltava aikaleima, joka on sama tai myöhempi kuin vähimmäisarvo.

```java
// Aseta sallittu enimmäisaikaleima: 31. joulukuuta 2023 klo 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Parhaat käytännöt {#best-practices}

Jotta `DateTimeField`-komponentin käyttö tarjoaisi optimaalisen käyttäjäkokemuksen, harkitse seuraavia parhaita käytäntöjä:

- **Paikallistetun päivämäärän näyttäminen**: Paikallista päivämäärämuoto ja ota huomioon alueelliset mieltymykset varmistaaksesi, että päivämäärät esitetään käyttäjälle tutussa muodossa.

- **Lisää aikavyöhykkeet**: Jos sovelluksesi käsittelee aikaherkkää tietoa eri aikavyöhykkeillä, harkitse aikavyöhykkeen valinnan sisällyttämistä päivämääräkentän yhteyteen, jotta varmistetaan tarkka päivämäärä-aikojen kuvaus.

- **Esteettömyys**: Hyödynnä `DateTimeField`-komponenttia esteettömyys huomioiden. Varmista, että se täyttää esteettömyysstandardit, kuten asianmukaisten etikettien tarjoamisen ja yhteensopivuuden apuvälineiden kanssa.

- **Nykyisen päivämäärän automaattinen täyttäminen**: Harkitse vaihtoehdon tarjoamista nykyisen päivämäärän ja ajan automaattiseen täyttämiseen oletusarvona päivämäärä- ja aikakentässä, jos se on sovelluksesi käyttötilanteeseen sopivaa.
