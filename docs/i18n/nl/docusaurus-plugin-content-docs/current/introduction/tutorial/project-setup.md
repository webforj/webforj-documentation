---
title: Projectconfiguratie
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 8c9cc05d141898612beff7f1c3f73fae
---
Om deze tutorial te beginnen, heeft u een locatie nodig voor uw project waar u uw klassen en bronnen kunt beheren. De volgende secties beschrijven de verschillende manieren waarop u uw webforJ-project voor deze tutorial kunt maken.

## Gebruik van de broncode {#using-source-code}

De eenvoudigste manier om deze tutorial te volgen, is door naar de broncode te verwijzen. U kunt het gehele project downloaden of het van GitHub klonen:

<!-- vale off -->
- Download ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub Repository: Clone het project [direct van GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Projectstructuur {#project-structure}

Het project heeft zes subdirectories, één voor elke stap van de tutorial, en elke bevat een uitvoerbare app. Door deze stappen te volgen, kunt u zien hoe de app evolueert van een basisopstelling naar een volledig functioneel klantenbeheersysteem.

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

Als u liever een nieuw project maakt, kunt u [startforJ](https://docs.webforj.com/startforj) gebruiken om een minimaal startproject te genereren. Zie [Aan de slag](/docs/introduction/getting-started) voor meer gedetailleerde informatie over het gebruik van startforJ.

:::note Vereiste instellingen
- Kies in de dropdown **webforJ versie** de versie **26.01 of hoger**.
- Kies onder **Runs on** voor **Spring Boot**.

## Gebruik van de opdrachtregel {#using-command-line}

U kunt ook een nieuw project genereren met de volgende opdracht:

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

De twee genoemde manieren om een nieuw project te creëren, maken gebruik van webforJ [archetypes](/docs/building-ui/archetypes/overview), die automatisch de benodigde configuraties aan uw project toevoegen. Dit omvat Spring [afhankelijkheden](/docs/integrations/spring/spring-boot), de webforJ Maven-plugin die de frontendbronnen bouwt en in de gaten houdt, en de volgende eigenschappen in `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## De app uitvoeren {#running-the-app}

Om de app in actie te zien terwijl u door de tutorial vordert:

1. Navigeer naar de directory voor de gewenste stap. Dit moet de bovenste directory voor die stap zijn, die de `pom.xml` bevat.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

   De gegenereerde POM configureert deze standaardopdracht om de app te compileren, de webforJ frontend-watcher te starten en Spring Boot uit te voeren.

Running the app automatically opens a new browser at `http://localhost:8080`.
