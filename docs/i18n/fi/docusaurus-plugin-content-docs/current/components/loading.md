---
title: Loading
sidebar_position: 65
_i18n_hash: 45fa6bcfc4a2fd5995a06dc98b6f91bf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading` komponentti näyttää peiton tietyllä komponentilla tai alueella, merkiten, että toiminto on käynnissä ja estää väliaikaisesti vuorovaikutuksen. Se toimii hyvin tehtävissä, kuten datan latauksessa, laskennassa tai taustaprosesseissa. Globaalien, koko sovellusta koskevien prosessien osalta [`BusyIndicator`](../components/busyindicator) komponentti peittää koko käyttöliittymän.

<!-- INTRO_END -->

## Perusteet {#basics}

Yksinkertaisin tapa luoda `Loading` komponentti on alustaa se ilman lisäasetuksia. Oletusarvoisesti tämä näyttää peruspyörijän sen vanhempien sisällön päällä. Voit kuitenkin myös tarjota viestin lisätiedoksi.

Esimerkki `Loading` komponentista, jossa on viesti:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Rajaus {#scoping}

`Loading` komponentti webforJ:ssä voi rajata itsensä tiettyyn vanhempaan säiliöön, kuten `Div`, varmistaen, että se estää vain käyttäjän vuorovaikutuksen kyseisessä elementissä. Oletusarvoisesti `Loading` komponentti on suhteellinen vanhemmalleen, mikä tarkoittaa, että se peittää vanhemman komponentin eikä koko sovellusta.

Rajata `Loading` komponentti vain sen vanhempaan, yksinkertaisesti lisää `Loading` komponentti vanhempaan säiliöön. Esimerkiksi, jos lisäät sen `Div`-elementtiin, latauspeitto koskee vain tuota `Div`:tä:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading estää vain vuorovaikutuksen parentDiv:ssa
```

## Taustakuva {#backdrop}

`Loading` komponentti webforJ:ssä antaa sinun näyttää taustakuvan estääksesi käyttäjän vuorovaikutuksen prosessin ollessa käynnissä. Oletusarvoisesti komponentti mahdollistaa taustakuvan, mutta voit halutessasi myös sammuttaa sen.

`Loading` komponentilla taustakuva on näkyvissä oletusarvoisesti. Voit nimenomaan aktivoida tai sammuta sen käyttämällä `setBackdropVisible()` metodia:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Sammuttaa taustakuvan
loading.open();
```
:::info Taustakuva pois
Vaikka sammutat taustakuvan, `Loading` komponentti jatkaa käyttäjän vuorovaikutuksen estämistä varmistaakseen, että taustalla oleva prosessi valmistuu katkeamatta. Taustakuva hallitsee vain visuaalista peittoa, ei vuorovaikutuksen estämiskäyttäytymistä.
:::

## `Spinner` {#spinner}

`Loading` komponentti webforJ:ssä sisältää `Spinner`:n, joka visuaalisesti osoittaa, että taustatoiminto on käynnissä. Voit mukauttaa tätä pyörijää useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Esimerkki siitä, kuinka voit mukauttaa pyörijää `Loading` komponentissa:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Käyttötapaukset {#use-cases}
- **Datan hankinta**  
   Kun noudat datan palvelimelta tai API:sta, `Loading` komponentti peittää tietyn osan käyttöliittymästä, kuten kortin tai lomakkeen, informoiden käyttäjiä siitä, että järjestelmä toimii taustalla. Tämä on ihanteellinen, kun haluat näyttää edistymistä vain yhdestä osasta näyttöä estämättä koko käyttöliittymää.

- **Sisällön lataus korteissa/osioissa**  
   `Loading` komponenttia voidaan rajoittaa tietyille alueille sivulla, kuten yksittäisiin kortteihin tai säiliöihin. Tämä on hyödyllistä, kun haluat ilmoittaa, että tietty osio käyttöliittymästä latautuu edelleen, samalla kun käyttäjät voivat vuorovaikuttaa muiden sivun osien kanssa.

- **Monimutkaiset lomakkeen lähetykset**  
   Pitkissä lomakkeen lähetyksissä, joissa vahvistaminen tai käsittely vie aikaa, `Loading` komponentti tarjoaa visuaalista palautetta käyttäjille, rauhoittaen heitä siitä, että heidän syötteensä on aktiivisesti käsittelyssä.

## Tyylit {#styling}

<TableBuilder name="Loading" />
