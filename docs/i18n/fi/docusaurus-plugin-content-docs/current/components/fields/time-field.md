---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: aa5cbd6fb54c91be419380eeaf26e65b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` on käyttöliittymäkomponentti, joka mahdollistaa käyttäjien syöttää tai valita aikoja tunneissa, minuuteissa ja mahdollisesti sekunneissa. Se tarjoaa intuitiivisen ja tehokkaan tavan käsitellä ajallisesti liittyvää tietoa eri sovelluksissa.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Käytöt {#usages}

`TimeField` on ihanteellinen aikoja valittaessa ja näyttäessä sovelluksessasi. Tässä on joitain esimerkkejä siitä, milloin käyttää `TimeField`:

1. **Tapahtumien aikataulutus**: Aikakentät ovat olennaisia sovelluksille, jotka liittyvät aikojen asettamiseen tapahtumille, tapaamisille tai kokouksille.

2. **Ajan seuranta ja kirjaus**: Sovellukset, jotka seuraavat aikaa, kuten työajanseurantataulukot, tarvitsevat aikakenttiä tarkkojen tietojen kirjaamiseen.

3. **Muistutukset ja hälytykset**: Aikakentän käyttö yksinkertaistaa sisäänsyöttöprosessia käyttäjille, jotka asettavat muistutuksia tai hälytyksiä sovelluksessasi.

## Minimi- ja maksimiarvo {#min-and-max-value}

`setMin()` ja `setMax()` -menetelmien avulla voit määrittää hyväksyttävien aikojen alueen.

- **`setMin()`-menetelmälle**: Jos komponenttiin syötetty arvo on aikaisempi kuin määritetty vähimmäisaika, komponentti ei läpäise rajoitusvalidointia. Kun sekä minimi- että maksimiarvot on asetettu, minimiarvon on oltava aika, joka on sama tai aikaisempi kuin maksimiarvo.

- **`setMax()`-menetelmälle**: Jos komponenttiin syötetty arvo on myöhäisempi kuin määritetty enimmäisaika, komponentti ei läpäise rajoitusvalidointia. Kun sekä minimi- että maksimiarvot on asetettu, maksimiarvon on oltava aika, joka on sama tai myöhempi kuin minimiarvo.

## Arvojen käsittely ja lokalisointi {#value-handling-and-localization}

Sisäisesti `TimeField`-komponentti esittää arvonsa `LocalTime`-objektin avulla `java.time`-paketista. Tämä mahdollistaa kehittäjille tarkkojen aikaarvojen käsittelyn riippumatta siitä, kuinka ne esitetään visuaalisesti.

Vaikka **asiakkaan puolen komponentti näyttää ajan käyttäjän selaimen paikallisasetusten mukaan**, purettu ja tallennettu muoto on aina standardoitu muotoon `HH:mm:ss`.

Jos asetat raakatekstin arvon, käytä `setText()`-menetelmää varovaisesti:

```java
timeField.setText("09:15:00"); // voimassa
```

:::warning
 Kun käytät `setText()`-menetelmää, `IllegalArgumentException` heitetään, jos komponentti ei voi jäsentää sisäänsyöttöä muodossa `HH:mm:ss`.
:::

:::info Valitsimen käyttöliittymä 
Ajan valitsimen syöttöliittymän ulkonäkö riippuu paitsi valitusta paikallisasetuksesta myös käytetystä selaimesta ja käyttöjärjestelmästä. Tämä varmistaa automaattisen johdonmukaisuuden käyttöliittymässä, johon käyttäjät ovat jo tottuneet.
:::

## Staattiset apuohjelmat {#static-utilities}

`TimeField`-luokka tarjoaa myös seuraavat staattiset apuohjelmat:

- `fromTime(String timeAsString)`: Muuntaa aikana olevan merkkijonon HH:mm:ss-muodossa `LocalTime`-objektiksi, jota voidaan sitten käyttää tämän luokan kanssa tai muualla.

- `toTime(LocalTime time)`: Muuntaa `LocalTime`-ajan merkkijonoksi HH:mm:ss-muodossa.

- `isValidTime(String timeAsString)`: Tarkistaa, onko annettu merkkijono voimassa olevan HH:mm:ss-ajan. Tämä palauttaa boolean-arvon true, jos näin on, muuten false.

## Parhaat käytännöt {#best-practices}

- **Tarjoa selkeät aikamuodon esimerkit**: Näytä käyttäjille selkeästi odotettu aikamuoto lähellä `TimeField`:ia. Käytä esimerkkejä tai paikkamerkkejä auttaaksesi heitä syöttämään ajan oikein. Jos mahdollista, näytä aikamuoto käyttäjän sijainnin mukaan.

- **Saavutettavuus**: Hyödynnä `TimeField`-komponenttia saavutettavuus mielessä pitäen, varmistaen että se täyttää saavutettavuusstandardit, kuten oikeiden etikettien tarjoamisen, riittävän värieron ja yhteensopivuuden apuvälineiden kanssa.

- **Nollausvaihtoehto**: Tarjoa käyttäjille tapa tyhjentää `TimeField` helposti tyhjään tai oletustilaan.
