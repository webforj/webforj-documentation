---
title: Prerequisites
sidebar_position: 1
_i18n_hash: 04d23999dd3acdb300f018ac2a5aeeb7
---
Met webforJ aan de slag gaan is eenvoudig, omdat er slechts een paar vereisten zijn. Gebruik deze gids om uw ontwikkelomgeving in te stellen met de essentiële tools die u nodig heeft om aan de slag te gaan met webforJ.

## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

Een Java Development Kit (JDK) is de belangrijkste vereiste voor ontwikkeling met webforJ en biedt de noodzakelijke tools om Java-apps te compileren, uitvoeren en beheren. Java **21** is vereist om compatibiliteit met webforJ te waarborgen en toegang te krijgen tot de nieuwste functies en beveiligingsupdates van het Java-ecosysteem. Het webforJ-framework is compatibel met de officiële Oracle JDK's en de open source Eclipse Temurin JDK's.

### JDK-installatielinks: {#jdk-installation-links}
:::tip  
Als u een UNIX-gebaseerd besturingssysteem gebruikt, is het aan te raden om [SDKMAN!](https://sdkman.io/) te gebruiken om uw Java-omgeving te beheren. Hiermee kunt u eenvoudig schakelen tussen verschillende Java-leveranciers zonder extra gedoe.  

Als alternatief kunt u [Jabba](https://github.com/shyiko/jabba) gebruiken, dat zowel op UNIX-gebaseerde systemen als op Windows werkt. Het is een degelijke cross-platformoplossing voor het beheren van Java-versies.  
:::

- Officiële Oracle JDK's zijn te vinden op de [Java Downloads](https://www.oracle.com/java/technologies/downloads/) pagina van Oracle. 
  - Selecteer Java versie **21**.
  - Klik op het tabblad voor Linux, macOS of Windows.
  - Klik op de link die overeenkomt met de architectuur van uw computer. 
  - Zie Oracle's [JDK Installatiehandleiding](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) voor volledige informatie over het installeren van een Oracle JDK.
- Open source JDK's zijn te vinden op de [Eclipse Temurin™ Laatste Releases](https://adoptium.net/temurin/releases/) pagina van Adoptium. 
  - Gebruik de dropdown-menu's om het besturingssysteem, de architectuur, het pakkettype en JDK versie **21** te selecteren. 
  - Klik op de link in de tabel voor het archieftype dat u wilt downloaden.
  - Zie Adoptium's [Installatiehandleiding](https://adoptium.net/installation/) voor volledige informatie over het installeren van een Eclipse Temurin JDK.

### Verifieer uw JDK-installatie {#verify-your-jdk-installation}
Na het installeren van de JDK, verifieert u de installatie door de volgende opdracht in uw terminal of opdrachtprompt uit te voeren:

```bash
java -version
```

Als uw JDK correct is geïnstalleerd, ziet u uitvoer met de details van uw JDK-versie, die versie **21** aangeeft.

## Apache Maven {#apache-maven}

[Apache Maven](https://maven.apache.org/index.html) is een buildautomatiserings- en afhankelijkheidsbeheerprogramma dat het proces vereenvoudigt van het opnemen van externe bibliotheken zoals webforJ in uw project. Naast het helpen met afhankelijkheidsbeheer kan Maven taken automatiseren zoals het compileren van code, het uitvoeren van tests en het inpakken van toepassingen.

### Maven-installatielinks {#maven-installation-links}
- Om de nieuwste versie van Maven te installeren, gaat u naar de [Apache Maven Download Pagina](https://maven.apache.org/download.cgi). 
  - De pagina [Apache Maven installeren](https://maven.apache.org/install.html) van Maven heeft een overzicht van het installatieproces. 
  - Baeldung's [Hoe Maven te installeren op Windows, Linux en Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) is een meer diepgaande installatiehandleiding voor elk besturingssysteem.

### Verifieer uw Maven-installatie {#verify-your-maven-installation}

Na het installeren van Maven, verifieert u de installatie door de volgende opdracht in uw terminal of opdrachtprompt uit te voeren:

```bash
mvn -v
```

Als Maven correct is geïnstalleerd, zou de uitvoer de Maven-versie, Java-versie en informatie over het besturingssysteem moeten tonen.

## Java IDE {#java-ide}

Een Java IDE biedt een uitgebreide omgeving voor het schrijven, testen en debuggen van uw code. Er zijn veel IDE's om uit te kiezen, dus u kunt degene kiezen die het beste bij uw workflow past. Enkele populaire keuzes voor Java-ontwikkeling zijn:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Een lichte, uitbreidbare code-editor met ondersteuning voor Java via plugins.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Bekend om zijn krachtige Java-ondersteuning en rijke plugin-ecosysteem.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: Een gratis, open source IDE voor Java en andere talen, bekend om zijn gebruiksgemak en ingebouwde projecttemplates.
