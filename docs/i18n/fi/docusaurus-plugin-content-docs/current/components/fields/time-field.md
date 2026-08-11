---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9688647e85d453578ccd59934e52e26b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` on käyttöliittymäkomponentti, joka sallii käyttäjien syöttää tai valita aikoja tunneissa, minuuteissa ja tarvittaessa sekunteina. Se tarjoaa intuitiivisen ja tehokkaan tavan käsitellä aikarajoituksia eri sovelluksissa.

<!-- INTRO_END -->

## Käyttö `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yleisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki luo muistutus `TimeField`:n, joka on alustettu nykyiseen aikaan.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Käyttötarkoitukset {#usages}

`TimeField` on ihanteellinen aikojen valitsemiseen ja näyttämiseen sovelluksessa. Tässä on joitakin esimerkkejä, milloin käyttää `TimeField`:ia:

1. **Tapahtumien aikataulutus**: Aikakentät ovat välttämättömiä sovelluksissa, joissa asetetaan aikoja tapahtumille, tapaamisille tai kokoontumisille.

2. **Ajan seuranta ja kirjaaminen**: Sovelluksissa, jotka seuraavat aikaa, kuten työaikakirjanpidossa, tarvitaan aikakenttiä tarkkoihin merkintöihin.

3. **Muistutukset ja hälytykset**: Aikakentän käyttäminen yksinkertaistaa syöttöprosessia käyttäjille, jotka asettavat muistutuksia tai hälytyksiä sovelluksessasi.

## Minimi- ja maksimiarvo {#min-and-max-value}

`setMin()`- ja `setMax()`-menetelmien avulla voit määrittää hyväksyttävien aikojen alueen.

- **`setMin()`-menetelmälle**: Jos komponenttiin syötetty arvo on aikaisempi kuin määritetty minimiaika, komponentti epäonnistuu rajoitusvalidoinnissa. Kun sekä minimi- että maksimiarvot on asetettu, minimiarvon on oltava aikaraja, joka on sama tai aikaisempi kuin maksimiaika.

- **`setMax()`-menetelmälle**: Jos komponenttiin syötetty arvo on myöhempi kuin määritetty maksimiaika, komponentti epäonnistuu rajoitusvalidoinnissa. Kun sekä minimi- että maksimiarvot on asetettu, maksimiarvon on oltava aikaraja, joka on sama tai myöhempi kuin minimiaika.

## Arvon käsittely ja lokalisointi {#value-handling-and-localization}

Sisäisesti `TimeField`-komponentti esittää arvonsa käyttäen `LocalTime`-objektia `java.time`-paketista. Tämä mahdollistaa kehittäjille tarkkojen aikojen käsittelyn riippumatta niiden visuaalisesta esittämisestä.

Vaikka **asiakaspuolen komponentti näyttää ajan käyttäjän selaimen alueasetusten mukaan**, jäsennelty ja tallennettu muoto on aina standardisoitu muotoon `HH:mm:ss`.

Jos asetat raakatulojohdon arvon, käytä `setText()`-menetelmää varovasti:

```java
timeField.setText("09:15:00"); // voimassa
```

:::warning
 Kun käytät `setText()`-menetelmää, heitetään `IllegalArgumentException`, jos komponentti ei voi jäsentää syötettä muodossa `HH:mm:ss`.
:::


:::info Picker UI
Aikavalitsimen käyttöliittymän ulkoasu riippuu paitsi valitusta alueesta myös käytettävästä selaimesta ja käyttöjärjestelmästä. Tämä varmistaa automaattisen johdonmukaisuuden käyttäjille jo tutussa käyttöliittymässä.
:::

## Staattiset työkalut {#static-utilities}

`TimeField`-luokka tarjoaa myös seuraavat staattiset apumenetelmät:

- `fromTime(String timeAsString)`: Muuntaa ajan merkkijonon HH:mm:ss-muodossa `LocalTime`-objektiksi, jota voidaan hyödyntää tässä luokassa tai muualla.

- `toTime(LocalTime time)`: Muuntaa `LocalTime`:n ajaksi merkkijonona HH:mm:ss-muodossa.

- `isValidTime(String timeAsString)`: Tarkistaa, onko annettu merkkijono kelvollinen HH:mm:ss-aika. Tämä palauttaa totuusarvon true, jos näin on, false muuten.

## Parhaat käytännöt {#best-practices}

- **Tarjoa selkeitä aikamuotoesimerkkejä**: Näytä käyttäjille selvästi odotettu aikamuoto `TimeField`:in läheisyydessä. Käytä esimerkkejä tai paikkamerkkejä auttaaksesi heitä syöttämään ajan oikein. Jos mahdollista, näytä aikamuoto käyttäjän sijainnin mukaan.

- **Saatavuus**: Hyödynnä `TimeField`-komponenttia saatavuus huomioiden varmistaen, että se täyttää saatavuusstandardit, kuten tarjoamalla asianmukaiset etiketit, riittävän värieroja ja yhteensopivuutta apuvälineiden kanssa.

- **Nollausvaihtoehto**: Tarjoa käyttäjille tapa tyhjentää `TimeField` helposti tyhjään tai oletustilaan.
