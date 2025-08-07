---
title: Prerequisites
sidebar_position: 1
_i18n_hash: 04d23999dd3acdb300f018ac2a5aeeb7
---
Aloittaminen webforJ:n kanssa on yksinkertaista, koska ennakkoedellytyksiä on vain muutama. Käytä tätä opasta kehitysympäristön asettamiseen tarvittavilla työkaluilla, joita tarvitset päästäksesi alkuun webforJ:n kanssa.

## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

Java Development Kit (JDK) on tärkein vaatimus webforJ:llä kehittämiseen, sillä se tarjoaa tarvittavat työkalut Java-sovellusten kääntämiseen, suorittamiseen ja hallintaan. Java **21** vaaditaan yhteensopivuuden varmistamiseksi webforJ:n kanssa ja pääsyn saamiseksi Java-ekosysteemin viimeisimpiin ominaisuuksiin ja turvallisuuspäivityksiin. webforJ-kehys on yhteensopiva virallisten Oracle JDK:iden ja avointen lähdekoodien Eclipse Temurin JDK:iden kanssa.

### JDK asennuslinkit: {#jdk-installation-links}
:::tip  
Jos käytät UNIX-pohjaista käyttöjärjestelmää, suositellaan [SDKMAN!:n](https://sdkman.io/) käyttöä Java-ympäristön hallintaan. Sen avulla voit helposti vaihtaa eri Java-toimittajien välillä ilman ylimääräistä vaivannäköä.  

Vaihtoehtoisesti voit käyttää [Jabba](https://github.com/shyiko/jabba), joka toimii sekä UNIX-pohjaisissa järjestelmissä että Windowsissa. Se on hyvä käyttöjärjestelmien välinen ratkaisu Java-versioiden hallintaan.  
:::

- Viralliset Oracle JDK:t löytyvät Oraclen [Java Downloads](https://www.oracle.com/java/technologies/downloads/) -sivulta. 
  - Valitse Java-versio **21**.
  - Napsauta Linux-, macOS- tai Windows-välilehteä.
  - Napsauta linkkiä, joka vastaa tietokoneesi arkkitehtuuria. 
  - Katso Oraclen [JDK Installation Guide](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) saadaksesi täydelliset tiedot Oracle JDK:n asentamisesta.
- Avoimen lähdekoodin JDK:t löytyvät Adoptiumin [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) -sivulta. 
  - Käytä pudotusvalikkoja valitaksesi käyttöjärjestelmän, arkkitehtuurin, pakettityypin ja JDK-version **21**. 
  - Napsauta taulukossa olevaa linkkiä, joka vastaa haluamaasi arkistotyyppiä ladataksesi.
  - Katso Adoptiumin [Installation Guide](https://adoptium.net/installation/) saadaksesi täydelliset tiedot Eclipse Temurin JDK:n asentamisesta.

### Vahvista JDK-asennuksesi {#verify-your-jdk-installation}
Asennuksen jälkeen varmista, että JDK on asennettu oikein suorittamalla seuraava komento terminaalissasi tai komentokehotteessasi:

```bash
java -version
```

Jos JDK:si on asennettu oikein, näet tulosteen, jossa on JDK-versiotiedot, mikä osoittaa version **21**.

## Apache Maven {#apache-maven}

[Apache Maven](https://maven.apache.org/index.html) on rakennusautomaatio- ja riippuvuudenhallintatyökalu, joka yksinkertaistaa ulkoisten kirjastojen, kuten webforJ:n, lisäämistä projektiisi. Maven voi lisäksi automatisoida tehtäviä, kuten koodin kääntämistä, testien suorittamista ja sovellusten pakkaamista.

### Maven asennuslinkit {#maven-installation-links}
- Asentaaksesi viimeisimmän version Mavanista, siirry [Apache Maven Download Page](https://maven.apache.org/download.cgi) -sivulle. 
  - Mavanin [Installing Apache Maven](https://maven.apache.org/install.html) -sivulla on yleiskatsaus asennusprosessista. 
  - Baeldungin [How to Install Maven on Windows, Linux, and Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) tarjoaa syvällisemmän asennusoppaan jokaiselle käyttöjärjestelmälle.

### Vahvista Maven-asennuksesi {#verify-your-maven-installation}

Asennuksen jälkeen varmista, että Maven on asennettu oikein suorittamalla seuraava komento terminaalissasi tai komentokehotteessasi:

```bash
mvn -v
```

Jos Maven on asennettu oikein, tulosteen tulisi näyttää Maven-versio, Java-versio ja käyttöjärjestelmän tiedot.

## Java IDE {#java-ide}

Java IDE tarjoaa kattavan ympäristön koodisi kirjoittamiseen, testaamiseen ja virheenkorjaamiseen. Valittavana on monia IDE:itä, joten voit valita mieleisesi sen mukaan, mikä sopii työskentelytapaasi. Joitain suosittuja valintoja Java-kehityksessä ovat:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Kevyt, laajennettavissa oleva koodieditori, jossa on Java-tuki liitännäisten kautta.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Tunnettu tehokkaasta Java-tuestaan ja rikkaasta liitännäisvalikoimastaan.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: Ilmainen, avoimen lähdekoodin IDE Java:lle ja muille kielille, tunnettu käytön helppoudesta ja sisäänrakennetuista projektimalleista.
