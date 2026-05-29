---
title: Loading
sidebar_position: 65
_i18n_hash: c81b8d0ced3e4097693a186a05f18dbf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading`-komponentti näyttää peiton tietyllä komponentilla tai alueella, joka ilmoittaa, että operaatio on käynnissä ja estää tilapäisesti vuorovaikutuksen. Se sopii hyvin tehtäville, kuten tietojen lataamiseen, laskentatehtäviin tai taustaprosesseihin. Globaalien, koko sovelluksen kattavien prosessien osalta [`BusyIndicator`](../components/busyindicator) -komponentti kattaa koko käyttöliittymän.

<!-- INTRO_END -->

## Perusteet {#basics}

Yksinkertaisin tapa luoda `Loading`-komponentti on alustaa se ilman lisäasetuksia. Oletusarvoisesti tämä näyttää perussilmukan sen vanhempien sisällön päällä. Voit kuitenkin myös antaa viestin lisäämään kontekstiä.

Tässä on esimerkki `Loading`-komponentin luomisesta viestin kanssa:

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Rajaus {#scoping}

`Loading`-komponentti webforJ:ssä voi rajoittua tiettyyn vanhempaan säilöön, kuten `Div`:iin, varmistaen, että se estää käyttäjävuorovaikutuksen vain siinä elementissä. Oletusarvoisesti `Loading`-komponentti on suhteellinen sen vanhempaan, mikä tarkoittaa, että se peittää vanhemman komponentin eikä koko sovellusta.

Rajoittaaksesi `Loading`-komponentin sen vanhempaan, lisää vain `Loading`-komponentti vanhempaan säilöön. Esimerkiksi, jos lisäät sen `Div`:iin, latauspeitto koskee vain sitä `Div`:iä:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading estää vuorovaikutuksen vain parentDivissä
```

## Taustapeitto {#backdrop}

`Loading`-komponentti webforJ:ssä mahdollistaa taustapeiton näyttämisen, jotta käyttäjävuorovaikutus estetään, kun prosessi on käynnissä. Oletusarvoisesti komponentti aktivoi taustapeiton, mutta voit tarvittaessa sammuttaa sen.

`Loading`-komponentille taustapeitto näkyy oletusarvoisesti. Voit nimenomaisesti aktivoida tai poistaa sen käytöstä `setBackdropVisible()`-menetelmällä:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Poistaa taustapeiton käytöstä
loading.open();
```
:::info Taustapeitto pois
Vaikka poistat taustapeiton käytöstä, `Loading`-komponentti jatkaa käyttäjävuorovaikutuksen estämistä varmistaakseen, että taustalla oleva prosessi saadaan päätökseen ilman keskeytyksiä. Taustapeitto hallitsee vain visuaalista peittoa, ei vuorovaikutuksen estämiskäyttäytymistä.
:::

## `Spinner` {#spinner}

`Loading`-komponentti webforJ:ssä sisältää `Spinner`:in, joka visuaalisesti osoittaa, että taustaprosessi on käynnissä. Voit mukauttaa tätä spinneriä useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Tässä on esimerkki siitä, kuinka voit mukauttaa spinneriä `Loading`-komponentin sisällä:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Käyttötapaukset {#use-cases}
- **Tietojen hakeminen**  
   Kun haetaan tietoja palvelimelta tai API:sta, `Loading`-komponentti peittää tietyn osan käyttöliittymästä, kuten kortin tai lomakkeen, ilmoittaakseen käyttäjille, että järjestelmä työskentelee taustalla. Tämä on ihanteellinen, kun haluat näyttää edistymistä vain yhdellä ruudun osalla ilman, että koko käyttöliittymä estetään.

- **Sisällön lataaminen korteissa/alueissa**  
   `Loading`-komponenttia voidaan rajoittaa tiettyihin sivun alueisiin, kuten yksittäisiin kortteihin tai säilöihin. Tämä on hyödyllistä, kun haluat osoittaa, että tietty käyttöliittymän osa on edelleen latautumassa, samalla kun käyt käyttäjä vuorovaikutusta muiden sivun osien kanssa.

- **Monimutkaiset lomake lähetykset**  
   Pitkissä lomake lähetyksissä, joissa validoimiseen tai käsittelyyn kuluu aikaa, `Loading`-komponentti tarjoaa visuaalista palautetta käyttäjille, rauhoittaen heitä siitä, että heidän syötteensä käsitellään aktiivisesti.

## Tyylitys {#styling}

<TableBuilder name="Loading" />
