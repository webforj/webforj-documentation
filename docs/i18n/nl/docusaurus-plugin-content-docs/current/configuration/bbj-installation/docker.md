---
sidebar_position: 1
title: Docker
_i18n_hash: 8cc797ca5ca7e8ba3a8cd9f3aec41d74
---
# Docker-installatie

Deze sectie van de documentatie behandelt de stappen die nodig zijn voor gebruikers die met Docker willen ontwikkelen. Wijzigingen aan uw code worden op uw ontwikkelmachine aangebracht, en de resulterende app wordt in Docker uitgevoerd. 

## 1. Docker downloaden {#1-downloading-docker}

Het installatieproces voor Docker verschilt iets tussen Windows-, Mac- en Linux-gebruikers. Zie de sectie hieronder die overeenkomt met uw besturingssysteem.

### Windows {#windows}

:::info
Het wordt aanbevolen om de nieuwste versie van Windows Subsystem for Linux te downloaden. Meer informatie is te vinden [op deze link](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Download Docker Desktop:**
>- Bezoek de downloadpagina van Docker Desktop voor Windows: [Docker Desktop voor Windows](https://www.docker.com/products/docker-desktop/)
>- Klik op de knop "Get Docker Desktop for Windows" om de installer te downloaden.

**2. Installeer Docker Desktop:**
>- Voer de gedownloade installer uit.
>- Volg de installatiewizard, en zorg ervoor dat Hyper-V is ingeschakeld (als daarom wordt gevraagd) aangezien Docker voor Windows Hyper-V gebruikt voor virtualisatie.
>- Nadat de installatie is voltooid, wordt Docker Desktop automatisch gestart.

**3. Verifieer de installatie:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren dat Docker is geïnstalleerd en correct werkt.

### Mac {#mac}

**1. Download Docker Desktop:**
>- Bezoek de downloadpagina van Docker Desktop voor Mac: [Docker Desktop voor Mac](https://www.docker.com/products/docker-desktop/)

**2. Installeer Docker Desktop:**
>- Voer de gedownloade installer uit.
>- Nadat de installatie is voltooid, wordt Docker Desktop automatisch gestart.

**3. Verifieer de installatie:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren dat Docker is geïnstalleerd en correct werkt.

## 2. Configuratie {#2-configuration}

Zodra Docker Desktop is gedownload, zoek naar de laatste webforJ-image, die momenteel onder de naam `webforj/sandbox` staat.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klik op de lijst met tags om de beschikbare opties te zien

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Selecteer voor de meest recente build "rc"

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Haal de image binnen om uw container te starten

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Zodra de download is voltooid, klik op de run-knop, waarmee de configuratie-instellingen worden geopend

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Open het menu "Optionele instellingen"

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Selecteer een gewenste hostpoort waar u uw app kunt zien draaien binnen Docker

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klik op "Run" om de container te starten

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Belangrijk
Zorg ervoor dat u het aangepaste hostpoortnummer dat u opgeeft noteert, aangezien dit later nodig is.
:::

## 3. Uw app uitvoeren {#3-running-your-app}

Zodra de container is gemaakt, kunnen webforJ-toepassingen binnen de container worden uitgevoerd in plaats van lokaal. Eerst moeten we het POM-bestand van uw project correct configureren. Zodra dit is gedaan, zult u het specifieke URL in de browser bezoeken om de app te zien.

### Uw POM-bestand configureren {#configuring-your-pom-file}

Een webforJ-project in de Docker-container uitvoeren vereist het gebruik van de webforJ Install Plugin, die kan worden geconfigureerd met behulp van uw POM-bestand:

Maak een nieuwe `<plugin>`-vermelding in de `<plugins>` sectie van POM. De onderstaande code toont een startvermelding die kan worden gebruikt en aangepast indien nodig voor uw project:

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

Zodra een vermelding zoals de bovenstaande is gemaakt, pas de volgende informatie aan:

- Verander de `<deployurl>`-vermelding om het poortnummer dat overeenkomt met de **Host port** die u hebt geconfigureerd voor uw container in de vorige stap.

- Zorg ervoor dat de `<classname>`-vermelding overeenkomt met de naam van de app die u wilt uitvoeren.

- Als uw `<username>` en `<password>` inloggegevens verschillen voor uw installatie van BBj, wijzig deze dan.

### Het starterproject gebruiken {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### De app starten {#launching-the-app}

Zodra dit is gedaan, voert u een `mvn install` uit in uw projectdirectory. Dit zal de webforJ-installplugin uitvoeren en u in staat stellen toegang te krijgen tot uw app. Om de app te zien, moet u naar het volgende URL gaan:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vervang `YourHostPort` door de Host port die u met Docker hebt geconfigureerd, en `YourPublishName` wordt vervangen door de tekst binnen de `<publishname>`-tag van de POM. Als alles goed is gedaan, zou u uw app moeten zien verschijnen.
