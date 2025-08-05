---
sidebar_position: 1
title: Docker
_i18n_hash: 642936b8f7fd836ca4510eab19087a8c
---
# Docker-installatie

Deze sectie van de documentatie behandelt de stappen die nodig zijn voor gebruikers die willen ontwikkelen met Docker. Wijzigingen aan uw code worden aangebracht op uw ontwikkelingsmachine, en de resulterende app wordt uitgevoerd in Docker.

## 1. Docker downloaden {#1-downloading-docker}

Het installatieproces voor Docker verschilt lichtjes tussen Windows-, Mac- en Linux-gebruikers. Zie de sectie hieronder die overeenkomt met uw besturingssysteem.

### Windows {#windows}

:::info
Het wordt aanbevolen om de nieuwste versie van Windows Subsystem for Linux te downloaden. Meer informatie is te vinden [via deze link](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Download Docker Desktop:**
>- Bezoek de downloadpagina voor Docker Desktop voor Windows: [Docker Desktop voor Windows](https://www.docker.com/products/docker-desktop/)
>- Klik op de knop "Get Docker Desktop for Windows" om de installer te downloaden.

**2. Installeer Docker Desktop:**
>- Voer de gedownloade installer uit.
>- Volg de installatie-wizard en zorg ervoor dat Hyper-V is ingeschakeld (indien gevraagd), aangezien Docker voor Windows Hyper-V voor virtualisatie gebruikt.
>- Zodra de installatie is voltooid, start Docker Desktop automatisch.

**3. Verifieer Installatie:**
>- Open een terminal en voer het commando `docker --version` uit om te verifiëren dat Docker correct is geïnstalleerd en werkt.

### Mac {#mac}

**1. Download Docker Desktop:**
>- Bezoek de downloadpagina voor Docker Desktop voor Mac: [Docker Desktop voor Mac](https://www.docker.com/products/docker-desktop/)

**2. Installeer Docker Desktop:**
>- Voer de gedownloade installer uit.
>- Zodra de installatie is voltooid, start Docker Desktop automatisch.

**3. Verifieer Installatie:**
>- Open een terminal en voer het commando `docker --version` uit om te verifiëren dat Docker correct is geïnstalleerd en werkt.

## 2. Configuratie {#2-configuration}

Zodra Docker Desktop is gedownload, zoekt u naar de nieuwste webforJ-afbeelding, die momenteel onder de naam `webforj/sandbox` staat.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klik op de lijst met tags om de beschikbare opties te zien.

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Voor de meest recente build, selecteer "rc".

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Haal de afbeelding op om uw container te starten.

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Zodra de download is voltooid, klikt u op de 'Run'-knop, die de configuratie-instellingen opent.

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Open het menu "Optionele instellingen".

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Selecteer een gewenste hostpoort waar u uw app binnen Docker kunt zien draaien.

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klik op "Run" om de container te starten.

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Belangrijk
Zorg ervoor dat u het aangepaste Host-poortnummer noteert dat u opgeeft, aangezien dit later nodig is.
:::

## 3. Uw app uitvoeren {#3-running-your-app}

Zodra de container is gemaakt, kunnen webforJ-applicaties binnen de container worden uitgevoerd in plaats van lokaal. Eerst is het nodig om het POM-bestand van uw project correct te configureren. Zodra dit is gedaan, toont een specifieke URL in de browser de app.

### Configureren van uw POM-bestand {#configuring-your-pom-file}

Een webforJ-project in de Docker-container uitvoeren vereist het gebruik van de webforJ Install Plugin, die kan worden geconfigureerd met behulp van uw POM-bestand:

Maak een nieuwe `<plugin>`-vermelding in de `<plugins>`-sectie van de POM. De volgende code toont een startvermelding die kan worden gebruikt en aangepast naar de behoeften van uw project:

:::important
Als uw POM-bestand geen `<plugins>`-sectie heeft, maak er dan een.
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

Zodra een vermelding vergelijkbaar met de bovenstaande is gemaakt, pas dan de volgende informatie aan:

- Wijzig de `<deployurl>`-vermelding om het poortnummer te gebruiken dat overeenkomt met de **Host-poort** die u eerder voor uw container hebt geconfigureerd.

- Zorg ervoor dat de `<classname>`-vermelding overeenkomt met de naam van de app die u wilt uitvoeren.

- Als uw `<username>` en `<password>` inloggegevens anders zijn voor uw installatie van BBj, wijzig deze dan.

### Gebruik van het starterproject {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### De app starten {#launching-the-app}

Zodra dit is gedaan, voert u een `mvn install` uit in uw projectdirectory. Dit voert de webforJ install-plugin uit en stelt u in staat om toegang te krijgen tot uw app. Om de app te zien, gaat u naar de volgende URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vervang `YourHostPort` door de Host-poort die u met Docker hebt geconfigureerd, en `YourPublishName` wordt vervangen door de tekst binnen de `<publishname>`-tag van de POM. Als dit correct is gedaan, zou u uw app moeten zien renderen.
