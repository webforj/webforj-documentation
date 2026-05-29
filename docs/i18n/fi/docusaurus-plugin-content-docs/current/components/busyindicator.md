---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 456b6118cd6219f530c5292611ba46e0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` on täysikokoinen peite, joka ilmoittaa meneillään olevasta prosessista ja estää käyttäjän vuorovaikutuksen, kunnes se on valmis. Se kattaa koko käyttöliittymän operaation aikana, kuten alustuksessa tai tietojen synkronoinnissa. Kun [`Loading`](../components/loading) -komponentti keskittyy käyttöliittymän tiettyihin alueisiin, `BusyIndicator` vaikuttaa globaalisti.

<!-- INTRO_END -->

## Perusteet {#basics}

`BusyIndicator` webforJ:ssä näkyy yksinkertaisena pyörijänä, joten sen käyttäminen on helppoa ilman konfigurointia. Voit kuitenkin mukauttaa sitä lisäämällä viestin, säätämällä pyörijän teeman tai muuttamalla näkyvyysasetuksia. Tämä antaa sinulle mahdollisuuden tarjota enemmän kontekstia tai tyyliä säilyttäen samalla toimivan, valmiin ratkaisun.

Esimerkissämme `BusyIndicator` estää kaikki käyttäjätoimet koko käyttöliittymässä, kunnes operaatio on valmis.

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Taustat {#backdrops}

`BusyIndicator`-komponentti webforJ:ssä mahdollistaa taustan näyttämisen, joka estää käyttäjävuorovaikutuksen prosessin aikana. Oletusarvoisesti komponentti ottaa taustan käyttöön, mutta voit halutessasi kytkeä sen pois päältä.

`BusyIndicator` näyttää oletusarvoisesti taustan. Voit hallita taustan näkyvyyttä käyttämällä `setBackdropVisible()`-metodia, kuten alla:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Poistaa taustan käytöstä
busyIndicator.open();
```
:::info Taustan pois päältä kytkeminen
Vaikka poistat taustan käytöstä, `BusyIndicator`-komponentti jatkaa käyttäjävuorovaikutuksen estämistä varmistaakseen, että taustalla oleva prosessi valmistuu keskeytyksettä. Tausta ohjaa vain visuaalista peitettä, ei vuorovaikutuksen estämiskäyttäytymistä.
:::

## `Spinner` {#spinner}

`BusyIndicator`-komponentti webforJ:ssä sisältää `Spinner`-elementin, joka visuaalisesti ilmoittaa, että taustatoiminto on käynnissä. Voit mukauttaa tätä pyörijää useilla vaihtoehdoilla, kuten sen koolla, nopeudella, suunnalla, teemalla ja näkyvyydellä.

Tässä on esimerkki siitä, kuinka voit mukauttaa pyörijää `BusyIndicator`-komponentin sisällä:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Käyttötapaukset {#use-cases}
- **Koko sivun prosessointi**  
   `BusyIndicator` sopii hyvin suuremmille, koko sivua kattaville toiminnoille, kuten kun käyttäjä aloittaa tehtävän, joka vaikuttaa koko sivuun, esimerkiksi tiedoston lataaminen tai tietojen käsittely useissa osissa. Se voi tiedottaa käyttäjille, että koko sovellus on työssä, estäen lisävuorovaikutukset, kunnes prosessi on valmis.

- **Kriittiset järjestelmätoiminnot**  
   Kun suoritetaan järjestelmälle kriittisiä tehtäviä, kuten tietojen synkronointia, järjestelmälaajuisen päivityksen soveltamista tai arkansanoinnin käsittelyä, `BusyIndicator` antaa selkeää visuaalista palautetta siitä, että merkittävä operaatio on käynnissä, jolloin käyttäjän on odotettava, kunnes se on valmis.

- **Asynkroniset tietojen lataukset**  
   Scenaarioissa, joissa on mukana asynkronista tietojen käsittelyä, kuten useiden API-kutsujen tekemisessä tai monimutkaisten laskentojen odottamisessa, `BusyIndicator`-komponentti ilmoittaa aktiivisesti, että järjestelmä on varattu, kehottamalla käyttäjiä odottamaan ennen lisätoimien suorittamista.

## Tyylittely {#styling}

<TableBuilder name="BusyIndicator" />
