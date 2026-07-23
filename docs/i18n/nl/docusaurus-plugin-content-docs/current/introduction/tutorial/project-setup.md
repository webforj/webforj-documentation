---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 00d93e4eb2ef6afad342bdbc64324e3e
---
Om deze tutorial te beginnen, heb je een locatie nodig voor je project waar je je klassen en middelen kunt beheren. De volgende secties beschrijven de verschillende manieren waarop je je webforJ-project voor deze tutorial kunt maken.

## Gebruik van de broncode {#using-source-code}

De gemakkelijkste manier om deze tutorial te volgen is door naar de broncode te verwijzen. Je kunt het gehele project downloaden of het van GitHub klonen:

<!-- vale off -->
- Download ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub Repository: Clone het project [direct van GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Projectstructuur {#project-structure}

Het project heeft zes subdirectory's, één voor elke stap van de tutorial, en elke bevat een uitvoerbare app. Meedoen stelt je in staat om te zien hoe de app vordert van een basisconfiguratie naar een volledig functioneel klantbeheersysteem.

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

Als je de voorkeur geeft aan het maken van een nieuw project, kun je [startforJ](https://docs.webforj.com/startforj) gebruiken om een minimaal starterproject te genereren. Zie [Aan de slag](/docs/introduction/getting-started) voor meer gedetailleerde informatie over het gebruik van startforJ.

:::note Vereiste instellingen
- In de dropdown **webforJ-versie**, kies webforJ versie **26.00 of hoger**.
- In de dropdown **Flavor**, kies **webforJ + Spring Boot**.
:::

## Gebruik van de opdrachtregel {#using-command-line}

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

De twee genoemde manieren om een nieuw project te maken, gebruiken webforJ [archetypes](/docs/building-ui/archetypes/overview), die automatisch de benodigde configuraties aan je project toevoegen, zoals Spring [afhankelijkheden](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) aan je POM en de volgende eigenschappen in `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## De app uitvoeren {#running-the-app}

Om de app in actie te zien terwijl je door de tutorial gaat:

1. Navigeer naar de directory voor de gewenste stap. Dit zou de bovenste directory voor die stap moeten zijn, met de `pom.xml`.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

Running the app automatically opens a new browser at `http://localhost:8080`.
