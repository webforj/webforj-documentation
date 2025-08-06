---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 0ecb07a1364b90d27e17484ade2199ae
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` tarjoaa visuaalisia vihjeitä varmistaakseen, että käyttäjät ovat tietoisia käynnissä olevista prosesseista, estäen heitä vuorovaikuttamasta järjestelmän kanssa ennenaikaisesti. Se kattaa tyypillisesti koko sovelluksen käyttöliittymän globaalien toimintojen aikana.

Kun [`Loading`](../components/loading) -komponentti keskittyy sovelluksen tiettyihin alueisiin tai komponentteihin, `BusyIndicator` käsittelee globaaleja, sovelluksen laajuisia prosesseja ja estää vuorovaikutuksen koko käyttöliittymässä. Tämä laajuusero tekee [`Loading`](../components/loading) -komponentista ihanteellisen enemmän paikallisiin, komponentti-spesifisiin tilanteisiin, kuten datan lataamiseen tietyssä sivun osassa. Sen sijaan `BusyIndicator` soveltuu järjestelmänlaajuisiin toimintoihin, jotka vaikuttavat koko sovellukseen, kuten sovelluksen alustamiseen tai suuren datasyyn suorittamiseen.

## Perusteet {#basics}

`BusyIndicator` webforJ:ssä näyttää yksinkertaiselta pyörijältä, joten sen käyttö ilman asetuksia on helppoa. Voit kuitenkin räätälöidä sitä lisäämällä viestin, säätämällä pyörijän teeman tai muuttamalla näkyvyysasetuksia. Tämä mahdollistaa enemmän kontekstin tai tyylin tarjoamisen samalla, kun säilytät toimivan, valmiin ratkaisun.

Tässä esimerkissä `BusyIndicator` estää kaikki käyttäjän toimet koko käyttöliittymässä, kunnes toimeksianto on valmis.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Taustat {#backdrops}

`BusyIndicator` -komponentti webforJ:ssä mahdollistaa taustan näyttämisen, joka estää käyttäjän vuorovaikutuksen prosessin ollessa käynnissä. Oletusarvoisesti komponentti sallii taustan, mutta voit halutessasi poistaa sen käytöstä.

`BusyIndicator` näyttää taustan oletuksena. Voit hallita taustan näkyvyyttä `setBackdropVisible()` -menetelmällä, kuten alla on esitetty:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Poistaa taustan käytöstä
busyIndicator.open();
```
:::info Taustan Poistaminen
Vaikka poistat taustan käytöstä, `BusyIndicator` -komponentti jatkaa käyttäjän vuorovaikutuksen estämistä varmistaakseen, että taustalla oleva prosessi valmistuu keskeytyksettä. Tausta hallitsee vain visuaalista peittoa, ei vuorovaikutuksen estokäyttäytymistä.
:::

## `Spinner` {#spinner}

`BusyIndicator` -komponentti webforJ:ssä sisältää `Spinner`:in, joka visuaalisesti ilmoittaa, että taustatoiminto on käynnissä. Voit mukauttaa tätä pyörijää useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Tässä on esimerkki siitä, kuinka voit mukauttaa pyörijää `BusyIndicator` -komponentissa:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Käyttötapaukset {#use-cases}
- **Sivun laajuiset prosessit**  
   `BusyIndicator` on hyvin soveltuva suuremmille, sivun laajuisille toiminnoille, kuten silloin, kun käyttäjä aloittaa tehtävän, joka vaikuttaa koko sivuun, kuten tiedoston lataaminen tai datan käsittely useissa osissa. Se voi ilmoittaa käyttäjille, että koko sovellus on töissä, estäen lisävuorovaikutusta, kunnes prosessi on valmis.

- **Kriittiset järjestelmätoiminnot**  
   Kun suoritetaan järjestelmän kriittisiä tehtäviä, kuten datan synkronointia, järjestelmänlaajuisten päivitysten soveltamista tai arkaluontoisten tietojen käsittelyä, `BusyIndicator` antaa selkeää visuaalista palautetta siitä, että suuri operaatio on käynnissä, mikä antaa käyttäjälle mahdollisuuden odottaa, kunnes se on valmis.

- **Asynkroniset datan lataukset**  
   Tapahtumissa, joissa on mukana asynkronista datan käsittelyä, kuten useiden API:iden kutsumisessa tai monimutkaisten laskentojen odottamisessa, `BusyIndicator` -komponentti ilmoittaa aktiivisesti, että järjestelmä on varattu, kehottamalla käyttäjiä odottamaan ennen lisätoimien suorittamista.

## Tyylittely {#styling}

<TableBuilder name="BusyIndicator" />
