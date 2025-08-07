---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
sidebar_class_name: updated-content
_i18n_hash: ee9981c57d9964a3f759b116dbd75af2
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

<ParentLink parent="Field" />

`DateField` on kenttäkomponentti, joka mahdollistaa käyttäjien syöttää tai valita päivämääriä vuosien, kuukausien ja päivien mukaan. Se tarjoaa intuitiivisen ja tehokkaan tavan käsitellä päivämäärätietoja eri sovelluksissa ja tarjoaa joustavuuden validoida käyttäjän syötteet.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Kentän arvo (`LocalDate`) {#field-value-localdate}

`DateField`-komponentti tallentaa arvonsa sisäisesti `LocalDate`-objektina, joka edustaa päivämäärää ilman aikaa tai aikavyöhyke tietoa. Tämä mahdollistaa kalenteripohjaisten syötteiden tarkan käsittelyn eri järjestelmien välillä.

:::info Näytetty arvo VS jäsennelty arvo 
Vaikka **näytetty arvo** mukautuu käyttäjän selaimen paikalliseen asetukseen, varmistaen alueellisesti tuttua muotoilua (esim. `MM/DD/YYYY` Yhdysvalloissa tai `DD.MM.YYYY` Euroopassa), **jäsennelty arvo** nojaa aina kiinteään muotoon `yyyy-MM-dd`.
:::

### `LocalDate` arvon saaminen ja asettaminen {#getting-and-setting-the-localdate-value}

Jos haluat noutaa nykyisen arvon, käytä `getValue()`-metodia:

```java
LocalDate value = dateField.getValue();
```

Asettaaksesi arvon ohjelmallisesti, käytä `setValue()`-metodia:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### `setText()`-metodin käyttäminen {#using-settext}

Voit myös määrittää arvon raakana merkkijonona, mutta sen on noudatettava täsmällistä `yyyy-MM-dd`-muotoa:

```java
dateField.setText("2024-04-27"); // voimassa

dateField.setText("04/27/2024"); // ei voimassa
```

:::warning
 Kun käytät `setText()`-metodia, `IllegalArgumentException` heitetään, jos komponentti ei voi jäsentää syötettä `yyyy-MM-dd`-muodossa.
:::

## Käytännöt {#usages}

`DateField` on ihanteellinen päivämäärien valitsemiseen ja näyttämiseen sovelluksessasi. Tässä on joitakin esimerkkejä siitä, milloin `DateField`-komponenttia kannattaa käyttää:

1. **Tapahtumasuunnittelu ja kalenterit**: Päivämääräkentät ovat välttämättömiä sovelluksissa, jotka sisältävät tapahtumien ajoittamista, tapaamisten varaamista tai tärkeiden päivämäärien seuraamista.

2. **Lomake syötteet**: Yksinkertaistaa päivämäärän valintaprosessia käyttäjälle, joka täyttää lomaketta, joka vaatii päivämäärän, kuten syntymäpäivän.

3. **Varaus- ja varausjärjestelmät**: Sovellukset, jotka sisältävät varaamisen ja varausjärjestelmiä, vaativat usein käyttäjiltä tiettyjen päivämäärien syöttämistä. Päivämääräkenttä yksinkertaistaa prosessia ja varmistaa tarkan päivämäärän valinnan.

4. **Tehtävien hallinta ja määräajat**: Päivämääräkentät ovat arvokkaita sovelluksissa, jotka liittyvät tehtävien hallintaan tai määräaikojen asettamiseen. Käyttäjät voivat helposti määrittää eräpäiviä, aloituspäiviä tai muuta aikarajoitettua tietoa.

## Minimi- ja maksimiarvo {#min-and-max-value}

### Minimiarvo {#the-min-value}
`setMin()`-metodi määrittää varhaisimman päivämäärän, jonka käyttäjä voi syöttää komponenttiin. Jos syöte on aikaisempi kuin määritetty vähimmäismäärä, se epäonnistuu rajoitusvalidaatiossa. Kun sitä käytetään yhdessä `setMax()`-metodin kanssa, minimimäärän on oltava päivämäärä, joka on sama tai aikaisempi kuin maksimi.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Sallittu vähimmäismäärä: 1. tammikuuta 2023
```

### Maksimiarvo {#the-max-value}
`setMax()`-metodi määrittää viimeisen päivämäärän, jonka komponentti hyväksyy. Jos syötetty päivämäärä on myöhempi kuin määritetty maksimi, syöte on virheellinen. Kun molemmat arvot on määritetty, maksimin on oltava päivämäärä, joka on sama tai myöhäisempi kuin minimi.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Sallittu maksimimäärä: 31. joulukuuta 2023
```

## Staattiset apuohjelmat {#static-utilities}

`DateField`-luokka tarjoaa myös seuraavat staattiset apuohjelmat:

- `fromDate(String dateAsString)`: Muuntaa päivämäärämerkkijonon `yyyy-MM-dd`-muodossa `LocalDate`-objektiksi, jota voidaan sitten käyttää tässä kentässä tai muualla.

- `toDate(LocalDate date)`: Muuntaa `LocalDate`-objektin päivämäärämerkkijonoksi `yyyy-MM-dd`-muodossa.

- `isValidDate(String dateAsString)`: Tarkistaa, onko annettu merkkijono voimassa oleva `yyyy-MM-dd`-päivämäärä.

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `DateField`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

- **Esteettömyys**: Käytä asianmukaisia etikettejä varmistaaksesi, että avustavia teknologioita käyttävät käyttäjät voivat helposti navigoida ja käyttää päivämäärkenttiä sovelluksessasi.

- **Nykyisen päivämäärän automaattinen täyttö**: Jos se on sovelluksesi käyttötapaan sopivaa, automaattisesti täytä päivämääräkenttä nykyisellä päivämäärällä.
