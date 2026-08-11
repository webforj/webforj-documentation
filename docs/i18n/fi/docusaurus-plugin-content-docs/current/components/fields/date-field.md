---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: 173c4a1d080dc6e0c01828131af61c08
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

`DateField`-komponentti mahdollistaa käyttäjien syöttävän tai valitsevan päiväyksen vuoden, kuukauden ja päivän mukaan. Se käsittelee voimassaolon tarkistuksen automaattisesti, joten väärin muotoillut päivämäärät havaitaan ennen lomakkeen lähettämistä.

<!-- INTRO_END -->

## Käyttäminen `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yleisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraavassa esimerkissä luodaan lähtö- ja paluupäivämäärä kentät, jotka pysyvät synkronoituna, minimi- ja maksimi rajoituksilla, jotka rajoittavat valittavaa alueetta.

<ComponentDemo
path='/webforj/datefield'
files={['src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java']}
/>

## Kentän arvo (`LocalDate`) {#field-value-localdate}

`DateField`-komponentti tallentaa arvonsa sisäisesti `LocalDate`-objektina, joka edustaa päivämäärää ilman aikaa tai aikavyöhyke-informaatiota. Tämä mahdollistaa kalenteripohjaisten syötteiden tarkan käsittelyn eri järjestelmissä.

:::info Näytetty arvo VS jäsennelty arvo
Vaikka **näytetty arvo** mukautuu käyttäjän selaimen paikallistamiseen, varmistaen alueellisesti tutun muotoilun (esim. `MM/DD/YYYY` Yhdysvalloissa tai `DD.MM.YYYY` Euroopassa), **jäsennelty arvo** tukeutuu aina kiinteään muotoon `yyyy-MM-dd`.
:::

### `LocalDate`-arvon hakeminen ja asettaminen {#getting-and-setting-the-localdate-value}

Nykyisen arvon hakemiseksi käytä `getValue()`-metodia:

```java
LocalDate value = dateField.getValue();
```

Arvon asettamiseen ohjelmallisesti käytä `setValue()`-metodia:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### `setText()`-käyttäminen {#using-settext}

Voit myös määrittää arvon raakana merkkijonona, mutta sen on noudatettava tarkkaa `yyyy-MM-dd`-muotoa:

```java
dateField.setText("2024-04-27"); // voimassa

dateField.setText("04/27/2024"); // ei voimassa
```

:::warning
 `setText()`-metodia käytettäessä `IllegalArgumentException` heitetään, jos komponentti ei voi jäsentää syötettä muodossa `yyyy-MM-dd`.
:::

## Käytöt {#usages}

`DateField` soveltuu erinomaisesti päivämäärien valitsemiseen ja näyttämiseen sovelluksessasi. Tässä on muutamia esimerkkejä siitä, milloin `DateField`-komponenttia kannattaa käyttää:

1. **Tapahtumien aikataulutus ja kalenterit**: Päivämääräkentät ovat välttämättömiä sovelluksissa, joissa aikatauloidaan tapahtumia, varataan aikoja tai pidetään kirjaa tärkeistä päivämääristä.

2. **Lomakekentät**: Helpota päivämäärän valintaprosessia käyttäjälle, joka täyttää lomaketta, joka vaatii päivämäärän, kuten syntymäpäivän.

3. **Varausjärjestelmät**: Sovellukset, joissa on varausjärjestelmiä, vaativat usein käyttäjiltä tietojen syöttämistä tietyistä päivämääristä. Päivämääräkenttä sujuvoittaa prosessia ja varmistaa tarkan päivämäärän valinnan.

4. **Tehtävien hallinta ja määräpäivämäärät**: Päivämääräkentät ovat arvokkaita sovelluksissa, jotka liittyvät tehtävien hallintaan tai määräpäivämäärien asettamiseen. Käyttäjät voivat helposti määrittää eräpäiviä, aloituspäiviä tai muita aikarajoitteisia tietoja.

## Minimi- ja maksimiarvo {#min-and-max-value}

### Minimiarvo {#the-min-value}
`setMin()`-metodi määrittelee aikaisimman päivämäärän, jonka käyttäjä voi syöttää komponenttiin. Jos syöte on aikaisempi kuin määritetty vähimmäisarvo, se epäonnistuu rajoitusvalidoinnissa. Kun käytetään yhdessä `setMax()`-metodin kanssa, vähimmäisen on oltava päivämäärä, joka on sama tai aikaisempi kuin maksimi.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Vähimmäis sallittu: 1. tammikuuta 2023
```

### Maksimiarvo {#the-max-value}
`setMax()`-metodi määrittelee viimeisen päivämäärän, jonka komponentti hyväksyy. Jos syötetty päivämäärä on myöhempi kuin määritetty maksimi, syöte on virheellinen. Kun molemmat arvot on määritetty, maksimin on oltava päivämäärä, joka on sama tai myöhempi kuin minimum.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maksimi sallittu: 31. joulukuuta 2023
```

## Staattiset apuohjelmat {#static-utilities}

`DateField`-luokka tarjoaa myös seuraavat staattiset apuohjelmat:

- `fromDate(String dateAsString)`: Muuntaa aikamuotoisen päivämäärämerkkijonon `yyyy-MM-dd`-muodossa `LocalDate`-objektiksi, jota voidaan sitten käyttää tällä kentällä tai muualla.

- `toDate(LocalDate date)`: Muuntaa `LocalDate`-objektin päivämäärämerkkijonoksi `yyyy-MM-dd`-muodossa.

- `isValidDate(String dateAsString)`: Tarkistaa, onko annettu merkkijono voimassa oleva `yyyy-MM-dd`-päivämäärä.

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen käyttäessäsi `DateField`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

- **Esteettömyys**: Käytä oikeita etikettejä varmistaaksesi, että apuvälineitä käyttävät käyttäjät voivat helposti navigoida ja käyttää sovelluksesi päivämääräkenttiä.

- **Automaattinen nykyisen päivämäärän täydentäminen**: Jos se on sopivaa sovelluksesi käyttötarkoitukseen, täytä päivämääräkenttä automaattisesti nykyisellä päivämäärällä.
