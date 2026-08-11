---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: e17c9249d41752ed1f4b98d18028371a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading`-komponentti näyttää peiton tietyllä komponentilla tai alueella, merkiten, että toimenpide on käynnissä ja väliaikaisesti estäen vuorovaikutuksen. Se toimii hyvin tehtävissä kuten tietojen lataaminen, laskentatehtävät tai taustaprosessit. Globaaleihin, sovelluksen laajuisiin prosesseihin [`BusyIndicator`](../components/busyindicator) -komponentti kattaa koko käyttöliittymän.

<!-- INTRO_END -->

## Perusteet {#basics}

Yksinkertaisin tapa luoda `Loading`-komponentti on alustaa se ilman lisäasetuksia. Oletusarvoisesti tämä näyttää peruspyörijän sen vanhempien sisällön päällä. Voit kuitenkin myös antaa viestin lisäkonkreettisuuden vuoksi.

Tässä on esimerkki `Loading`-komponentin luomisesta viestin kanssa:

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Rajaus {#scoping}

`Loading`-komponentti webforJ:ssä voi rajata itsensä tiettyyn vanhempaan säilytykseen, kuten `Div`, varmistaen että se estää käyttäjävuorovaikutuksen vain siinä elementissä. Oletusarvoisesti `Loading`-komponentti on suhteellinen sen vanhemmalle, tarkoittaen että se peittää vanhemman komponentin eikä koko sovellusta.

Rajoittaaksesi `Loading`-komponentin vanhemmalleen, lisää yksinkertaisesti `Loading`-komponentti vanhempaan säilytykseen. Esimerkiksi, jos lisäät sen `Div`:lle, latauspeitto koskee vain sitä `Div`:ä:

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading estää vuorovaikutuksen vain parentDiv:ssä
```

## Tausta {#backdrop}

`Loading`-komponentti webforJ:ssa mahdollistaa taustan näyttämisen, joka estää käyttäjävuorovaikutuksen prosessin aikana. Oletusarvoisesti komponentti ottaa taustan käyttöön, mutta voit valita sen poistamisen tarvittaessa.

`Loading`-komponentille tausta on näkyvissä oletuksena. Voit nimenomaan aktivoida tai poistaa sen käytöstä `setBackdropVisible()` -menetelmällä:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Poistaa taustan käytöstä
loading.open();
```
:::info Tausta pois
Vaikka poistatkin taustan käytöstä, `Loading`-komponentti jatkaa käyttäjävuorovaikutuksen estämistä varmistaakseen, että taustaprosessi suoritetaan keskeytyksettä. Tausta hallitsee vain visuaalista peittoa, ei vuorovaikutuksen estokäyttäytymistä.
:::

## `Spinner` {#spinner}

`Loading`-komponentti webforJ:ssa sisältää `Spinnerin`, joka visuaalisesti osoittaa että taustatoiminto on käynnissä. Voit mukauttaa tätä pyörijää useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Tässä on esimerkki siitä, kuinka voit mukauttaa pyörijää `Loading`-komponentin sisällä:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Käyttötapaukset {#use-cases}
- **Tietojen hakeminen**
   Kun haetaan tietoja palvelimelta tai API:sta, `Loading`-komponentti peittää tietyn osan käyttöliittymästä, kuten kortin tai lomakkeen, kertoakseen käyttäjille, että järjestelmä työskentelee taustalla. Tämä on ihanteellinen, kun haluat näyttää edistymistä vain yhteen osaan näyttöä estämättä koko käyttöliittymää.

- **Sisällön lataaminen korteissa/alueilla**
   `Loading`-komponentti voidaan rajata tiettyihin alueisiin sivulla, kuten yksittäisiin kortteihin tai säilytyksiin. Tämä on hyödyllistä, kun haluat osoittaa, että tietty käyttöliittymän osa on vielä lataamassa, samalla kun käyttäjät voivat vuorovaikuttaa muihin osiin sivusta.

- **Monimutkaiset lomakkeen lähetykset**
   Pitkien lomakkeen lähetyksien kohdalla, joissa validointi tai käsittely vie aikaa, `Loading`-komponentti tarjoaa visuaalista palautetta käyttäjille, rauhoittaen heitä, että heidän syötteensä on aktiivisesti käsittelyssä.

## Tyylittely {#styling}

<TableBuilder name="Loading" />
