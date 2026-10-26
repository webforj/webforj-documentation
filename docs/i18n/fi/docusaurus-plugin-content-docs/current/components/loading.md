---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: 6e4493f64eb29033ed8a5d594accdb33
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading`-komponentti näyttää peitteen tietyllä komponentilla tai alueella, mikä osoittaa, että operaatio on käynnissä ja estää väliaikaisesti vuorovaikutuksen. Se toimii hyvin tehtävissä, kuten tietojen lataamisessa, laskennassa tai taustaprosesseissa. Globaaleissa, koko sovelluksen laajuisissa prosesseissa [`BusyIndicator`](../components/busyindicator) -komponentti kattaa koko käyttöliittymän.

<!-- INTRO_END -->

`Loading`-komponentin alustus ilman lisäasetuksia näyttää pyörivän kuorman sen vanhempien sisällön päällä. Siirrä viesti, kuten esimerkissä alla, kun prosessi tarvitsee enemmän kontekstia.

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Sopi {#scoping}

`Loading`-komponentti webforJ:ssä voi rajata itsensä tiettyyn vanhempaan säiliöön, kuten `Div`:iin, varmistaen, että se estää käyttäjävuorovaikutuksen vain kyseisessä elementissä. Oletuksena `Loading`-komponentti on suhteellinen sen vanhemmalle, mikä tarkoittaa, että se peittää vanhemman komponentin eikä koko sovellusta.

Rajoittaaksesi `Loading`-komponenttia sen vanhempaan, lisää yksinkertaisesti `Loading`-komponentti vanhempaan säiliöön. Esimerkiksi, jos lisäät sen `Div`:iin, latauspeite kohdistuu vain siihen `Div`:iin:

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading estää vuorovaikutuksen vain parentDiv:ssä
```

## Tausta {#backdrop}

`Loading`-komponentti webforJ:ssä antaa sinun näyttää taustan estääksesi käyttäjävuorovaikutuksen, kun prosessi on käynnissä. Oletuksena komponentti mahdollistaa taustan, mutta voit halutessasi katkaista sen.

`Loading`-komponentilla tausta on näkyvissä oletuksena. Voit nimenomaisesti aktivoida tai kytkeä sen pois päältä käyttämällä `setBackdropVisible()`-metodia:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Poistaa taustan käytöstä
loading.open();
```
:::info Tausta Pois
Vaikka kytket taustan pois päältä, `Loading`-komponentti jatkaa käyttäjävuorovaikutuksen estämistä varmistaakseen, että taustaprosessi valmistuu keskeytyksettä. Tausta hallitsee vain visuaalista peitettä, ei vuorovaikutuksen estäytymiskäyttäytymistä.
:::

## `Spinner` {#spinner}

`Loading`-komponentti webforJ:ssä sisältää `Spinner`:in, joka visuaalisesti osoittaa, että taustatehtävä on käynnissä. Voit mukauttaa tätä pyörivää kuormaa useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Tässä on esimerkki siitä, kuinka voit mukauttaa pyörivää kuormaa `Loading`-komponentin sisällä:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
]}
height='300px'
/>

## Käyttötapaukset {#use-cases}
- **Tietojen hakeminen**
   Kun haet tietoja palvelimelta tai API:sta, `Loading`-komponentti peittää tietyn osan käyttöliittymästä, kuten kortin tai lomakkeen, ilmoittaen käyttäjille, että järjestelmä työskentelee taustalla. Tämä on ihanteellista, kun haluat näyttää edistystä vain yhdessä osassa näyttöä ilman, että koko käyttöliittymä estyy.

- **Sisällön lataaminen korteissa/osioissa**
   `Loading`-komponentti voidaan rajoittaa tiettyihin sivun alueisiin, kuten yksittäisiin kortteihin tai säiliöihin. Tämä on hyödyllistä, kun haluat osoittaa, että tietty käyttöliittymän osa lataa edelleen, samalla kun käyttäjät voivat vuorovaikuttaa muiden sivun osien kanssa.

- **Monimutkaiset lomakesyötteet**
   Pitkäkestoisille lomakesyötteille, joissa validoimiseen tai käsittelyyn kuluu aikaa, `Loading`-komponentti antaa visuaalista palautetta käyttäjille, rauhoittaen heitä siitä, että heidän syötteensä käsitellään aktiivisesti.

## Tyylit {#styling}

<TableBuilder name="Loading" />
