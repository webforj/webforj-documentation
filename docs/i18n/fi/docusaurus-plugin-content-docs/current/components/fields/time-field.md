---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: ca6e544259fc218b59cebd14d34e4530
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` on käyttöliittymäkomponentti, joka mahdollistaa käyttäjien syöttää tai valita aikoja tunneissa, minuuteissa ja valinnaisesti sekunneissa. Se tarjoaa intuitiivisen ja tehokkaan tavan käsitellä aikaan liittyvää tietoa erilaisissa sovelluksissa.

<!-- INTRO_END -->

## Käyttäminen `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yhteisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraavassa esimerkissä luodaan muistutus `TimeField`, joka on alustettu nykyiseen aikaan.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Käyttötapaukset {#usages}

`TimeField` on ihanteellinen aikojen valintaan ja näyttämiseen sovelluksessasi. Tässä on joitakin esimerkkejä siitä, milloin käyttää `TimeField`:ia:

1. **Tapahtumien aikatauluttaminen**: Aikakentät ovat välttämättömiä sovelluksissa, joissa asetetaan aikoja tapahtumille, tapaamisille tai kokouksille.

2. **Ajan seuranta ja kirjaaminen**: Sovellukset, jotka seuraavat aikaa, kuten työaikakirjanpito, tarvitsevat aikakenttiä tarkkoja merkintöjä varten.

3. **Muistutukset ja hälytykset**: Aikakentän käyttäminen yksinkertaistaa syöttöprosessia käyttäjille, jotka asettavat muistutuksia tai hälytyksiä sovelluksessasi.

## Minimi ja maksimiarvo {#min-and-max-value}

`setMin()`- ja `setMax()`-metodien avulla voit määrittää hyväksyttävien aikojen alueen.

- **`setMin()`-metodille**: Jos komponenttiin syötetty arvo on aikaisempi kuin määritetty minimiaika, komponentti epäonnistuu vaatimustenmukaisuuden tarkistuksessa. Kun sekä minimi- että maksimiarvot on asetettu, minimivälin aikarajan on oltava sama tai aikaisempi kuin maksimiarvo.

- **`setMax()`-metodille**: Jos komponenttiin syötetty arvo on myöhäisempi kuin määritetty maksimiaika, komponentti epäonnistuu vaatimustenmukaisuuden tarkistuksessa. Kun sekä minimi- että maksimiarvot on asetettu, maksimivälin aikarajan on oltava sama tai myöhäisempi kuin minimiväli.

## Arvojen käsittely ja lokalisaatio {#value-handling-and-localization}

Sisäisesti `TimeField`-komponentti edustaa arvoaan käyttämällä `LocalTime`-objektia `java.time`-paketista. Tämä mahdollistaa kehittäjille vuorovaikutuksen täsmällisten aikavälin arvojen kanssa riippumatta siitä, miten ne visualisoidaan.

Vaikka **asiakaspään komponentti näyttää ajan käyttäjän selaimen paikallisessa muodossa**, jäsennelty ja tallennettu muoto on aina standardoitu muodossa `HH:mm:ss`.

Jos asetat raakatekstiarvon, käytä `setText()`-metodia varovaisesti:

```java
timeField.setText("09:15:00"); // voimassa
```

:::warning
 Kun käytät `setText()`-metodia, `IllegalArgumentException` heitetään, jos komponentti ei voi jäsentää syötettä muodossa `HH:mm:ss`.
:::


:::info Valitsimen UI 
Aikavalitsimen syötteen käyttöliittymän ulkoasu riippuu ei ainoastaan valitusta paikallisuudesta, vaan myös käytetystä selaimesta ja käyttöjärjestelmästä. Tämä takaa automaattisen yhteensopivuuden käyttöliittymän kanssa, johon käyttäjät ovat jo tottuneet.
:::

## Staattiset työkalut {#static-utilities}

`TimeField`-luokka tarjoaa myös seuraavat staattiset utiliittimet:

- `fromTime(String timeAsString)`: Muunna aikamerkkijono HH:mm:ss-muodossa LocalTime-objektiin, jota voidaan sitten hyödyntää tässä luokassa tai muualla.

- `toTime(LocalTime time)`: Muunna LocalTime aikamerkkijonoksi HH:mm:ss-muodossa.

- `isValidTime(String timeAsString)`: Tarkista, onko annettu merkkijono voimassa oleva HH:mm:ss-aika. Tämä palauttaa boolean-arvon true, jos näin on, muuten false.

## Parhaat käytännöt {#best-practices}

- **Tarjoa selkeitä aikamuotoesimerkkejä**: Näytä käyttäjille selvästi odotettu aikamuoto `TimeField`in läheisyydessä. Käytä esimerkkejä tai paikkamerkkejä auttaaksesi heitä syöttämään ajan oikein. Jos mahdollista, näytä aikamuoto käyttäjän sijainnin perusteella.

- **Saavutettavuus**: Hyödynnä `TimeField`-komponenttia saavutettavuus mielessä pitäen, varmistaen että se täyttää saavutettavuusstandardit, kuten tarjoamalla asianmukaiset etiketit, riittävä väriarvo ja yhteensopivuus apuvälineiden kanssa.

- **Nollausvaihtoehto**: Tarjoa käyttäjille tapa tyhjentää `TimeField` helposti tyhjään tai oletustilaan.
