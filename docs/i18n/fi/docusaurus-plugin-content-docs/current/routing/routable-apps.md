---
sidebar_position: 2
title: Routable Apps
description: >-
  Enable webforJ routing with the @Routify annotation to scan packages, manage
  frames, and control browser history.
_i18n_hash: bea0848523a00ddfff8d79265ea699ac
---
Routing in webforJ on ominainen työkalu. Kehittäjät voivat valita webforJ:n reititysratkaisun tai perinteisen mallin, jossa käytetään `Frame`-manipulaatiota ilman syvää linkittämistä. Reitityksen mahdollistamiseksi **`@Routify`**-annotation on sovellettava `App`-luokan tasolla. Tämä antaa webforJ:lle oikeuden hallita selaimen historiaa, reagoida navigointitapahtumiin ja renderöidä sovelluksen komponentteja URL-osoitteen perusteella.

:::info
Jos haluat oppia lisää käyttöliittymien rakentamisesta kehyksillä, sisäänrakennetuilla ja mukautetuilla komponenteilla, vieraile [Käyttöliittymien rakentaminen](../building-ui/overview).
:::

## `@Routify`-annotaation tarkoitus {#purpose-of-the-routify-annotation}

**`@Routify`** mahdollistaa kehyksen automaattisen reittien rekisteröinnin, kehyksen näkyvyyden hallinnan ja reitityskäyttäytymisen määrittämisen, kuten virheenkorjauksen ja kehyksen alustamisen, mikä mahdollistaa dynaamisen ja joustavan reitityksen sovelluksessa.

## `@Routify`-käyttö {#usage-of-routify}

**`@Routify`**-annotation on sovellettava pääsovelluksen luokan tasolla. Se määrittelee pakettijoukon, jota skannataan reittien löytämiseksi, ja käsittelee muita reititykseen liittyviä asetuksia, kuten kehyksen alustamista ja näkyvyyden hallintaa.

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
    // Sovelluksen logiikka menee tänne
  }
}
```

:::tip Routify Oletusasetukset
**`@Routify`**-annotation on varustettu kohtuullisilla oletusasetuksilla. Se olettaa, että nykyinen paketti, jossa sovellus määritellään, yhdessä kaikkien sen alapakettien kanssa, skannataan reittejä varten. Lisäksi se olettaa, että sovellus hallitsee oletuksena vain yhtä kehystä. Jos sovelluksesi seuraa tätä rakennetta, ei tarvitse antaa mukautettuja asetuksia annotaatiolle.
:::

## `@Routify`-avainelementit {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

`packages`-elementti määrittelee, mitkä paketit skannataan reittimäärittelyjen löytämiseksi. Se mahdollistaa reittien automaattisen löytämisen ilman manuaalista rekisteröintiä, jolloin sovelluksen reititysjärjestelmän laajentaminen sujuu helpommin.

```java
@Routify(packages = {"com.myapp.views"})
```

Jos paketteja ei ole määritetty, käytetään sovelluksen oletuspakettia.

### 2. **`defaultFrameName`** {#2-defaultframename}

Tämä elementti määrittelee sovelluksen alustaman oletuskehyksen nimen. Kehykset edustavat ylimmän tason käyttöliittymäkonteksteja, ja tämä asetus hallitsee, miten ensimmäinen kehys nimetään ja sitä hallitaan.

```java
@Routify(defaultFrameName = "MainFrame")
```

Oletuksena, jos arvoa ei ole selvästi annettu, se asetetaan arvoon `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

`initializeFrame`-lippu määrää, aloitetaanko kehys automaattisesti ensimmäisenä sovelluksen käynnistyessä. Asettaminen arvoon `true` yksinkertaistaa ensimmäisen kehyksen asennusta.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Tämä elementti hallitsee, tuleeko kehyksen automaattisesti vaihtaa näkyvyyttä navigoinnin aikana. Kun tämä on käytössä, vastaava reitti näyttää automaattisesti vastaavan kehyksen samalla, kun muut piilotetaan, varmistaen puhtaan ja keskittyneen käyttöliittymän. Tämä asetus on merkityksellinen vain, jos sovelluksesi hallitsee useita kehyksiä.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

`debug`-lippu mahdollistaa tai keskeyttää reitityksen virheenkorjaustilan. Kun se on käytössä, reititysinfo ja -toiminnat kirjataan konsoliin helpomman virheenkorjauksen vuoksi kehityksen aikana.

```java
@Routify(debug = true)
```

:::info Reitittimen virheenkorjaustila ja webforJ:n virheenkorjaustila
Jos reitittimen virheenkorjaustila on asetettu arvoon `true`, mutta webforJ:n virheenkorjaustila on asetettu arvoon `false`, konsolissa ei näytetä mitään virheenkorjaustietoja.
:::
