---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
sidebar_class_name: updated-content
_i18n_hash: 70f471320621b40dc1bb4170e4cbf752
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

`DateTimeField`-komponentti on suunniteltu sallimaan käyttäjien syöttää sekä päivämäärä että aika. Tämä sisältää vuoden, kuukauden ja päivän määrittämisen yhdessä tuntien ja minuuttien kanssa. Se tarjoaa käyttäjille mahdollisuuden validoida syötteensä tarkkuuden vuoksi tai hyödyntää erityistä päivämäärä-aika -valintarajapintaa valintaprosessin sujuvoittamiseksi.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Käytöt {#usages}

`DateTimeField` on parasta käyttää tilanteissa, joissa sekä päivämäärän **että** ajan tallentaminen tai näyttäminen on olennaista sovelluksellesi. Tässä on joitakin esimerkkejä, milloin käyttää `DateTimeField`:ia:

1. **Tapahtumien aikatauluttaminen ja kalenterit**: Anna käyttäjien aikatauluttaa tapahtumia, varata aikoja ja hallita kalentereitaan tehokkaasti antamalla heille yksi komponentti, joka sallii päivämäärän ja ajan valitsemisen.
<!-- vale off -->
2. **Sisään- ja uloskirjautumiset**: Helpota käyttäjien sisään- ja uloskirjautumisaikojen valintaa, kun jakso voi kestää useita päiviä.
<!-- vale on -->
3. **Tietojen lokitus ja aikaleimat**: Hyödynnä `DateTimeField`eja sovelluksissa, jotka sisältävät tapahtumien aikojen tai käyttäjän syöttämien tietojen tallentamisen.

4. **Tehtävien hallinta ja määräajat**: `DateTimeField`eja on arvokkaita sovelluksissa, jotka liittyvät tehtävien hallintaan tai määräaikojen asettamiseen, joissa sekä päivämäärä että aika ovat olennaisia tarkassa aikatauluttamisessa.

## Kenttäarvo (`LocalDateTime`) {#field-value-localdatetime}

Sisäisesti `DateTimeField`-komponentti edustaa arvoaan käyttämällä `LocalDateTime`-objektia `java.time`-paketista. Tämä tarjoaa tarkan hallinnan sekä päivämäärä- että aikakomponenteista syötteessä.

Vaikka **asiakaspinnan** arvo renderoidaan käyttäjän selaimen alueellisten asetusten mukaan (esim. päivämäärä- ja aikamuodot, jotka vastaavat paikallisia käytäntöjä), **analysoitu** arvo noudattaa tiukkaa ja ennustettavaa rakennetta: **`yyyy-MM-ddTHH:mm:ss`**.

### Arvon saaminen ja asettaminen {#getting-and-setting-the-value}

Nykyisen arvon saamiseksi käytä `getValue()`-metodia:

```java
LocalDateTime value = dateTimeField.getValue();
```

Arvon ohjelmallista asettamista varten käytä `setValue()`-metodia:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Käyttämällä `setText()` {#using-settext}

Jos haluat asettaa arvon raakana merkkijonona, sen on noudatettava täsmällistä muotoa `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // kelvollinen

dateTimeField.setText("24-04-27T14:30:00"); // kelvoton
```

:::warning
 Kun käytät `setText()`-metodia, `IllegalArgumentException` heitetään, jos komponentti ei voi jäsentää syötettä muodossa `yyyy-MM-ddTHH:mm:ss`.
:::

## Staattiset työkalut {#static-utilities}

DateTimeField-luokka tarjoaa myös seuraavat staattiset utiliittimet:

- `fromDateTime(String dateTimeAsString)`: Muuntaa päivämäärä- ja aika-merkkijonon muodossa `yyyy-MM-ddTHH:mm:ss` `LocalDateTime`-objektiksi, jota voidaan käyttää tämän luokan tai muualla.

- `toDateTime(LocalDateTime dateTime)`: Muuntaa `LocalDateTime`-objektin päivämäärä- ja aika-merkkijonoksi muodossa `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)`: Tarkistaa, onko annettu merkkijono kelvollinen `yyyy-MM-ddTHH:mm:ss` -päivämäärä ja aika. Tämä palauttaa totuusarvon true, jos näin on, ja false muuten.

## Minimi- ja maksimiarvo {#min-and-max-value}

### Minimiarvo {#the-min-value}

Jos komponenttiin syötetty arvo on aikaisempi kuin määritetty vähimmäisaika, komponentti epäonnistuu rajoitevalidoinnissa. Kun sekä minimi- että maksimiarvot on asetettu, minimiarvon on oltava aikaleima, joka on sama tai aikaisempi kuin maksimiarvo.

```java
// Aseta vähintään sallittu aikaleima: 1. tammikuuta 2023 klo 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### Maksimiarvo {#the-max-value}

Jos komponenttiin syötetty arvo on myöhempi kuin määritetty enimmäisaika, komponentti epäonnistuu rajoitevalidoinnissa. Kun sekä minimi- että maksimiarvot on asetettu, maksimiarvon on oltava aikaleima, joka on sama tai myöhempi kuin minimiarvo.

```java
// Aseta enintään sallittu aikaleima: 31. joulukuuta 2023 klo 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen käytettäessä `DateTimeField`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

- **Paikallinen päivämäärän esitys**: Paikallistaminen päivämäärämuotoon ja alueellisten mieltymysten sisällyttäminen varmistaa, että päivämäärät esitetään käyttäjälle tutussa muodossa.

- **Sisällytä aikavyöhykkeet**: Jos sovelluksesi käsittelee aikaherkkää tietoa eri aikavyöhykkeillä, harkitse aikavyöhykkeen valinnan sisällyttämistä päivämääräkentän ohella varmistaaksesi tarkka päivämäärä-aika -esitys.

- **Esteettömyys**: Hyödynnä `DateTimeField`-komponenttia esteettömyys mielessä. Varmista, että se täyttää esteettömyysstandardit, kuten tarjoamalla asianmukaiset labelit ja olevan yhteensopiva apuvälineiden kanssa.

- **Automaattinen nykyisen päivämäärän täyttö**: Harkitse nykyisen päivämäärän ja ajan automaattisen täytön tarjoamista oletusarvona päivämääräkentässä, jos se on sopivaa sovelluksesi käyttötarkoituksen kannalta.
