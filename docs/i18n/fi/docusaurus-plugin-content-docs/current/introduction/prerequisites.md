---
title: Prerequisites
description: >-
  Set up a webforJ development environment with JDK 21, Apache Maven, and a
  supported Java IDE such as IntelliJ, VS Code, or NetBeans.
sidebar_position: 1
_i18n_hash: d5a639b85898cdb73710fdbbd8ff8033
---
WebforJ:n kanssa alkuun pääseminen on helppoa, koska vaatimuksia on vain muutama. Käytä tätä opasta asettaaksesi kehitysympäristösi perustyökalujen kanssa, joita tarvitset aloittaaksesi webforJ:n käytön.

<!-- vale off -->
## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

<!-- vale on -->

Java Development Kit (JDK) on tärkein vaatimus webforJ:llä kehittämiseen, sillä se tarjoaa tarvittavat työkalut Java-sovellusten kokoamiseen, suorittamiseen ja hallintaan. 
Java **21** on pakollinen, jotta varmistetaan yhteensopivuus webforJ:n kanssa ja pääsy Java-ekosysteemin viimeisimpiin ominaisuuksiin ja tietoturvapäivityksiin. WebforJ-tila on yhteensopiva virallisten Oracle JDK:iden sekä avoimen lähdekoodin Eclipse Temurin JDK:iden kanssa.
<!-- vale off -->
### JDK installation links: {#jdk-installation-links}
<!-- vale on -->
:::tip  
Jos käytät UNIX-pohjaista käyttöjärjestelmää, suositellaan käyttämään [SDKMAN!](https://sdkman.io/) Java-ympäristön hallintaan. Se antaa sinun helposti vaihtaa eri Java-toimittajien välillä ilman ylimääräistä vaivannäköä.  

Vaihtoehtoisesti voit käyttää [Jabba](https://github.com/Jabba-Team/jabba), joka toimii sekä UNIX-pohjaisilla järjestelmillä että Windowsilla. Se on luotettava monialustaratkaisu Java-versioiden hallintaan.  
:::

- Viralliset Oracle JDK:t löytyvät Oracle:n [Java Downloads](https://www.oracle.com/java/technologies/downloads/) -sivulta. 
  - Valitse Java-versio **21**.
  - Napsauta Linux-, macOS- tai Windows-välilehteä.
  - Napsauta linkkiä, joka vastaa tietokoneesi arkkitehtuuria. 
  - Katso Oracle:n [JDK Installation Guide](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) saadaksesi täydelliset tiedot Oracle JDK:n asentamisesta.
- Avoimen lähdekoodin JDK:t löytyvät Adoptiumin [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) -sivulta. 
  - Käytä pudotusvalikkoja valitaksesi käyttöjärjestelmän, arkkitehtuurin, pakettityypin ja JDK-version **21**. 
  - Napsauta taulukosta linkkiä haluamallesi arkistotyypille, jonka haluat ladata.
  - Katso Adoptiumin [Installation Guide](https://adoptium.net/installation/) saadaksesi täydelliset tiedot Eclipse Temurin JDK:n asentamisesta.

<!-- vale off -->
### Verify your JDK installation {#verify-your-jdk-installation}
<!-- vale on -->
Asennettuasi JDK:n varmista asennus suorittamalla seuraava komento pääte- tai komentokehoteikkunassa:

```bash
java -version
```

Jos JDK on asennettu oikein, näet tulosteen, jossa on JDK-version tiedot, mikä osoittaa, että versio on **21**.
<!-- vale off -->
## Apache Maven {#apache-maven}
<!-- vale on -->

[Apache Maven](https://maven.apache.org/index.html) on build-automaatio- ja riippuvuudenhallintatyökalu, joka yksinkertaistaa prosessia ulkoisten kirjastoiden, kuten webforJ:n, sisällyttämiseksi projektiisi. 
Riippuvuudenhallinnan lisäksi Maven voi automatisoida tehtäviä, kuten koodin kääntämistä, testien suorittamista ja sovellusten pakkaamista.

### Maven installation links {#maven-installation-links}
- Asentaaksesi uusimman version Mavenista, siirry [Apache Maven Download Page](https://maven.apache.org/download.cgi) -sivulle. 
  - Mavenin [Installing Apache Maven](https://maven.apache.org/install.html) -sivulla on yleiskatsaus asennusprosessista. 
  - Baeldungin [How to Install Maven on Windows, Linux, and Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) tarjoaa syvällisemmän asennusoppaan kuhunkin käyttöjärjestelmään.

<!-- vale off -->
### Verify your Maven installation {#verify-your-maven-installation}

<!-- vale on -->

Asennettuasi Mavenin varmista asennus suorittamalla seuraava komento pääte- tai komentokehoteikkunassa:

```bash
mvn -v
```

Jos Maven on asennettu oikein, tulosteen tulisi näyttää Mavenin versio, Java-versio ja käyttöjärjestelmän tiedot.

## Java IDE {#java-ide}

Java IDE tarjoaa kattavan ympäristön koodin kirjoittamiseen, testaamiseen ja virheenkorjaukseen. Valittavana on monia IDEjä, joten voit valita sen, joka sopii työvirtaasi parhaiten. Joitakin suosittuja valintoja Java-kehitykselle ovat:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Kevyt, laajennettavissa oleva koodieditori, joka tukee Javaa liitännäisten avulla.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Tunnettu tehokkaasta Java-tuesta ja rikkaasta liitännäis-ekosysteemistään.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: Ilmainen, avoimen lähdekoodin IDE Javaa ja muita kieliä varten, tunnettu käytön helppoudesta ja sisäänrakennetuista projektimalleista.
