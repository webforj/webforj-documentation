---
sidebar_position: 1
title: Docker
_i18n_hash: 49f4e9eb5470926c186e323e4d67377f
---
# Docker-installatie

Deze sectie van de documentatie behandelt de stappen die nodig zijn voor gebruikers die met Docker willen ontwikkelen. Wijzigingen aan uw code worden gemaakt op uw ontwikkelingsmachine en de resulterende app zal worden uitgevoerd in Docker.

## 1. Docker downloaden {#1-downloading-docker}

Het installatieproces voor Docker verschilt iets tussen Windows-, Mac- en Linux-gebruikers. Bekijk de onderstaande sectie die overeenkomt met uw besturingssysteem.

### Windows {#windows}

:::info
Het wordt aanbevolen om de nieuwste versie van Windows Subsystem for Linux te downloaden. Meer informatie is te vinden [op deze link](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Docker Desktop downloaden:**
>- Bezoek de downloadpagina voor Docker Desktop voor Windows: [Docker Desktop voor Windows](https://www.docker.com/products/docker-desktop/)
>- Klik op de knop "Docker Desktop voor Windows krijgen" om de installer te downloaden.

**2. Docker Desktop installeren:**
>- Voer de gedownloade installer uit.
>- Volg de installatiewizard en zorg ervoor dat Hyper-V is ingeschakeld (indien gevraagd), aangezien Docker voor Windows Hyper-V gebruikt voor virtualisatie.
>- Zodra de installatie is voltooid, wordt Docker Desktop automatisch gestart.

**3. Installatie verifiëren:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren dat Docker correct is geïnstalleerd en werkt.

### Mac {#mac}

**1. Docker Desktop downloaden:**
>- Bezoek de downloadpagina voor Docker Desktop voor Mac: [Docker Desktop voor Mac](https://www.docker.com/products/docker-desktop/)

**2. Docker Desktop installeren:**
>- Voer de gedownloade installer uit.
>- Zodra de installatie is voltooid, wordt Docker Desktop automatisch gestart.

**3. Installatie verifiëren:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren dat Docker correct is geïnstalleerd en werkt.

## 2. Configuratie {#2-configuration}

Zodra Docker Desktop is gedownload, zoekt u naar de nieuwste webforJ-image, die momenteel onder de naam `webforj/sandbox` valt.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klik op de lijst met tags om de beschikbare opties te bekijken

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Voor de meest recente build, selecteer "rc"

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Haal de image op om uw container te starten

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Zodra de download is voltooid, klikt u op de startknop, waarmee de configuratie-instellingen worden geopend

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Open het menu "Optionele instellingen"

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Selecteer een gewenste hostpoort waar u uw app binnen Docker kunt zien draaien

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klik op "Uitvoeren" om de container te starten

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Belangrijk
Zorg ervoor dat u het aangepaste Host-poortnummer dat u opgeeft, noteert, aangezien dit later nodig zal zijn.
:::

## 3. Uw app uitvoeren {#3-running-your-app}

Zodra de container is gemaakt, kunnen webforJ-applicaties binnen de container worden uitgevoerd in plaats van lokaal. Eerst is het nodig om het POM-bestand van uw project correct te configureren. Zodra dit is gedaan, zal naar een specifieke URL in de browser gaan de app tonen.

### Configureren van uw POM-bestand {#configuring-your-pom-file}

Het draaien van een webforJ-project in de Docker-container vereist het gebruik van de webforJ Install Plugin, die kan worden geconfigureerd via uw POM-bestand:

Maak een nieuwe `<plugin>`-invoer in de sectie `<plugins>` van de POM. De volgende code toont een startinvoer die kan worden gebruikt en aangepast zoals nodig voor uw project:

:::important
Als uw POM-bestand geen `<plugins>`-sectie heeft, creëer er dan een.
:::

```xml
<plugin>
<groupId>com.webforj</groupId>
<artifactId>webforj-install-maven-plugin</artifactId>
<version>${webforj.version}</version>
<executions>
    <execution>
    <goals>
        <goal>install</goal>
    </goals>
    </execution>
</executions>
<configuration>
    <deployurl>http://localhost:8888/webforj-install</deployurl>
    <classname>samples.HelloWorldApp</classname>
    <publishname>hello-world</publishname>
    <debug>true</debug>
</configuration>
</plugin>
```

Zodra een invoer vergelijkbaar met de bovenstaande is gemaakt, kunt u de volgende informatie aanpassen:

- Wijzig de invoer `<deployurl>` zodat deze het poortnummer gebruikt dat overeenkomt met de **Host-port** die u in de vorige stap voor uw container hebt geconfigureerd.

- Zorg ervoor dat de invoer `<classname>` overeenkomt met de naam van de app die u wilt uitvoeren.

- Als uw `<username>` en `<password>` inloggegevens verschillen voor uw installatie van BBj, wijzig deze dan.

### Gebruik maken van het starterproject {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### De app starten {#launching-the-app}

Zodra dit gedaan is, voert u een `mvn install` uit in uw projectdirectory. Dit zal de webforJ-installatieplugin uitvoeren en u in staat stellen om toegang te krijgen tot uw app. Om de app te zien, gaat u naar de volgende URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vervang `YourHostPort` door de Host-poort die u met Docker hebt geconfigureerd, en `YourPublishName` wordt vervangen door de tekst in de `<publishname>`-tag van de POM. Als alles correct is gedaan, zou u uw app moeten kunnen weergeven.
