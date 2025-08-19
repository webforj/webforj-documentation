---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
sidebar_class_name: updated-content
_i18n_hash: 9f7f8e2c82305667ea1ace187df17915
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

<ParentLink parent="Field" />

`DateField` on kenttäkomponentti, joka sallii käyttäjien syöttää tai valita päivämääriä vuosittain, kuukausittain ja päivittäin. Se tarjoaa intuitiivisen ja tehokkaan tavan käsitellä päivämäärätietoja eri sovelluksissa, ja tarjoaa joustavuutta validoida käyttäjän syötteitä.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Kentän arvo (`LocalDate`) {#field-value-localdate}

`DateField`-komponentti tallentaa arvonsa sisäisesti `LocalDate`-objektina, joka edustaa päivämäärää ilman aikaa tai aikavyöhyke tietoa. Tämä mahdollistaa tarkan kalenteriperusteisten syötteiden käsittelyn eri järjestelmissä.

:::info Näytetty arvo VS jäsennelty arvo 
Vaikka **näytetty arvo** mukautuu käyttäjän selaimen alueasetuksiin, varmistaen alueellisesti tutun muotoilun (esim. `MM/DD/YYYY` Yhdysvalloissa tai `DD.MM.YYYY` Euroopassa), **jäsennelty arvo** perustuu aina kiinteään muotoon `yyyy-MM-dd`.
:::

### `LocalDate`-arvon saaminen ja asettaminen {#getting-and-setting-the-localdate-value}

Nykyisen arvon hakemiseksi käytä `getValue()`-metodia:

```java
LocalDate value = dateField.getValue();
```

Arvon asettamiseksi ohjelmallisesti käytä `setValue()`-metodia:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### `setText()`-käyttö {#using-settext}

Voit myös määrätä arvon raakana merkkijonona, mutta sen on noudatettava tarkkaa `yyyy-MM-dd`-muotoa:

```java
dateField.setText("2024-04-27"); // kelvollinen

dateField.setText("04/27/2024"); // ei kelvollinen
```

:::warning
 Kun käytät `setText()`-metodia, `IllegalArgumentException` heitetään, jos komponentti ei voi jäsentää syötettä `yyyy-MM-dd`-muodossa.
:::

## Käyttötarkoitukset {#usages}

`DateField` on ihanteellinen päivämäärien valitsemiseen ja näyttämiseen sovelluksessasi. Tässä on joitain esimerkkejä siitä, milloin käyttää `DateField`-komponenttia:

1. **Tapahtumien aikatauluttaminen ja kalenterit**: Päivämääräkentät ovat välttämättömiä sovelluksissa, joissa aikataulutetaan tapahtumia, varataan aikarajoja tai pidetään kirjaa tärkeistä päivämääristä.

2. **Lomake syötteet**: Yksinkertaista päivämäärän valintaprosessia käyttäjälle, joka täyttää lomaketta, joka vaatii päivämäärän, kuten syntymäpäivän.

3. **Varaus- ja varausjärjestelmät**: Sovellukset, joissa on varausta tai varausjärjestelmiä, edellyttävät usein, että käyttäjät syöttävät tiettyjä päivämääriä. Päivämääräkenttä virtaviivaistaa prosessia ja varmistaa tarkan päivämääriä.

4. **Tehtävien hallinta ja määräajat**: Päivämääräkentät ovat arvokkaita sovelluksissa, jotka liittyvät tehtävien hallintaan tai aikarajojen asettamiseen. Käyttäjät voivat helposti määrittää eräpäiviä, aloituspäiviä tai muuta aikarajoitettua tietoa.

## Minimi- ja maksimiarvo {#min-and-max-value}

### Minimiväli {#the-min-value}
`setMin()`-metodi määrittelee aikaisimman päivämäärän, jonka käyttäjä voi syöttää komponenttiin. Jos syöte on aikaisempi kuin määritetty vähimmäismäärä, se epäonnistuu rajoitusten validoinnissa. Kun käytetään yhdessä `setMax()`-metodin kanssa, minimipäivämäärän on oltava sama tai aikaisempi kuin maksimi.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Sallittu vähimmäispäivämäärä: 1. tammikuuta 2023
```

### Maksimiväli {#the-max-value}
`setMax()`-metodi määrittää viimeisimmän päivämäärän, jonka komponentti hyväksyy. Jos syötetty päivämäärä on myöhempi kuin määritetty maksimi, syöte on kelvoton. Kun molemmat arvot on määritetty, maksimipäivämäärän on oltava sama tai myöhempi kuin minimipäivämäärä.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Sallittu enimmäispäivämäärä: 31. joulukuuta 2023
```

## Staattiset työkalut {#static-utilities}

`DateField`-luokka tarjoaa myös seuraavat staattiset työkalumetodit:

- `fromDate(String dateAsString)`: Muuntaa päivämäärämerkkijonon `yyyy-MM-dd`-muodossa `LocalDate`-objektiksi, jota voidaan sitten käyttää tämän kentän kanssa tai muualla.

- `toDate(LocalDate date)`: Muuntaa `LocalDate`-objektin päivämäärämerkkijonoksi `yyyy-MM-dd`-muodossa.

- `isValidDate(String dateAsString)`: Tarkistaa, onko annettu merkkijono kelvollinen `yyyy-MM-dd`-päivämäärä.

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `DateField`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

- **Saavutettavuus**: Käytä asianmukaisia etikettejä varmistaaksesi, että apuvälineitä käyttävät käyttäjät voivat helposti navigoida ja käyttää päivämääräkenttiä sovelluksessasi.

- **Nykyisen päivämäärän automaattinen täyttö**: Jos se on sopivaa sovelluksesi käyttötapaukseen, automaattisesti täytä päivämääräkenttä nykyisellä päivämäärällä.
