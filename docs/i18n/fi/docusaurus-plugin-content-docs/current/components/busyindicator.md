---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 30ca15f8b8170f6d7da6a786ddafea7f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` on täydellinen näyttöpeitto, joka ilmoittaa meneillään olevasta prosessista ja estää käyttäjäinteraktion, kunnes se on valmis. Se peittää koko käyttöliittymän toiminnoissa, kuten aloituksessa tai datan synkronoinnissa. Kun [`Loading`](../components/loading) komponentti keskittyy tiettyihin alueisiin käyttöliittymässä, `BusyIndicator` soveltuu globaaliin käyttöön.

<!-- INTRO_END -->

## Perusteet {#basics}

`BusyIndicator` webforJ:ssä näkyy yksinkertaisena pyörijänä, mikä tekee siitä helppokäyttöisen ilman konfigurointia. Voit kuitenkin mukauttaa sitä lisäämällä viestin, säätämällä pyörijän teemaa tai muokkaamalla näkyvyysasetuksia. Tämä antaa sinulle mahdollisuuden tarjota enemmän kontekstia tai tyyliä samalla, kun säilytät toimivan, valmiina olevan ratkaisun.

Esimerkissä `BusyIndicator` estää käyttäjän kaikki toiminnot koko käyttöliittymässä, kunnes operaatio on valmis.

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Taustat {#backdrops}

`BusyIndicator` komponentti webforJ:ssä antaa sinun näyttää taustakuvan estämään käyttäjäinteraktion, kun prosessi on käynnissä. Oletuksena komponentti mahdollistaa taustakuvan, mutta voit valita sen poistamisen käytöstä tarvittaessa.

`BusyIndicator` näyttää taustakuvan oletuksena. Voit hallita taustakuvan näkyvyyttä käyttämällä `setBackdropVisible()` metodia, kuten alla on esitetty:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Poistaa taustakuvan käytöstä
busyIndicator.open();
```
:::info Taustakuvan poistaminen käytöstä
Vaikka poistat taustakuvan käytöstä, `BusyIndicator` komponentti estää edelleen käyttäjäinteraktion varmistaakseen, että taustalla oleva prosessi valmistuu keskeytyksettä. Taustakuva ohjaa vain visuaalista peittoa, ei interaktion estämiskäyttäytymistä.
:::

## `Spinner` {#spinner}

`BusyIndicator` komponentti webforJ:ssä sisältää `Spinnerin`, joka visuaalisesti ilmoittaa taustatoiminnasta. Voit mukauttaa tätä pyörijää useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Tässä on esimerkki siitä, miten voit mukauttaa pyörijää `BusyIndicator` komponentin sisällä:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Käyttötapaukset {#use-cases}
- **Koko sivun prosessointi**
   `BusyIndicator` sopii erinomaisesti suurempiin, koko sivun toimiin, kuten silloin, kun käyttäjä aloittaa tehtävän, joka vaikuttaa koko sivuun, kuten tiedoston lataaminen tai tietojen käsitteleminen useilla alueilla. Se voi ilmoittaa käyttäjille, että koko sovellus on toiminnassa, estäen lisäinteraktiot, kunnes prosessi on valmis.

- **Kriittiset järjestelmätoiminnot**
   Kun suoritetaan järjestelmän kannalta kriittisiä tehtäviä, kuten tietojen synkronointia, järjestelmätason päivitysten soveltamista tai arkaluonteisten tietojen käsittelyä, `BusyIndicator` antaa selkeää visuaalista palautetta siitä, että suuri operaatio on käynnissä, jolloin käyttäjä voi odottaa sen valmistumista.

- **Asynkroniset tietolataukset**
   Situatioissa, joissa on mukana asynkronista tietojenkäsittelyä, kuten useiden API:en kutsuissa tai monimutkaisten laskentojen odottamisessa, `BusyIndicator` komponentti aktivisesti ilmoittaa, että järjestelmä on varattu, kehottamalla käyttäjiä odottamaan ennen lisätoimia.

## Tyylittely {#styling}

<TableBuilder name="BusyIndicator" />
