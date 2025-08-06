---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: 0d5052fd2f20b391e0eaadbf7c771e5e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

Voit käyttää `NumberField`-komponenttia käyttäjän numeerisen syötteen vastaanottamiseen. Se varmistaa, että vain voimassa olevia numeerisia arvoja syötetään ja tarjoaa kätevän käyttöliittymän numeroiden syöttämiseen.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Kenttäarvo {#field-value}

`NumberField`-komponentti tallentaa arvonsa `Double`-tyyppisenä, mikä mahdollistaa tarkkojen kokonais- ja desimaalilukujen käsittelemisen.

### Nykyisen arvon saaminen {#getting-the-current-value}

Voit hakea käyttäjän syöttämän numeerisen arvon seuraavasti:

```java
Double currentValue = numberField.getValue();
```

### Uuden arvon asettaminen {#setting-a-new-value}

Voit asettaa kentän ohjelmallisesti:

```java
numberField.setValue(42.5);
```

Jos arvoa ei ole syötetty ja oletusarvoa ei ole asetettu, `getValue()` palauttaa `null`.

:::tip
Vaikka kenttä on suunniteltu hyväksymään vain voimassa olevat numeeriset syötteet, pitää muistaa, että taustalla oleva arvo voi olla null. Testaa aina null-arvo ennen tuloksen käyttöä.
:::

## Käytöt {#usages}

`NumberField` on parhaiten käytössä tilanteissa, joissa numeerisen datan kaappaaminen, näyttäminen tai manipulointi on olennaista sovelluksellesi. Tässä muutamia esimerkkejä siitä, milloin käyttää `NumberField`-komponenttia:

1. **Numeeriset syötekentät**: Kun suunnittelet lomakkeita, jotka vaativat numeerisia syötteitä, `NumberField`-komponentin käyttö yksinkertaistaa syöttöprosessia käyttäjille. Tämä on erityisen hyödyllistä sovelluksille, jotka keräävät käyttäjätietoja tai vaativat numeerisia arvoja.

2. **Datan analysointi ja laskelmat**: `NumberField` on erityisen arvokas sovelluksissa, jotka sisältävät datan analysointia, laskelmia tai matemaattisia operaatioita. Ne mahdollistavat käyttäjien numeeristen arvojen syöttämisen tai manipuloinnin tarkasti.

3. **Taloudelliset ja budjetointisovellukset**: Sovellukset, jotka liittyvät taloudellisiin laskelmiin, budjetointiin tai kulujen seurantaan, vaativat usein tarkkaa numeerista syöttöä. `NumberField` varmistaa taloudellisten lukujen tarkan syötön.

4. **Mittaaminen ja yksikkömuunnokset**: Sovelluksissa, jotka käsittelevät mittauksia tai yksikkömuunnoksia, `NumberField` on ihanteellinen numeeristen arvojen syöttämiseen, kuten pituuden, painon tai tilavuuden kanssa.

## Minimi- ja maksimiarvo {#min-and-max-value}

`setMin()`-metodin avulla voit määrittää numero kentässä hyväksyttävän vähimmäisarvon. Jos käyttäjä syöttää arvon, joka on alle tämän raja-arvon, komponentti epäonnistuu rajoitteiden vahvistuksessa ja antaa asianmukaista palautetta.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Vähimmäisarvo: 0.0
```

Erikseen `setMax()`-metodi mahdollistaa enimmäisarvon määrittämisen. Jos käyttäjä syöttää arvon, joka on yli tämän rajan, syöttö hylätään. Kun sekä vähimmäis- että enimmäisarvot on asetettu, enimmäisarvon on oltava suurempi tai yhtä suuri kuin vähimmäisarvo.

```java
numberField.setMax(100.0); // Enimmäisarvo: 100.0
```

Tässä konfiguraatiossa arvon, kuten -5 tai 150, syöttäminen olisi virheellistä, kun taas arvot välillä 0 ja 100 hyväksytään.

## Hienosyvyys {#granularity}

Voit käyttää `setStep()`-metodia määrittääksesi hienosyvyyden, jota arvon on noudatettava, kun käyttäjä käyttää nuolinäppäimiä arvon muuttamiseen. Tämä lisää tai vähentää komponentin arvoa tietyllä askeleella joka kerta. Tämä ei päde, kun käyttäjä syöttää arvon suoraan, vaan vain vuorovaikutuksessa `NumberField`-komponentin kanssa nuolinäppäimillä.

## Paikkateksti {#placeholder-text}

Voit asettaa paikkatekstin `NumberField`-komponentille käyttämällä `setPlaceholder()`-metodia. Paikkateksti näkyy, kun kenttä on tyhjänä, auttaen käyttäjää syöttämään asianmukaisia tietoja `NumberField`-komponenttiin.

:::tip Anna selkeä konteksti tarkkuudelle
Jos numeerinen syöttö liittyy tiettyyn mittayksikköön tai sillä on erityinen konteksti, tarjoa selkeää merkintää tai lisätietoa opastaaksesi käyttäjiä ja varmistaaksesi tarkan syöttämisen.
:::

## Parhaat käytännöt {#best-practices}

Saadaksesi saumatonta integraatiota ja optimaalista käyttäjäkokemusta, ota huomioon seuraavat parhaat käytännöt käytettäessä `NumberField`-komponenttia:

- **Saavutettavuus**: Käytä `NumberField`-komponenttia huomioiden saavutettavuus, noudattaen saavutettavuusstandardeja, kuten asianmukaiset merkinnät, näppäimistön navigointituen ja yhteensopivuus apuvälineiden kanssa. Varmista, että käyttäjät, joilla on vamma, voivat vuorovaikuttaa `NumberField`-komponentin kanssa tehokkaasti.

- **Hyödynnä lisäys-/vähennysnappeja**: Jos se on sovelluksesi kannalta tarkoituksenmukaista, harkitse lisäys- ja vähennysnapien hyödyntämistä `NumberField`-komponentissa. Tämä mahdollistaa käyttäjien säätää numeerista arvoa tietyllä lisäyksellä tai vähennyksellä yhdellä napsautuksella.
