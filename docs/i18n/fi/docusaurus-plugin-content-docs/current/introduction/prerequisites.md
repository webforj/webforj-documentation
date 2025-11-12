---
title: Edellytykset
sidebar_position: 1
_i18n_hash: 079539f07a72647e2faa9a9a5eda5634
---
Aloittaminen webforJ:n kanssa on helppoa, koska vaatimuksia on vain muutama. Käytä tätä opasta kehitysympäristön asettamiseen tarvitsemasi olennaisten työkalujen kanssa, jotta pääset alkuun webforJ:n parissa.

## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

Java Development Kit (JDK) on tärkein vaatimus webforJ:llä kehittämiselle, sillä se tarjoaa tarvittavat työkalut Java-sovellusten kääntämiseen, suorittamiseen ja hallintaan. Java **21** on vaadittu yhteensopivuuden varmistamiseksi webforJ:n kanssa sekä pääsyn saamiseksi Java-ekosysteemin uusimpiin ominaisuuksiin ja tietoturvapäivityksiin. webforJ-alusta on yhteensopiva virallisten Oracle JDK:iden ja avoimen lähdekoodin Eclipse Temurin JDK:iden kanssa.

### JDK installation links: {#jdk-installation-links}
:::tip  
Jos käytät UNIX-pohjaista käyttöjärjestelmää, on suositeltavaa käyttää [SDKMAN!](https://sdkman.io/) -työkalua Java-ympäristösi hallintaan. Sen avulla voit helposti vaihtaa eri Java-toimittajien välillä ilman lisävaivannäköä.  

Vaihtoehtoisesti voit käyttää [Jabba](https://github.com/shyiko/jabba), joka toimii sekä UNIX-pohjaisilla järjestelmillä että Windowsilla. Se on hyvä alustojen välinen ratkaisu Java-versioiden hallintaan.  
:::

- Viralliset Oracle JDK:t löytyvät Oracle:n [Java Downloads](https://www.oracle.com/java/technologies/downloads/) sivulta. 
  - Valitse Java-versio **21**.
  - Napsauta Linux-, macOS- tai Windows-välilehteä.
  - Napsauta linkkiä, joka vastaa tietokoneesi arkkitehtuuria. 
  - Katso Oracle:n [JDK Installation Guide](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) saadaksesi täydelliset tiedot Oracle JDK:n asentamisesta.
- Avoimen lähdekoodin JDK:t löytyvät Adoptiumin [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) sivulta. 
  - Käytä pudotusvalikoita valitaksesi käyttöjärjestelmä, arkkitehtuuri, pakettityyppi ja JDK-versio **21**. 
  - Napsauta taulukossa linkkiä haluamasi arkistotyypin lataamiseksi.
  - Katso Adoptiumin [Installation Guide](https://adoptium.net/installation/) saadaksesi täydelliset tiedot Eclipse Temurin JDK:n asentamisesta.

### Verify your JDK installation {#verify-your-jdk-installation}
Kun olet asentanut JDK:n, varmista asennus suorittamalla seuraava komento terminaalissasi tai komentokehotteessasi:

```bash
java -version
```

Jos JDK on asennettu oikein, näet tulosteessa JDK-version tiedot, jotka osoittavat version **21**.

## Apache Maven {#apache-maven}

[Apache Maven](https://maven.apache.org/index.html) on rakennusautomaatio- ja riippuvuuden hallintatyökalu, joka yksinkertaistaa ulkoisten kirjastojen, kuten webforJ:n, lisäämisen prosessia projektiisi. 
Maven ei ainoastaan auta riippuvuuden hallinnassa, vaan se voi myös automatisoida tehtäviä, kuten koodin kääntämistä, testien suorittamista ja sovellusten pakkaamista.

### Maven installation links {#maven-installation-links}
- Asentaaksesi uusimman version Mavenista, siirry [Apache Maven Download Page](https://maven.apache.org/download.cgi) sivulle. 
  - Mavenin [Installing Apache Maven](https://maven.apache.org/install.html) sivulla on yleiskatsaus asennusprosessista. 
  - Baeldungin [How to Install Maven on Windows, Linux, and Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) on syvällisempi asennusopas jokaiselle käyttöjärjestelmälle.

### Verify your Maven installation {#verify-your-maven-installation}

Kun olet asentanut Mavenin, varmista asennus suorittamalla seuraava komento terminaalissasi tai komentokehotteessasi:

```bash
mvn -v
```

Jos Maven on asennettu oikein, tulosteessa tulisi näkyä Mavenin versio, Java-versio ja käyttöjärjestelmän tiedot.

## Java IDE {#java-ide}

Java IDE tarjoaa kattavan ympäristön koodisi kirjoittamiseen, testaamiseen ja virheiden debuggamiseen. Valittavana on monia IDE:itä, joten voit valita sellaisen, joka sopii työskentelytapaasi. Joitakin suosittuja valintoja Java-kehityksessä ovat:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Kevyt, laajennettava koodieditori, jossa on Java-tuki laajennusten avulla.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Tunnettu voimakkaasta Java-tuestaan ja rikkaasta laajennus-ekosysteemistään.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: Ilmainen, avoimen lähdekoodin IDE Java- ja muiden kielten kehittämiseen, joka on tunnettu käytön helppoudesta ja valmiista projektimalleista.
