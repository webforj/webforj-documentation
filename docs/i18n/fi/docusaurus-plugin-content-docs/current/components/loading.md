---
title: Loading
sidebar_position: 65
_i18n_hash: 9bdb4d5c978b4070d3628566e5105088
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading`-komponentti webforJ:ssä näyttää päällekkäisyyden, joka osoittaa operaation käsittelyä, estäen tilapäisesti käyttäjävuorovaikutuksen kunnes tehtävä on valmis. Tämä toiminto parantaa käyttäjäkokemusta, erityisesti tilanteissa, joissa tehtävät kuten tietojen lataus, laskentaprosessit tai taustaprosessit saattavat kestää jonkin aikaa. Globaaleille, sovellustasoisille prosesseille harkitse [`BusyIndicator`](../components/busyindicator) -komponentin käyttöä, joka estää vuorovaikutuksen koko käyttöliittymässä.

## Perusteet {#basics}

Yksinkertaisin tapa luoda `Loading`-komponentti on alustaa se ilman lisäasetuksia. Oletusarvoisesti tämä näyttää peruskierroksen sen vanhemman sisällön päällä. Voit kuitenkin myös antaa viestin lisäyhteydeksi.

Tässä on esimerkki `Loading`-komponentin luomisesta viestin kanssa:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Rajaus {#scoping}

`Loading`-komponentti webforJ:ssä voi rajautua tiettyyn vanhempaan konttiin, kuten `Div`, varmistaen, että se estää käyttäjävuorovaikutuksen vain siinä elementissä. Oletusarvoisesti `Loading`-komponentti on suhteellinen sen vanhempaan, mikä tarkoittaa, että se peittää vanhemman komponentin eikä koko sovellusta.

Rajoittaaksesi `Loading`-komponentin sen vanhempaan, lisää yksinkertaisesti `Loading`-komponentti vanhempaan konttiin. Esimerkiksi, jos lisäät sen `Div`:iin, latauspeite koskee vain sitä `Div`:iä:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading estää vain vuorovaikutuksen parentDivissä
```

## Tausta {#backdrop}

`Loading`-komponentti webforJ:ssä mahdollistaa taustan näyttämisen käyttäjävuorovaikutuksen estämiseksi prosessin aikana. Oletusarvoisesti komponentti aktivoi taustan, mutta voit valita sen poistamisen tarvittaessa.

`Loading`-komponentissa tausta on näkyvissä oletusarvoisesti. Voit nimenomaisesti aktivoida tai poistaa sen käyttämällä `setBackdropVisible()`-metodia:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Poistaa taustan käytöstä
loading.open();
```
:::info Tausta pois
Vaikka poistat taustan, `Loading`-komponentti estää silti käyttäjävuorovaikutuksen varmistaakseen, että taustalla oleva prosessi valmistuu keskeytyksettä. Tausta hallitsee vain visuaalista päällekkäisyyttä, ei vuorovaikutuksen estämiskäyttäytymistä.
:::

## `Spinner` {#spinner}

`Loading`-komponentti webforJ:ssä sisältää `Spinner`:in, joka osoittaa visuaalisesti, että taustaprosessi on käynnissä. Voit mukauttaa tätä kierrosta useilla vaihtoehdoilla, mukaan lukien sen koko, nopeus, suunta, teema ja näkyvyys.

Tässä on esimerkki siitä, miten voit mukauttaa kierrosta `Loading`-komponentissa:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Käyttötapaukset {#use-cases}
- **Tietojen hakeminen**  
   Kun haetaan tietoja palvelimelta tai API:lta, `Loading`-komponentti peittää tietyn osan käyttöliittymästä, kuten kortin tai lomakkeen, kertoen käyttäjille, että järjestelmä työskentelee taustalla. Tämä on ihanteellinen, kun haluat näyttää edistystä vain yhdellä osalla näyttöä estämättä koko käyttöliittymää.

- **Sisällön lataaminen korteissa/osissa**  
   `Loading`-komponentti voidaan rajata tiettyihin alueisiin sivulla, kuten yksittäisiin kortteihin tai kontteihin. Tämä on hyödyllistä, kun haluat osoittaa, että tietty käyttöliittymän osa on edelleen lataamassa, samalla kun käyttäjät voivat vuorovaikuttaa muiden sivun osien kanssa.

- **Monimutkaiset lomakkeen lähetykset**  
   Pidemmille lomakkeen lähetyksille, joissa validointi tai käsittely vie aikaa, `Loading`-komponentti tarjoaa visuaalista palautetta käyttäjille, rauhoittaen heitä siitä, että heidän syötteensä käsitellään aktiivisesti.

## Tyylittely {#styling}

<TableBuilder name="Loading" />
