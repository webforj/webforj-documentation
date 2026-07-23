---
title: Prerequisites
description: >-
  Set up a webforJ development environment with JDK 21, Apache Maven, and a
  supported Java IDE such as IntelliJ, VS Code, or NetBeans.
sidebar_position: 1
_i18n_hash: 03fdfcdc58e52eabd51a8f9dbda568e6
---
Aloittaminen webforJ:n kanssa on yksinkertaista, koska vaatimuksia on vain muutama. Käytä tätä opasta asetuksesi kehitysympäristölle ja niille olennaisille työkaluilla, joita tarvitset päästäksesi alkuun webforJ:n kanssa.

## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

Java Development Kit (JDK) on tärkein vaatimus webforJ:llä kehittämiseen, sillä se tarjoaa tarvittavat työkalut Java-sovellusten kääntämiseen, suorittamiseen ja hallintaan. Java **21** on vaadittu, jotta varmistetaan yhteensopivuus webforJ:n kanssa ja pääsy Java-ekosysteemin viimeisimpiin ominaisuuksiin ja tietoturvapäivityksiin. WebforJ-kehys on yhteensopiva virallisten Oracle JDK:iden ja avoimen lähdekoodin Eclipse Temurin JDK:iden kanssa.

### JDK installation links: {#jdk-installation-links}
:::tip
Jos käytät UNIX-pohjaista käyttöjärjestelmää, suositellaan käytettäväksi [SDKMAN!](https://sdkman.io/) hallitsemaan Java-ympäristöäsi. Se mahdollistaa helposti vaihtamisen eri Java-toimittajien välillä ilman ylimääräistä vaivannäköä.

Vaihtoehtoisesti voit käyttää [Jabba](https://github.com/Jabba-Team/jabba), joka toimii sekä UNIX-pohjaisilla järjestelmillä että Windowsissa. Se on hyvä monialustaratkaisu Java-versioiden hallintaan.
:::

- Viralliset Oracle JDK:t löytyvät Oraclen [Java Downloads](https://www.oracle.com/java/technologies/downloads/) -sivulta.
  - Valitse Java-versio **21**.
  - Napsauta Linux-, macOS- tai Windows-välilehteä.
  - Napsauta linkkiä, joka vastaa tietokoneesi arkkitehtuuria.
  - Katso Oraclen [JDK Installation Guide](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) saadaksesi täydelliset tiedot Oracle JDK:n asentamisesta.
- Avoimen lähdekoodin JDK:t löytyvät Adoptiumin [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) -sivulta.
  - Käytä alasvetovalikkoja valitaksesi käyttöjärjestelmä, arkkitehtuuri, pakettityyppi ja JDK-versio **21**.
  - Napsauta taulukossa linkkiä arkistotyypille, jonka haluat ladata.
  - Katso Adoptiumin [Installation Guide](https://adoptium.net/installation/) saadaksesi täydelliset tiedot Eclipse Temurin JDK:n asentamisesta.

### Verify your JDK installation {#verify-your-jdk-installation}
Asennettuasi JDK:n varmista asennus suorittamalla seuraava komento terminaalissasi tai komentokehotteessasi:

```bash
java -version
```

Jos JDK:si on asennettu oikein, näet tulosteen, jossa on JDK-versiotiedot, mikä osoittaa version **21**.

## Apache Maven {#apache-maven}

[Apache Maven](https://maven.apache.org/index.html) on rakennusautomaatio- ja riippuvuuksien hallintatyökalu, joka yksinkertaistaa ulkoisten kirjastoiden, kuten webforJ:n, lisäämistä projektiisi. Maven auttaa riippuvuuksien hallinnassa, ja se voi automatisoida tehtäviä kuten koodin kääntämistä, testien suorittamista ja sovellusten pakkaamista.

### Maven installation links {#maven-installation-links}
- Asentaaksesi viimeisimmän version Mavenista, siirry [Apache Maven Download Page](https://maven.apache.org/download.cgi) -sivulle.
  - Mavenin [Installing Apache Maven](https://maven.apache.org/install.html) -sivulla on yleiskatsaus asennusprosessista.
  - Baeldungin [How to Install Maven on Windows, Linux, and Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) on perusteellisempi asennusopas kullekin käyttöjärjestelmälle.

### Verify your Maven installation {#verify-your-maven-installation}
Asennettuasi Mavenin varmista asennus suorittamalla seuraava komento terminaalissasi tai komentokehotteessasi:

```bash
mvn -v
```

Jos Maven on asennettu oikein, tulosteen tulisi näyttää Maven-versio, Java-versio ja käyttöjärjestelmätiedot.

## Java IDE {#java-ide}

Java IDE tarjoaa kattavan ympäristön koodisi kirjoittamiseen, testaamiseen ja virheenkorjaukseen. Valittavana on monia IDE:itä, joten voit valita sen, joka parhaiten sopii työskentelytapaasi. Joitakin suosittuja valintoja Java-kehitykselle ovat:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Kevyt, laajennettava koodieditori, jossa on Java-tuki liitännäisten kautta.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Tunnettu tehokkaasta Java-tuesta ja monipuolisista liitännäisistä.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: Ilmainen, avoimen lähdekoodin IDE Java:lle ja muille kielille, tunnettu käytettävyyttään ja sisäänrakennetuista projektiensa malleista.
