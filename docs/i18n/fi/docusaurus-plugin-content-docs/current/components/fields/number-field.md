---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: 2fcf0727f1bcfd60a2800bad252733ba
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

Voit käyttää `NumberField`-komponenttia käyttäjän syöttämän numeerisen arvon vastaanottamiseen. Se varmistaa, että vain kelvollisia numeerisia arvoja syötetään, ja tarjoaa kätevän käyttöliittymän numeroiden syöttämiseen.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Kentän arvo {#field-value}

`NumberField`-komponentti tallentaa arvonsa `Double`-tyyppiseksi, mikä mahdollistaa sekä kokonais- että desimaaliarvojen tarkan käsittelyn.

### Nykyisen arvon hankkiminen {#getting-the-current-value}

Voit noutaa käyttäjän syötteen numeerisen arvon näin:

```java
Double currentValue = numberField.getValue();
```

### Uuden arvon asettaminen {#setting-a-new-value}

Voit asettaa kentän ohjelmallisesti:

```java
numberField.setValue(42.5);
```

Jos arvoa ei ole syötetty eikä oletusarvoa ole asetettu, `getValue()` palauttaa `null`.

:::tip
Vaikka kenttä on suunniteltu hyväksymään vain kelvollista numeerista syöttöä, muista, että taustalla oleva arvo voi olla null. Testaa aina null-arvo ennen tuloksen käyttämistä.
:::

## Käyttötapaukset {#usages}

`NumberField`-komponentti on paras käyttää tilanteissa, joissa numeeristen tietojen tallentaminen, näyttäminen tai käsitteleminen on tärkeää sovelluksellesi. Tässä on joitakin esimerkkejä tilanteista, joissa `NumberField`-komponenttia tulisi käyttää:

1. **Numeeriset Syöttölomakkeet**: Kun suunnittelet lomakkeita, jotka vaativat numeerisia syötteitä, `NumberField`-komponentin käyttö yksinkertaistaa syöttöprosessia käyttäjille. Tämä on erityisen hyödyllistä sovelluksille, jotka keräävät käyttäjätietoja tai vaativat numeerisia arvoja.

2. **Datan Analysointi ja Laskelmat**: `NumberField` on erityisen arvokas sovelluksissa, joissa käsitellään dataa, laskelmia tai matemaattisia operaatioita. Se mahdollistaa käyttäjien syöttää tai käsitellä numeerisia arvoja tarkasti.

3. **Rahoitus- ja Budjetointisovellukset**: Sovellukset, jotka käsittelevät rahoituslaskelmia, budjetointia tai kulujen seurantaa, vaativat usein tarkkoja numeerisia syötteitä. `NumberField` varmistaa rahoituslukujen tarkan syöttämisen.

4. **Mittaaminen ja Yksikkömuunnokset**: Sovelluksissa, jotka käsittelevät mittauksia tai yksikkömuunnoksia, `NumberField` on ihanteellinen numeeristen arvojen syöttämiseen, joissa on yksiköitä, kuten pituus, paino tai tilavuus.

## Minimi- ja maksimiarvo {#min-and-max-value}

`setMin()`-metodin avulla voit määrittää kentän sääntöihin hyväksyttävän vähimmäisarvon. Jos käyttäjä syöttää arvon, joka on alle tämän rajan, komponentti ei läpäise rajoitteen vahvistusta ja antaa asianmukaista palautetta.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Sallittu vähimmäisarvo: 0.0
```

Erikseen `setMax()`-metodi antaa sinun määrittää hyväksyttävä enimmäisarvo. Jos käyttäjä syöttää arvon, joka on tämän rajan yläpuolella, syöttö hylätään. Kun sekä vähimmäis- että enimmäisarvot on asetettu, enimmäisarvon on oltava suurempi tai yhtä suuri kuin vähimmäisarvo.

```java
numberField.setMax(100.0); // Sallittu enimmäisarvo: 100.0
```

Tässä asetuksessa, arvo kuten -5 tai 150 olisi väärä, kun taas arvot 0 ja 100 hyväksytään.

## Tarkkuus {#granularity}

Voit käyttää `setStep()`-metodia määrittääksesi tarkan arvon, johon arvon on noudatettava nuolinäppäimiä käyttöä varten. Tämä kasvattaa tai pienentää komponentin arvoa tietyllä askeleella joka kerta. Tämä ei koske, kun käyttäjä syöttää arvon suoraan, vaan vain vuorovaikutuksessa `NumberField`-komponentin kanssa nuolinäppäimillä.

## Paikkamerkki {#placeholder-text}

Voit asettaa paikkamerkki tekstin `NumberField`-komponentille käyttämällä `setPlaceholder()`-metodia. Paikkamerkki näkyy, kun kenttä on tyhjänä, mikä auttaa ohjaamaan käyttäjää syöttämään oikeaa tietoa `NumberField`-komponenttiin.

:::tip Anna selkeä konteksti tarkkuuden varmistamiseksi
Jos numeerinen syöttö liittyy tiettyyn mittayksikköön tai sillä on erityinen konteksti, tarjoa selkeä merkintä tai lisätietoa ohjataksesi käyttäjiä ja varmistaaksesi tarkan syötön.
:::

## Parhaat käytännöt {#best-practices}

Varman integraation ja optimaalisen käyttäjäkokemuksen varmistamiseksi, harkitse seuraavia parhaita käytäntöjä käyttäessäsi `NumberField`-komponenttia:

- **Saavutettavuus**: Hyödynnä `NumberField`-komponenttia saavutettavuus huomioiden, noudattaen saavutettavuusstandardeja, kuten oikeiden merkintöjen, näppäimistön navigointituen ja yhteensopivuuden apuvälineiden kanssa. Varmista, että käyttäjät, joilla on vammoja, voivat vuorovaikuttaa `NumberField`-komponentin kanssa tehokkaasti.

- **Hyödynnä Kasvatus/Pienennysnuppeja**: Jos se on sovelluksellesi sopivaa, harkitse kasvatus- ja pienennysnappien käyttämistä `NumberField`-komponentin yhteydessä. Tämä antaa käyttäjille mahdollisuuden säätää numeerista arvoa tietyllä kasvutuksella tai pienennyksellä yhdellä napsautuksella.
