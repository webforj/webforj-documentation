---
sidebar_position: 2
title: Routable Apps
_i18n_hash: edec1086b0723febd831816f8d1fa76a
---
Routing in webforJ on valinnainen työkalu. Kehittäjät voivat valita webforJ-reititysratkaisun tai perinteisen mallin, jossa käytetään `Frame`-manipulaatiota ilman syvää linkittämistä. Reitityksen mahdollistamiseksi **`@Routify`**-annotaatio on sovellettava `App`-luokan tason. Tämä antaa webforJ:lle valtuudet hallita selainhistorian, reagoida navigointitapahtumiin ja renderöidä sovelluksen komponentit URL-osoitteen perusteella.

:::info
Jos haluat oppia lisää käyttöliittymien rakentamisesta kehysten, sisäänrakennettujen ja mukautettujen komponenttien avulla, vieraile [Käyttöliittymien rakentaminen](../building-ui/overview).
:::

## `@Routify`-annotaation tarkoitus {#purpose-of-the-routify-annotation}

**`@Routify`** mahdollistaa kehyksen automaattisen reitityksen rekisteröinnin, kehysten näkyvyyden hallinnan ja reititys käyttäytymisten, kuten vianetsinnän ja kehysten alustus, määrittämisen, mikä mahdollistaa dynaamisen ja joustavan reitityksen sovelluksessa.

## `@Routify`-annotaation käyttö {#usage-of-routify}

**`@Routify`**-annotaatio sovelletaan pääsovellusluokan tason. Se määrittää pakettijoukon, jota skannataan reittien varalta, ja käsittelee muita reititykseen liittyviä asetuksia, kuten kehysten alustus ja näkyvyyden hallinta.

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
    // Sovelluslogiikka menee tänne
  }
}
```

:::tip Routify'n oletusasetukset
**`@Routify`**-annotaatio tulee kohtuullisten oletusasetusten kanssa. Se olettaa, että nykyistä pakettia, jossa sovellus on määritelty, sekä kaikkia sen alipaketteja, tulisi skannata reittien varalta. Lisäksi se olettaa, että sovellus hallitsee oletuksena vain yhtä kehystä. Jos sovelluksesi noudattaa tätä rakennetta, ei ole tarpeen antaa mukautettuja asetuksia annotaatioon.
:::

## `@Routify`-annotaation tärkeimmät elementit {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

`packages`-elementti määrittää, mitkä paketit tulisi skannata reittimäärityksiä varten. Se mahdollistaa reittien automaattisen löytämisen ilman manuaalista rekisteröintiä, mikä sujuvoittaa sovelluksen reititysjärjestelmän laajentamista.

```java
@Routify(packages = {"com.myapp.views"})
```

Jos paketteja ei ole määritelty, sovelluksen oletuspakettia käytetään.

### 2. **`defaultFrameName`** {#2-defaultframename}

Tämä elementti määrittää oletuskehuksen nimen, jonka sovellus alustaa. Kehykset edustavat ylimpiä käyttöliittymätuotteita, ja tämä asetus hallitsee, miten ensimmäinen kehys nimetään ja hallitaan.

```java
@Routify(defaultFrameName = "MainFrame")
```

Oletusarvoisesti, jos arvoa ei ole nimenomaan annettu, se asetetaan `Routify.DEFAULT_FRAME_NAME`:ksi.

### 3. **`initializeFrame`** {#3-initializeframe}

`initializeFrame`-lippu määrää, alustaako kehys automaattisesti ensimmäisen kehys sovelluksen käynnistyessä. Tämän asettaminen arvoksi `true` yksinkertaistaa ensimmäisen kehysasetuksen.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Tämä elementti hallitsee, hallitseeko kehys automaattisesti kehysten näkyvyyden navigoinnin aikana. Kun se on käytössä, vastaava reitti näyttää automaattisesti vastaavan kehyksen ja piilottaa muut, varmistaen siistin ja keskittyneen käyttöliittymän. Tämä asetus on merkityksellinen vain, kun sovellus hallitsee useita kehyksiä.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

`debug`-lippu mahdollistaa tai estää reititysvirhetilan. Kun se on käytössä, reititystiedot ja toiminnot kirjataan konsoliin helpompaa vianetsintää varten kehityksen aikana.

```java
@Routify(debug = true)
```

:::info Reititin debug-tila ja webforJ debug-tila  
Jos reitittimen debug-tila on asetettu todeksi, mutta webforJ:n debug-tila on asetettu epätodeksi, konsolissa ei näytetä vianetsintätietoja.  
:::
