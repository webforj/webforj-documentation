---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: aa5037e2faa2968328081b1811dcabb0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

`NumberField`-komponentti hyväksyy numeerisen syötteen ja hylkää virheelliset arvot automaattisesti. Se tukee minimi- ja maksimirajoja, askelvälejä ja paikkamerkkitekstiä.

<!-- INTRO_END -->

## Käyttö `NumberField` {#using-numberfield}

<ParentLink parent="Field" />

`NumberField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yhteisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki luo `NumberField`:in, jossa on etiketti ja paikkamerkkiteksti.

<ComponentDemo
path='/webforj/numberfield'
files={['src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java']}
/>

## Kentän arvo {#field-value}

`NumberField`-komponentti tallentaa arvonsa `Double`-tyyppisenä, mikä mahdollistaa numeroiden ja desimaalilukuisten arvojen tarkan käsittelyn.

### Nykyisen arvon saaminen {#getting-the-current-value}

Voit palauttaa käyttäjän syöttämän numeerisen arvon seuraavasti:

```java
Double currentValue = numberField.getValue();
```

### Uuden arvon asettaminen {#setting-a-new-value}

Aseta kenttä ohjelmallisesti:

```java
numberField.setValue(42.5);
```

Jos arvoa ei ole syötetty ja vakioarvoa ei ole asetettu, `getValue()` palauttaa `null`.

:::tip
Vaikka kenttä on suunniteltu hyväksymään vain voimassa olevia numeerisia syötteitä, pidä mielessä, että taustalla oleva arvo voi olla `null`. Testaa aina null-arvo ennen kuin käytät tulosta.
:::

## Käyttötavat {#usages}

`NumberField` on parhaiten käytössä skenaarioissa, joissa numeeristen tietojen tallentaminen, näyttäminen tai käsitteleminen on oleellista sovelluksellesi. Tässä on joitakin esimerkkejä, milloin `NumberField`ia kannattaa käyttää:

1. **Numeeriset Syöte Lomakkeet**: Kun suunnittelet lomakkeita, jotka vaativat numeerisia syötteitä, `NumberField`in käyttäminen yksinkertaistaa syöttöprosessia käyttäjille. Tämä on erityisen hyödyllistä sovelluksille, jotka keräävät käyttäjätietoja tai vaativat numeerisia arvoja.

2. **Datan Analysointi ja Laskelmat**: `NumberField` on erityisen arvokas sovelluksille, jotka sisältävät datan analysointia, laskelmia tai matemaattisia operaatioita. Ne mahdollistavat käyttäjien syöttää tai käsitellä numeerisia arvoja tarkasti.

3. **Rahoitus- ja Budjetointisovellukset**: Sovellukset, jotka vaativat rahoituslaskelmia, budjetointia tai kulujen seurantaa, tarvitsevat usein tarkkoja numeerisia syötteitä. `NumberField` varmistaa rahoitustietojen tarkan syöttämisen.

4. **Mittaa ja Yksikkö Muunnos**: Sovelluksissa, jotka käsittelevät mittauksia tai yksikkömuunnoksia, `NumberField` on ihanteellinen numeeristen arvojen syöttämiseen, joissa on yksiköitä, kuten pituus, paino tai tilavuus.

## Minim ja maksimi arvo {#min-and-max-value}

`setMin()`-menetelmällä voit määrittää minimi hyväksyttävän arvon numeroikentässä. Jos käyttäjä syöttää arvon, joka on matalampi kuin tämä kynnys, komponentti epäonnistuu rajoituksen vahvistamisessa ja antaa asianmukaisen palautteen.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Minimi sallittu: 0.0
```

Erikseen `setMax()`-menetelmä mahdollistaa maksimi hyväksyttävän arvon määrittämisen. Jos käyttäjä syöttää arvon, joka on korkeampi kuin tämä raja, syöttö hylätään. Kun sekä minimi- että maksimiarvot on asetettu, maksimiarvon on oltava suurempi tai yhtä suuri kuin minimiarvo.

```java
numberField.setMax(100.0); // Maksimi sallittu: 100.0
```

Tässä konfiguraatiossa arvon, kuten -5 tai 150, syöttäminen olisi virheellistä, kun taas arvot välillä 0 ja 100 hyväksytään.

## Tarkkuus {#granularity}

Voit käyttää `setStep()`-menetelmää määrittääksesi tarkkuuden, jota arvon on noudatettava, kun käytetään nuolinäppäimiä arvon muokkaamiseen. Tämä kasvattaa tai pienentää komponentin arvoa tietyllä askeleella joka kerta. Tämä ei päde, kun käyttäjä syöttää arvon suoraan, vaan vain vuorovaikutuksessa `NumberField`:n kanssa nuolinäppäimillä.

## Paikkamerkkiteksti {#placeholder-text}

Voit asettaa paikkamerkkitekstiä `NumberField`:lle käyttämällä `setPlaceholder()`-menetelmää. Paikkamerkkiteksti näkyy, kun kenttä on tyhjennetty, auttaen käyttäjää syöttämään asianmukaista tietoa `NumberField`:iin.

:::tip Anna selkeä konteksti tarkkuutta varten
Jos numeerinen syöte liittyy tiettyyn mittayksikköön tai sillä on erityinen konteksti, tarjoa selkeä merkintä tai lisätietoa ohjataksesi käyttäjiä ja varmistaaksesi tarkka syöttö.
:::

## Parhaat käytännöt {#best-practices}

Saadaksesi sujuvan integraation ja optimaalisen käyttäjäkokemuksen, harkitse seuraavia parhaita käytäntöjä käyttäessäsi `NumberField`:ia:

- **Saavutettavuus**: Hyödynnä `NumberField`-komponenttia saavutettavuus mielessä pitäen, noudattaen saavutettavuusstandardeja, kuten asianmukaista merkitsemistä, näppäimistön navigointituen ja yhteensopivuutta apuvälineiden kanssa. Varmista, että vammaiset käyttäjät voivat vuorovaikuttaa `NumberField`in kanssa tehokkaasti.

- **Hyödynnä Lisäys/Pienennyspainikkeita**: Jos se on sovelluksellesi sopivaa, harkitse lisäys- ja pienennyspainikkeiden hyödyntämistä `NumberField`in kanssa. Tämä mahdollistaa käyttäjien säätää numeerista arvoa tietyllä lisäyksellä tai pienennyksellä yhdellä napsautuksella.
