---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: f9a028daac660f61634ad84d00cb0130
---
Om deze tutorial te beginnen, hebt u een locatie voor uw project nodig waar u uw klassen en middelen kunt beheren. De volgende secties beschrijven de verschillende manieren waarop u uw webforJ-project voor deze tutorial kunt aanmaken.

## Gebruik van de broncode {#using-source-code}

De eenvoudigste manier om deze tutorial te volgen, is door te verwijzen naar de broncode. U kunt het hele project downloaden of het clonen vanuit GitHub:

<!-- vale off -->
- Download ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub Repository: Clone het project [direct van GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Projectstructuur {#project-structure}

Het project heeft vijf subdirectory's, één voor elke stap van de tutorial, en elke bevat een uitvoerbare app. Door mee te volgen, kunt u zien hoe de app evolueert van een basisopstelling naar een volledig functioneel klantenbeheersysteem.

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
└───5-validating-and-binding-data
```

<!-- vale off -->
## Gebruik van startforJ {#using-startforj}
<!-- vale on -->

Als u liever een nieuw project wilt aanmaken, kunt u [startforJ](https://docs.webforj.com/startforj) gebruiken om een minimale starterproject te genereren. Zie [Aan de slag](/docs/introduction/getting-started) voor meer gedetailleerde informatie over het gebruik van startforJ.

:::note Vereiste instellingen
- Kies in de dropdown **webforJ-versie** de webforJ-versie **25.10 of hoger**.
- Kies in de dropdown **Flavor** voor **webforJ + Spring Boot**. 
:::

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

## Configuraties

De twee genoemde manieren om een nieuw project te maken, gebruiken webforJ [archetypes](/docs/building-ui/archetypes/overview), die automatisch de benodigde configuraties aan uw project toevoegen, zoals Spring [afhankelijkheden](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) aan uw POM en de volgende eigenschappen in `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## De app uitvoeren {#running-the-app}

Om de app in actie te zien terwijl u door de tutorial voortschrijdt:

1. Navigeer naar de directory voor de gewenste stap. Dit moet de bovenliggende directory voor die stap zijn, met de `pom.xml`.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

<!-- vale Google.WordList = NO -->
De app uitvoeren opent automatisch een nieuwe browser op `http://localhost:8080`.
<!-- vale Google.WordList = YES -->
