---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: a996ccdd786de35de1dece0a5fc8f27a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

`DateField`-komponentti antaa käyttäjien syöttää tai valita päivämäärän vuosien, kuukausien ja päivien mukaan. Se huolehtii validoinnista automaattisesti, joten väärin muotoiltuja päivämääriä havaitaan ennen lomakkeen lähettämistä.

<!-- INTRO_END -->

## Käyttäminen `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` perii jaettu `Field`-luokan, joka tarjoaa yhteisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraavassa esimerkissä luodaan lähtö- ja paluupäivämääräkentät, jotka pysyvät synkronoituna, ja niille asetetaan minimi- ja maksimirajoitukset valittavalle alueelle.

<ComponentDemo
path='/webforj/datefield'
files={['src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java']}
/>

## Kentän arvo (`LocalDate`) {#field-value-localdate}

`DateField`-komponentti tallentaa arvonsa sisäisesti `LocalDate`-objektina, joka edustaa päivämäärää ilman aikaa tai aikavyöhyketietoja. Tämä mahdollistaa kalenteripohjaisten syötteiden tarkan käsittelyn eri järjestelmissä.

:::info Näytettävä arvo VS parsittu arvo 
Vaikka **näytettävä arvo** mukautuu käyttäjän selainalueeseen, varmistaen alueellisesti tutut muotoilut (esim. `MM/DD/YYYY` Yhdysvalloissa tai `DD.MM.YYYY` Euroopassa), **parsittu arvo** perustuu aina kiinteään muotoon `yyyy-MM-dd`.
:::

### `LocalDate`-arvon saaminen ja asettaminen {#getting-and-setting-the-localdate-value}

Nykyisen arvon hakemiseksi käytä `getValue()`-menetelmää:

```java
LocalDate value = dateField.getValue();
```

Arvon asettamiseksi ohjelmallisesti käytä `setValue()`-menetelmää:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### `setText()`-käyttäminen {#using-settext}

Voit myös määrittää arvon raakana merkkijonona, mutta sen on noudatettava tarkkaa `yyyy-MM-dd`-muotoa:

```java
dateField.setText("2024-04-27"); // voimassa

dateField.setText("04/27/2024"); // virheellinen
```

:::warning
 Kun käytät `setText()`-menetelmää, jos komponentti ei voi jäsentää syötettä `yyyy-MM-dd`-muodossa, heitetään `IllegalArgumentException`.
:::

## Käyttötapauksia {#usages}

`DateField` on ihanteellinen päivämäärien valitsemiseen ja näyttämiseen sovelluksessasi. Tässä on muutamia esimerkkejä tilanteista, joissa kannattaa käyttää `DateField`:ia:

1. **Tapahtumien aikataulutus ja kalenterit**: Päivämääräkentät ovat välttämättömiä sovelluksissa, jotka liittyvät tapahtumien aikatauluttamiseen, tapaamisten varaamiseen tai tärkeiden päivien seuraamiseen.

2. **Lomake syötteet**: Yksinkertaista päivämäärän valintaprosessia käyttäjälle, joka täyttää lomaketta, joka vaatii päivämäärän, kuten syntymäpäivän.

3. **Varaus- ja varausjärjestelmät**: Sovellukset, jotka käsittelevät varausta ja varausjärjestelmiä, vaativat usein käyttäjiä syöttämään tiettyjä päivämääriä. Päivämääräkenttä sujuvoittaa prosessia ja varmistaa tarkan päivämäärän valinnan.

4. **Tehtävien hallinta ja määräajat**: Päivämääräkentät ovat arvokkaita sovelluksissa, jotka liittyvät tehtävien hallintaan tai määräaikojen asettamiseen. Käyttäjät voivat helposti määrittää eräpäiviä, aloituspäiviä tai muita aikarajoja.

## Minimi- ja maksimiarvot {#min-and-max-value}

### Minimiarvo {#the-min-value}
`setMin()`-menetelmä määrittää aikaisimman päivämäärän, jonka käyttäjä voi syöttää komponenttiin. Jos syöte on aikaisempi kuin määritetty vähimmäisarvo, se epäonnistuu ehtovaatimusten tarkistuksessa. Kun käytetään yhdessä `setMax()`-menetelmän kanssa, minimipäivämäärän on oltava sama tai aikaisempi kuin maksimi.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Sallitut vähimmäispäivämäärät: 1. tammikuuta 2023
```

### Maksimiarvo {#the-max-value}
`setMax()`-menetelmä määrittää viimeisen päivämäärän, jonka komponentti hyväksyy. Jos syötetty päivämäärä on myöhäisempi kuin määritetty maksimi, syöte on virheellinen. Kun molemmat arvot on määritetty, maksimipäivämäärän on oltava sama tai myöhempi kuin minimipäivämäärä.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Sallitut maksimipäivämäärät: 31. joulukuuta 2023
```

## Staattiset apufunktiot {#static-utilities}

`DateField`-luokka tarjoaa myös seuraavat staattiset apumenetelmät:

- `fromDate(String dateAsString)`: Muuntaa päivämäärämerkkijonon `yyyy-MM-dd`-muodossa `LocalDate`-objektiksi, jota voidaan käyttää tässä kentässä tai muualla.

- `toDate(LocalDate date)`: Muuntaa `LocalDate`-objektin päivämäärämerkkijonoksi `yyyy-MM-dd`-muodossa.

- `isValidDate(String dateAsString)`: Tarkistaa, onko annettu merkkijono voimassa `yyyy-MM-dd`-päivämäärä.

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen käyttäessäsi `DateField`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

- **Esteettömyys**: Käytä sopivia etikettejä varmistaaksesi, että apuvälineitä käyttävät käyttäjät voivat helposti navigoida ja käyttää päivämääräkenttiä sovelluksessasi.

- **Nykyisen päivämäärän automaattinen täyttäminen**: Jos se on sovelluksesi käyttötapaan sopivaa, automaattisesti täytä päivämääräkenttä nykyisellä päivämäärällä.
