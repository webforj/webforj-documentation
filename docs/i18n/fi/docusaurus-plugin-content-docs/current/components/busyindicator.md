---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: e8d5c8ba0e26f0cc8fb98a640069347f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` on täysikokoinen peitto, joka signaaloi meneillään olevaa prosessia ja estää käyttäjäinteraktiot, kunnes se on valmis. Se kattaa koko käyttöliittymän toimenpiteiden, kuten alustamisen tai tietojen synkronoinnin, aikana. Vaikka [`Loading`](../components/loading) -komponentti keskittyy tiettyihin alueisiin käyttöliittymässä, `BusyIndicator` vaikuttaa globaalisti.

<!-- INTRO_END -->

## Perusteet {#basics}

`BusyIndicator` webforJ:ssä näkyy yksinkertaisena spinnertä, mikä tekee sen käytöstä helppoa ilman konfigurointia. Voit kuitenkin mukauttaa sitä lisäämällä viestin, säätämällä spinnertä teemaa tai muokkaamalla näkyvyysasetuksia. Tämä mahdollistaa kontekstin tai tyylin tarjoamisen samalla kun säilytetään toiminnallinen, heti käyttövalmis ratkaisu.

Tässä esimerkissä `BusyIndicator` estää kaikki käyttäjän toimet koko käyttöliittymässä, kunnes toimenpide on valmis.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Taustat {#backdrops}

`BusyIndicator`-komponentti webforJ:ssä mahdollistaa taustan näyttämisen, joka estää käyttäjäinteraktiot prosessin aikana. Oletusarvoisesti komponentti mahdollistaa taustan, mutta voit halutessasi kytkeä sen pois.

`BusyIndicator` näyttää taustan oletusarvoisesti. Voit hallita taustan näkyvyyttä käyttämällä `setBackdropVisible()`-metodia, kuten alla on esitetty:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Poistaa taustan käytöstä
busyIndicator.open();
```
:::info Taustan kytkeminen pois
Vaikka kytket taustan pois, `BusyIndicator`-komponentti estää edelleen käyttäjäinteraktiot varmistaakseen, että taustalla oleva prosessi valmistuu keskeytyksettä. Tausta säätelee vain visuaalista peittoa, eikä vuorovaikutuksen estämiskäyttäytymistä.
:::

## `Spinner` {#spinner}

`BusyIndicator`-komponentti webforJ:ssä sisältää `Spinner`-komponentin, joka visuaalisesti osoittaa, että taustaprosessi on käynnissä. Voit mukauttaa tätä spinnertä useilla vaihtoehdoilla, kuten sen koon, nopeuden, suunnan, teeman ja näkyvyyden mukaan.

Tässä on esimerkki siitä, miten voit mukauttaa spinnertä `BusyIndicator`-komponentin sisällä:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Käyttötapaukset {#use-cases}
- **Sivun Laajuinen Prosessointi**  
   `BusyIndicator` sopii hyvin suurempiin, sivun laajuisiin toimiin, kuten kun käyttäjä käynnistää tehtävän, joka vaikuttaa koko sivuun, kuten tiedoston lataaminen tai tietojen käsittely useissa osioissa. Se voi tiedottaa käyttäjille, että koko sovellus on toiminnassa, estäen lisäinteraktion, kunnes prosessi on valmis.

- **Kriittiset Järjestelmätoiminnot**  
   Kun suoritetaan järjestelmän kannalta kriittisiä tehtäviä, kuten tietojen synkronointia, järjestelmänlaajuisten päivitysten soveltamista tai herkän tiedon käsittelyä, `BusyIndicator` antaa selkeän visuaalisen palautteen siitä, että suurta toimintoa ollaan suorittamassa, jolloin käyttäjä voi odottaa sen valmistumista.

- **Asynkroniset Tietolataukset**  
   Tapahtumissa, joissa on mukana asynkronista tietojen käsittelyä, kuten kun kutsutaan useita API:ita tai odotetaan monimutkaisia laskentatehtäviä, `BusyIndicator`-komponentti indikoi aktiivisesti, että järjestelmä on varattu, ja kehottaa käyttäjiä odottamaan ennen lisätoimintoja.

## Tyylittely {#styling}

<TableBuilder name="BusyIndicator" />
