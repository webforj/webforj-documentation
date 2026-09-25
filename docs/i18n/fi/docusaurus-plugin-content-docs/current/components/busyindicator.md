---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 663fb0d605695631bad3753aadf178e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` on täysikokoiseen peittoon, joka ilmoittaa meneillään olevasta prosessista ja estää käyttäjäinteraktion, kunnes prosessi on valmis. Se peittää koko käyttöliittymän toimien aikana, kuten alustamis- tai tietosynkronoinneissa. Kun [`Loading`](../components/loading) komponentti keskittyy käyttöliittymän tiettyihin alueisiin, `BusyIndicator` soveltuu globaaliin käyttöön.

`BusyIndicator` näkyy spinaajana ilman erityistä konfiguraatiota. Voit lisätä viestin, vaihtaa spinaajan teeman tai säätää näkyvyysasetuksia, kun prosessi tarvitsee lisää kontekstia.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Taustat {#backdrops}

`BusyIndicator` komponentti webforJ:ssä mahdollistaa taustan näyttämisen, joka estää käyttäjäinteraktion prosessin aikana. Oletuksena komponentti mahdollistaa taustan, mutta voit halutessasi kytkeä sen pois päältä.

`BusyIndicator` näyttää taustan oletuksena. Voit hallita taustan näkyvyyttä käyttämällä `setBackdropVisible()` menetelmää, kuten alla on esitetty:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Poistaa taustan käytöstä
busyIndicator.open();
```
:::info Taustan Poistaminen
Vaikka poistat taustan käytöstä, `BusyIndicator` komponentti jatkaa käyttäjäinteraktion estämistä varmistaakseen, että taustalla oleva prosessi valmistuu keskeytyksettä. Tausta hallitsee vain visuaalista peittoa, ei interaktion estäytymistä.
:::

## `Spinner` {#spinner}

`BusyIndicator` komponentti webforJ:ssä sisältää `Spinner`-osion, joka visuaalisesti ilmoittaa, että taustaprosessi on käynnissä. Voit mukauttaa tätä spinaajaa useilla vaihtoehdoilla, kuten sen koossa, nopeudessa, suunnassa, teemassa ja näkyvyydessä.

Tässä on esimerkki siitä, kuinka voit mukauttaa spinaajaa `BusyIndicator` komponentin sisällä:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Käyttötapaukset {#use-cases}
- **Koko Sivun Prosessointi**
   `BusyIndicator` soveltuu hyvin suuremmille, koko sivun laajuisille toiminnoille, kuten kun käyttäjä aloittaa tehtävän, joka vaikuttaa koko sivuun, kuten tiedoston lataaminen tai tietojen käsittely useilla alueilla. Se voi ilmoittaa käyttäjille, että koko sovellus on työn alla, estäen lisäinteraktiot prosessin täydelliseen valmistumiseen saakka.

- **Kriittiset Järjestelmätoiminnat**
   Kun suoritat järjestelmän kriittisiä tehtäviä, kuten tietojen synkronointia, järjestelmälaajuisia päivityksiä tai arkaluontoisten tietojen käsittelyä, `BusyIndicator` antaa selkeää visuaalista palautetta siitä, että merkittävä toimintaprosessi on käynnissä, jolloin käyttäjä voi odottaa sen valmistumista.

- **Asynkroniset Tiedon Lataukset**
   Tapahtumissa, joissa on mukana asynkronista tietojenkäsittelyä, kuten useiden API:iden kutsuminen tai monimutkaisten laskentatehtävien odottaminen, `BusyIndicator` komponentti aktiivisesti ilmoittaa, että järjestelmä on varattu, kehottaen käyttäjiä odottamaan ennen lisätoimenpiteitä.

## Tyylittely {#styling}

<TableBuilder name="BusyIndicator" />
