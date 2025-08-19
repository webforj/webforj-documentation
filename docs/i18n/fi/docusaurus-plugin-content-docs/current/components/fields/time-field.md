---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: 6421e3007af8e795adefa317a13363f0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` on käyttöliittymäkomponentti, jonka avulla käyttäjät voivat syöttää tai valita aikoja tunteina, minuutteina ja valinnaisesti sekunteina. Se tarjoaa intuitiivisen ja tehokkaan tavan käsitellä aikaan liittyvää tietoa erilaisissa sovelluksissa.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Käytöt {#usages}

`TimeField` on ihanteellinen aikojen valitsemiseen ja näyttämiseen sovelluksessasi. Tässä on joitakin esimerkkejä siitä, milloin käyttää `TimeField`:

1. **Tapahtumien aikataulutus**: Aikakentät ovat olennaisia sovelluksissa, jotka liittyvät aikojen asettamiseen tapahtumille, tapaamisille tai kuukausille.

2. **Ajan seuranta ja kirjaaminen**: Aikojen seurantaan tarkoitetuissa sovelluksissa, kuten työtunneissa, tarvitaan aikakenttiä tarkkojen tietojen syöttämiseen.

3. **Muistutukset ja hälytykset**: Aikakentän käyttö yksinkertaistaa syöttöprosessia käyttäjille, jotka asettavat muistutuksia tai hälytyksiä sovellukseesi.

## Minimi- ja maksimiarvo {#min-and-max-value}

`setMin()`- ja `setMax()`-menetelmien avulla voit määrittää hyväksyttävien aikojen alueen.

- **`setMin()`-menetelmälle**: Jos komponenttiin syötetty arvo on aikaisempi kuin määritetty vähimmäisaika, komponentti ei läpäise vaatimustenmukaisuuden tarkistusta. Kun sekä minimi- että maksimiarvot on asetettu, minimivälin on oltava sama tai aikaisempi kuin maksimiarvo.

- **`setMax()`-menetelmälle**: Jos komponenttiin syötetty arvo on myöhäisempi kuin määritetty enimmäisaika, komponentti ei läpäise vaatimustenmukaisuuden tarkistusta. Kun sekä minimi- että maksimiarvot on asetettu, maksimiarvon on oltava sama tai myöhempi kuin minimiväli.

## Arvon käsittely ja lokalisointi {#value-handling-and-localization}

Sisäisesti `TimeField`-komponentti esittää arvonsa käyttämällä `LocalTime`-objektia `java.time`-paketista. Tämä antaa kehittäjille mahdollisuuden käsitellä tarkkoja aikaarvoja riippumatta siitä, kuinka niitä esitetään visuaalisesti.

Vaikka **asiakaspohjainen komponentti näyttää ajan käyttäjän selaimen sijainnin mukaan**, jäsentämis- ja tallennusformaatin on aina oltava standardoitu muotoon `HH:mm:ss`.

Jos asetat raakatekstiarvon, käytä `setText()`-menetelmää varovaisesti:

```java
timeField.setText("09:15:00"); // voimassa
```

:::warning
 Kun käytät `setText()`-menetelmää, `IllegalArgumentException` heitetään, jos komponentti ei voi jäsentää syötettä muodossa `HH:mm:ss`.
:::

:::info Valitsimen käyttöliittymä
Aikavalitsimen syötteen käyttöliittymän ulkonäkö riippuu paitsi valitusta paikasta myös käytettävästä selaimesta ja käyttöjärjestelmästä. Tämä varmistaa automaattisen johdonmukaisuuden käyttöliittymässä, johon käyttäjät ovat jo tottuneet.
:::

## Statiikkaiset työkalut {#static-utilities}

`TimeField`-luokka tarjoaa myös seuraavat staattiset apumenetelmät:

- `fromTime(String timeAsString)`: Muuntaa aikajonon HH:mm:ss-muodossa `LocalTime`-objektiksi, jota voidaan sitten käyttää tässä luokassa tai muualla.

- `toTime(LocalTime time)`: Muuntaa `LocalTime`-objektin aikajonoksi HH:mm:ss-muodossa.

- `isValidTime(String timeAsString)`: Tarkistaa, onko annettu merkkijono voimassa oleva HH:mm:ss-aika. Tämä palauttaa boolean-arvon true, jos niin on, false muuten.

## Parhaat käytännöt {#best-practices}

- **Tarjoa selkeitä aikamuoto-esimerkkejä**: Näytä käyttäjille selkeästi odotettu aikamuoto `TimeField`-lähellä. Käytä esimerkkejä tai paikkamerkkejä auttaaksesi heitä syöttämään ajan oikein. Jos mahdollista, näytä aikamuoto käyttäjän sijainnin mukaan.

- **Saavutettavuus**: Käytä `TimeField`-komponenttia saavutettavuus mielessä pitäen, varmistaen että se täyttää saavutettavuusstandardit, kuten oikeiden etikettien tarjoaminen, riittävä värikontrasti ja yhteensopivuus avustavien teknologioiden kanssa.

- **Nollausvaihtoehto**: Tarjoa tapa, jolla käyttäjät voivat helposti tyhjentää `TimeField`-kentän tyhjään tai oletustilaan.
