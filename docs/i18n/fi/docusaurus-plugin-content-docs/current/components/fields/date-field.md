---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: bf6829e0fafbd0c69a49a5563e8a298b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

`DateField`-komponentti antaa käyttäjille mahdollisuuden syöttää tai valita päivämäärän vuosina, kuukausina ja päivinä. Se käsittelee validointia automaattisesti, joten väärin muotoillut päivämäärät havaitaan ennen lomakkeen lähettämistä.

<!-- INTRO_END -->

## Käyttäen `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yleisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki luo lähtö- ja paluu-DatetimeFieldit, jotka pysyvät synkronoituna, minimi- ja maksimi-rajoituksilla, jotka rajoittavat valittavaa aluetta.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Kenttäarvo (`LocalDate`) {#field-value-localdate}

`DateField`-komponentti tallentaa arvonsa sisäisesti `LocalDate`-objektina, joka edustaa päivämäärää ilman aikavyöhyke- tai aikainformaatiota. Tämä mahdollistaa tarkat kalenteripohjaisten syötteiden käsittelyn eri järjestelmissä.

:::info Näytetty arvo VS käsitelty arvo 
Vaikka **näytetty arvo** mukautuu käyttäjän selaimen aluekohtaisiin asetuksiin varmistaen alueellisesti tutun muotoilun (esim. `MM/DD/YYYY` Yhdysvalloissa tai `DD.MM.YYYY` Euroopassa), **käsitelty arvo** nojaa aina kiinteään muotoon `yyyy-MM-dd`.
:::

### `LocalDate`-arvon saaminen ja asettaminen {#getting-and-setting-the-localdate-value}

Nykyisen arvon noutamiseksi käytetään `getValue()`-metodia:

```java
LocalDate value = dateField.getValue();
```

Arvon asettamiseksi ohjelmallisesti käytetään `setValue()`-metodia:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### `setText()`-käyttö {#using-settext}

Voit myös määrittää arvon raakana merkkijonona, mutta se on noudatettava tarkkaa `yyyy-MM-dd`-muotoa:

```java
dateField.setText("2024-04-27"); // voimassa

dateField.setText("04/27/2024"); // ei voimassa
```

:::warning
 Kun käytetään `setText()`-metodia, heitetään `IllegalArgumentException`, jos komponentti ei pysty käsittelemään syötettä `yyyy-MM-dd`-muodossa.
:::

## Käyttötapaukset {#usages}

`DateField` on ihanteellinen päivämäärien valitsemiseen ja näyttämiseen sovelluksessasi. Tässä on joitain esimerkkejä siitä, milloin käyttää `DateField`-komponenttia:

1. **Tapahtumasuunnittelu ja kalenterit**: Päivämääräkentät ovat olennaisia sovelluksissa, jotka liittyvät tapahtumien aikatauluttamiseen, tapaamisten varaamiseen tai tärkeiden päivämäärien seuraamiseen.

2. **Lomakekentät**: Yksinkertaista päivämäärän valintaprosessia käyttäjälle, joka täyttää lomakkeen, johon vaaditaan päivämäärä, kuten syntymäpäivä.

3. **Varaus- ja varausjärjestelmät**: Sovellukset, jotka liittyvät varaamiseen ja varausjärjestelmiin, vaativat usein käyttäjiä syöttämään tiettyjä päivämääriä. Päivämääräkenttä virtaviivaistaa prosessia ja varmistaa tarkan päivämäärän valinnan.

4. **Tehtävien hallinta ja määräajat**: Päivämääräkentät ovat arvokkaita sovelluksissa, jotka liittyvät tehtävien hallintaan tai määräaikoihin. Käyttäjät voivat helposti määrittää eräpäiviä, aloituspäiviä tai muuta aikakriittistä tietoa.

## Minimi- ja maksimiarvo {#min-and-max-value}

### Minimiarvo {#the-min-value}
`setMin()`-metodi määrittää aikaisimman päivämäärän, jonka käyttäjä voi syöttää komponenttiin. Jos syöte on aikaisempi kuin määritetty vähimmäisarvo, se epäonnistuu rajoitteen validoinnissa. Kun sitä käytetään yhdessä `setMax()`-metodin kanssa, vähimmäisarvon on oltava sama tai aikaisempi kuin maksimi.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Sallittu vähimmäismäärä: 1. tammikuuta 2023
```

### Maksimiarvo {#the-max-value}
`setMax()`-metodi määrittää viimeisen päivämäärän, jonka komponentti hyväksyy. Jos syötetty päivämäärä on myöhempi kuin määritetty maksimimäärä, syöte on virheellinen. Kun molemmat arvot on määritetty, maksimimäärän on oltava sama tai myöhempi kuin vähimmäismäärä.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Sallittu enimmäismäärä: 31. joulukuuta 2023
```

## Staattiset apufunktiot {#static-utilities}

`DateField`-luokka tarjoaa myös seuraavat staattiset apufunktiot:

- `fromDate(String dateAsString)`: Muuntaa päivämäärämerkkijonon `yyyy-MM-dd`-muodossa `LocalDate`-objektiksi, jota voidaan käyttää tässä kentässä tai muualla.

- `toDate(LocalDate date)`: Muuntaa `LocalDate`-objektin päivämäärämerkkijonoksi `yyyy-MM-dd`-muodossa.

- `isValidDate(String dateAsString)`: Tarkistaa, onko annettu merkkijono voimassa oleva `yyyy-MM-dd`-päivämäärä.

## Parhaat käytännöt {#best-practices}

Optimaalisen käyttäjäkokemuksen varmistamiseksi `DateField`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

- **Saavutettavuus**: Hyödynnä asianmukaisia etikettejä varmistaaksesi, että avustavat teknologiat voivat helposti navigoida ja käyttää päivämääräkenttiä sovelluksessasi.

- **Nykyisen päivämäärän automaattinen täyttö**: Jos se on sovelluksesi käyttötapaan soveltuvaa, automaattisesti täytä päivämääräkenttä nykyisellä päivämäärällä.
