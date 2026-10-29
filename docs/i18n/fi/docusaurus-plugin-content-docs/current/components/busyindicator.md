---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: a6ab827c0422e8f10369500acc310007
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` on koko näytön peite, joka merkitsee meneillään olevaa prosessia ja estää käyttäjäinteraktion, kunnes se on valmis. Se kattaa koko käyttöliittymän toimenpiteiden, kuten alustamisen tai tietojen synkronoinnin aikana. Vaikka [`Loading`](../components/loading) komponentti keskittyy tiettyihin alueisiin käyttöliittymässä, `BusyIndicator` vaikuttaa globaalisti.

`BusyIndicator` näkyy pyörijänä ilman, että määrityksiä tarvitaan. Voit lisätä viestin, vaihtaa pyörijän teeman tai säätää näkyvyysasetuksia, kun prosessille tarvitaan enemmän kontekstia.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Taustat {#backdrops}

`BusyIndicator` komponentti webforJ:ssä mahdollistaa taustan näyttämisen, joka estää käyttäjäinteraktion, kun prosessi on käynnissä. Oletusarvoisesti komponentti mahdollistaa taustan, mutta voit valita sen poistamisen tarvittaessa.

`BusyIndicator` näyttää taustan oletuksena. Voit hallita taustan näkyvyyttä `setBackdropVisible()` -metodin avulla, kuten alla on esitetty:

```java
BusyIndicator busyIndicator = App.getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Poistaa taustan käytöstä
busyIndicator.open();
```
:::info Taustan poistaminen
Vaikka poistat taustan käytöstä, `BusyIndicator` komponentti jatkaa käyttäjäinteraktion estämistä varmistaakseen, että taustalla oleva prosessi valmistuu keskeytyksettä. Tausta ohjaa vain visuaalista peitettä, ei interaktion estokäyttäytymistä.
:::

## `Spinner` {#spinner}

`BusyIndicator` komponentti webforJ:ssä sisältää `Spinnerin`, joka visuaalisesti osoittaa, että taustalla oleva operaatio on käynnissä. Voit mukauttaa tätä pyörijää useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Tässä on esimerkki siitä, miten voit mukauttaa pyörijää `BusyIndicator` komponentissa:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Käyttötapaukset {#use-cases}
- **Koko sivun käsittely**
   `BusyIndicator` on hyvin soveltuva suuremmille, koko sivun operaatioille, kuten silloin, kun käyttäjä käynnistää tehtävän, joka vaikuttaa koko sivuun, kuten tiedoston lataamiseen tai tietojen käsittelyyn useilla alueilla. Se voi tiedottaa käyttäjille, että koko sovellus on toiminnassa, estäen lisäinteraktion, kunnes prosessi on valmis.

- **Kriittiset järjestelmätoimenpiteet**
   Kun suoritetaan järjestelmän kriittisiä tehtäviä, kuten tietojen synkronointia, järjestelmälaajuisten päivitysten tekemistä tai arkaluontoisten tietojen käsittelyä, `BusyIndicator` antaa selvää visuaalista palautetta siitä, että merkittävä operaatio on käynnissä, jolloin käyttäjä voi odottaa sen valmistuvan.

- **Asynkroniset tietokuormat**
   Skenaarioissa, joissa on mukana asynkronista tietojenkäsittelyä, kuten useiden API:en kutsuminen tai monimutkaisten laskentojen odottaminen, `BusyIndicator` komponentti osoittaa aktiivisesti, että järjestelmä on kiireinen, kehottamalla käyttäjiä odottamaan ennen lisätoimintojen suorittamista.

## Tyylittely {#styling}

<TableBuilder name="BusyIndicator" />
