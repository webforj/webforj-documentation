---
sidebar_position: 1
title: Docker
_i18n_hash: ec7566378c3ec80f071b7391742ec353
---
# Docker installatie

Dit gedeelte van de documentatie behandelt de stappen die vereist zijn voor gebruikers die willen ontwikkelen met Docker. Wijzigingen aan uw code worden aangebracht op uw ontwikkelingsmachine, en de resulterende app wordt uitgevoerd in Docker.

## 1. Docker downloaden {#1-downloading-docker}

Het installatieproces voor Docker verschilt licht tussen Windows-, Mac- en Linux-gebruikers. Zie het onderstaande gedeelte dat overeenkomt met uw besturingssysteem.

### Windows {#windows}

:::info
Het wordt aanbevolen de nieuwste versie van Windows Subsystem for Linux te downloaden. Meer informatie vindt u [op deze link](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Download Docker Desktop:**
>- Bezoek de downloadpagina voor Docker Desktop voor Windows: [Docker Desktop voor Windows](https://www.docker.com/products/docker-desktop/)
>- Klik op de knop "Docker Desktop voor Windows ophalen" om de installer te downloaden.

**2. Installeer Docker Desktop:**
>- Voer de gedownloade installer uit.
>- Volg de installatiewizard en zorg ervoor dat u Hyper-V inschakelt (indien gevraagd), aangezien Docker voor Windows Hyper-V gebruikt voor virtualisatie.
>- Zodra de installatie is voltooid, wordt Docker Desktop automatisch gestart.

**3. Verifieer de installatie:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren dat Docker correct is geïnstalleerd en werkt.

### Mac {#mac}

**1. Download Docker Desktop:**
>- Bezoek de downloadpagina voor Docker Desktop voor Mac: [Docker Desktop voor Mac](https://www.docker.com/products/docker-desktop/)

**2. Installeer Docker Desktop:**
>- Voer de gedownloade installer uit.
>- Zodra de installatie is voltooid, wordt Docker Desktop automatisch gestart.

**3. Verifieer de installatie:**
>- Open een terminal en voer de opdracht `docker --version` uit om te verifiëren dat Docker correct is geïnstalleerd en werkt.

## 2. Configuratie {#2-configuration}

Zodra Docker Desktop is gedownload, zoek naar de nieuwste webforJ afbeelding, die momenteel onder de naam `webforj/sandbox` staat.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klik op de lijst met tags om de beschikbare opties te zien.

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Kies voor de meest recente build "rc".

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Haal de afbeelding op om uw container te starten.

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Zodra de download is voltooid, klikt u op de knop uitvoeren, waarmee de configuratie-instellingen worden geopend.

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Open het menu "Optionele instellingen".

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Selecteer een gewenste hostpoort waar u uw app binnen Docker kunt zien draaien.

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klik op "Uitvoeren" om de container te starten.

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Belangrijk
Zorg ervoor dat u het aangepaste hostpoortnummer dat u opgeeft, noteert, aangezien dit later nodig zal zijn.
:::

## 3. Uw app uitvoeren {#3-running-your-app}

Zodra de container is aangemaakt, kunnen webforJ-applicaties binnen de container worden uitgevoerd in plaats van lokaal. Eerst is het noodzakelijk om het POM-bestand van uw project correct te configureren. Zodra dit is gedaan, zal het bezoeken van een specifieke URL in de browser de app weergeven.

### Configureren van uw POM-bestand {#configuring-your-pom-file}

Het uitvoeren van een webforJ-project in de Docker-container vereist het gebruik van de webforJ Install Plugin, die kan worden geconfigureerd met behulp van uw POM-bestand:

Maak een nieuwe `<plugin>`-vermelding in de `<plugins>`-sectie van de POM. De volgende code toont een startvermelding die kan worden gebruikt en verder kan worden aangepast naar behoefte voor uw project:

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

Zodra een vermelding vergelijkbaar met de bovenstaande is gemaakt, past u de volgende informatie aan:

- Verander de `<deployurl>`-vermelding naar het poortnummer dat overeenkomt met de **Host port** die u voor uw container hebt geconfigureerd in de vorige stap.

- Zorg ervoor dat de `<classname>`-vermelding overeenkomt met de naam van de app die u wilt uitvoeren.

- Als uw `<username>` en `<password>` inloggegevens anders zijn voor uw installatie van BBj, wijzig deze dan.

### Gebruik maken van het starterproject {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### De app lanceren {#launching-the-app}

Zodra dit is gedaan, voert u een `mvn install` uit in uw projectmap. Dit zal de webforJ install-plugin uitvoeren en u in staat stellen uw app te openen. Om de app te zien, wilt u naar de volgende URL gaan:

`http://localhost:YourHostPort/webapp/YourPublishName`

Vervang `YourHostPort` door de Host-port die u met Docker hebt geconfigureerd, en `YourPublishName` wordt vervangen door de tekst binnen de `<publishname>`-tag van de POM. Als dit correct is gedaan, zou u uw app moeten zien renderen.
