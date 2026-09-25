---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: 8106f15ba96904324822afd0169ec09b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading`-komponentti näyttää päällekkäisyyden tiettyyn komponenttiin tai alueeseen, merkitsemällä, että operaatio on käynnissä ja tilapäisesti estäen vuorovaikutuksen. Se toimii hyvin tehtävissä, kuten tietojen latauksessa, laskennassa tai taustaprosesseissa. Globaalien, sovellustason prosessien osalta [`BusyIndicator`](../components/busyindicator) -komponentti kattaa koko käyttöliittymän.

<!-- INTRO_END -->

`Loading`-komponentin alustaminen ilman lisäasetuksia näyttää pyörivän latauskuvakkeen sen vanhempien sisällön päällä. Siirrä viesti, kuten seuraavassa esimerkissä, kun prosessi vaatii enemmän taustatietoa.

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Skaalaus {#scoping}

`Loading`-komponentti webforJ:ssä voi kohdistaa itsensä tiettyyn vanhempaan sisältöön, kuten `Div`, varmistaen, että se estää vuorovaikutuksen vain siinä elementissä. Oletuksena `Loading`-komponentti on suhteellinen sen vanhempaan, mikä tarkoittaa, että se peittää vanhemman komponentin eikä koko sovellusta.

Rajoittaaksesi `Loading`-komponentin sen vanhempaan, lisää yksinkertaisesti `Loading`-komponentti vanhempaan sisältöön. Esimerkiksi, jos lisäät sen `Div`:hen, latausepäilys soveltuu vain siihen `Div`:hin:

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading estää vain vuorovaikutuksen parentDiv:ssä
```

## Tausta {#backdrop}

`Loading`-komponentti webforJ:ssä mahdollistaa taustakuvan näyttämisen estääkseen vuorovaikutuksen käyttäjältä, kun prosessi on käynnissä. Oletuksena komponentti mahdollistaa taustan, mutta voit halutessasi poistaa sen käytöstä.

`Loading`-komponentille tausta on näkyvissä oletuksena. Voit nimenomaisesti ottaa sen käyttöön tai pois päältä `setBackdropVisible()`-menetelmää käyttämällä:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Poistaa taustan käytöstä
loading.open();
```
:::info Tausta pois päältä
Vaikka poistatkin taustan käytöstä, `Loading`-komponentti estää edelleen vuorovaikutuksen käyttäjältä varmistaakseen, että taustalla oleva prosessi valmistuu keskeytyksettä. Tausta hallitsee vain visuaalista peittämistä, ei vuorovaikutuksen estokäyttäytymistä.
:::

## `Spinner` {#spinner}

`Loading`-komponentti webforJ:ssä sisältää `Spinner`:in, joka visuaalisesti osoittaa, että taustaprosessi on käynnissä. Voit mukauttaa tätä pyörivää latauskuvaketta useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Tässä on esimerkki siitä, kuinka voit mukauttaa pyörivää latauskuvaketta `Loading`-komponentin sisällä:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Käyttötapaukset {#use-cases}
- **Tietojen haku**
   Kun noudat tietoja palvelimelta tai API:lta, `Loading`-komponentti peittää tietyn osan käyttöliittymästä, kuten kortin tai lomakkeen, ilmoittaen käyttäjille, että järjestelmä toimii taustalla. Tämä on ihanteellista, kun haluat näyttää edistymisen vain yhdellä ruudun osalla estämättä koko käyttöliittymää.

- **Sisällön lataaminen korteissa/osioissa**
   `Loading`-komponentti voidaan rajata tiettyihin sivun osiin, kuten yksittäisiin kortteihin tai säiliöihin. Tämä on hyödyllistä, kun haluat osoittaa, että tietty käyttöliittymän osa on vielä latautumassa samalla kun käyttäjät voivat vuorovaikuttaa muissa osissa sivua.

- **Monimutkaiset lomakkeen lähetykset**
   Pitkissä lomakkeen lähetystapahtumissa, joissa vahvistaminen tai käsittely vie aikaa, `Loading`-komponentti tarjoaa visuaalista palautetta käyttäjille, rauhoittaen heitä, että heidän syötteensä käsitellään aktiivisesti.

## Tyylittely {#styling}

<TableBuilder name="Loading" />
