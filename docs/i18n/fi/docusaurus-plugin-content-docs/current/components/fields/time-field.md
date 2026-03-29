---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 994cad91e2870d59f3c0eec7c2b47141
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` on käyttöliittymäkomponentti, joka mahdollistaa käyttäjien syöttää tai valita aikoja tunneissa, minuuteissa ja mahdollisesti sekunneissa. Se tarjoaa intuitiivisen ja tehokkaan tavan käsitellä aikaperusteista tietoa eri sovelluksissa.

<!-- INTRO_END -->

## Using the `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yhteiset ominaisuudet kaikille kenttäkomponenteille. Seuraava esimerkki luo muistutus `TimeField`-komponentin, joka on alustettu nykyisessä ajassa.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usages {#usages}

`TimeField` on ihanteellinen aikojen valitsemiseen ja näyttämiseen sovelluksessasi. Tässä on joitakin esimerkkejä siitä, milloin käyttää `TimeField`-komponenttia:

1. **Tapahtumien aikataulutus**: Aikakentät ovat olennaisia sovelluksissa, joissa asetetaan aikoja tapahtumille, tapaamisille tai kokouksille.

2. **Ajan seuranta ja kirjaaminen**: Aikaa seuraavissa sovelluksissa, kuten työaikakirjanpidossa, tarvitaan aikakenttiä tarkkoja merkintöjä varten.

3. **Muistutukset ja hälytykset**: Aikakentän käyttäminen yksinkertaistaa syöttöprosessia käyttäjille, jotka asettavat muistutuksia tai hälytyksiä sovelluksessasi.

## Min and max value {#min-and-max-value}

`setMin()`- ja `setMax()`-menetelmien avulla voit määrittää hyväksyttävien aikojen alueen.

- **`setMin()` varten**: Jos komponenttiin syötetty arvo on aikaisempi kuin määritetty vähimmäisaika, komponentti epäonnistuu rajoitusvalidoinnissa. Kun sekä min- että max-arvot on asetettu, min-arvon on oltava aika, joka on sama tai aikaisempi kuin max-arvo.

- **`setMax()` varten**: Jos komponenttiin syötetty arvo on myöhäisempi kuin määritetty enimmäisaika, komponentti epäonnistuu rajoitusvalidoinnissa. Kun sekä min- että max-arvot on asetettu, max-arvon on oltava aika, joka on sama tai myöhäisempi kuin min-arvo. 

## Value handling and localization {#value-handling-and-localization}

Sisäisesti `TimeField`-komponentti edustaa arvoaan käyttäen `LocalTime`-objektia `java.time`-paketista. Tämä mahdollistaa kehittäjien vuorovaikuttaa tarkkojen aikaarvojen kanssa riippumatta siitä, miten ne visuaalisesti renderöidään.

Vaikka **asiakaspuolen komponentti näyttää ajan käyttäjän selaimen alueellasi**, analysoitu ja tallennettu formaatti on aina standardoitu muodossa `HH:mm:ss`.

Jos asetat raakatekstiarvon, käytä `setText()`-menetelmää varovasti:

```java
timeField.setText("09:15:00"); // kelvollinen
```

:::warning
 Käyttäessäsi `setText()`-menetelmää, `IllegalArgumentException` heitetään, jos komponentti ei voi analysoida syötettä muodossa `HH:mm:ss`.
:::


:::info Picker UI 
Aikavalitsimen syöttöliittymän ulkoasu riippuu paitsi valitusta alueesta, myös käytettävästä selaimesta ja käyttöjärjestelmästä. Tämä varmistaa automaattisen johdonmukaisuuden käyttöliittymään, johon käyttäjät ovat jo tottuneet.
:::

## Static utilities {#static-utilities}

`TimeField`-luokka tarjoaa myös seuraavat staattiset apumenetelmät:

- `fromTime(String timeAsString)`: Muuttaa aikajonon HH:mm:ss muodossa LocalTime-objektiksi, jota voidaan sitten käyttää tässä luokassa tai muualla.

- `toTime(LocalTime time)`: Muuttaa LocalTime aikajonoksi HH:mm:ss muodossa.

- `isValidTime(String timeAsString)`: Tarkistaa, onko annettu merkkijono kelvollinen HH:mm:ss aika. Tämä palauttaa boolean-arvon true, jos näin on, muuten false.

## Best practices {#best-practices}

- **Tarjoa selkeitä aikamuotoesimerkkejä**: Näytä käyttäjille selvästi odotettu aikamuoto lähellä `TimeField`-komponenttia. Käytä esimerkkejä tai paikkamerkkejä auttaaksesi heitä syöttämään ajan oikein. Jos mahdollista, näytä aikamuoto käyttäjän sijainnin perusteella.

- **Saatavuus**: Käytä `TimeField`-komponenttia saatavuus mielessä, varmistaen, että se täyttää saatavuusstandardit, kuten tarjoamalla kunnolliset etiketit, riittävä väriero ja yhteensopivuus apuvälineiden kanssa.

- **Nollausvaihtoehto**: Tarjoa tapa, jolla käyttäjät voivat helposti tyhjentää `TimeField`-komponentin tyhjään tai oletustilaan.
