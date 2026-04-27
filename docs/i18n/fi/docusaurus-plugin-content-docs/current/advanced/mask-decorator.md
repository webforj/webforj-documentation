---
sidebar_position: 16
title: MaskDecorator
sidebar_class_name: new-content
_i18n_hash: 30ecd8eeaa79a3e5f963e319373d1378
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/MaskDecorator" top='true'/>

`MaskDecorator` on staattinen apuluokka, jota käytetään maskien soveltamiseen merkkijonoille, numeroille, päivämäärille ja ajoille syöttökentän ulkopuolella. Se käyttää samaa maskisyntaksia kuin webforJ:n [maskatut kenttäkomponentit](/docs/components/fields/masked/overview), mikä tekee arvojen muotoilusta ja jäsentämisestä johdonmukaista—oli kyseessä sitten näyttölappu, [`Table`](/docs/components/table/overview) renderöijä tai muu sovelluksesi sijainti.

Käytä `MaskDecorator`-luokkaa, kun sinun tarvitsee muotoilla arvoja ohjelmallisesti näytettäväksi, ei interaktiivista syöttöä varten, kuten taulukon solujen renderöijissä, vain luku -tunnisteissa, viedyissä raporteissa tai missä tahansa kontekstissa, jossa lomakekenttä ei ole sopiva. Interaktiivista muotoilua käyttäjän kirjoittaessa varten käytä sen sijaan maskattua kenttäkomponenttia.

## Merkkijonojen maskaus {#masking-strings}

Käytä `forString()` -menetelmää, jotta voit soveltaa merkki-maskia tavanomaiseen merkkijonoon:

```java
String result = MaskDecorator.forString("abc123", "AAA-000");
// → "ABC-123"
```

Maski määrittää, mitkä merkit hyväksytään jokaisessa sijainnissa.

### Maskimerkit {#string-mask-characters}

| Merkki   | Kuvaus                  |
|----------|-------------------------|
| `X`      | Mikä tahansa tulostettava merkki |
| `a`      | Mikä tahansa aakkosellinen merkki |
| `A`      | Mikä tahansa aakkosellinen merkki; pienet kirjaimet muuttuvat suuriksi |
| `0`      | Mikä tahansa numero (0–9) |
| `z`      | Mikä tahansa numero tai kirjain |
| `Z`      | Mikä tahansa numero tai kirjain; pienet kirjaimet muuttuvat suuriksi |

Muut merkit maskissa käsitellään kirjaimellisesti ja lisätään sellaisinaan tulosteeseen. Virheellisiä merkkejä syötteessä ei oteta huomioon, lyhyet syötteet täydennetään välilyönneillä ja pitkät syötteet katkaistaan maskin mahtuvuuden mukaan.

### Esimerkit {#string-examples}

```java
MaskDecorator.forString("1234567890", "(000) 000-0000");  // → "(123) 456-7890"
MaskDecorator.forString("a1b2c3",     "A0A 0A0");         // → "A1B 2C3"
MaskDecorator.forString("1234",       "ZZZZ-0000");        // → "1234-    " (täydennetty)
```

## Numeroiden maskaus {#masking-numbers}

Käytä `forNumber()` -menetelmää muotoillaksesi numeerista arvoa numeromaskin avulla:

```java
String result = MaskDecorator.forNumber(1234567.89, "#,###,##0.00");
// → "1,234,567.89"
```

### Maskimerkit {#number-mask-characters}

| Merkki   | Kuvaus                  |
|----------|-------------------------|
| `0`      | Korvataan aina numerolla (0–9) |
| `#`      | Estää etunollat. Korvataan täyttömerkillä desimaalipisteen vasemmalla puolella. Peränumeroissa oikealla puolella, korvataan tilalla tai nollalla. Muuten, korvataan numerolla |
| `,`      | Käytetään ryhmittelyerottimena. Korvataan täyttömerkillä, jos numeroita ei ole vielä asetettu; muuten esitetään pilkku |
| `-`      | Näyttää `-`, jos numero on negatiivinen; korvataan täyttömerkillä, jos positiivinen |
| `+`      | Näyttää `+`, jos positiivinen, tai `-`, jos negatiivinen |
| `$`      | Aina tuottaa dollarimerkin |
| `(`      | Lisätään `(`, jos numero on negatiivinen; korvataan täyttömerkillä, jos positiivinen |
| `)`      | Lisätään `)`, jos numero on negatiivinen; korvataan täyttömerkillä, jos positiivinen |
| `CR`     | Lisätään `CR` negatiivisille numeroille; kaksi välilyöntiä positiivisille numeroille |
| `DR`     | Lisätään `CR` negatiivisille numeroille; `DR` positiivisille numeroille |
| `*`      | Aina lisätään tähti |
| `.`      | Merkitsee desimaalipistettä. Korvataan täyttömerkillä, jos tulosteessa ei esiinny numeroita. Desimaalin jälkeen täyttömerkki muuttuu välilyönniksi |
| `B`      | Aina muuttuu välilyönniksi; mikä tahansa muu kirjaimellinen merkki kopioidaan sellaisenaan |

Merkit `-`, `+`, `$` ja `(` voivat kellua: ensimmäinen esiintymä siirretään viimeiseen sijaintiin, jossa `#` tai `,` korvattiin täyttömerkillä.

:::info Pyöristyskäyttäytyminen
`forNumber()` pyöristää arvot vastaamaan maskin desimaalitarkkuutta. Esimerkiksi `MaskDecorator.forNumber(12.34567, "###0.00")` tuottaa `"  12.35"`.
:::

### Esimerkit {#number-examples}

```java
MaskDecorator.forNumber(1234.5,    "###,##0.00");  // → "  1,234.50"
MaskDecorator.forNumber(-9876.0,   "###,##0.00-"); // → "  9,876.00-"
MaskDecorator.forNumber(42.0,      "$###,##0.00"); // → "     $42.00"
MaskDecorator.forNumber(0.5,       "#0.000");      // → " 0.500"
```

## Päivämäärien maskaus {#masking-dates}

Käytä `forDate()` -menetelmää muotoillaksesi `LocalDate`-arvoa päivämäärämaskin avulla:

```java
LocalDate date = LocalDate.of(2025, 7, 4);
String result = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
// → "07/04/2025"
```

Käytä `parseDate()`-menetelmää parsitaksesi maskatun päivämäärämerkkijonon takaisin `LocalDate`:ksi:

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl");
// → LocalDate.of(2025, 7, 4)
```

Paikalliskielitietoista ylikuormitusta on saatavana, kun parsitaan merkkijonoja, jotka sisältävät viikonnumero viittauksia:

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl", Locale.US);
```

### Päivämäärämuotoindikaattorit {#date-format-indicators}

| Formaatti | Kuvaus                |
|-----------|-----------------------|
| `%Y`      | Vuosi                 |
| `%M`      | Kuukausi              |
| `%D`      | Päivä                 |

### Muokkaimet {#date-modifiers}

Valinnainen muokkaaja seuraa heti muotoindikaattoria:

| Muokkaaja | Kuvaus                      |
|-----------|-----------------------------|
| `z`       | Nollatäyte                  |
| `s`       | Lyhyt tekstiesitys         |
| `l`       | Pitkä tekstiesitys         |
| `p`       | Pakattu numero              |
| `d`       | Desimaali (oletusmuoto)    |

### Esimerkit {#date-examples}

```java
LocalDate d = LocalDate.of(2025, 3, 5);

MaskDecorator.forDate(d, "%Mz/%Dz/%Yl");  // → "03/05/2025"
MaskDecorator.forDate(d, "%Dz.%Mz.%Yz");  // → "05.03.25"
MaskDecorator.forDate(d, "%Dl, %Ml %Dz");  // → "Wednesday, March 05"
MaskDecorator.forDate(d, "%Yl-%Mz-%Dz");  // → "2025-03-05"
```

## Aikojen maskaus {#masking-times}

Käytä `forTime()` -menetelmää muotoillaksesi `LocalTime`-arvoa aikamaskin avulla:

```java
LocalTime time = LocalTime.of(14, 30, 0);
String result = MaskDecorator.forTime(time, "%Hz:%mz");
// → "14:30"
```

Käytä `parseTime()`-menetelmää parsitaksesi maskatun aikamerkkijonon takaisin `LocalTime`:ksi:

```java
LocalTime time = MaskDecorator.parseTime("14:30", "%Hz:%mz");
// → LocalTime.of(14, 30)
```

Paikalliskielitietoista ylikuormitusta on saatavana, kun parsitaan merkkijonoja, jotka sisältävät paikallistettuja AM/PM arvoja:

```java
LocalTime time = MaskDecorator.parseTime("02:30 pm", "%hz:%mz %p", Locale.US);
```

### Aikamuotoindikaattorit {#time-format-indicators}

| Formaatti | Kuvaus                  |
|-----------|-------------------------|
| `%H`      | Tunti (24-tuntinen kello) |
| `%h`      | Tunti (12-tuntinen kello) |
| `%m`      | Minuutti                |
| `%s`      | Sekunti                 |
| `%p`      | am/pm                   |

### Muokkaimet {#time-modifiers}

Aikamaskit käyttävät samoja muokkaimia kuin päivämäärämaskit. Katso [Päivämäärämutaajat](#date-modifiers).

### Esimerkit {#time-examples}

```java
LocalTime t = LocalTime.of(9, 5, 30);

MaskDecorator.forTime(t, "%Hz:%mz:%sz");  // → "09:05:30"
MaskDecorator.forTime(t, "%hz:%mz %p");   // → "09:05 am"
MaskDecorator.forTime(t, "%Hz%mz");       // → "0905"
```

## Päivämäärän ja ajan maskaus {#masking-datetime}

Käytä `forDateTime()` -menetelmää muotoillaksesi `LocalDateTime`-arvoa yhdistetyllä päivämäärä- ja aikamaskeilla:

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);
String result = MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");
// → "07/04/2025 14:30"
```

### Muotoindikaattorit {#datetime-format-indicators}

`forDateTime()` tukee kaikkia päivämäärä- ja aikamuotoindikaattoreita missä tahansa yhdistelmässä. Katso [Päivämäärämuotoindikaattorit](#date-format-indicators) ja [Aikamuotoindikaattorit](#time-format-indicators) täydellistä luetteloa varten.

### Muokkaimet {#datetime-modifiers}

Kaikki [Päivämäärämuokkaimet](#date-modifiers) käsittääva muokkaimet soveltuvat sekä päivämäärä- että aikasideille yhdistetyssä maskissa.

### Esimerkit {#datetime-examples}

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);

MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");      // → "07/04/2025 14:30"
MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz:%sz");  // → "07/04/2025 14:30:00"
MaskDecorator.forDateTime(dt, "%Dz.%Mz.%Yz %hz:%mz %p");  // → "04.07.25 02:30 pm"
```

## Null-arvojen käsittely {#handling-null-results}

:::warning
Kaikki `for*()` ja `parse*()` -menetelmät palauttavat `null`, jos syöte on virheellinen tai sitä ei voida jäsentää. Varmista aina, että tulos ei ole null ennen sen käyttöä sovelluksesi logiikassa.
:::

```java
String formatted = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
if (formatted != null) {
  label.setText(formatted);
}
```
