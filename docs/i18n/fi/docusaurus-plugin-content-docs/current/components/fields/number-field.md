---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: e1cde7099182ddabd898e0c5391fe8b7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

`NumberField`-komponentti hyväksyy numeerisen syötteen ja hylkää virheelliset arvot automaattisesti. Se tukee minimi- ja maksimiarvoja, askelvälejä ja paikkamerkintätekstiä.

<!-- INTRO_END -->

## Käyttäminen `NumberField` {#using-numberfield}

<ParentLink parent="Field" />

`NumberField` laajentaa yhteistä `Field`-luokkaa, joka tarjoaa yhteisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki luo `NumberField`-komponentin, jossa on etiketti ja paikkamerkintäteksti.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Kentän arvo {#field-value}

`NumberField`-komponentti tallentaa arvonsa `Double`-tyyppiseen muotoon, mikä mahdollistaa sekä kokonaislukujen että desimaalilukujen tarkan käsittelyn.

### Nykyisen arvon hakeminen {#getting-the-current-value}

Voit noutaa käyttäjän syöttämän numeerisen arvon seuraavasti:

```java
Double currentValue = numberField.getValue();
```

### Uuden arvon asettaminen {#setting-a-new-value}

Aseta kenttäohjelmallisesti:

```java
numberField.setValue(42.5);
```

Jos arvoa ei ole syötetty eikä oletusarvoa ole asetettu, `getValue()` palauttaa `null`.

:::tip
Vaikka kenttä on suunniteltu hyväksymään vain voimassa olevaa numeerista syötettä, pidä mielessä, että taustalla oleva arvo voi olla nolla. Testaa aina muuttujan null-arvo ennen kuin käytät tulosta.
:::

## Käyttötarkoitukset {#usages}

`NumberField` on parasta käyttää tilanteissa, joissa numeeristen tietojen kerääminen, näyttäminen tai käsittely on olennaista sovelluksellesi. Tässä on joitakin esimerkkejä siitä, milloin käyttää `NumberField`-komponenttia:

1. **Numeeriset syöte lomakkeet**: Kun suunnittelet lomakkeita, jotka vaativat numeerisia syötteitä, `NumberField`-komponentti yksinkertaistaa syöttöprosessia käyttäjille. Tämä on erityisen hyödyllistä sovelluksille, jotka keräävät käyttäjätietoja tai vaativat numeerisia arvoja.

2. **Datankäsittely ja laskelmat**: `NumberField` on erityisen arvokas sovelluksissa, joissa käsitellään dataa, laskelmia tai matemaattisia operaatioita. Ne mahdollistavat käyttäjien syöttää tai manipuloida numeerisia arvoja tarkasti.

3. **Rahoitus- ja budjetointisovellukset**: Sovellukset, jotka käsittelevät taloudellisia laskelmia, budjetointia tai kulujen seurantaa, vaativat usein tarkkoja numeerisia syötteitä. `NumberField` varmistaa taloudellisten lukujen tarkan syöttämisen.

4. **Mittaaminen ja yksikön muuntaminen**: Sovelluksissa, joissa käsitellään mittauksia tai yksikön muuntamista, `NumberField` on ihanteellinen numeeristen arvojen syöttämiseen, joissa on yksiköitä, kuten pituus, paino tai tilavuus.

## Minimi- ja maksimimäärä {#min-and-max-value}

`setMin()`-metodin avulla voit määrittää vähimmäis hyväksyttävän arvon numeerisessa kentässä. Jos käyttäjä syöttää tätä alhaisemman arvon, komponentti epäonnistuu rajoitustarkistuksessa ja antaa asianmukaista palautetta.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Vähimmäisarvo: 0.0
```

Erikseen `setMax()`-metodi mahdollistaa maksimimäärän määrittämisen. Jos käyttäjä syöttää tätä korkeamman arvon, syöttö hylätään. Kun sekä vähimmäis- että enimmäisarvot on asetettu, enimmäisarvon on oltava suurempi tai yhtä suuri kuin vähimmäisarvo.

```java
numberField.setMax(100.0); // Maksimiarvo: 100.0
```

Tässä konfiguraatiossa arvojen, kuten -5 tai 150 syöttäminen olisi virheellistä, kun taas arvot, jotka ovat 0 ja 100 välillä, hyväksytään.

## Tarkkuus {#granularity}

Voit käyttää `setStep()`-metodia määrittääksesi tarkkuuden, joka arvon on noudatettava, kun käytetään nuolinäppäimiä arvon muuttamiseen. Tämä lisää tai vähentää komponentin arvoa tietyllä askeleella joka kerta. Tämä ei koske suoraa syöttöä, vaan vain vuorovaikutusta `NumberField`-komponentin kanssa nuolinäppäimillä.

## Paikkamerkintäteksti {#placeholder-text}

Voit asettaa paikkamerkintätekstiä `NumberField`-komponentille käyttämällä `setPlaceholder()`-metodia. Paikkamerkintäteksti näytetään, kun kenttä on tyhjää, ja se auttaa ohjaamaan käyttäjää syöttämään sopivaa tietoa `NumberField`-kenttään.

:::tip Anna selkeä konteksti tarkkuuden takaamiseksi
Jos numeerinen syöte liittyy tiettyyn mittayksikköön tai sillä on erityinen konteksti, tarjoa selkeää merkintää tai lisätietoa, jotta käyttäjät voivat syöttää tarkkoja arvoja.
:::

## Parhaat käytännöt {#best-practices}

Saavuttaaksesi saumatonta integrointia ja optimaalista käyttökokemusta, ota huomioon seuraavat parhaat käytännöt käytettäessä `NumberField`-komponenttia:

- **Saavutettavuus**: Hyödynnä `NumberField`-komponenttia saavutettavuus mielessä, noudattaen saavutettavuusstandardeja, kuten oikeaa merkintää, näppäimistön navigointitukea ja yhteensopivuutta apuvälineiden kanssa. Varmista, että käyttäjät, joilla on erityisiä tarpeita, voivat vuorovaikuttaa `NumberField`-komponentin kanssa tehokkaasti.

- **Hyödynnä lisäys/poistamisen painikkeita**: Jos se on sovelluksellesi sopivaa, harkitse lisäys- ja poisto-painikkeiden käyttämistä `NumberField`-komponentin kanssa. Tämä auttaa käyttäjiä säätämään numeerista arvoa tietyllä lisäyksellä tai vähennyksellä yhdellä napsautuksella.
