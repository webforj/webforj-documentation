---
sidebar_position: 1
title: Docker
description: >-
  Install Docker Desktop on Windows or Mac, pull the webforj/sandbox image, and
  run BBjServices in a container for webforJ development.
_i18n_hash: 4981bf7da8b63063a781d946d29895e6
---
# Docker-installatie

Dit gedeelte van de documentatie behandelt de stappen die nodig zijn voor gebruikers die met Docker willen ontwikkelen. Wijzigingen aan uw code worden aangebracht op uw ontwikkelmachine en de resulterende app wordt uitgevoerd in Docker.

## 1. Docker downloaden {#1-downloading-docker}

Het installatieproces voor Docker verschilt enigszins tussen Windows-, Mac- en Linux-gebruikers. Zie het onderstaande gedeelte dat overeenkomt met uw besturingssysteem.

### Windows {#windows}

:::info
Het wordt aanbevolen om de nieuwste versie van Windows Subsystem for Linux te downloaden. Meer informatie is te vinden [op deze link](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Download Docker Desktop:**
>- Bezoek de downloadpagina voor Docker Desktop voor Windows: [Docker Desktop voor Windows](https://www.docker.com/products/docker-desktop/)
>- Klik op de knop "Get Docker Desktop for Windows" om de installer te downloaden.

**2. Installeer Docker Desktop:**
>- Voer de gedownloade installer uit.
>- Volg de installatie-assistent en zorg ervoor dat Hyper-V is ingeschakeld (indien gevraagd), aangezien Docker voor Windows Hyper-V gebruikt voor virtualisatie.
>- Zodra de installatie is voltooid, wordt Docker Desktop automatisch gestart.

**3. Controleer de installatie:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren of Docker correct is geïnstalleerd en werkt.

### Mac {#mac}

**1. Download Docker Desktop:**
>- Bezoek de downloadpagina voor Docker Desktop voor Mac: [Docker Desktop voor Mac](https://www.docker.com/products/docker-desktop/)

**2. Installeer Docker Desktop:**
>- Voer de gedownloade installer uit.
>- Zodra de installatie is voltooid, wordt Docker Desktop automatisch gestart.

**3. Controleer de installatie:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren of Docker correct is geïnstalleerd en werkt.

## 2. Configuratie {#2-configuration}

Zodra Docker Desktop is gedownload, zoekt u naar het nieuwste webforJ-image, dat momenteel onder de naam `webforj/sandbox` valt.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klik op de lijst met tags om de beschikbare opties te bekijken

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Selecteer voor de meest recente build "rc"

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Haal het image op om uw container te starten

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Zodra de download is voltooid, klikt u op de run-knop, die de configuratie-instellingen opent

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Open het menu "Optionele instellingen"

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Selecteer een gewenste hostpoort waar u uw app kunt zien draaien binnen Docker

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klik op "Run" om de container te starten

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Belangrijk
Zorg ervoor dat u het aangepaste Host port-nummer dat u opgeeft noteert, aangezien dit later nodig is.
:::

## 3. Uw app uitvoeren {#3-running-your-app}

Zodra de container is gemaakt, kunnen webforJ-toepassingen binnen de container worden uitgevoerd in plaats van lokaal. Eerst is het noodzakelijk om het POM-bestand van uw project correct te configureren. Zodra dit is gedaan, toont een specifieke URL in de browser de app.

### Uw POM-bestand configureren {#configuring-your-pom-file}

Het draaien van een webforJ-project in de Docker-container vereist het gebruik van de webforJ Install Plugin, die kan worden geconfigureerd met behulp van uw POM-bestand:

Maak een nieuwe `<plugin>`-vermelding in de `<plugins>`-sectie van POM. De volgende code toont een startvermelding die kan worden gebruikt en naar behoefte kan worden aangepast voor uw project:

:::important
Als uw POM-bestand geen `<plugins>`-sectie heeft, maak er dan een aan.
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

- Wijzig de `<deployurl>`-vermelding om het poortnummer te gebruiken dat overeenkomt met de **Host port** die u voor uw container heeft geconfigureerd in de vorige stap.

- Zorg ervoor dat de `<classname>`-vermelding overeenkomt met de naam van de app die u wilt uitvoeren.

- Als uw `<username>` en `<password>`-gegevens verschillen voor uw installatie van BBj, wijzig deze dan.

### Het starterproject gebruiken {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### De app starten {#launching-the-app}

Zodra dit is gedaan, voert u een `mvn install` uit in uw projectdirectory. Dit zal de webforJ-install-plugin uitvoeren en u in staat stellen om toegang te krijgen tot uw app. Om de app te zien, gaat u naar de volgende URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vervang `YourHostPort` door de Host port die u met Docker heeft geconfigureerd, en `YourPublishName` wordt vervangen door de tekst binnen de `<publishname>`-tag van de POM. Als alles correct is gedaan, zou u uw app moeten zien renderen.
