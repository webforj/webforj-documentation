---
title: Prerequisites
sidebar_position: 1
_i18n_hash: 079539f07a72647e2faa9a9a5eda5634
---
Het starten met webforJ is eenvoudig, omdat er maar een paar vereisten zijn. Gebruik deze gids om uw ontwikkelomgeving in te stellen met de essentiële tools die u nodig heeft om aan de slag te gaan met webforJ.

<!-- vale off -->
## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

<!-- vale on -->

Een Java Development Kit (JDK) is de belangrijkste vereiste voor het ontwikkelen met webforJ, omdat deze de noodzakelijke tools biedt om Java-apps te compileren, uit te voeren en te beheren. 
Java **21** is vereist om compatibiliteit met webforJ te waarborgen en toegang te krijgen tot de nieuwste functies en beveiligingsupdates van het Java-ecosysteem. Het webforJ-framework is compatibel met officiële Oracle JDK's en de open source Eclipse Temurin JDK's.
<!-- vale off -->
### JDK-installatielinks: {#jdk-installation-links}
<!-- vale on -->
:::tip  
Als u een op UNIX gebaseerd besturingssysteem gebruikt, wordt aanbevolen om [SDKMAN!](https://sdkman.io/) te gebruiken om uw Java-omgeving te beheren. Hiermee kunt u gemakkelijk schakelen tussen verschillende Java-leveranciers zonder extra gedoe.  

Als alternatief kunt u [Jabba](https://github.com/shyiko/jabba) gebruiken, dat werkt op zowel op UNIX gebaseerde systemen als Windows. Het is een solide platformonafhankelijke oplossing voor het beheren van Java-versies.  
:::

- Officiële Oracle JDK's zijn te vinden op de [Java Downloads](https://www.oracle.com/java/technologies/downloads/) pagina van Oracle. 
  - Selecteer Java-versie **21**.
  - Klik op het tabblad voor Linux, macOS of Windows.
  - Klik op de link die overeenkomt met de architectuur van uw computer. 
  - Zie Oracle's [JDK Installatiehandleiding](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) voor volledige informatie over het installeren van een Oracle JDK.
- Open source JDK's zijn te vinden op de [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) pagina van Adoptium. 
  - Gebruik de dropdown-menu's om het besturingssysteem, de architectuur, het pakket-type en JDK-versie **21** te selecteren. 
  - Klik op de link in de tabel voor het archieftype dat u wilt downloaden.
  - Zie Adoptium's [Installatiehandleiding](https://adoptium.net/installation/) voor volledige informatie over het installeren van een Eclipse Temurin JDK.

<!-- vale off -->
### Verifieer uw JDK-installatie {#verify-your-jdk-installation}
<!-- vale on -->
Na het installeren van de JDK, verifieer de installatie door de volgende opdracht in uw terminal of opdrachtprompt uit te voeren:

```bash
java -version
```

Als uw JDK correct is geïnstalleerd, ziet u uitvoer met uw JDK-versie details, die versie **21** aangeeft.
<!-- vale off -->
## Apache Maven {#apache-maven}
<!-- vale on -->

[Apache Maven](https://maven.apache.org/index.html) is een tool voor bouwautomatisering en afhankelijkheidsbeheer die het proces vereenvoudigt om externe bibliotheken zoals webforJ in uw project op te nemen. 
Naast het helpen bij afhankelijkheidsbeheer, kan Maven taken automatiseren zoals het compileren van code, het uitvoeren van tests en het inpakken van applicaties.

### Maven-installatielinks {#maven-installation-links}
- Ga naar de [Apache Maven Downloadpagina](https://maven.apache.org/download.cgi) om de nieuwste versie van Maven te installeren. 
  - Maven's [Installing Apache Maven](https://maven.apache.org/install.html) pagina biedt een overzicht van het installatieproces. 
  - Baeldung's [Hoe Maven te installeren op Windows, Linux en Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) is een meer uitgebreide installatiehandleiding voor elk besturingssysteem.

<!-- vale off -->
### Verifieer uw Maven-installatie {#verify-your-maven-installation}

<!-- vale on -->

Na het installeren van Maven, verifieer de installatie door de volgende opdracht in uw terminal of opdrachtprompt uit te voeren:

```bash
mvn -v
```

Als Maven correct is geïnstalleerd, zou de uitvoer de Maven-versie, Java-versie en informatie over het besturingssysteem moeten weergeven.

## Java IDE {#java-ide}

Een Java IDE biedt een uitgebreide omgeving voor het schrijven, testen en debuggen van uw code. Er zijn veel IDE's om uit te kiezen, zodat u degene kunt kiezen die het beste bij uw workflow past. Enkele populaire keuzes voor Java-ontwikkeling zijn:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Een lichte, uitbreidbare code-editor met Java-ondersteuning via plugins.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Bekend om zijn krachtige Java-ondersteuning en rijke plugin-ecosysteem.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: Een gratis, open source IDE voor Java en andere talen, bekend om zijn gebruiksvriendelijkheid en ingebouwde projecttemplates.
