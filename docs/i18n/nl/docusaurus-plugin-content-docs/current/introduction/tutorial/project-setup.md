---
title: Projectinstelling
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 1704f647af5396bd4efd4fdbcc4da978
---
Om deze tutorial te beginnen, heb je een locatie voor je project nodig waar je je klassen en middelen kunt beheren. De volgende secties beschrijven de verschillende manieren waarop je je webforJ-project voor deze tutorial kunt maken.

## Gebruik van de source code {#using-source-code}

De eenvoudigste manier om deze tutorial te volgen, is door naar de broncode te verwijzen. Je kunt het hele project downloaden of het van GitHub klonen:

<!-- vale off -->
- Download ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub Repository: Clone het project [directly from GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Projectstructuur {#project-structure}

Het project heeft zes subdirectories, één voor elke stap van de tutorial, en elke map bevat een uitvoerbare app. Door mee te volgen, kun je zien hoe de app zich ontwikkelt van een basisopstelling naar een volledig functioneel klantbeheersysteem.

```
webforj-tutorial
│   .gitignore
│   LICENSE
│   README.md
│
├───1-creating-a-basic-app
├───2-working-with-data
├───3-routing-and-composites
├───4-observers-and-route-parameters
├───5-validating-and-binding-data
└───6-integrating-an-app-layout
```

## Gebruik van startforJ {#using-startforj}

Als je liever een nieuw project wilt maken, kun je [startforJ](https://docs.webforj.com/startforj) gebruiken om een minimaal starterproject te genereren. Zie [Aan de slag](/docs/introduction/getting-started) voor meer gedetailleerde informatie over het gebruik van startforJ.

:::note Vereiste instellingen
- Kies in de dropdown voor **webforJ versie** de webforJ versie **26.01 of hoger**.
- Kies in de dropdown voor **Flavor** **webforJ + Spring Boot**.

## Gebruik van de commandoregel {#using-command-line}

Je kunt ook een nieuw project genereren met de volgende opdracht:

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=com.webforj.tutorial \
  -DartifactId=customer-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
```powershell
mvn -B archetype:generate `
  -DarchetypeGroupId="com.webforj" `
  -DarchetypeArtifactId="webforj-archetype-hello-world" `
  -DarchetypeVersion="LATEST" `
  -DgroupId="com.webforj.tutorial" `
  -DartifactId="customer-app" `
  -Dversion="1.0-SNAPSHOT" `
  -Dflavor="webforj-spring"
```
  </TabItem>
  <TabItem value="cmd" label="Opdrachtprompt">
```
mvn -B archetype:generate ^
  -DarchetypeGroupId="com.webforj" ^
  -DarchetypeArtifactId="webforj-archetype-hello-world" ^
  -DarchetypeVersion="LATEST" ^
  -DgroupId="com.webforj.tutorial" ^
  -DartifactId="customer-app" ^
  -Dversion="1.0-SNAPSHOT" ^
  -Dflavor="webforj-spring"
```
  </TabItem>
</Tabs>
<!-- vale on -->

## Configuraties {#configurations}

De twee genoemde manieren om een nieuw project te maken, gebruiken webforJ [archetypes](/docs/building-ui/archetypes/overview), die automatisch de benodigde configuraties aan je project toevoegen. Dit omvat Spring [dependencies](/docs/integrations/spring/spring-boot), de webforJ Maven-plugin die frontendbronnen bouwt en bewaakt, en de volgende eigenschappen in `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## De app uitvoeren {#running-the-app}

Om de app in actie te zien terwijl je door de tutorial vordert:

1. Navigeer naar de directory voor de gewenste stap. Dit moet de bovenliggende directory voor die stap zijn, met de `pom.xml`.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

   De gegenereerde POM configureert deze standaardopdracht om de app te compileren, de webforJ frontend-watcher te starten en Spring Boot uit te voeren.

Running the app automatically opens a new browser at `http://localhost:8080`.
