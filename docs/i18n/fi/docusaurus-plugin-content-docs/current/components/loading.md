---
title: Loading
sidebar_position: 65
_i18n_hash: fd3e1e31d1a614494358f9d67a9d3cd8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading`-komponentti webforJ:ssä näyttää päällekkäisyyden, joka merkitsee toimintaprosessia, estäen väliaikaisesti käyttäjien vuorovaikutuksen, kunnes tehtävä on suoritettu. Tämä ominaisuus parantaa käyttäjäkokemusta erityisesti tilanteissa, joissa tehtävät, kuten tietojen lataus, laskenta tai taustaprosessit, voivat kestää jonkin aikaa. Globaalien, sovelluksen laajuisten prosessien osalta kannattaa harkita [`BusyIndicator`](../components/busyindicator) -komponentin käyttöä, joka estää vuorovaikutuksen koko käyttöliittymässä.

## Perusteet {#basics}

Yksinkertaisin tapa luoda `Loading`-komponentti on alustaa se ilman lisäasetuksia. Oletuksena tämä näyttää yksinkertaisen pyörivän kuvion sen vanhempien sisällön päällä. Voit kuitenkin myös tarjota viestin lisäkonkreettisuutta varten.

Tässä on esimerkki `Loading`-komponentin luomisesta viestin kanssa:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Laajuus {#scoping}

`Loading`-komponentti webforJ:ssä voi rajata itsensä tiettyyn vanhemman säiliöön, kuten `Div`, varmistaen, että se estää vain käyttäjien vuorovaikutuksen siinä elementissä. Oletuksena `Loading`-komponentti on suhteellinen sen vanhempaan, mikä tarkoittaa, että se peittää vanhemman komponentin eikä koko sovellusta.

Rajoittaaksesi `Loading`-komponentin sen vanhempaan, lisää yksinkertaisesti `Loading`-komponentti vanhempaan säiliöön. Esimerkiksi, jos lisäät sen `Div`:iin, latauspeite soveltuu vain tuohon `Div`:iin:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Lataaminen estää vain vuorovaikutuksen parentDiv:ssä
```

## Taustapeite {#backdrop}

`Loading`-komponentti webforJ:ssä antaa sinun näyttää taustapeitteen, joka estää käyttäjien vuorovaikutuksen, kun prosessi on käynnissä. Oletuksena komponentti mahdollistaa taustapeitteen, mutta voit halutessasi poistaa sen käytöstä.

`Loading`-komponentissa taustapeite on näkyvissä oletuksena. Voit erikseen ottaa sen käyttöön tai poistaa käytöstä `setBackdropVisible()`-metodilla:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Poistaa taustapeitteen käytöstä
loading.open();
```
:::info Taustapeite pois
Vaikka poistat taustapeitteen käytöstä, `Loading`-komponentti estää silti käyttäjien vuorovaikutuksen varmistaakseen, että taustaprosessi suoritetaan keskeyttämättä. Taustapeite ohjaa vain visuaalista peitettä, ei vuorovaikutuksen estotoimintoa.
:::

## `Spinner` {#spinner}

`Loading`-komponentti webforJ:ssä sisältää `Spinner`:in, joka visuaalisesti osoittaa, että taustatoiminto on käynnissä. Voit räätälöidä tätä pyörivää kuvaa useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Tässä on esimerkki siitä, kuinka voit räätälöidä pyörivän kuvion `Loading`-komponentin sisällä:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Käyttötapaukset {#use-cases}
- **Tietojen hakeminen**  
   Kun haetaan tietoja palvelimelta tai API:lta, `Loading`-komponentti peittää tiettyjä käyttöliittymäosuuksia, kuten kortin tai lomakkeen, informoiden käyttäjiä, että järjestelmä toimii taustalla. Tämä on ihanteellinen tilanne, kun haluat näyttää etenemisen vain yhdellä osalla näyttöä estämättä koko käyttöliittymää.

- **Sisällön lataaminen korteissa/osiin**  
   `Loading`-komponenttia voidaan rajata tiettyihin sivualueisiin, kuten yksittäisiin kortteihin tai säiliöihin. Tämä on hyödyllistä, kun haluat ilmaista, että tietty käyttöliittymäosa lataa yhä, samalla kun käyttäjät voivat olla vuorovaikutuksessa muiden sivuosien kanssa.

- **Monimutkaiset lomake-lähetykset**  
   Pitkillä lomake-lähetyksillä, joissa validointi tai käsittely vie aikaa, `Loading`-komponentti tarjoaa visuaalista palautetta käyttäjille, varmistaen heidät siitä, että heidän syötteensä käsitellään aktiivisesti.

## Tyylittely {#styling}

<TableBuilder name="Loading" />
