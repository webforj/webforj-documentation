---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: a61f487d0d763856c6055898a7284011
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` tarjoaa visuaalisia vihjeitä varmistaakseen, että käyttäjät ovat tietoisia käynnissä olevista prosesseista, estäen heitä vuorovaikuttamasta järjestelmän kanssa liian aikaisin. Se kattaa tyypillisesti koko sovelluksen käyttöliittymän globaalien toimintojen aikana.

Kun [`Loading`](../components/loading) komponentti keskittyy tiettyihin alueisiin tai komponentteihin sovelluksessa, `BusyIndicator` käsittelee globaaleja, sovellustason prosesseja ja estää vuorovaikutuksen koko käyttöliittymässä. Tämä erottava tekijä tekee [`Loading`](../components/loading) komponentista erinomaisen enemmän paikallisiin, komponenttikohtaisiin tilanteisiin, kuten tietojen lataamiseen tietyn sivun osan sisällä. Sen sijaan `BusyIndicator` on sopiva järjestelmätason operaatioihin, jotka vaikuttavat koko sovellukseen, kuten sovelluksen alustamiseen tai merkittävän tietosynkronoinnin suorittamiseen.

## Perustiedot {#basics}

`BusyIndicator` webforJ:ssa näkyy yksinkertaisena pyörijänä, mikä tekee siitä helppokäyttöisen ilman asetuksia. Voit kuitenkin mukauttaa sitä lisäämällä viestin, säätämällä pyörijän teemaa tai muokkaamalla näkyvyysasetuksia. Tämä mahdollistaa lisätiedon tai tyylin tarjoamisen säilyttäen samalla toimivan, valmiin ratkaisun.

Tässä esimerkissä `BusyIndicator` estää kaikki käyttäjätoimet koko käyttöliittymässä, kunnes operaatio on valmis.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Taustat {#backdrops}

`BusyIndicator` komponentti webforJ:ssa mahdollistaa taustan näyttämisen estääksee käyttäjävuorovaikutuksen, kun prosessi on käynnissä. Oletusarvoisesti komponentti mahdollistaa taustan, mutta voit halutessasi kytkeä sen pois päältä.

`BusyIndicator` näyttää taustan oletusarvoisesti. Voit hallita taustan näkyvyyttä käyttämällä `setBackdropVisible()` metodia, kuten alla on esitetty:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Poistaa taustan käytöstä
busyIndicator.open();
```
:::info Taustan Poistaminen
Vaikka kytket taustan pois päältä, `BusyIndicator` komponentti estää edelleen käyttäjävuorovaikutuksen varmistaakseen, että taustalla oleva prosessi valmistuu keskeytyksettä. Tausta vain hallitsee visuaalista peittoa, ei vuorovaikutuksen estämiskäyttäytymistä.
:::

## `Spinner` {#spinner}

`BusyIndicator` komponentti webforJ:ssa sisältää `Spinner`in, joka visuaalisesti ilmoittaa, että taustatoiminto on käynnissä. Voit mukauttaa tätä pyörijää useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Tässä on esimerkki siitä, kuinka voit mukauttaa pyörijää `BusyIndicator` komponentin sisällä:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Käyttötapaukset {#use-cases}
- **Sivun Laajuinen Prosessointi**  
   `BusyIndicator` soveltuu hyvin suurempiin, sivun laajuisiin operaatioihin, kuten silloin, kun käyttäjä käynnistää tehtävän, joka vaikuttaa koko sivuun, kuten tiedoston lataamiseen tai tietojen käsittelyyn useilla alueilla. Se voi ilmoittaa käyttäjille, että koko sovellus on käynnissä, estäen lisävaihtoehtojen valitsemisen, kunnes prosessi on valmis.

- **Kriittiset Järjestelmätoiminnot**  
   Kun suoritetaan järjestelmän kannalta kriittisiä tehtäviä, kuten tietojen synkronointia, järjestelmälaajuisia päivityksiä tai arkaluontoisen tiedon käsittelyä, `BusyIndicator` antaa selkeää visuaalista palautetta siitä, että merkittävä operaatio on käynnissä, jolloin käyttäjä voi odottaa, kunnes se on valmis.

- **Asynkroniset Tiedon Lataukset**  
   Tapahtumissa, joissa on mukana asynkronista tiedonkäsittelyä, kuten useiden rajapintojen kutsu tai monimutkaisten laskentojen odottaminen, `BusyIndicator` komponentti ilmoittaa aktiivisesti, että järjestelmä on varattu, kehottamalla käyttäjiä odottamaan, ennen kuin he tekevät lisätoimia.

## Tyylittely {#styling}

<TableBuilder name="BusyIndicator" />
