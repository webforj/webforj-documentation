---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 72ee1120081fa9f4d4fed86c13741d5b
---
Om met deze tutorial te beginnen, heb je een locatie voor je project nodig waar je je klassen en bronnen kunt beheren. De volgende secties beschrijven de verschillende manieren waarop je je webforJ-project voor deze tutorial kunt aanmaken.

## Gebruik van de broncode {#using-source-code}

De gemakkelijkste manier om deze tutorial te volgen is door te verwijzen naar de broncode. Je kunt het hele project downloaden of het van GitHub klonen:

<!-- vale off -->
- Download ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub Repository: Clone het project [direct van GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Projectstructuur {#project-structure}

Het project heeft zes subdirectories, één voor elke stap van de tutorial, en elke bevat een uitvoerbare app. Volg de stappen om te zien hoe de app van een basisopzet naar een volledig functioneel klantbeheersysteem evolueert.

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

Als je liever een nieuw project wilt creëren, kun je [startforJ](https://docs.webforj.com/startforj) gebruiken om een minimale starterproject te genereren. Zie [Aan de slag](/docs/introduction/getting-started) voor meer gedetailleerde informatie over het gebruik van startforJ.

:::note Vereiste instellingen
- In de dropdown voor **webforJ-versie**, kies webforJ versie **26.01 of hoger**.
- In de dropdown voor **Flavor**, kies **webforJ + Spring Boot**.

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

De twee genoemde manieren om een nieuw project te maken gebruiken webforJ [archetypes](/docs/building-ui/archetypes/overview), die automatisch de benodigde configuraties aan je project toevoegen. Dit omvat Spring [afhankelijkheden](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies), de webforJ Maven-plugin die de frontendbronnen bouwt en volgt, en de volgende eigenschappen in `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## De app uitvoeren {#running-the-app}

Om de app in actie te zien terwijl je door de tutorial vordert:

1. Navigeer naar de directory voor de gewenste stap. Dit moet de bovenste directory voor die stap zijn, waarin de `pom.xml` staat.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

   De gegenereerde POM configureert deze standaardopdracht om de app te compileren, de webforJ-frontendwatcher te starten en Spring Boot uit te voeren.

De app openen automatisch een nieuwe browser op `http://localhost:8080`.
