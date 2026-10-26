---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9b4c187f1c86207e13b38812f0eb7e6c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` on käyttöliittymäkomponentti, joka mahdollistaa käyttäjien ajan syöttämisen tai valitsemisen tunti-minuutti-tarkkuudella. Jos sekunnit annetaan, komponentti hylkää ne.

<!-- INTRO_END -->

## Käyttö `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yleisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki luo muistutuksen `TimeField`, joka on alustettu nykyiseen aikaan.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Käyttötarkoitukset {#usages}

`TimeField` on ihanteellinen ajan valitsemiseen ja esittämiseen sovelluksessasi. Tässä on joitakin esimerkkejä siitä, milloin käyttää `TimeField`-komponenttia:

1. **Tapahtumien Aikatauluttaminen**: Aikakentät ovat olennaisia sovelluksissa, jotka sisältävät aikojen asettamisen tapahtumille, tapaamisille tai kokouksille.

2. **Ajan Seuranta ja Kirjaaminen**: Aikojen seurantaan, kuten työtunteihin, tarvitaan aikakenttiä tarkkojen tietojen syöttämiseen.

3. **Muistutukset ja Hälytykset**: Aikakentän käyttö yksinkertaistaa syöttöprosessia käyttäjille, jotka asettavat muistutuksia tai hälytyksiä sovelluksessasi.

## Minimija maksimiarvo {#min-and-max-value}

`setMin()`- ja `setMax()`-metodien avulla voit määrittää hyväksyttävien aikojen alueen.

- **`setMin()`-metodille**: Jos komponenttiin syötetty arvo on aikaisempi kuin määritetty minimiaika, komponentti epäonnistuu rajoitusvalidoinnissa. Kun sekä min- että max-arvot on asetettu, minimiajan on oltava sama tai aikaisempi kuin maksimiaika.

- **`setMax()`-metodille**: Jos komponenttiin syötetty arvo on myöhäisempi kuin määritetty maksimiaika, komponentti epäonnistuu rajoitusvalidoinnissa. Kun sekä min- että max-arvot on asetettu, maksimiajan on oltava sama tai myöhäisempi kuin minimaika.

## Arvon käsittely ja lokalisointi {#value-handling-and-localization}

Sisäisesti `TimeField`-komponentti edustaa arvoaan käyttäen `LocalTime`-objektia `java.time`-paketista. Tämä mahdollistaa kehittäjille vuorovaikuttaa tarkkojen aikojen kanssa riippumatta siitä, miten ne näytetään visuaalisesti.

Selaimen perusteella valitaan, miten valitsija esittää ajan käyttäjän paikallisessa ympäristössä. Kentän tekstiarvo käyttää 24 tunnin `HH:mm`-muotoa, ja sen `LocalTime`-arvo on katkaistu minuutteihin.

Jos asetat raakatekstiarvon, käytä `setText()`-metodia huolellisesti:

```java
timeField.setText("09:15");    // kelpaa
timeField.setText("09:15:30"); // myös kelpaa; sekunnit hylätään, jää vain 09:15
```

:::warning
Kun käytät `setText()`, `IllegalArgumentException` heitetään, jos syötettä ei voida jäsentää kelvolliseksi ajaksi. Sekä `HH:mm`- että `HH:mm:ss`-syötteet hyväksytään, mutta sekunnit hylätään.
:::


:::info Valitsijan käyttöliittymä
Ajanvalitsijan syöttöliittymän ulkonäkö riippuu valitusta paikallisesta ympäristöstä, selaimesta ja käyttöjärjestelmästä. Tämä luo automaattista yhtenäisyyttä käyttöliittymän kanssa, johon käyttäjät ovat jo tottuneet.
:::

## Staattiset työkaluohjelmat {#static-utilities}

`TimeField`-luokka tarjoaa myös seuraavat staattiset työkalumetodit:

- `fromTime(String timeAsString)`: Jäsentää aikatekstin, sekunnit mukaan lukien tai ilman, `LocalTime`-objektiksi, joka on katkaistu minuutteihin.

- `toTime(LocalTime time)`: Muuntaa `LocalTime`-objektin merkkijonoksi `HH:mm`-muodossa, hyläten sekunnit.

- `isValidTime(String timeAsString)`: Tarkistaa, onko aikamerkkijono kelvollinen, mukaan lukien `HH:mm`- ja `HH:mm:ss`-syötteet. Palauttaa `true`, jos se on kelvollinen, ja `false` muuten.

## Parhaat käytännöt {#best-practices}

- **Tarjoa Selkeät Aikamuotoesimerkit**: Näytä käyttäjille selvästi odotettu aikamuoto lähellä `TimeField`-komponenttia. Käytä esimerkkejä tai paikkamerkkejä auttaaksesi heitä syöttämään ajan oikein. Jos mahdollista, näytä aikamuoto käyttäjän sijainnin mukaan.

- **Esteettömyys**: Käytä `TimeField`-komponenttia esteettömyys mielessä pitäen, noudattaen esteettömyysstandardeja, kuten asianmukaisia etikettejä, riittävää värieroa ja yhteensopivuutta apuvälineiden kanssa.

- **Nollausvaihtoehto**: Tarjoa tapa käyttäjille tyhjentää `TimeField` helposti tyhjään tai oletustilaan.
