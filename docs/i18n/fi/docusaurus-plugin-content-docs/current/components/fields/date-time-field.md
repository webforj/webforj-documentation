---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 9ccb17716ffd5fa2d32781fc603d5042
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

`DateTimeField`-komponentti antaa käyttäjien syöttää sekä päivämäärän että ajan yhdessä kentässä, kattaen vuoden, kuukauden, päivän, tunnit ja minuutit. Se validoi syötteen tarkkuuden ja voi esittää päivämäärä- ja aikanäyttöä valinnan helpottamiseksi.

<!-- INTRO_END -->

## Käyttäminen `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yhteisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki luo nimetty `DateTimeField`-komponentin lähtöpäivän ja ajan valitsemista varten.

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## Käyttötavat {#usages}

`DateTimeField` on paras käyttää skenaarioissa, joissa sekä päivämäärän **että** ajan tallentaminen tai näyttäminen on olennaista sovelluksellesi. Tässä on joitain esimerkkejä siitä, milloin käyttää `DateTimeField`-komponenttia:

1. **Tapahtumasuunnittelu ja kalenterit**: Anna käyttäjien tehokkaasti aikatauluttaa tapahtumia, varata tapaamisia ja hallita kalentereitaan antamalla heille yksi komponentti, joka mahdollistaa päivämäärän ja ajan valinnan.
<!-- vale off -->
2. **Sisään- ja uloskirjautuminen**: Helpota käyttäjien valintaa sisään- ja uloskirjautumisaikoihin, kun jakso voi kestää useita päiviä.
<!-- vale on -->
3. **Tietojen kirjaaminen ja aikaleimat**: Hyödynnä `DateTimeFields`-komponentteja sovelluksissa, jotka liittyvät tapahtumien esiintymisajan tai käyttäjän tietojen lähetysaikojen tallentamiseen.

4. **Tehtävien hallinta ja määräajat**: `DateTimeFields` ovat arvokkaita sovelluksissa, joissa käsitellään tehtävien hallintaa tai määräaikojen asettamista, joissa sekä päivämäärä että aika ovat tärkeitä tarkan aikataulutuksen kannalta.

## Kentän arvo (`LocalDateTime`) {#field-value-localdatetime}

Sisäisesti `DateTimeField`-komponentti esittää arvonsa käyttämällä `LocalDateTime`-objektia `java.time`-paketista. Arvot pyöristetään minuutteihin, joten sekunnit ja murto-osasekunnit hylätään.

Selaimessa päivämäärä ja aika näytetään käyttäjän alueen mukaan, kun taas kentän tekstiarvo käyttää muotoa `yyyy-MM-ddTHH:mm`. `LocalDateTime` ei sisällä aikavyöhykettä, eikä syötteitä, joissa on aikavyöhykemerkintöjä kuten `Z` tai `+02:00`, hyväksytä.

### Arvon saaminen ja asettaminen {#getting-and-setting-the-value}

Nykyisen arvon hakemiseksi käytä `getValue()`-metodia:

```java
LocalDateTime value = dateTimeField.getValue();
```

Arvon asettamiseksi ohjelmallisesti käytä `setValue()`-metodia:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30));
```

### `setText()`-menetelmän käyttäminen {#using-settext}

Syötteen asettamiseksi raakana merkkijonona käytä `setText()`-metodia paikallisella päivämäärä-ajalla, kuten `yyyy-MM-ddTHH:mm`. Syötteessä voi myös olla sekunteja tai murto-osasekunteja, jotka hylätään.

```java
dateTimeField.setText("2024-04-27T14:30");       // kelpaa
dateTimeField.setText("2024-04-27T14:30:45");    // kelpaa; sekunnit hylätään

dateTimeField.setText("24-04-27T14:30");        // kelvoton; käytä neljän numeron vuotta
dateTimeField.setText("2024-04-27T14:30Z");      // kelvoton; aikavyöhykemerkintöjä ei hyväksytä
```

:::warning
Kun käytät `setText()`, `IllegalArgumentException` tulee esiin, jos syötettä ei voida jäsentää kelvolliseksi paikalliseksi päivämääräksi. Sekuntitarkkuudella syöte ja syöte, jossa on sekunteja, hyväksytään, mutta aikavyöhykemerkintöjä ei hyväksytä.
:::

## Staattiset työkalut {#static-utilities}

`DateTimeField`-luokka tarjoaa myös seuraavat staattiset työkalumenetelmät:

- `fromDateTime(String dateTimeAsString)`: jäsentää paikallisen päivämäärä-ajan merkkijonon, jossa voi olla sekunteja tai ilman niitä, `LocalDateTime`-objektiin, joka on pyöristetty minutoihin.

- `toDateTime(LocalDateTime dateTime)`: muuntaa `LocalDateTime`-objektin merkkijonoksi `yyyy-MM-ddTHH:mm` -muodossa. Sekunnit ja murto-osasekunnat hylätään.

- `isValidDateTime(String dateTimeAsString)`: tarkistaa, onko merkkijono kelvollinen paikallinen päivämäärä-aika. Syöte, jossa on sekunteja tai murto-osasekunteja, hyväksytään. Palauttaa `false` kelvottomille syötteille tai aikavyöhykemerkinnöille.

## Minimi- ja maksimimäärä {#min-and-max-value}

### Minimimäärä {#the-min-value}

Jos komponenttiin syötetty arvo on aikaisempi kuin määritelty minimiaikaleima, komponentti ei läpäise rajoitteiden validointia. Kun sekä minimi- että maksimimäärät on asetettu, minimimäärän on oltava aikaleima, joka on sama tai aikaisempi kuin maksimimäärä.

```java
// Aseta minimihyväksyttävä aikaleima: 1. tammikuuta 2023 klo 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### Maksimimäärä {#the-max-value}

Jos komponenttiin syötetty arvo on myöhempi kuin määritelty maksimiaikaleima, komponentti ei läpäise rajoitteiden validointia. Kun sekä minimi- että maksimimäärät on asetettu, maksimimäärän on oltava aikaleima, joka on sama tai myöhempi kuin minimimäärä.

```java
// Aseta maksimihyväksyttävä aikaleima: 31. joulukuuta 2023 klo 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Parhaat käytännöt {#best-practices}

Jotta varmistat optimaalisen käyttökokemuksen `DateTimeField`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

- **Paikallistettu päivämääränäyttö**: Päivämäärä-formaatin paikallistaminen ja alueellisten mieltymysten sisällyttäminen varmistaa, että päivämäärät esitetään käyttäjälle tuttuna muotona.

- **Käsittele aikavyöhykkeitä erikseen**: Jos sovelluksesi toimii eri aikavyöhykkeillä, tarjoa erillinen aikavyöhykkeen valitsin ja käsittele muunnoksia sovelluksessasi. `DateTimeField` tallentaa vain paikallisen päivämäärän ja ajan.

- **Saavutettavuus**: Hyödynnä `DateTimeField`-komponenttia ajatellen saavutettavuutta. Varmista, että se täyttää saavutettavuusstandardit, kuten asianmukaiset tarrat ja yhteensopivuus apuvälineiden kanssa.

- **Auto-täytä nykyinen päivämäärä**: Harkitse mahdollisuuden tarjoamista nykyisen päivämäärän ja ajan automaattiseen täyttämiseen oletusarvona päivämääräajankentässä, jos se on sovelluksesi käyttötapauksen kannalta tarpeen.
