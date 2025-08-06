---
sidebar_position: 2
title: Routable Apps
_i18n_hash: 889bb5d90fac8315d6b7b1cf766fadea
---
Routing in webforJ on vaihtoehtoinen työkalu. Kehittäjät voivat valita webforJ-reititysratkaisun tai perinteisen mallin, jossa käytetään `Frame`-manipulaatiota ilman syvää linkitystä. Reitityksen mahdollistamiseksi **`@Routify`**-annotaatio on sovellettava luokan tasolla, joka toteuttaa `App`. Tämä myöntää webforJ:lle oikeuden hallita selainhistorian, vastata navigointitapahtumiin ja renderöidä sovelluksen komponentit URL-osoitteen perusteella.

:::info  
Jos haluat oppia lisää käyttöliittymien rakentamisesta kehyksillä, sisäänrakennetuilla ja mukautetuilla komponenteilla, vieraile kohdassa [Käyttöliittymien rakentaminen](../building-ui/basics).  
:::

## `@Routify` -annotaation tarkoitus {#purpose-of-the-routify-annotation}

**`@Routify`** mahdollistaa kehyksen automaattisen reittien rekisteröinnin, kehysten näkyvyyden hallinnan ja reitityskäyttäytymisen määrittämisen, kuten virheenkorjauksen ja kehysten aloituksen, mikä mahdollistaa dynaamisen ja joustavan reitityksen sovelluksessa.

## `@Routify` käytön {#usage-of-routify}

**`@Routify`** -annotaatio sovelletaan pääsovelluksen luokan tasolla. Se määrittää paketit, joita skannataan reittejä varten, ja käsittelee muita reititykseen liittyviä asetuksia, kuten kehysten aloituksen ja näkyvyyden hallinnan.

Tässä on yksinkertainen esimerkki:  

```java  
@Routify(  
  packages = {"com.myapp.views"},  
  defaultFrameName = "MainFrame",  
  initializeFrame = true,  
  manageFramesVisibility = false,  
  debug = true  
)  
public class MyApp extends App {  

  @Override  
  public void run() {  
    // Sovelluksen logiikka menee tänne  
  }  
}  
```

:::tip Routify Oletusasetukset  
**`@Routify`** -annotaatio sisältää kohtuulliset oletusasetukset. Se olettaa, että nykyistä pakettia, jossa sovellus on määritelty, yhdessä kaikkien sen aliopakettien kanssa, tulisi skannata reittien löytämiseksi. Lisäksi se olettaa, että sovellus hallitsee vain yhtä kehystä oletuksena. Jos sovelluksesi seuraa tätä rakennetta, sinun ei tarvitse antaa mitään mukautettuja asetuksia annotaatioon.  
:::

## `@Routify`:n avainelementit {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

`packages`-elementti määrittelee, mitkä paketit tulisi skannata reittimäärittelyjen löytämiseksi. Se mahdollistaa reittien automaattisen löydön ilman manuaalista rekisteröintiä, mikä nopeuttaa sovelluksen reititysjärjestelmän laajentamista.

```java  
@Routify(packages = {"com.myapp.views"})  
```

Jos paketteja ei ole määritelty, käytetään sovelluksen oletuspakettia.

### 2. **`defaultFrameName`** {#2-defaultframename}

Tämä elementti määrittää oletuskehyksen nimen, jonka sovellus alustaa. Kehykset edustavat huipputason käyttöliittymäkonteksteja, ja tämä asetus hallitsee, miten ensimmäisen kehys nimetään ja hallitaan.

```java  
@Routify(defaultFrameName = "MainFrame")  
```

Oletuksena, jos arvoa ei ole nimenomaisesti annettu, se on asetettu arvoon `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

`initializeFrame`-lippu määrittää, tulisiko kehyksen automaattisesti alustaa ensimmäinen kehys sovelluksen käynnistyessä. Asettaen tämän `true`:ksi yksinkertaistaa ensimmäisen kehysten asetusta.

```java  
@Routify(initializeFrame = true)  
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Tämä elementti hallitsee, tulisiko kehyksen automaattisesti kytkeä kehyksen näkyvyyttä navigoinnin aikana. Kun se on käytössä, vastaava reitti näyttää automaattisesti vastaavan kehyksen piilottaen muut, varmistaen puhtaan ja keskittymään käyttöliittymän. Tämä asetus on vain relevantti, kun sovellus hallitsee useita kehyksiä.

```java  
@Routify(manageFramesVisibility = true)  
```

### 5. **`debug`** {#5-debug}

`debug`-lippu mahdollistaa tai estää reitityksen virheenkorjaustilan. Kun se on käytössä, reititysinformaatiota ja toimintoja kirjataan konsoliin helpottamaan virheenkorjausta kehitysvaiheessa.

```java  
@Routify(debug = true)  
```

:::info Router Debug Mode ja webforJ Debug Mode  
Jos reitittimen virheenkorjaustila on asetettu `true`:ksi, mutta webforJ:n virheenkorjaustila on asetettu `false`:ksi, konsolissa ei näytetä virheenkorjaustietoja.  
:::
