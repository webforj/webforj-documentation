---
sidebar_position: 1
title: Docker
description: >-
  Install Docker Desktop on Windows or Mac, pull the webforj/sandbox image, and
  run BBjServices in a container for webforJ development.
_i18n_hash: 6c644b1d4f54cfa22e3b978714a5849e
---
# Docker-installatie

:::danger Verwijderd en zal worden verwijderd
De Docker-installatie met de `webforj/sandbox` afbeelding is verouderd en zal in een toekomstige release worden verwijderd. Begin geen nieuwe projecten hiermee. Verplaatsen bestaande projecten naar [BBjShip](/docs/configuration/bbjship/overview), dat webforJ-apps in BBjServices installeert, implementeert en uitvoert. [Bestaande projecten](/docs/configuration/bbjship/existing-projects) vermeldt de wijzigingen die een project nodig heeft.
:::

Dit gedeelte van de documentatie behandelt de stappen die nodig zijn voor gebruikers die willen ontwikkelen met Docker. Wijzigingen aan uw code
worden gemaakt op uw ontwikkelmachine, en de resulterende app zal in Docker worden uitgevoerd.

## 1. Docker downloaden {#1-downloading-docker}

Het installatieproces voor Docker verschilt iets tussen Windows-, Mac- en Linux-gebruikers. Zie het gedeelte hieronder dat overeenkomt met uw besturingssysteem.

### Windows {#windows}

:::info
Het wordt aanbevolen om de nieuwste versie van Windows Subsystem for Linux te downloaden. Meer informatie is te vinden [op deze link](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Download Docker Desktop:**
>- Bezoek de download pagina voor Docker Desktop voor Windows: [Docker Desktop voor Windows](https://www.docker.com/products/docker-desktop/)
>- Klik op de knop "Get Docker Desktop for Windows" om de installer te downloaden.

**2. Installeer Docker Desktop:**
>- Voer de gedownloade installer uit.
>- Volg de installatieassistent en zorg ervoor dat Hyper-V is ingeschakeld (als daarom wordt gevraagd), aangezien Docker voor Windows Hyper-V gebruikt voor virtualisatie.
>- Zodra de installatie is voltooid, wordt Docker Desktop automatisch gestart.

**3. Verifieer de installatie:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren dat Docker correct is geïnstalleerd en werkt.

### Mac {#mac}

**1. Download Docker Desktop:**
>- Bezoek de download pagina voor Docker Desktop voor Mac: [Docker Desktop voor Mac](https://www.docker.com/products/docker-desktop/)

**2. Installeer Docker Desktop:**
>- Voer de gedownloade installer uit.
>- Zodra de installatie is voltooid, wordt Docker Desktop automatisch gestart.

**3. Verifieer de installatie:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren dat Docker correct is geïnstalleerd en werkt.

<!-- ### Linux

**1. Installeer Docker Engine**
>- Bezoek de download pagina voor Docker Desktop voor Mac: [Docker voor Linux](https://docs.docker.com/engine/install/)

**2. Verifieer de installatie:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren dat Docker correct is geïnstalleerd en werkt. -->

## 2. Configuratie {#2-configuration}

Zodra Docker Desktop is gedownload, zoek naar de nieuwste webforJ-afbeelding, die momenteel onder de naam `webforj/sandbox` staat.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klik op de lijst met tags om de beschikbare opties te bekijken

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Voor de recentste build, selecteer "rc"

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Haal de afbeelding op om uw container te starten

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Zodra de download is voltooid, klikt u op de knop 'Run', waarmee de configuratie-instellingen worden geopend

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Open het menu "Optionele instellingen"

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Selecteer een gewenste hostpoort waar u uw app in Docker kunt zien draaien

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klik op "Run" om de container te starten

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

<!-- Klik op de `Run` knop, wat een configuratievenster opent. Deze instellingen zijn optioneel, maar het is sterk aanbevolen om de configuratie-instelling `Host port` op te geven, aangezien dit later nodig zal zijn bij het uitvoeren van uw app.

![Configuratie](/img/bbj-installation/docker/2.png)

Zodra dit is voltooid, klikt u op de `Run` knop onderaan het venster, wat een nieuwe container met uw opgegeven instellingen zal aanmaken. -->

:::success Belangrijk
Zorg ervoor dat u het aangepaste Host poortnummer dat u opgeeft noteert, aangezien dit later nodig zal zijn.
:::

## 3. Uw app uitvoeren {#3-running-your-app}

Zodra de container is aangemaakt, kunnen webforJ-applicaties binnen de container worden uitgevoerd in plaats van lokaal. Eerst is het noodzakelijk om het POM-bestand van uw project correct te configureren. Zodra dit is gedaan, zal het bezoeken van een specifieke URL in de browser de app weergeven.

### Uw POM-bestand configureren {#configuring-your-pom-file}

Het draaien van een webforJ-project in de Docker-container vereist het gebruik van de webforJ Install Plugin, die kan worden geconfigureerd met uw POM-bestand:

Maak een nieuwe `<plugin>`-invoer in het `<plugins>`-gedeelte van POM. De volgende code toont een startinvoer die kan worden gebruikt en zonodig kan worden aangepast voor uw project:

:::important
Als uw POM-bestand geen `<plugins>`-sectie heeft, maak er dan één aan.
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

Zodra een invoer vergelijkbaar met de bovenstaande is aangemaakt, pas dan de volgende informatie aan:

- Verander de `<deployurl>`-invoer om het poortnummer te gebruiken dat u overeen laat komen met de **Host poort** die u heeft geconfigureerd voor uw container in de vorige stap.

- Zorg ervoor dat de `<classname>`-invoer overeenkomt met de naam van de app die u wilt uitvoeren.

- Als uw `<username>` en `<password>` inloggegevens verschillend zijn voor uw installatie van BBj, wijzig deze dan.

### Het starterproject gebruiken {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### De app starten {#launching-the-app}

Nadat dit is gedaan, voert u een `mvn install` uit in uw projectdirectory. Dit zal de webforJ-installatieplugin uitvoeren en u toegang geven tot uw app. Om de app te zien, gaat u naar de volgende URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vervang `YourHostPort` door de Host-poort die u met Docker heeft geconfigureerd, en `YourPublishName` door de tekst binnen de `<publishname>`-tag van de POM. Als dit correct is gedaan, zou u uw app moeten zien renderen.
