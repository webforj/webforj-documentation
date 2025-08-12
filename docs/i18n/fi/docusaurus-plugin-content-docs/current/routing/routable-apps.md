---
sidebar_position: 2
title: Routable Apps
_i18n_hash: 6d09e8327e3391cedd4e8059d9390d09
---
Routing webforJ:ssä on valinnainen työkalu. Kehittäjät voivat valita webforJ-reititysrationaalin tai perinteisen mallin, jossa käytetään `Frame`-manipulaatiota ilman syvälinkitystä. Reitityksen mahdollistamiseksi **`@Routify`**-annotaatio on käytettävä luokalla, joka toteuttaa `App`. Tämä antaa webforJ:lle oikeuden hallita selainhistoriaa, reagoida navigointitapahtumiin ja renderöidä sovelluksen komponentit URL-osoitteen perusteella.

:::info
Lisätietoja käyttöliittymien rakentamisesta kehyksillä, sisäänrakennetuilla ja mukautetuilla komponenteilla saat vierailemalla [Käyttöliittymien rakentaminen](../building-ui/basics) -osiossa.
:::

## `@Routify`-annotaation tarkoitus {#purpose-of-the-routify-annotation}

**`@Routify`** mahdollistaa kehykseen automaattisen reittien rekisteröinnin, kehyksen näkyvyyden hallinnan ja reitityksen käyttäytymisen määrittämisen, kuten virheenkorjauksen ja kehyksen alustamisen, mahdollistaen dynaamisen, joustavan reitityksen sovelluksessa.

## `@Routify`-annotaation käyttö {#usage-of-routify}

**`@Routify`**-annotaatio käytetään pääsovelluksen luokan tasolla. Se määrittelee paketit, jotka skannataan reittejä varten, ja käsittelee muita reititykseen liittyviä asetuksia, kuten kehyksen alustamista ja näkyvyyden hallintaa.

Tässä on perusesimerkki:

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
    // Sovelluslogiikka tähän
  }
}
```

:::tip Routifyn oletusasetukset
**`@Routify`**-annotaatio sisältää kohtuullisia oletusasetuksia. Sen oletetaan, että nykyistä pakettia, jossa sovellus on määritelty, sekä kaikkia sen alipaketteja skannataan reittejä varten. Lisäksi sen oletetaan, että sovellus hallitsee vain yhtä kehystä oletusarvoisesti. Jos sovelluksesi noudattaa tätä rakennetta, sinun ei tarvitse antaa mukautettuja asetuksia annotaatiolle.
:::

## `@Routify`:n avainelementit {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

`packages`-elementti määrittelee, mitkä paketit skannataan reittimäärityksiä varten. Se mahdollistaa automaattisen reittien löytymisen ilman manuaalista rekisteröintiä, mikä virtaviivaistaa sovelluksen reititysjärjestelmän laajentamista.

```java
@Routify(packages = {"com.myapp.views"})
```

Jos paketteja ei ole määritelty, käytetään sovelluksen oletuspakettia.

### 2. **`defaultFrameName`** {#2-defaultframename}

Tämä elementti määrittelee oletuskehyksen nimen, jonka sovellus alustaa. Kehykset edustavat korkeimman tason käyttöliittymäastioita, ja tämä asetus hallitsee, miten ensimmäinen kehys nimetään ja hallitaan.

```java
@Routify(defaultFrameName = "MainFrame")
```

Oletusarvoisesti, jos sitä ei määritellä erikseen, arvo on asetettu `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

`initializeFrame`-lippu määrittelee, aloitetaanko kehys automaattisesti, kun sovellus käynnistyy. Asettamalla tämän arvoksi `true` yksinkertaistaa ensimmäisen kehyksen asetusta.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Tämä elementti hallitsee, hallitseeko kehys automaattisesti kehysten näkyvyyttä navigoinnin aikana. Kun se on käytössä, vastaava reitti näyttää automaattisesti vastaavan kehyksen samalla, kun muut piilotetaan, varmistaen siistin ja keskittyneen käyttöliittymän. Tämä asetus on merkityksellinen vain, kun sovellus hallitsee useita kehyksiä.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

`debug`-lippu mahdollistaa tai estää reitityksen virheenkorjaustilan. Kun se on käytössä, reititysinformaatiota ja toimintoja kirjataan konsoliin helpottamaan virheenkorjausta kehityksen aikana.

```java
@Routify(debug = true)
```

:::info Reitittimen virheenkorjaustila ja webforJ:n virheenkorjaustila  
Jos reitittimen virheenkorjaustila on asetettu `true`, mutta webforJ:n virheenkorjaustila on asetettu `false`, konsolissa ei näytetä virheenkorjaustietoja.  
:::
