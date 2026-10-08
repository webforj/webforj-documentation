---
title: Esitietovaatimukset
description: >-
  What a webforJ development environment needs, a Java 21 or higher JDK, Maven
  or Gradle, and an editor with Java support.
sidebar_position: 1
_i18n_hash: 038e0cf692852d650329b263c25aaf55
---
Getting started with webforJ onko helppoa, koska tarpeita on vain muutama. Käytä tätä oppaata asettaaksesi kehitysympäristösi niillä olennaisilla työkaluilla, joita tarvitset päästäksesi alkuun webforJ:n kanssa.

<!-- vale off -->
## Java Development Kit (JDK) {#java-development-kit-jdk-21}
<!-- vale on -->

webforJ vaatii Java **21** tai uudemman. Mikä tahansa jakelu, joka on tuossa versiossa, toimii, joten valitse se, jota tiimisi jo käyttää.

:::tip Suositeltu kehitykseen
Kehitä [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) -version avulla. Se hyväksyy `-XX:+AllowEnhancedClassRedefinition` -option, joka mahdollistaa [hotswap-työkalun](/docs/configuration/deploy-reload/hotswap) tuoda muutoksen luokan rakenteeseen, uuden kentän tai uuden metodin käynnissä olevaan sovellukseen.

Muualla tehdyissä muokkauksissa metodin rungossa päivitykset vaikuttavat paikallisesti, mutta muutos luokan rakenteeseen odottaa uudelleenkäynnistystä. Valinta koskee vain konetta, jolla kehität, eikä vaikuta pakkaamaasi tai minne sen julkaiset.
:::

Versiohallintaohjelma on helpoin tapa asentaa JDK ja helpoin tapa siirtyä versioiden välillä myöhemmin. [SDKMAN!](https://sdkman.io/) kattaa UNIX-järjestelmät, ja [Jabba](https://github.com/Jabba-Team/jabba) kattaa UNIX-järjestelmät ja Windowsin. SDKMAN!:n alla `sdk install java 21.0.11-jbr` saa sinulle JetBrains Runtime -version.

Jos haluat ladata version itse:

- **Oracle JDK**: [Java Downloads](https://www.oracle.com/java/technologies/downloads/) -sivu, Oracle:n [asennusopas](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html).
- **Eclipse Temurin**: [viimeisimmät julkaisut](https://adoptium.net/temurin/releases/) -sivu, Adoptiimin [asennusopas](https://adoptium.net/installation/).
- **JetBrains Runtime**: [julkaisut](https://github.com/JetBrains/JetBrainsRuntime/releases) -sivu.

Suorita `java -version` varmistaaksesi, mikä versio on polullasi.

## Build tool {#build-tool}

webforJ rakennetaan Mavenilla tai Gradlella. [Archetypes](/docs/introduction/getting-started) generoivat Maven-projekteja, joten Maven on nopein tapa uusiin sovelluksiin, ja olemassa oleva Gradle-build toimii samalla tavalla.

<Tabs>
<TabItem value="maven" label="Maven">

Asenna Maven [Apache Mavenin lataussivulta](https://maven.apache.org/download.cgi), seuraten Maveneiden [asennusohjeita](https://maven.apache.org/install.html) tai Baeldungin [opasta jokaiselle käyttöjärjestelmälle](https://www.baeldung.com/install-maven-on-windows-linux-mac).

Suorita `mvn -v` varmistaaksesi asennuksen.

</TabItem>
<TabItem value="gradle" label="Gradle">

Asenna Gradle seuraamalla Gradlen [asennusoppaita](https://gradle.org/install/).

Suorita `gradle -v` varmistaaksesi asennuksen. Projekti, joka toimittaa Gradle-wrapperin, ei tarvitse ollenkaan asennusta, sillä `./gradlew` lataa version, jonka projekti määrittää.

</TabItem>
</Tabs>

Molemmat buildit suorittavat webforJ:n build-aika työtä [webforJ build pluginin](/docs/configuration/build-plugin) kautta, jota projekti, joka on luotu archetypestä, jo sisältää.

## Editor {#java-ide}

Mikä tahansa Java-tukea tarjoava editori toimii, joten käytä sitä, joka sopii työskentelytapaasi. Yleisiä valintoja:

- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Java-tuki ja laajennus-ekosysteemi valmiina.
- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Kevyt editori, joka saa Java-tukensa laajennuksista.
- **[Zed](https://zed.dev/download)**: Koodieditori, joka hyödyntää Javaa laajennuksen kautta, joka lataa ja hallinnoi Eclipse Java -kielipalvelinta puolestasi.
